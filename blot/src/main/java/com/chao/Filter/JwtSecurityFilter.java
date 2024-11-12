package com.chao.Filter;

import cn.hutool.jwt.JWT;
import com.chao.service.impl.UserServiceImpl;
import com.chao.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token==null||token.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }
        //判断jwt是否正确
        if(!JWT.of(token).setKey(UserServiceImpl.JWT_KEY).verify()){
            throw new RuntimeException("token错误");
        }
        log.debug("jwt正确");
        //解析jwt
        String name = (String) JWT.of(token).getPayload("name");
        ThreadLocalUtils.set(name);
        //保存到SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name,null,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
