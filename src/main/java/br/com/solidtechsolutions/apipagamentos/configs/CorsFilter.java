package br.com.solidtechsolutions.apipagamentos.configs;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

public class CorsFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CorsFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String origin = httpRequest.getHeader("Origin");

        if (origin != null && !isOriginAllowed(origin)) {
            System.out.println("CORS request from origin " + origin + " is not allowed.");
        }

        chain.doFilter(request, response);
    }

    private boolean isOriginAllowed(String origin) {
        return origin.equals("https://solidtechsolutions.com.br/");
    }

    @Override
    public void destroy() {
    }
}
