package com.plant.database.model.dto;

/**
 * RegisterReq
 *
 * @author 18044703
 * @date 2020/5/23
 */
public class RegisterReq {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
