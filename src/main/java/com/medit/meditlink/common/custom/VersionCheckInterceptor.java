package com.medit.meditlink.common.custom;

import com.medit.meditlink.common.Constant;
import com.medit.meditlink.common.exception.GlobalException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class VersionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        final Map<String, String> pathVariables =
                        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables == null || pathVariables.isEmpty()) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }

        String apiVersion = pathVariables.get("v");
        if (apiVersion.equals(Constant.ApiVersion.VERSION_1)) {
            return true;
        } else {
            throw new GlobalException.InvalidAPIVersion(apiVersion);
        }
    }

}
