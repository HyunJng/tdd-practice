package org.example.demo.common.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@Deprecated // SpringSecurity 도입하면서 사용 불가
public class JsonRequestCachingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        filterChain.doFilter(contentCachingRequestWrapper, servletResponse);
    }
}
