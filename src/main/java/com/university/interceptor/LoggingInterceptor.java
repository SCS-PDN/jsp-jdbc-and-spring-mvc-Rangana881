package com.university.interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class LoggingInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        String method = request.getMethod();
        String user = (request.getSession().getAttribute("studentName") != null)
                ? request.getSession().getAttribute("studentName").toString()
                : "Anonymous";

        if (uri.contains("/login") && method.equalsIgnoreCase("POST")) {
            System.out.println("[LOGIN ATTEMPT] " + user + " at " + LocalDateTime.now());
        } else if (uri.contains("/register") && method.equalsIgnoreCase("POST")) {
            System.out.println("[COURSE REGISTRATION] " + user + " at " + LocalDateTime.now());
        }

        return true;
    }
}
