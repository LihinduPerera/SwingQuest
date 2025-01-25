package com.mycompany.swingquest_ead_cw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.swingquest_ead_cw.SwingQuestGame.SwingQuest;
import com.mycompany.swingquest_ead_cw.model.QuestionModel;
import com.mycompany.swingquest_ead_cw.view.ModifyQuestionsFrame;
import com.mycompany.swingquest_ead_cw.view.QuizFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.naming.directory.ModificationItem;

public class MainMenuFrame extends javax.swing.JFrame {

    private java.util.List<QuestionModel> questions;

    public MainMenuFrame() {
        initComponents();
        customizeUI();
        loadQuestions();
    }

    private void loadQuestions() {
        try {
            String questionsResponse = ApiClient.getQuestions();
            ObjectMapper objectMapper = new ObjectMapper();
            questions = objectMapper.readValue(questionsResponse, 
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, QuestionModel.class));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading quiz: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        btn_QandA = new javax.swing.JButton();
        btn_ModifyQuestions = new javax.swing.JButton();
        btn_SwingQuest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setResizable(false);

        jLabel1.setText("Welcome to SwingQuest!");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        btn_QandA.setText("Play Q&A");
        btn_ModifyQuestions.setText("Modify Questions");
        btn_SwingQuest.setText("Play SwingQuest");

        btn_QandA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn_QandAActionPerformed(evt);
            }
        });

        btn_SwingQuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn_SwingQuestActionPerformed(evt);
            }
        });

        // Add the action listener for the Modify Questions button
        btn_ModifyQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn_ModifyQuestionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(btn_QandA, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_SwingQuest, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_ModifyQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_QandA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_SwingQuest, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ModifyQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void customizeUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        jLabel1.setFont(new Font("Verdana", Font.BOLD, 24));
        jLabel1.setForeground(new Color(50, 50, 150));

        btn_QandA.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_QandA.setBackground(new Color(70, 130, 180));
        btn_QandA.setForeground(Color.BLACK);
        btn_QandA.setFocusPainted(false);
        btn_QandA.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_QandA.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn_ModifyQuestions.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_ModifyQuestions.setBackground(new Color(34, 139, 34));
        btn_ModifyQuestions.setForeground(Color.BLACK);
        btn_ModifyQuestions.setFocusPainted(false);
        btn_ModifyQuestions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_ModifyQuestions.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn_SwingQuest.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_SwingQuest.setBackground(new Color(255, 165, 0));
        btn_SwingQuest.setForeground(Color.BLACK);
        btn_SwingQuest.setFocusPainted(false);
        btn_SwingQuest.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_SwingQuest.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn_QandA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_QandA.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_QandA.setBackground(new Color(70, 130, 180));
            }
        });

        btn_SwingQuest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SwingQuest.setBackground(new Color(255, 140, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SwingQuest.setBackground(new Color(255, 165, 0));
            }
        });

        btn_ModifyQuestions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ModifyQuestions.setBackground(new Color(50, 205, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ModifyQuestions.setBackground(new Color(34, 139, 34));
            }
        });
    }

    private void btn_SwingQuestActionPerformed(java.awt.event.ActionEvent evt) {
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions available.");
            return;
        }
        try {
            QuizFrame quizFrame = new QuizFrame(questions);
            quizFrame.setLocationRelativeTo(null);
            quizFrame.setResizable(false);
            quizFrame.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading quiz: " + e.getMessage());
        }
    }

    private void btn_QandAActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void btn_ModifyQuestionsActionPerformed(java.awt.event.ActionEvent evt) {
        // Create and show ModifyQuestionsFrame
        ModifyQuestionsFrame modifyFrame = new ModifyQuestionsFrame();
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setVisible(true);
        this.dispose(); // Optionally dispose of this frame
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenuFrame().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btn_ModifyQuestions;
    private javax.swing.JButton btn_QandA;
    private javax.swing.JButton btn_SwingQuest;
    private javax.swing.JLabel jLabel1;
}
