package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.service.JwtTokenService;
import com.nusiss.dmss.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(HttpServletResponse response, @RequestBody Map<String, String> requestParams) {
        String username = requestParams.get("username");
        String password = requestParams.get("password");

        Map<String, Object> loginResponse = loginService.login(username, password);

        return ResponseEntity.status(200).body(new ApiResponse<>(true, "Login successfully", loginResponse));
    }
}
//@RestController
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private JwtTokenService jwtTokenService;
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseEntity<ApiResponse<String>> login(HttpServletResponse response, @RequestBody Map<String, String> requestParams) {
//        String username = requestParams.get("username");
//        String password = requestParams.get("password");
//
//        String token = loginService.login(username, password);
//        //jwtTokenService.addTokenToCookie(response, token);
//
//        return ResponseEntity.status(200).body(new ApiResponse<>(true, "Login successfully", token));
//
//
//    }
//
//}
