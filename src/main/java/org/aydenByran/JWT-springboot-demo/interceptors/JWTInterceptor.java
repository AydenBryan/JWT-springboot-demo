package org.cold92.back.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.cold92.back.utils.JWTUtils;
import org.cold92.back.utils.ResponseUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT拦截器，对除了登录注册以外的接口进行保护。
 * 用户登录后，Token被存储在前端，每一次请求都放在Header中带给后端，中间被拦截器拦下来做验证
 * @author admin
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String, Object> resultMap = new HashMap<>();
        if (token != null) {
            try {
                JWTUtils.verifyToken(token);
                resultMap.put("state", true);
                resultMap.put("msg", "已放行");
                ResponseUtil.transmitMap(response, resultMap);
                return true;
            } catch (SignatureVerificationException e1) {
                resultMap.put("msg", "无效签名");
            } catch (TokenExpiredException e2) {
                resultMap.put("msg", "Token已过期");
            } catch (AlgorithmMismatchException e3) {
                resultMap.put("msg", "算法不匹配");
            } catch (InvalidClaimException e4) {
                resultMap.put("msg", "Payload失效");
            }
        } else {
            resultMap.put("msg", "Token为空");
        }
        resultMap.put("state", false);
        ResponseUtil.transmitMap(response, resultMap);
        return false;
    }
}
