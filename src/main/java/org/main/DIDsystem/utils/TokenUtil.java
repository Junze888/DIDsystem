package org.main.DIDsystem.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;


public class TokenUtil {


    // 密钥，用于签名和验证 JWT
    static SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // 将密钥转换为 Base64 编码的字符串
    static String base64Key = Encoders.BASE64.encode(secretKey.getEncoded());

    // 生成 JWT
    public static String generateJwt(String DID, long expirationTimeMillis) {
        return Jwts.builder()
                .setSubject(DID)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS256, base64Key)
                .compact();
    }

    // 验证 JWT
    public static boolean validateJwt(String token) {
        try {
            Jwts.parser().setSigningKey(base64Key).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
