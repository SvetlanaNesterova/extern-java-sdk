/*
 * MIT License
 *
 * Copyright (c) 2018 SKB Kontur
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.kontur.extern_api.sdk.provider.auth;

import static ru.kontur.extern_api.sdk.adaptor.QueryContext.SESSION_ID;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.kontur.extern_api.sdk.adaptor.ApiException;
import ru.kontur.extern_api.sdk.adaptor.ApiResponse;
import ru.kontur.extern_api.sdk.adaptor.HttpClient;
import ru.kontur.extern_api.sdk.adaptor.QueryContext;
import ru.kontur.extern_api.sdk.provider.ApiKeyProvider;
import ru.kontur.extern_api.sdk.provider.AuthenticationProvider;
import ru.kontur.extern_api.sdk.provider.LoginAndPasswordProvider;
import ru.kontur.extern_api.sdk.provider.UriProvider;

/**
 * @author Aleksey Sukhorukov
 */
public class AuthenticationProviderByPass implements AuthenticationProvider {

    private final String authPrefix;
    private ApiKeyProvider apiKeyProvider;
    private LoginAndPasswordProvider loginAndPasswordProvider;
    private UriProvider authBaseUriProvider;
    private String sid;

    private HttpClient httpClient;

    public AuthenticationProviderByPass(
            UriProvider authBaseUriProvider,
            LoginAndPasswordProvider loginAndPasswordProvider,
            ApiKeyProvider apiKeyProvider,
            String authPrefix) {

        this.authBaseUriProvider = authBaseUriProvider;
        this.loginAndPasswordProvider = loginAndPasswordProvider;
        this.apiKeyProvider = apiKeyProvider;
        this.authPrefix = authPrefix == null ? DEFAULT_AUTH_PREFIX : authPrefix;
    }

    public AuthenticationProviderByPass(
            UriProvider authBaseUriProvider,
            LoginAndPasswordProvider loginAndPasswordProvider,
            ApiKeyProvider apiKeyProvider) {
        this(authBaseUriProvider, loginAndPasswordProvider, apiKeyProvider, DEFAULT_AUTH_PREFIX);
    }

    @Override
    public AuthenticationProviderByPass httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public void setApiKeyProvider(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    public void setLoginAndPasswordProvider(LoginAndPasswordProvider loginAndPasswordProvider) {
        this.loginAndPasswordProvider = loginAndPasswordProvider;
    }

    public void setAuthBaseUriProvider(UriProvider authBaseUriProvider) {
        this.authBaseUriProvider = authBaseUriProvider;
    }

    @Override
    public QueryContext<String> sessionId() {
        QueryContext<String> cxt = new QueryContext<String>("")
                .setApiKeyProvider(apiKeyProvider);

        if (sid != null) {
            return new QueryContext<String>().setResult(sid, QueryContext.SESSION_ID);
        }

        try {

            if (loginAndPasswordProvider == null) {
                return cxt.setServiceError("Missing the login provider");
            }

            String login = loginAndPasswordProvider.getLogin();
            String pass = loginAndPasswordProvider.getPass();
            String apiKey = apiKeyProvider.getApiKey();

            if (apiKey == null) {
                cxt.setServiceError("Missing the required parameter 'api key'");
            }

            httpClient.setServiceBaseUri(authBaseUriProvider.getUri());

            Map<String, Object> queryParams = new HashMap<String, Object>() {
                private static final long serialVersionUID = 1L;

                {
                    put("login", login);
                    put("apiKey", apiKey);
                }
            };

            Map<String, String> headerParams = Collections.singletonMap("Content-Type", "text/plain");
            ApiResponse<ResponseSid> resp = httpClient.submitHttpRequest("/authenticate-by-pass", "POST",
                    queryParams,
                    pass,
                    headerParams,
                    Collections.emptyMap(),
                    ResponseSid.class
            );

            sid = resp.getData().getSid();
            cxt.setResult(sid, SESSION_ID);
        } catch (ApiException x) {
            cxt.setServiceError(x);
        }

        return cxt;
    }

    @Override
    public String authPrefix() {
        return authPrefix;
    }

}
