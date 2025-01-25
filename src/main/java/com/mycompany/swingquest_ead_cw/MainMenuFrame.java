package com.mycompany.swingquest_ead_cw;

import com.mycompany.swingquest_ead_cw.SwingQuestGame.SwingQuest;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends javax.swing.JFrame {

    public MainMenuFrame() {
        initComponents();
        customizeUI();
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
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void customizeUI() {
        // Set a modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label customization
        jLabel1.setFont(new Font("Verdana", Font.BOLD, 24));
        jLabel1.setForeground(new Color(50, 50, 150));

        // Buttons customization
        btn_QandA.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_QandA.setBackground(new Color(70, 130, 180));
        btn_QandA.setForeground(Color.WHITE);
        btn_QandA.setFocusPainted(false);
        btn_QandA.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_QandA.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn_ModifyQuestions.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_ModifyQuestions.setBackground(new Color(34, 139, 34));
        btn_ModifyQuestions.setForeground(Color.WHITE);
        btn_ModifyQuestions.setFocusPainted(false);
        btn_ModifyQuestions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_ModifyQuestions.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn_SwingQuest.setFont(new Font("Arial", Font.PLAIN, 16));
        btn_SwingQuest.setBackground(new Color(255, 165, 0));
        btn_SwingQuest.setForeground(Color.WHITE);
        btn_SwingQuest.setFocusPainted(false);
        btn_SwingQuest.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn_SwingQuest.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse hover effect to buttons
        btn_QandA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_QandA.setBackground(new Color(100, 149, 237)); // Lighter blue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_QandA.setBackground(new Color(70, 130, 180)); // Original color
            }
        });

        btn_SwingQuest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SwingQuest.setBackground(new Color(255, 140, 0)); // Lighter orange
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SwingQuest.setBackground(new Color(255, 165, 0)); // Original color
            }
        });

        btn_ModifyQuestions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ModifyQuestions.setBackground(new Color(50, 205, 50)); // Lighter green
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ModifyQuestions.setBackground(new Color(34, 139, 34)); // Original color
            }
        });
    }

    private void btn_SwingQuestActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame game = new JFrame("SwingQuest");
        game.setSize(boardWidth , boardHeight);
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingQuest swingGame = new SwingQuest(2);
        game.add(swingGame);
        game.pack();
        swingGame.requestFocus();
        game.setVisible(true);
    }

    private void btn_QandAActionPerformed(java.awt.event.ActionEvent evt) {
        // Placeholder for Q&A button action
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenuFrame().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton btn_ModifyQuestions;
    private javax.swing.JButton btn_QandA;
    private javax.swing.JButton btn_SwingQuest;
    private javax.swing.JLabel jLabel1;
}
