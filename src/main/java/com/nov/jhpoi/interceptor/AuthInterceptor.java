package com.nov.jhpoi.interceptor;

import com.auth0.jwt.JWT;
import com.nov.jhpoi.config.Address;
import com.nov.jhpoi.utils.pojo.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TimeZone;

/**
 * token拦截器
 * @author november
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("Authorization");
        if(token==null){
            // token为空,用户未登录
            // response.sendRedirect("");
            return false;
        }
        try{
            // 校验token
            if(TokenUtils.verify(token, Address.TOKEN_SEC)){
                return true;
            }
            // response.sendRedirect("");
            return false;
        }catch (Exception e){
            // token错误（userId不存在 或者 token验证失败）
            // response.sendRedirect("");
            e.printStackTrace();
        }
        return false;
    }
}
