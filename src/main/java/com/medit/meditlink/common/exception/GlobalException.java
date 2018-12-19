package com.medit.meditlink.common.exception;

import com.medit.meditlink.common.model.ErrorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class GlobalException {

	@AllArgsConstructor
	public static class InvalidAPIVersion extends RuntimeException {
		@Getter
		String apiVersion;
	}


	@AllArgsConstructor
	public static class InvalidSchemaVersion extends RuntimeException {
		@Getter
		String schemaVersion;
	}


	@AllArgsConstructor
	public static class AccessDenied extends RuntimeException {
	}


	@AllArgsConstructor
	public static class BadReqeust extends RuntimeException {
		@Getter
        BindingResult bindingResult;
	}


	@AllArgsConstructor
	public static class InvalidOAuth2 {
		private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
		private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();

		private HttpServletRequest request;
		private HttpServletResponse response;
		private AuthenticationException authenticationException;
		private AccessDeniedException accessDeniedException;

		public InvalidOAuth2(HttpServletRequest request, HttpServletResponse response,
							 AuthenticationException authenticationException) {
			this.request = request;
			this.response = response;
			this.authenticationException = authenticationException;
		}

		public InvalidOAuth2(HttpServletRequest request, HttpServletResponse response,
							 AccessDeniedException accessDeniedException) {
			this.request = request;
			this.response = response;
			this.accessDeniedException = accessDeniedException;
		}

		public void handle(String type) throws IOException, ServletException {
			try {
				ResponseEntity<OAuth2Exception> OAuthResult = null;
				if (type.equalsIgnoreCase("access")) {
					OAuthResult = exceptionTranslator.translate(this.accessDeniedException);
				}
				else {
					OAuthResult = exceptionTranslator.translate(this.authenticationException);
				}

				// error info
				int statusCode = OAuthResult.getStatusCode().value();
				String code = OAuthResult.getBody().getOAuth2ErrorCode();
				String path = getPath();

				ErrorResponseDto.Response errorResponse = new ErrorResponseDto.Response();
				errorResponse.setTimestamp(Instant.now());
				errorResponse.setStatus(statusCode);
				errorResponse.setCode(code);
				errorResponse.setPath(path);
				ResponseEntity<ErrorResponseDto.Response> result = new ResponseEntity<>(errorResponse, OAuthResult.getStatusCode());

				response.setStatus(OAuthResult.getStatusCode().value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(new JSONObject(errorResponse).toString());
				response.flushBuffer();
			}
			catch (ServletException e) {
				if (handlerExceptionResolver.resolveException(request, response, this, e) == null) {
					throw e;
				}
			}
			catch (IOException e) {
				throw e;
			}
			catch (RuntimeException e) {
				throw e;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public String getPath() {
			return GlobalException.getPath(this.request);
		}
	}


	public static String getPath(final HttpServletRequest httpRequest) {
		String path = httpRequest.getRequestURI();
		if (httpRequest.getQueryString() != null) {
			path += "?" + httpRequest.getQueryString();
		}
		return path;
	}

}
