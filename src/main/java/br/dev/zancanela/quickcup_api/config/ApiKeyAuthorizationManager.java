package br.dev.zancanela.quickcup_api.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class ApiKeyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Value("${security.api.key}")
    private String securityApiKey;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || header.isEmpty() || !securityApiKey.equals(header)) {
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}