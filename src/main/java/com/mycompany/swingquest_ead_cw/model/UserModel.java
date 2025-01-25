/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swingquest_ead_cw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Lihindu Perera
 */
public class UserModel {
    @JsonProperty("userId")
    private int userId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("password")
    private String password;
    
    @JsonProperty("correctAnswersCount")
    private int correctAnswersCount;

    // Constructor
    public UserModel() {}

    public UserModel(int userId, String name, String password, int correctAnswersCount) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.correctAnswersCount = correctAnswersCount;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", correctAnswersCount=" + correctAnswersCount +
                '}';
    }
}
