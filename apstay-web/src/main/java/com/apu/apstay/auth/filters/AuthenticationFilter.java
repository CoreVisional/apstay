package com.apu.apstay.auth.filters;

import com.apu.apstay.security.SessionContext;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author alexc
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/manager/*", "/security/*", "/resident/*"})
public class AuthenticationFilter implements Filter {
    
    @Inject
    private SessionContext sessionContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        var _httpRequest = (HttpServletRequest) request;
        var _httpResponse = (HttpServletResponse) response;

        _httpResponse.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate");
        _httpResponse.setHeader("Pragma", "no-cache");
        _httpResponse.setDateHeader("Expires", 0);
        
        var _userId = sessionContext.getCurrentUserId();
        if (_userId == null) {
            _httpResponse.sendRedirect(_httpRequest.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
