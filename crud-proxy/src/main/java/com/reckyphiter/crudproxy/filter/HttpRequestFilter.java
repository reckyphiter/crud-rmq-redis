package com.reckyphiter.crudproxy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * @author Recky Phiter
 */
@Component
@Order(1)
public class HttpRequestFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
