package com.mycompany.swingquest_ead_cw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.swingquest_ead_cw.model.QuestionModel;
import com.mycompany.swingquest_ead_cw.model.UserModel;
import com.mycompany.swingquest_ead_cw.view.HostQuestionsFrame;
import com.mycompany.swingquest_ead_cw.view.ModifyQuestionsFrame;
import com.mycompany.swingquest_ead_cw.view.QuizFrame;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
                objectMapper.getTypeFactory().constructCollectionType(java.util.ArrayList.class, QuestionModel.class));
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
        btn_GenerateReport = new javax.swing.JButton();  // Add new button

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setResizable(false);

        jLabel1.setText("Welcome to SwingQuest!");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        btn_QandA.setText("Play Q&A");
        btn_ModifyQuestions.setText("Modify Questions");
        btn_SwingQuest.setText("Play SwingQuest");
        btn_GenerateReport.setText("Generate Report");  // Set button text

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

        // Add action listener for the "Generate Report" button
        btn_GenerateReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn_GenerateReportActionPerformed(evt);
            }
        });

        // Layout changes for the new button at the bottom
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(btn_GenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE)) // Adjust position for the report button
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
                .addGap(40, 40, 40)
                .addComponent(btn_GenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)  // Add report button to layout
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

        btn_GenerateReport.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_GenerateReport.setBackground(new Color(138, 43, 226));  // Pick a color for the button
        btn_GenerateReport.setForeground(Color.WHITE);
        btn_GenerateReport.setFocusPainted(false);
        btn_GenerateReport.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_GenerateReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Action handler for the Generate Report button
    private void btn_GenerateReportActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Fetch user data from the API
            String usersResponse = ApiClient.getUsers();
            ObjectMapper objectMapper = new ObjectMapper();
            List<UserModel> users = objectMapper.readValue(usersResponse, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, UserModel.class));

            if (users == null || users.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No user data available to generate the report.");
                return;
            }

            // Prepare the data source for the report
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);

            // Load the report template (user_report.jrxml)
            InputStream reportStream = getClass().getResourceAsStream("/user_report.jrxml");
            if (reportStream == null) {
                JOptionPane.showMessageDialog(this, "Report template not found.");
                return;
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill the report with the data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Display the report in a viewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage());
        }
    }

    private void btn_SwingQuestActionPerformed(java.awt.event.ActionEvent evt) {
        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions available.");
            return;
        }

        try {
            Collections.shuffle(questions);  // Randomize the order of questions

            List<QuestionModel> selectedQuestions;
            if (questions.size() > 5) {
                selectedQuestions = new ArrayList<>(questions.subList(0, 5));  // Take the first 5 questions
            } else {
                selectedQuestions = new ArrayList<>(questions);  // Use all if fewer than 5 questions
            }

            QuizFrame quizFrame = new QuizFrame(selectedQuestions);
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
        HostQuestionsFrame hqf = new HostQuestionsFrame();
        hqf.frame.setVisible(true);  
        hqf.frame.setLocationRelativeTo(null);

        this.dispose();
    }

    private void btn_ModifyQuestionsActionPerformed(java.awt.event.ActionEvent evt) {
        ModifyQuestionsFrame modifyFrame = new ModifyQuestionsFrame();
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setVisible(true);
        this.dispose();
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
    private javax.swing.JButton btn_GenerateReport;  // Declare the new button
    private javax.swing.JLabel jLabel1;
}
