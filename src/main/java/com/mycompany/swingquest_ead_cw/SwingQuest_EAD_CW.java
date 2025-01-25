/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.swingquest_ead_cw;

import com.mycompany.swingquest_ead_cw.SwingQuestGame.SwingQuest;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lihindu Perera
 */
public class SwingQuest_EAD_CW {
     public static void main(String[] args) {
        try {
            // Get all users
            String users = ApiClient.getUsers();
            System.out.println("Users: " + users);

            // Create a new user
            String newUserResponse = ApiClient.createUser("John Doe", "password123");
            System.out.println("Create User Response: " + newUserResponse);

            // Get all questions
            String questions = ApiClient.getQuestions();
            System.out.println("Questions: " + questions);

            // Create a new question
            String newQuestionResponse = ApiClient.createQuestion("What is 2+2?", "1", "2", "3", "4", 4);
            System.out.println("Create Question Response: " + newQuestionResponse);
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        // Initialize the main menu UI
        MainMenuFrame mainMenu = new MainMenuFrame();
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setResizable(false);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setVisible(true);
    }
}
