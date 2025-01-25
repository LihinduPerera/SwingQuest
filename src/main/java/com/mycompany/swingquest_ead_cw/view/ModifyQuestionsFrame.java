package com.mycompany.swingquest_ead_cw.view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.swingquest_ead_cw.ApiClient;
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
    private JButton saveButton, deleteButton, addButton;
    private JScrollPane scrollPane;

    public ModifyQuestionsFrame() {
        setTitle("Modify Questions");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBackground(new Color(239, 240, 245));

        // Question input fields
        panel.add(new JLabel("Question:"));
        questionField = new JTextField();
        panel.add(questionField);

        panel.add(new JLabel("Answer 1:"));
        answer1Field = new JTextField();
        panel.add(answer1Field);

        panel.add(new JLabel("Answer 2:"));
        answer2Field = new JTextField();
        panel.add(answer2Field);

        panel.add(new JLabel("Answer 3:"));
        answer3Field = new JTextField();
        panel.add(answer3Field);

        panel.add(new JLabel("Answer 4:"));
        answer4Field = new JTextField();
        panel.add(answer4Field);

        panel.add(new JLabel("Correct Answer:"));
        correctAnswerComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        panel.add(correctAnswerComboBox);

        add(panel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add New Question");
        saveButton = new JButton("Save Question");
        deleteButton = new JButton("Delete Question");

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Table to display questions
        String[] columns = {"ID", "Question", "Answer 1", "Answer 2", "Answer 3", "Answer 4", "Correct Answer"};
        questionTable = new JTable();
        scrollPane = new JScrollPane(questionTable);
        add(scrollPane, BorderLayout.CENTER);

        loadQuestions();

        // Add listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewQuestion();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestion();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
            }
        });
    }

    private void loadQuestions() {
        try {
            String jsonResponse = ApiClient.getQuestions();
            // Assuming you have a utility method to convert the JSON response into a List of QuestionModel objects
            questions = new ObjectMapper().readValue(jsonResponse, new TypeReference<List<QuestionModel>>(){});
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
            loadQuestions(); // Reload the question list
            JOptionPane.showMessageDialog(this, "Question added successfully!");
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

            try {
                String response = ApiClient.updateQuestion(questionId, questionText, answer1, answer2, answer3, answer4, correctAnswer);
                loadQuestions(); // Reload the question list
                JOptionPane.showMessageDialog(this, "Question updated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update question.", "Error", JOptionPane.ERROR_MESSAGE);
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
                loadQuestions(); // Reload the question list
                JOptionPane.showMessageDialog(this, "Question deleted successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete question.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a question to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
