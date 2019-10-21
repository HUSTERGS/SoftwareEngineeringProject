package com.example.sudoku;

import org.litepal.crud.LitePalSupport;

public class userInfo extends LitePalSupport {
    private String userName;
    private String passwordHash;

    public String getUserName() {
        return this.userName;
    }
    public String getPasswordHash() {
        return this.passwordHash;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
