package com.buinam.schedulemanger.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.buinam.schedulemanger.model.AppUser;
import com.buinam.schedulemanger.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtils {
    private final static String SECRET = "secret";
    private final static Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
    private final static JWTVerifier verifier = JWT.require(algorithm).build();


    //generate token from userDetails
    public static String generateAccessToken(User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map((GrantedAuthority g) -> g.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
    }

    //generate token from user model
    public static String generateAccessToken(AppUser user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map((Role role) -> role.getName()).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }


    //get roles from token
    public static Collection<SimpleGrantedAuthority> getAuthorities(DecodedJWT decodedJWT) {
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities  = new ArrayList<>();
        Arrays.stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
//        return decodedJWT.getClaim("roles").asList(String.class).stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    //get roles from role model and map to SimpleGrantedAuthority
    public static Collection<SimpleGrantedAuthority> getAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities  = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    public static String getUserName(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }
}
