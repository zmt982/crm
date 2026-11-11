package ru.crm.product_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        log.info("Пришел запрос на {} с IP {} в {}",
                request.getRequestURI(),
                request.getRemoteAddr(),
                Instant.now());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Запрос без токена!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        log.info("Токен получен: {}...", token.substring(0, Math.min(token.length(), 15)));

        if ("192.168.9.9".equals(request.getRemoteAddr())) {
            log.error("Заблокирован запрос с запрещенного IP: {}", request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);

        log.info("Ответ сформирован. Статус: {}", response.getStatus());
    }
}
