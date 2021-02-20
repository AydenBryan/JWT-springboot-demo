package org.cold92.back.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author admin
 */
public class JWTUtils {

    /**
     * 私钥
     */
    private static final String SECRET = "1@3$.Ssd$%7^";

    /**
     * 创建Token
     * @param map 需要装载在Payload中的数据
     * @return Token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        // 声明过期时间：7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date expireTime = calendar.getTime();

        // Header默认加载，不写也行
        builder.withHeader(new HashMap<>());

        // Payload装载数据
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        builder.withExpiresAt(expireTime);

        // Signature配置，同时获取最终生成的Token
        String token = builder.sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    /**
     * 验证Token合法性，假如Token被修改，则抛出异常
     * @param token 待验证的Token
     */
    public static void verifyToken(String token) {
        // 验证Token必须使用和创建一样的算法和私钥
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 获取验证成功后的Token中的信息
     * @param token 已验证的Token
     * @return DecodedJWT实例
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }
}
