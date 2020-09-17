package com.blocker.kakao;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuthProvider {

    KAKAO {
        @Override
        public ClientRegistration.Builder getBuilder() {
            return getBuilder("kakao", ClientAuthenticationMethod.POST)
                    .scope("profile", "talk_message")
                    .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                    .tokenUri("https://kauth.kakao.com/oauth/token")
                    .userInfoUri("https://kapi.kakao.com/v2/user/me")
                    .clientId("2d3bdff993293b2a8c5a82f963175c8a")
                    .userNameAttributeName("id")
                    .clientName("Kakao");
        }
    },
    NAVER {
        @Override
        public ClientRegistration.Builder getBuilder() {
            return getBuilder("naver", ClientAuthenticationMethod.POST)
                    .scope("profile", "talk_message")
                    .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                    .tokenUri("https://nid.naver.com/oauth2.0/token")
                    .userInfoUri("https://openapi.naver.com/v1/nid/me")
                    .clientId("MaPOtdZfH4zVADw4AFJX")
                    .clientSecret("cAtFom_WbK")
                    .userNameAttributeName("id")
                    .clientName("Naver");
        }
    };
    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method) {

        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUriTemplate(CustomOAuthProvider.DEFAULT_LOGIN_REDIRECT_URL);

        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder();
    
}