package com.alklid.oauth.domain.oauth.custom;

import com.alklid.oauth.common.exception.GlobalException;
import com.alklid.oauth.common.model.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class CustomOAuthExceptionHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {

    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException authException)
            throws IOException, ServletException {
        new GlobalException.InvalidOAuth2(request, response, authException).handle("access");
    }

    public static class AuthTokenException {
        public static ResponseEntity getException(OAuth2Exception oAuth2Exception) {
            ErrorResponseDto.Response errorResponse = new ErrorResponseDto.Response();
            errorResponse.setTimestamp(Instant.now());
            errorResponse.setStatus(oAuth2Exception.getHttpErrorCode());
            errorResponse.setError(oAuth2Exception.getOAuth2ErrorCode());
            errorResponse.setMessage(oAuth2Exception.getMessage());
            errorResponse.setPath("/oauth/token");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
