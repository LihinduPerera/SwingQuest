package com.mycompany.swingquest_ead_cw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.swingquest_ead_cw.ApiClient;
import com.mycompany.swingquest_ead_cw.MainMenuFrame;
import com.mycompany.swingquest_ead_cw.model.UserModel;

public class HostQuestionsFrame {
    public JFrame frame;
    private JButton hostStateButton;
    private JButton backButton;
    private JTextArea userInfoArea;
    private boolean isHosting = false;

    public HostQuestionsFrame() {
        frame = new JFrame("Host Questions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Host State and User Info", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        hostStateButton = new JButton("Start Hosting");
        hostStateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        hostStateButton.setBackground(new Color(56, 150, 255));
        hostStateButton.setForeground(Color.BLACK);
        hostStateButton.setFocusPainted(false);
        hostStateButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        hostStateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        hostStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isHosting = !isHosting;
                if (isHosting) {
                    hostStateButton.setText("Stop Hosting");
                } else {
                    hostStateButton.setText("Start Hosting");
                    userInfoArea.setText("");
                }
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        ApiClient.updateHostingStatus(isHosting);
                        return null;
                    }

                    @Override
                    protected void done() {
                        if (isHosting) {
                            refreshUserData();
                        }
                    }
                }.execute();
            }
        });

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(220, 80, 80));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuFrame mainMenu = new MainMenuFrame();
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true);
                frame.dispose();
            }
        });

        userInfoArea = new JTextArea();
        userInfoArea.setEditable(false);
        userInfoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfoArea.setBackground(new Color(245, 245, 245));
        userInfoArea.setForeground(Color.DARK_GRAY);
        userInfoArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        userInfoArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(userInfoArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(hostStateButton, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void refreshUserData() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                String response = ApiClient.getUsers();
                ObjectMapper mapper = new ObjectMapper();
                UserModel[] users = mapper.readValue(response, UserModel[].class);

                if (users.length > 0) {
                    UserModel user = users[0];
                    String userInfo = "User Info:\n" + 
                                      "Name: " + user.getName() + "\n" +
                                      "Correct Answers Count: " + user.getCorrectAnswersCount();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            userInfoArea.setText(userInfo);
                        }
                    });
                }

                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HostQuestionsFrame();
            }
        });
    }
}
