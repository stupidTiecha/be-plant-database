package com.plant.database.service.serviceImpl;

import com.plant.database.mapper.UserMapper;
import com.plant.database.model.bean.User;
import com.plant.database.model.dto.RegisterReq;
import com.plant.database.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * UserServiceImpl
 *
 * @author 18044703
 * @date 2020/5/23
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User logIn(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        return userMapper.selectOne(user);
    }

    @Override
    public User register(RegisterReq registerReq) {
        User user = new User();
        user.setUserName(registerReq.getUserName());
        user.setPassword(DigestUtils.md5DigestAsHex(registerReq.getPassword().getBytes()));
        User temp = userMapper.selectOne(user);
        if (temp != null) {
            return null;
        }
        user.setUserId(UUID.randomUUID().toString());
        userMapper.insert(user);

        return user;
    }
}
