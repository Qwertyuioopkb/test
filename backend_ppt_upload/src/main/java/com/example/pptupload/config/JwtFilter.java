package com.example.pptupload.config;
import com.example.pptupload.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter (filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    // 白名单接口（不需要校验 token）
    private static final String[] WHITE_LIST = {
            "/auth/login"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // 如果是白名单，直接放行
        for (String url : WHITE_LIST) {
            if (path.startsWith(url)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 从请求头取出 token
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7); // 去掉 "Bearer "
        String username = JwtUtil.parseToken(token);

        if (username == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Invalid or expired token");
            return;
        }

        // Token 有效，放行
        chain.doFilter(request, response);
    }
}
