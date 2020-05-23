package com.plant.database.service;

import com.plant.database.model.bean.User;
import com.plant.database.model.dto.RegisterReq;

/**
 * @author jingyu chen
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    User logIn(String userName, String password);

    /**
     * 用户注册
     *
     * @param registerReq
     * @return
     */
    User register(RegisterReq registerReq);
}
