package com.example.pptupload.service.impl;

import com.example.pptupload.entity.User;
import com.example.pptupload.mapper.UserMapper;
import com.example.pptupload.service.SmsVerificationService;
import com.example.pptupload.service.UserService;
import com.example.pptupload.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final SmsVerificationService smsVerificationService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, SmsVerificationService smsVerificationService) {
        this.userMapper = userMapper;
        this.smsVerificationService = smsVerificationService;
    }

    @Override
    public void initiateLogin(String username, String password, String phone) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }

        User user = userMapper.findByUsername(username);
        if (user == null) {
            if (!StringUtils.hasText(phone)) {
                throw new IllegalArgumentException("注册新账号时需要提供手机号");
            }
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setPhone(phone);
            userMapper.insertUser(newUser);
            user = newUser;
        } else {
            if (!user.getPassword().equals(password)) {
                throw new IllegalArgumentException("用户名或密码错误");
            }
            if (!StringUtils.hasText(user.getPhone())) {
                if (StringUtils.hasText(phone)) {
                    userMapper.updatePhone(user.getId(), phone);
                    user.setPhone(phone);
                } else {
                    throw new IllegalArgumentException("账号未绑定手机号，请输入手机号后重试");
                }
            }
        }

        smsVerificationService.sendLoginCode(user);
    }

    @Override
    public String completeLogin(String username, String smsCode) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(smsCode)) {
            return null;
        }

        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }

        boolean verified = smsVerificationService.verifyCode(username, smsCode);
        if (verified) {
            return JwtUtil.generateToken(username);
        }
        return null;
    }
}
