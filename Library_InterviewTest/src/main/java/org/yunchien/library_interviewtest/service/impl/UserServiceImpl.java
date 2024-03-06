package org.yunchien.library_interviewtest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.yunchien.library_interviewtest.dao.UserDao;
import org.yunchien.library_interviewtest.dto.UserLoginRequest;
import org.yunchien.library_interviewtest.dto.UserRegisterRequest;
import org.yunchien.library_interviewtest.model.User;
import org.yunchien.library_interviewtest.service.UserService;
@Component
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊的email
        User user = userDao.getUserByPhone(userRegisterRequest.getPhoneNumber());
        if (user != null) {
            log.warn("該 電話號碼 {} 已經被註冊", userRegisterRequest.getPhoneNumber());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterRequest);

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        return null;
    }
}
