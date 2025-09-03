package com.ciudadania360.shared.trace;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdFilter implements Filter {

    public static final String TRANSACTION_ID = "transactionId";
    public static final String HEADER_NAME = "X-Request-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            String tx = req.getHeader(HEADER_NAME);
            if (tx == null || tx.isBlank()) {
                tx = UUID.randomUUID().toString();
            }

            // Ponemos en MDC para que aparezca en logs
            MDC.put(TRANSACTION_ID, tx);

            // Exponemos en la respuesta para correlación
            resp.setHeader(HEADER_NAME, tx);

            chain.doFilter(request, response);
        } finally {
            MDC.remove(TRANSACTION_ID);
        }
    }
}
