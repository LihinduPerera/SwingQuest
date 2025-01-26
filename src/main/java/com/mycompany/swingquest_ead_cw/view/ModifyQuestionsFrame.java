package com.mycompany.swingquest_ead_cw.view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.swingquest_ead_cw.ApiClient;
import com.mycompany.swingquest_ead_cw.MainMenuFrame;
import com.mycompany.swingquest_ead_cw.model.QuestionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ModifyQuestionsFrame extends JFrame {

    private List<QuestionModel> questions;
    private JTable questionTable;
    private JTextField questionField, answer1Field, answer2Field, answer3Field, answer4Field;
    private JComboBox<Integer> correctAnswerComboBox;
    private JButton saveButton, deleteButton, addButton, backButton, clearButton;
    private JScrollPane scrollPane;

    public ModifyQuestionsFrame() {
        setTitle("Modify Questions");
        setSize(750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Modify Question Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(20));

        panel.add(createLabeledField("Question:", questionField = new JTextField(20)));
        panel.add(createLabeledField("Answer 1:", answer1Field = new JTextField(20)));
        panel.add(createLabeledField("Answer 2:", answer2Field = new JTextField(20)));
        panel.add(createLabeledField("Answer 3:", answer3Field = new JTextField(20)));
        panel.add(createLabeledField("Answer 4:", answer4Field = new JTextField(20)));

        JPanel correctAnswerPanel = new JPanel();
        correctAnswerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        correctAnswerPanel.setBackground(new Color(255, 255, 255));
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        correctAnswerPanel.add(correctAnswerLabel);

        correctAnswerComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        correctAnswerComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        correctAnswerPanel.add(correctAnswerComboBox);

        panel.add(correctAnswerPanel);

        add(panel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(255, 255, 255));

        addButton = new JButton("Add New Question");
        saveButton = new JButton("Save Question");
        deleteButton = new JButton("Delete Question");
        backButton = new JButton("Back");
        clearButton = new JButton("Clear");

        styleButton(addButton);
        styleButton(saveButton);
        styleButton(deleteButton);
        styleButton(backButton);
        styleButton(clearButton);  // Styling the Clear button

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);  // Adding the Clear button
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"ID", "Question", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "Correct Answer"};
        questionTable = new JTable();
        questionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        questionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionTable.setRowHeight(30);
        questionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        questionTable.getTableHeader().setForeground(new Color(52, 152, 219));
        scrollPane = new JScrollPane(questionTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        add(scrollPane, BorderLayout.CENTER);

        loadQuestions();

        // Add a listener to populate fields when a row is selected
        questionTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = questionTable.getSelectedRow();
            if (selectedRow != -1) {
                // Populate the fields with the selected question's details
                questionField.setText((String) questionTable.getValueAt(selectedRow, 1));
                answer1Field.setText((String) questionTable.getValueAt(selectedRow, 2));
                answer2Field.setText((String) questionTable.getValueAt(selectedRow, 3));
                answer3Field.setText((String) questionTable.getValueAt(selectedRow, 4));
                answer4Field.setText((String) questionTable.getValueAt(selectedRow, 5));
                correctAnswerComboBox.setSelectedItem(Integer.parseInt((String) questionTable.getValueAt(selectedRow, 6)));
            }
        });

        addButton.addActionListener(e -> addNewQuestion());

        saveButton.addActionListener(e -> {
            updateQuestion();
            // Show success message after saving the question
            JOptionPane.showMessageDialog(ModifyQuestionsFrame.this, "Question saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        deleteButton.addActionListener(e -> deleteQuestion());

        backButton.addActionListener(e -> goBack());

        clearButton.addActionListener(e -> clearFields());  // Add action listener for the Clear button
    }

    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(255, 255, 255));

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(jLabel);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(field);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(52, 152, 219));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // Method to clear all text fields
    private void clearFields() {
        questionField.setText("");
        answer1Field.setText("");
        answer2Field.setText("");
        answer3Field.setText("");
        answer4Field.setText("");
        correctAnswerComboBox.setSelectedIndex(0);  // Resetting combo box to the default value (1)
    }

    private void loadQuestions() {
        try {
            String jsonResponse = ApiClient.getQuestions();
            questions = new ObjectMapper().readValue(jsonResponse, new TypeReference<List<QuestionModel>>() {
            });
            String[][] data = new String[questions.size()][7];
            for (int i = 0; i < questions.size(); i++) {
                QuestionModel question = questions.get(i);
                data[i] = new String[]{
                        String.valueOf(question.getQuestionId()),
                        question.getQuestion(),
                        question.getAnswer1(),
                        question.getAnswer2(),
                        question.getAnswer3(),
                        question.getAnswer4(),
                        String.valueOf(question.getCorrectAnswer())
                };
            }
            questionTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Question", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "Correct Answer"}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewQuestion() {
        String questionText = questionField.getText();
        String answer1 = answer1Field.getText();
        String answer2 = answer2Field.getText();
        String answer3 = answer3Field.getText();
        String answer4 = answer4Field.getText();
        int correctAnswer = (int) correctAnswerComboBox.getSelectedItem();

        try {
            String response = ApiClient.createQuestion(questionText, answer1, answer2, answer3, answer4, correctAnswer);
            loadQuestions();
            JOptionPane.showMessageDialog(this, "Question added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add question.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow != -1) {
            int questionId = Integer.parseInt(questionTable.getValueAt(selectedRow, 0).toString());
            String questionText = questionField.getText();
            String answer1 = answer1Field.getText();
            String answer2 = answer2Field.getText();
            String answer3 = answer3Field.getText();
            String answer4 = answer4Field.getText();
            int correctAnswer = (int) correctAnswerComboBox.getSelectedItem();

            // Show the success message immediately
            JOptionPane.showMessageDialog(this, "Question updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            try {
                // Perform the API call to update the question
                ApiClient.updateQuestion(questionId, questionText, answer1, answer2, answer3, answer4, correctAnswer);

                // Reload the questions to refresh the table (optional)
                loadQuestions();

            } catch (IOException e) {
                // Handle errors from the API call
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update question. An error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a question to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow != -1) {
            int questionId = Integer.parseInt(questionTable.getValueAt(selectedRow, 0).toString());
            try {
                String response = ApiClient.deleteQuestion(questionId);
                loadQuestions();
                JOptionPane.showMessageDialog(this, "Question deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete question.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a question to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        MainMenuFrame mainMenuFrame = new MainMenuFrame();
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setVisible(true);

        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModifyQuestionsFrame frame = new ModifyQuestionsFrame();
            frame.setVisible(true);
        });
    }
}
