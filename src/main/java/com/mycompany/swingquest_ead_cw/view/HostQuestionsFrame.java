package com.mycompany.swingquest_ead_cw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;
import com.mycompany.swingquest_ead_cw.ApiClient;
import com.mycompany.swingquest_ead_cw.MainMenuFrame;
import com.mycompany.swingquest_ead_cw.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HostQuestionsFrame {

    public JFrame frame;
    private JButton hostStateButton;
    private JButton backButton;
    private JTextArea userInfoArea;
    private boolean isHosting = false;
    
    private Timer userDataRefreshTimer;

    public HostQuestionsFrame() {
        // Initialize frame
        frame = new JFrame("Host Questions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title Label
        JLabel titleLabel = new JLabel("Host State and User Info", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Host State Button
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
                // Toggle the hosting state
                isHosting = !isHosting;

                // Update the button text based on the new hosting state
                hostStateButton.setText(isHosting ? "Stop Hosting" : "Start Hosting");

                if (!isHosting) {
                    userInfoArea.setText(""); // Clear user info when hosting is stopped
                }

                // Update the hosting status in the backend using SwingWorker
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        System.out.println("Updating hosting status to: " + isHosting);  // Debugging line
                        boolean success = ApiClient.updateHostingStatus(isHosting);  // Update status in backend

                        if (success) {
                            // After updating, refresh the hosting status and user data
                            refreshHostingStatus();
                            if (isHosting) {
                                refreshUserData();  // Refresh user data if hosting is active
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Called after the background task is done
                    }
                }.execute();
            }
        });

        // Back Button
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

        // User Info Area (TextArea)
        userInfoArea = new JTextArea();
        userInfoArea.setEditable(false);
        userInfoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfoArea.setBackground(new Color(245, 245, 245));
        userInfoArea.setForeground(Color.DARK_GRAY);
        userInfoArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        userInfoArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(userInfoArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top and Bottom Panels
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

        // Fetch the current hosting status on startup
        refreshHostingStatus();

        // Set up a timer to refresh user data automatically every 5 seconds
        userDataRefreshTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUserData();
            }
        });
        userDataRefreshTimer.start();
    }

    private void refreshUserData() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Fetch user data from the API
                String response = ApiClient.getUsers();
                ObjectMapper mapper = new ObjectMapper();
                UserModel[] users = mapper.readValue(response, UserModel[].class);

                if (users.length > 0) {
                    StringBuilder userInfo = new StringBuilder("Users Info:\n");
                    for (UserModel user : users) {
                        userInfo.append("Name: ").append(user.getName()).append("\n")
                                .append("Correct Answers Count: ").append(user.getCorrectAnswersCount()).append("\n\n");
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            userInfoArea.setText(userInfo.toString());
                        }
                    });
                }
                return null;
            }
        }.execute();
    }

    private void refreshHostingStatus() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Fetch the current hosting status from the backend
                boolean hostingStatus = ApiClient.getHostingStatus();  // Ensure this returns the correct boolean value

                // Debugging: Print to console to see if the status is fetched correctly
                System.out.println("Fetched hosting status: " + hostingStatus);

                // If the status has been updated correctly, update the UI button text
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Update isHosting with the fetched status
                        isHosting = hostingStatus;
                        hostStateButton.setText(isHosting ? "Stop Hosting" : "Start Hosting");
                    }
                });

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
