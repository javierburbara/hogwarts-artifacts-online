.oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt
        .authenticationEntryPoint(this.customBearerTokenAuthenticationEntryPoint)
        .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler)
    )
            )

private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
private final CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint;
private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;