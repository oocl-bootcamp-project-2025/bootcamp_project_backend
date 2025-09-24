package com.oocl.bootcampbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
    // 注意：实际项目中不要硬编码密钥，应使用环境变量或配置文件
    private static final String SECRET_KEY = "bootcamp_secret_key_2025_should_be_long_enough_for_hs256";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24小时

    // 生成签名密钥（使用 HS256 算法）
    private static SecretKey getSigningKey() {
        // 确保密钥长度足够（HS256 需要至少 256 位即 32 字节）
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey()) // 使用新的 signWith 方法，仅传入 SecretKey
                .compact();
    }

    public static String getUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder() // 使用 parserBuilder() 替代 parser()
                    .setSigningKey(getSigningKey()) // 使用新的 setSigningKey 方法
                    .build() // 构建解析器
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder() // 使用 parserBuilder() 替代 parser()
                    .setSigningKey(getSigningKey()) // 使用新的 setSigningKey 方法
                    .build() // 构建解析器
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            return claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
