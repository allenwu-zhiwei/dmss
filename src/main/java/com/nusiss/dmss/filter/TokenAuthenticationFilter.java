package com.nusiss.dmss.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.dmss.config.CustomException;
import com.nusiss.dmss.entity.User;
import com.nusiss.dmss.service.JwtTokenService;
import com.nusiss.dmss.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Configuration
public class TokenAuthenticationFilter extends OncePerRequestFilter  {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        try{
            String token = request.getHeader("authToken");
            if(!url.contains("login") && !url.contains("swagger") && !url.contains("api-docs")){
                if (token != null && !StringUtils.isEmpty(token)) {
                    try {
                        boolean isValidated = validateToken(token);
                        if(!isValidated){
                            throw new CustomException ("please login first");
                        }
                    } catch (ExpiredJwtException e) {
                        throw new CustomException("JWT token has expired.");
                    } catch (Exception e) {
                        throw new CustomException ("Invalid JWT token.");
                    }
                } else {
                    throw new CustomException ("No authToken found.");
                }
            }
            filterChain.doFilter(request, response);
        } catch (CustomException  e){

            handleException(response, e);
        }



    }
    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        // Prepare response structure
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("data", null);

        // Set response details
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");

        // Convert the response to JSON and write it
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    // Validate the JWT token
    private boolean validateToken(String token) throws Exception {
        Claims claims =  Jwts.parserBuilder()
                    .setSigningKey(JwtTokenService.SECRET_KEY) // Set the key used to validate the signature
                    .build()
                    .parseClaimsJws(token) // Parse the token
                    .getBody(); // Get the claims from the token


        // Extract information from the claims
        String username = claims.get("username", String.class);
        String password = claims.get("password", String.class);
        //String expiredDateTimeStr = claims.get("expiredDateTime", String.class);
        //LocalDateTime expiredDateTime = LocalDateTime.parse(expiredDateTimeStr, JwtTokenService.DATE_TIME_FORMATTER);

        List<User> users = userService.findUserByUsernameAndPassword(username, password);
        if(users!=null && users.size() == 1){
            return true;
        } else {
            return false;
        }
    }
}
