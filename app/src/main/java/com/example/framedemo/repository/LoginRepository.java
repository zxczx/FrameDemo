package com.example.framedemo.repository;

import com.example.framedemo.api.service.LoginService;
import com.example.framedemo.db.user.User;
import com.example.framedemo.rx.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;

public class LoginRepository {

    private LoginService loginService;

    public LoginRepository(LoginService loginService) {
        this.loginService = loginService;
    }


    public Single<User> login(String userName, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        return loginService.login(map)
                .map(new ResponseTransformer<>());
    }

}
