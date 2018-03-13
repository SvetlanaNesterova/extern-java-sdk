/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import ru.skbkontur.sdk.extern.providers.AccountProvider;
import ru.skbkontur.sdk.extern.providers.ApiKeyProvider;
import ru.skbkontur.sdk.extern.providers.AuthBaseUriProvider;
import ru.skbkontur.sdk.extern.providers.LoginAndPasswordAuthenticationProvider;
import ru.skbkontur.sdk.extern.providers.ServiceBaseUriProvider;

/**
 *
 * @author AlexS
 */
public class Configuration implements AccountProvider, ApiKeyProvider, LoginAndPasswordAuthenticationProvider, AuthBaseUriProvider, ServiceBaseUriProvider {
	public static final String DEFAULT_AUTH_PREFIX = "auth.sid ";
	
  @SerializedName("accountId")	private UUID accountId;
	@SerializedName("apiKey") private String apiKey;
	@SerializedName("authPrefix") private String authPrefix;
	@SerializedName("credentialSnils") private String credentialSnils;
	@SerializedName("serviceUserId") private String serviceUserId;
	@SerializedName("login") private String login;
	@SerializedName("pass") private String pass;
	@SerializedName("serviceBaseUri") private String serviceBaseUri;
	@SerializedName("authBaseUri") private String authBaseUri;
	@SerializedName("thumbprint") private String thumbprint; // a thumbprint of a signature certificate

	public Configuration() {
		authPrefix = DEFAULT_AUTH_PREFIX;
	}
	
	@Override
	public UUID accountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = UUID.fromString(accountId);
	}

	@Override
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String getAuthPrefix() {
		return authPrefix;
	}

	public void setAuthPrefix(String authPrefix) {
		this.authPrefix = authPrefix;
	}

	@Override
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String getServiceBaseUri() {
		return serviceBaseUri;
	}

	public void setServiceBaseUri(String serviceBaseUri) {
		this.serviceBaseUri = serviceBaseUri;
	}

	@Override
	public String getAuthBaseUri() {
		return authBaseUri;
	}

	public void setAuthBaseUri(String authBaseUri) {
		this.authBaseUri = authBaseUri;
	}

	public String getThumbprint() {
		return thumbprint;
	}

	public void setThumbprint(String thumbprint) {
		this.thumbprint = thumbprint;
	}

	public String getCredentialSnils() {
		return credentialSnils;
	}

	public void setCredentialSnils(String credentialSnils) {
		this.credentialSnils = credentialSnils;
	}

	public String getServiceUserId() {
		return serviceUserId;
	}

	public void setServiceUserId(String serviceUserId) {
		this.serviceUserId = serviceUserId;
	}
}
