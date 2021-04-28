package com.greenleaf.fruitshop.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int userId;
    private String username;
    private String nickname;
    private String password;
    private String gender;
    private String email;
    private String phone;
    private String location;
    private String detailAddress;
    private String identity;
    private String active;
    private Date updated;
    private Date created;
}
