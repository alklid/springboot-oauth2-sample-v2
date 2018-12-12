package com.alklid.oauth2.domain.oauth.custom;

import com.alklid.oauth2.common.DefineException;
import com.alklid.oauth2.common.ErrorResponseBean;
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

@Component
public class CustomOAuthExceptionHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException authException)
            throws IOException, ServletException {
        new DefineException.InvalidOAuth2(request, response, authException).handle("access");
    }

    public static class AuthTokenException {
        public static ResponseEntity getException(OAuth2Exception oAuth2Exception) {
            ErrorResponseBean errorResponseBean = new ErrorResponseBean();
            errorResponseBean.setStatus(oAuth2Exception.getHttpErrorCode());
            errorResponseBean.setError(oAuth2Exception.getOAuth2ErrorCode());
            errorResponseBean.setMessage(oAuth2Exception.getMessage());
            errorResponseBean.setPath("/oauth/token");
            return new ResponseEntity(errorResponseBean, HttpStatus.BAD_REQUEST);
        }
    }
}
