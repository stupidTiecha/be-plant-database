package com.plant.database.service;

import com.plant.database.model.bean.User;
import com.plant.database.model.dto.RegisterReq;

/**
 * @author jingyu chen
 */
public interface UserService {

    /**
     * user login
     *
     * @param userName
     * @param password
     * @return
     */
    User logIn(String userName, String password);

    /**
     * user register
     *
     * @param registerReq
     * @return
     */
    User register(RegisterReq registerReq);
}
