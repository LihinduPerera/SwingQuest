/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.swingquest_ead_cw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.swingquest_ead_cw.SwingQuestGame.SwingQuest;
import com.mycompany.swingquest_ead_cw.model.QuestionModel;
import com.mycompany.swingquest_ead_cw.view.QuizFrame;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lihindu Perera
 */
public class SwingQuest_EAD_CW {
     public static void main(String[] args) {
        try {
            // Get all questions from the API
            String questionsResponse = ApiClient.getQuestions();
            ObjectMapper objectMapper = new ObjectMapper();
            List<QuestionModel> questions = objectMapper.readValue(questionsResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, QuestionModel.class));
            
            // Launch the QuizFrame
            QuizFrame quizFrame = new QuizFrame(questions);
            quizFrame.setLocationRelativeTo(null);
            quizFrame.setResizable(false);
            quizFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading quiz: " + e.getMessage());
        }
//        MainMenuFrame mainMenu = new MainMenuFrame();
//        mainMenu.setLocationRelativeTo(null);
//        mainMenu.setResizable(false);
//        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainMenu.setVisible(true);
    }
}
