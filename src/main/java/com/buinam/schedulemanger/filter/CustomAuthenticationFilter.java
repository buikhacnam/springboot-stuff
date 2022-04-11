package com.buinam.schedulemanger.filter;

import com.buinam.schedulemanger.utils.JwtUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//        String username = request.getParameter("username");
//        String password = request.getParameter("password");

        String username = null;
        String password = null;

        try {
            String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            log.info("Request body: {}", test);
            username = new ObjectMapper().readValue(test, MapUser.class).getUsername();
            password = new ObjectMapper().readValue(test, MapUser.class).getPassword();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Username is: {}", username); log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        log.info("Authentication token: {}", authenticationToken);
        log.info("before authenticationManager.authenticate");
        //UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=drogba, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_ADMIN, ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_ADMIN, ROLE_USER]]
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        log.info("User: {}", user);
        String access_token = JwtUtils.generateAccessToken(user, request);
        String refresh_token = JwtUtils.generateRefreshToken(user, request);
        /*response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*/

        /*
            {
              "sub": "drogba",
              "roles": [
                "ROLE_ADMIN",
                "ROLE_USER"
              ],
              "iss": "http://localhost:8080/api/login",
              "exp": 1648725050
            }
        */
        log.info("Access token is: {}", access_token); log.info("Refresh token is: {}", refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        tokens.put("user", user.getUsername());
        tokens.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toString());
        log.info("Tokens: {}", tokens);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> message_error = new HashMap<>();
        message_error.put("error", failed.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(401);
        new ObjectMapper().writeValue(response.getOutputStream(), message_error);
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
    }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class MapUser {
    private String username;
    private String password;
}