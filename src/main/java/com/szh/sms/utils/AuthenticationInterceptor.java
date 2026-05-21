package com.szh.sms.utils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.szh.sms.dto.User;
import com.szh.sms.service.Upload.impl.UploadServiceImpl;
import com.szh.sms.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Description 拦截器去获取token并验证token
 **/
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
  private final Log logger = LogFactory.getLog(UploadServiceImpl.class);

  @Autowired
  UserService userService;

  @Override
  public boolean preHandle(@NonNull HttpServletRequest httpServletRequest,
                           @NonNull HttpServletResponse httpServletResponse,
                           @NonNull Object object) throws Exception {
    //通过所有OPTION请求
    if(httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")){
      return true;
    }

    String token = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
    String refreshToken = httpServletRequest.getHeader("freshToken");// 从 http 请求头中取出 token
    // 如果不是映射到方法直接通过
    if (!(object instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) object;
    Method method = handlerMethod.getMethod();
    //检查是否有passtoken注释，有则跳过认证
    if (method.isAnnotationPresent(PassToken.class)) {
      PassToken passToken = method.getAnnotation(PassToken.class);
      if (passToken.required()) {
        return true;
      }
    }

    // 获取 token 中的 用户信息
    String userValue = null;
    try {
      if (token == null || token.isEmpty()) {
        logger.error("=== 无token，请重新登录 ===");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write("{\"message\":\"无token，请重新登录\"}");
        return false;
      }
      userValue = JWT.decode(token).getAudience().get(0);
    } catch (JWTDecodeException j) {
      httpServletResponse.setStatus(401);
      httpServletResponse.getWriter().write("{\"message\":\"token解析失败\"}");
      return false;
    } catch (Exception e) {
      logger.error("=== 无token，请重新登录 ===");
      httpServletResponse.setStatus(401);
      httpServletResponse.getWriter().write("{\"message\":\"无token，请重新登录\"}");
      return false;
    }
    Map<String, Object> map = new HashMap<>();
    map.put("level", (userValue).substring(0,1));
    map.put("id", (userValue).substring(1));
    User user = userService.findUser(map);
    if (user == null) {
      throw new RuntimeException("用户不存在，请重新登录");
    }


    Date oldTime = JWT.decode(token).getExpiresAt();
    Date refreshTime = null;
    if (refreshToken != null && !refreshToken.isEmpty()) {
      refreshTime = JWT.decode(refreshToken).getExpiresAt();
    }
    long oldDiff = oldTime.getTime() - new Date().getTime();
    long refreshDiff = 0;
    if (refreshTime != null) {
      refreshDiff = refreshTime.getTime() - new Date().getTime();
    }
    if (oldDiff <= 0) {
      if (refreshTime == null || refreshDiff <= 0) {
        logger.error("=== token 已过期, 请重新登录 ===");
        httpServletResponse.sendError(401);
        return false;
      }
    }
    String newToken = userService.getToken(user, 60* 60 * 1000);
    String newRefToken = userService.getToken(user, 24*60*60*1000);
    // 更新token
    httpServletResponse.setHeader("Authorization", newToken);
    httpServletResponse.setHeader("freshToken", newRefToken);
    httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization,freshToken");

    //检查有没有需要用户权限的注解
//    if (method.isAnnotationPresent(UserLoginToken.class)) {  // 是否使用@UserLoginToken注解
//      UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
//      if (userLoginToken.required()) {
        // 执行认证
        // 利用用户密码，解密验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
          jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
          logger.error("=== token验证失败 ===");
          httpServletResponse.sendError(401);
          return false;
//          throw new RuntimeException("401");
        }
//        return true;
//      }
//    }
    return true;
  }

  @Override
  public void postHandle(@NonNull HttpServletRequest httpServletRequest,
                         @NonNull HttpServletResponse httpServletResponse,
                         @NonNull Object o,
                         @Nullable ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(@NonNull HttpServletRequest httpServletRequest,
                              @NonNull HttpServletResponse httpServletResponse,
                              @NonNull Object o,
                              @Nullable Exception e) throws Exception {
  }
}
