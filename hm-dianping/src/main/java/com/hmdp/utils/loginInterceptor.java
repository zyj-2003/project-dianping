package com.hmdp.utils;

import ch.qos.logback.classic.spi.STEUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//这个类是用来生成拦截器的类
public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.判断是否要拦截（ThreadLocal中是否有用户）
        if (UserHolder.getUser() == null) {
            // 没有需要拦截，并设置状态码
            response.setStatus(401);
            return false;
        }
        // 有用户刷新
        return true;
    }
}
