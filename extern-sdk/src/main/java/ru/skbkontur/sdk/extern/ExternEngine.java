/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern;

import ru.skbkontur.sdk.extern.service.SDKException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import ru.skbkontur.sdk.extern.event.AuthenticationEvent;
import ru.skbkontur.sdk.extern.event.AuthenticationListener;
import ru.skbkontur.sdk.extern.providers.AccountProvider;
import ru.skbkontur.sdk.extern.providers.ApiKeyProvider;
import ru.skbkontur.sdk.extern.providers.AuthenticationProvider;
import ru.skbkontur.sdk.extern.providers.CryptoProvider;
import ru.skbkontur.sdk.extern.providers.ServiceBaseUriProvider;
import ru.skbkontur.sdk.extern.providers.ServiceError;
import ru.skbkontur.sdk.extern.service.AccountService;
import ru.skbkontur.sdk.extern.service.DocflowService;
import ru.skbkontur.sdk.extern.service.DraftService;
import static ru.skbkontur.sdk.extern.service.SDKException.C_CRYPTO_ERROR_NO_CRYPTO_PROVIDER;
import ru.skbkontur.sdk.extern.service.impl.AccountServiceImpl;
import ru.skbkontur.sdk.extern.service.impl.BaseService;
import ru.skbkontur.sdk.extern.service.impl.DocflowServiceImpl;
import ru.skbkontur.sdk.extern.service.impl.DraftServiceImpl;
import ru.skbkontur.sdk.extern.service.transport.adaptors.AccountAdaptor;
import ru.skbkontur.sdk.extern.service.transport.adaptors.DocflowsAdaptor;
import ru.skbkontur.sdk.extern.service.transport.adaptors.DraftsAdaptor;
import ru.skbkontur.sdk.extern.service.transport.adaptors.QueryContext;
import static ru.skbkontur.sdk.extern.service.transport.adaptors.QueryContext.SESSION_ID;

/**
 *
 * @author AlexS
 */
public class ExternEngine implements AuthenticationListener {

	private static final Gson GSON = new Gson();

	private final Environment env;

	private AccountService accountService;
	
	private DraftService draftService;
	
	private DocflowService docflowService;
	
	private ServiceBaseUriProvider serviceBaseUriProvider;
	
	private EngineAuthenticationProvider authenticationProvider;
	
	private AccountProvider accountProvider;
	
	private ApiKeyProvider apiKeyProvider;
	
	private CryptoProvider cryptoProvider;
	
	private String sessionId;
	
	public ExternEngine(Configuration configuration) throws SDKException {
		env = new Environment();
		env.configuration = configuration;
		this.sessionId = null;
	}

	public Environment getEnvironment() {
		return env;
	}
	
	public Configuration getConfiguration() {
		return env.configuration;
	}
	
	public ExternEngine(String configPath) throws SDKException {
		// loads config data from the resourse file: extern-sdk-config.json
		this(loadConfiguration(configPath));
	}

	public AccountService getAccountService() {
		return accountService;
	}
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public DraftService getDraftService() {
		return draftService;
	}
	
	public void setDraftService(DraftService draftService) {
		this.draftService = draftService;
	}
	
	public DocflowService getDocflowService() {
		return docflowService;
	}
	
	public void setDocflowService(DocflowService docflowService) {
		this.docflowService = docflowService;
	}
	
	public void setServiceBaseUriProvider(ServiceBaseUriProvider serviceBaseUriProvider) {
		this.serviceBaseUriProvider = serviceBaseUriProvider;
	}
	
	public AuthenticationProvider getAuthenticationProvider() {
		return authenticationProvider;
	}
	
	public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
		if (authenticationProvider != null) {
			authenticationProvider.addAuthenticationListener(this);
			this.authenticationProvider = new EngineAuthenticationProvider(authenticationProvider);
		}
		else if (authenticationProvider != null) {
			AuthenticationProvider originAuthenticationProvider = this.authenticationProvider.getOriginAuthenticationProvider();
			originAuthenticationProvider.removeAuthenticationListener(this);
		}
	}
	
	public AccountProvider getAccountProvider() {
		return accountProvider;
	}
	
	public void setAccountProvider(AccountProvider accountProvider) {
		this.accountProvider = accountProvider;
	}
	
	public ApiKeyProvider getApiKeyProvider() {
		return apiKeyProvider;
	}
	
	public void setApiKeyProvider(ApiKeyProvider apiKeyProvider) {
		this.apiKeyProvider = apiKeyProvider;
	}
	
	public CryptoProvider getCryptoProvider() throws SDKException {
		if (cryptoProvider == null)
			throw new SDKException(C_CRYPTO_ERROR_NO_CRYPTO_PROVIDER);
		return cryptoProvider;
	}
	
	public void setCryptoProvider(CryptoProvider cryptoProvider) {
		this.cryptoProvider = cryptoProvider;
	}
	
	private static Configuration loadConfiguration(String path) throws SDKException {
		try (InputStream is = ExternEngine.class.getResourceAsStream(path)) {
			if (is == null) {
				throw new SDKException(SDKException.C_CONFIG_NOT_FOUND);
			}

			return GSON.fromJson(new JsonReader(new InputStreamReader(is)), Configuration.class);
		}
		catch (IOException x) {
			throw new SDKException(SDKException.C_CONFIG_LOAD, x);
		}
		catch (SDKException x) {
			throw x;
		}
		catch (Exception x) {
			throw new SDKException(SDKException.UNKNOWN, x);
		}
	}

	public void configureServices() throws SDKException {
		AccountServiceImpl accSrv = new AccountServiceImpl();
		configureService(accSrv);
		accSrv.setAccountApi(new AccountAdaptor());
		this.accountService = accSrv;
		
		DraftServiceImpl draftSrv = new DraftServiceImpl();
		configureService(draftSrv);
		draftSrv.setDraftsApi(new DraftsAdaptor());
		this.draftService = draftSrv;
		
		DocflowServiceImpl docflowSrv = new DocflowServiceImpl();
		configureService(docflowSrv);
		docflowSrv.setDraftsApi(new DocflowsAdaptor());
		this.docflowService = docflowSrv;
	}
	
	private void configureService(BaseService baseService) {
		baseService.setServiceBaseUriProvider(this.serviceBaseUriProvider);
		baseService.setAuthenticationProvider(this.authenticationProvider);
		baseService.setAccountProvider(this.accountProvider);
		baseService.setApiKeyProvider(this.apiKeyProvider);
		baseService.setCryptoProvider(this.cryptoProvider);
	}

	@Override
	public synchronized void authenticate(AuthenticationEvent authEvent) {
		sessionId = authEvent.getAuthCxt().isSuccess() ? authEvent.getAuthCxt().get() : null;
	}
	
	class EngineAuthenticationProvider implements AuthenticationProvider {
		
		private final AuthenticationProvider authenticationProvider;
		
		private EngineAuthenticationProvider(AuthenticationProvider authenticationProvider) {
			this.authenticationProvider = authenticationProvider;
		}
		
		public AuthenticationProvider getOriginAuthenticationProvider() {
			return authenticationProvider;
		}
		
		@Override
		public QueryContext<String> sessionId() {
			if (sessionId == null)
				return authenticationProvider.sessionId();
			else
				return new QueryContext<String>().setResult(sessionId, SESSION_ID);
		}

		@Override
		public String authPrefix() {
			return authenticationProvider.authPrefix();
		}

		@Override
		public void addAuthenticationListener(AuthenticationListener authListener) {
			authenticationProvider.addAuthenticationListener(authListener);
		}

		@Override
		public void removeAuthenticationListener(AuthenticationListener authListener) {
			authenticationProvider.removeAuthenticationListener(authListener);
		}

		@Override
		public void raiseUnauthenticated(ServiceError x) {
			authenticationProvider.raiseUnauthenticated(x);
		}
	}
}
