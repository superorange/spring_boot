package com.example.sp;

public class User {
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

    private String userName;
    private String password;
    public User(){}
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
