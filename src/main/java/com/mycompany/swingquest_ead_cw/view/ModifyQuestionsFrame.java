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

        styleButton(addButton);
        styleButton(saveButton);
        styleButton(deleteButton);

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

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

    private void loadQuestions() {
        try {
            String jsonResponse = ApiClient.getQuestions();
            questions = new ObjectMapper().readValue(jsonResponse, new TypeReference<List<QuestionModel>>() {});
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

            try {
                String response = ApiClient.updateQuestion(questionId, questionText, answer1, answer2, answer3, answer4, correctAnswer);
                loadQuestions();
                JOptionPane.showMessageDialog(this, "Question updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModifyQuestionsFrame frame = new ModifyQuestionsFrame();
            frame.setVisible(true);
        });
    }
}
