package fr.legunda.biblibouille.server.security;

import org.apache.commons.httpclient.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (isLoginUrl(httpServletRequest) || isKnownSession(httpServletRequest.getSession(false))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        else {
            httpServletResponse.sendError(HttpStatus.SC_FORBIDDEN);
        }
    }

    private boolean isKnownSession(HttpSession session) {
        return session != null;
    }

    private boolean isLoginUrl(HttpServletRequest httpServletRequest) {
        return "/resource/security/login".equals(httpServletRequest.getRequestURI());
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
