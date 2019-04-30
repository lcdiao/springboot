package cn.lcdiao.springboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: token签发的工具类
 * @author: SY_zheng
 * @create: 2019-04-08
 */
public class JwtUtils {

    /**
     * 私钥，请勿泄露，请问修改
     */
    private static final String SECRET = "d2t6QyW/Pc0S3r9PD-#U";

    /**
     * token过期时间为1天
     */
    private static final int CALENDAR_FIELD = Calendar.DATE;
    private static final int CALENDAR_INTERVAL = 1;


    /**
     * 生成token令牌
     * @param openId
     * @return
     */
    public static String createToken(String openId){
        try {
            Date date = new Date();
            // 设置过期时间
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(CALENDAR_FIELD, CALENDAR_INTERVAL);
            Date expiresDate = nowTime.getTime();

            Map<String, Object> map = new HashMap<>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            // 设置token
            String token = JWT.create()
                    // header
                    .withHeader(map)
                    // payload
                    .withClaim("iss", "Service")
                    .withClaim("aud", "web")
                    //
                    .withClaim("openId", null == openId || "".equals(openId) ? null : openId)
                    // 当前时间
                    .withIssuedAt(date)
                    // 过期时间
                    .withExpiresAt(expiresDate)
                    .sign(Algorithm.HMAC256(SECRET));
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        DecodedJWT jwt = null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwt = verifier.verify(token);
        return jwt.getClaims();

    }

    /**
     * 根据Token获取user_id
     * @param token
     * @return user_id
     */
    public static String getOpenId(String token) throws UnsupportedEncodingException {
        Map<String, Claim> claims = verifyToken(token);
        Claim claim = claims.get("openId");
        return claim.asString();
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(createToken("test"));
//        //System.out.println(getOpenId("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ3ZWIiLCJvcGVuSWQiOiJhc2Rhc2QiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNTU2MjQ3MjYzLCJpYXQiOjE1NTUzODMyNjN9.EqD2V2KUGPLHB5e0eiZwaUhgwv_j1BvUnb6WBOeN03s"));
//
//
//    }

//JWTDecodeException
//SignatureVerificationException
}