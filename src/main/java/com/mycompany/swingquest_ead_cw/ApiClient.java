/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swingquest_ead_cw;

/**
 *
 * @author Lihindu Perera
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:7108/api"; 

    // Method to get all users
    public static String getUsers() throws IOException {
        String url = BASE_URL + "/Users";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to get a user by ID
    public static String getUserById(int userId) throws IOException {
        String url = BASE_URL + "/Users/" + userId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to create a new user
    public static String createUser(String name, String password) throws IOException {
        String url = BASE_URL + "/Users";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            String json = "{\"name\": \"" + name + "\", \"password\": \"" + password + "\", \"correctAnswersCount\": 0}";
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to update a user
    public static String updateUser(int userId, String name, String password, int correctAnswersCount) throws IOException {
        String url = BASE_URL + "/Users/" + userId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(url);
            String json = "{\"userId\": " + userId + ", \"name\": \"" + name + "\", \"password\": \"" + password + "\", \"correctAnswersCount\": " + correctAnswersCount + "}";
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to delete a user
    public static String deleteUser(int userId) throws IOException {
        String url = BASE_URL + "/Users/" + userId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(url);
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to get all questions
    public static String getQuestions() throws IOException {
        String url = BASE_URL + "/Questions";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to get a question by ID
    public static String getQuestionById(int questionId) throws IOException {
        String url = BASE_URL + "/Questions/" + questionId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to create a new question
    public static String createQuestion(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) throws IOException {
        String url = BASE_URL + "/Questions";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            String json = "{\"question\": \"" + question + "\", \"answer1\": \"" + answer1 + "\", \"answer2\": \"" + answer2 + "\", \"answer3\": \"" + answer3 + "\", \"answer4\": \"" + answer4 + "\", \"correctAnswer\": " + correctAnswer + "}";
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to update a question
    public static String updateQuestion(int questionId, String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) throws IOException {
        String url = BASE_URL + "/Questions/" + questionId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(url);
            String json = "{\"questionId\": " + questionId + ", \"question\": \"" + question + "\", \"answer1\": \"" + answer1 + "\", \"answer2\": \"" + answer2 + "\", \"answer3\": \"" + answer3 + "\", \"answer4\": \"" + answer4 + "\", \"correctAnswer\": " + correctAnswer + "}";
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Method to delete a question
    public static String deleteQuestion(int questionId) throws IOException {
        String url = BASE_URL + "/Questions/" + questionId;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(url);
            CloseableHttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        }
    }
    
    public static void updateHostingStatus(boolean isHosting) throws IOException {
    String url = BASE_URL + "/HostingStatus";
    try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpPut request = new HttpPut(url);
        String json = "{\"isHosting\": " + isHosting + "}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");
        HttpResponse response = client.execute(request);
        EntityUtils.toString(response.getEntity()); // Read the response
    }
}

}