package com.nusiss.dmss.service;

import java.util.Map;

public interface LoginService {

    //public String login(String username, String password);
    public Map<String,Object> login(String username, String password);
}
