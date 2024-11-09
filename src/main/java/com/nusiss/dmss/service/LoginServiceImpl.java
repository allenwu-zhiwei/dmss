package com.nusiss.dmss.service;

import com.nusiss.dmss.config.CustomException;
import com.nusiss.dmss.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public Map<String, Object> login(String username, String password) {

        List<User> users = userService.findUserByUsernameAndPassword(username, password);

        // Validate username
        if (users == null || users.size() != 1) {
            throw new CustomException("Invalid username/password.");
        }

        User user = users.get(0);

        // Get token by username and password
        String token = jwtTokenService.generateToken(username, password);

        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("userId", user.getUserId());
        response.put("role", user.getRole().name()); // Assuming role is an enum

        return response;
    }

    private String getExpiredDateTime() {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30); // Add 30 minutes

        // Format the date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expirationDateStr = sdf.format(calendar.getTime());

        return expirationDateStr;
    }
}
//@Service
//public class LoginServiceImpl implements LoginService{
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtTokenService jwtTokenService;
//
//    @Override
//    public String login(String username, String password) {
//
//        List<User> users = userService.findUserByUsernameAndPassword(username, password);
//
//        //validate username
//        if(users == null || users.size() != 1){
//            throw new CustomException("Invalid username/password.");
//        }
//
//        //get token by username and password
//        String token = jwtTokenService.generateToken(username, password);
//
//        return token;
//    }
//
//    private String getExpiredDateTime(){
//        // Get the current date and time
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 30); // Add 30 minutes
//
//        // Format the date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String expirationDateStr = sdf.format(calendar.getTime());
//
//        return expirationDateStr;
//    }
//}
