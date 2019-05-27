package com.reckyphiter.crudproxy.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Recky Phiter
 */
@Component
@Order(0)
public class CacheFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final ContentCachingRequestWrapper requestToUse = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        final ContentCachingResponseWrapper responseToUse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(requestToUse, responseToUse);
        updateResponse(responseToUse);
    }

    @Override
    public void destroy() {

    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        final ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper != null) {
            responseWrapper.copyBodyToResponse();
        }
    }
}
