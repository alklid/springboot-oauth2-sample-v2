package com.medit.meditlink.common.custom;

import com.medit.meditlink.common.Constant;
import com.medit.meditlink.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class VersionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        /*
        * URL에서 path variable 가져와서, 첫 번째 variable인 apiVersion에 대해 확인
        * 지원하지 않는 버전에 대해서는 exception throw
        */
        final Map<String, String> pathVariables =
                        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // 아예 없는 API URL 호출일경우 NoHandlerFoundException throw
        if (pathVariables == null || pathVariables.isEmpty()) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }

        String apiVersion = pathVariables.get("v");
        if (apiVersion.equals(Constant.ApiVersion.VERSION_1)) {
            return true;
        }
        else {
            throw new GlobalException.InvalidAPIVersion(apiVersion);
        }
    }

}
