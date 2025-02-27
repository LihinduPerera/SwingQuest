package com.mycompany.swingquest_ead_cw.view;

import com.mycompany.swingquest_ead_cw.SwingQuestGame.SwingQuest;
import com.mycompany.swingquest_ead_cw.model.QuestionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizFrame extends JFrame {
    private int currentQuestionIndex = 0;
    private int correctAnswerCount = 0;
    private List<QuestionModel> questions;

    private JLabel questionLabel;
    private JRadioButton answer1Button, answer2Button, answer3Button, answer4Button;
    private ButtonGroup answerGroup;
    private JButton nextButton;

    public QuizFrame(List<QuestionModel> questions) {
        this.questions = questions;

        setTitle("Quiz App");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 20));

        Color backgroundColor = new Color(239, 240, 245);
        getContentPane().setBackground(backgroundColor);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setForeground(new Color(40, 40, 40));
        questionLabel.setPreferredSize(new Dimension(600, 70));
        contentPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));
        answersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        answersPanel.setBackground(backgroundColor);

        answer1Button = createAnswerButton(backgroundColor);
        answer2Button = createAnswerButton(backgroundColor);
        answer3Button = createAnswerButton(backgroundColor);
        answer4Button = createAnswerButton(backgroundColor);

        answerGroup = new ButtonGroup();
        answerGroup.add(answer1Button);
        answerGroup.add(answer2Button);
        answerGroup.add(answer3Button);
        answerGroup.add(answer4Button);

        answersPanel.add(answer1Button);
        answersPanel.add(answer2Button);
        answersPanel.add(answer3Button);
        answersPanel.add(answer4Button);

        contentPanel.add(answersPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(backgroundColor);
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Roboto", Font.PLAIN, 20));
        nextButton.setPreferredSize(new Dimension(220, 50));
        nextButton.setBackground(new Color(70, 130, 180));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        nextButton.setContentAreaFilled(false);
        nextButton.setOpaque(true);

        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(0, 123, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(70, 130, 180));
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    loadQuestion(currentQuestionIndex);
                } else {
                    showResultAndPlay();
                }
            }
        });

        footerPanel.add(nextButton, BorderLayout.CENTER);
        contentPanel.add(footerPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);

        loadQuestion(currentQuestionIndex);
    }

    private JRadioButton createAnswerButton(Color backgroundColor) {
        JRadioButton button = new JRadioButton();
        button.setFont(new Font("Roboto", Font.PLAIN, 20));
        button.setBackground(backgroundColor);
        button.setForeground(new Color(60, 60, 60));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(500, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 248, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private void loadQuestion(int index) {
        QuestionModel question = questions.get(index);
        questionLabel.setText("<html><div style='text-align: center;'>" + question.getQuestion() + "</div></html>");

        answer1Button.setText("<html><div style='text-align: center;'>" + question.getAnswer1() + "</div></html>");
        answer2Button.setText("<html><div style='text-align: center;'>" + question.getAnswer2() + "</div></html>");
        answer3Button.setText("<html><div style='text-align: center;'>" + question.getAnswer3() + "</div></html>");
        answer4Button.setText("<html><div style='text-align: center;'>" + question.getAnswer4() + "</div></html>");

        answerGroup.clearSelection();
    }

    private void checkAnswer() {
        QuestionModel question = questions.get(currentQuestionIndex);

        int selectedAnswer = -1;
        if (answer1Button.isSelected()) selectedAnswer = 1;
        else if (answer2Button.isSelected()) selectedAnswer = 2;
        else if (answer3Button.isSelected()) selectedAnswer = 3;
        else if (answer4Button.isSelected()) selectedAnswer = 4;

        if (selectedAnswer == question.getCorrectAnswer()) {
            correctAnswerCount++;
        }
    }

    private void showResultAndPlay() {
        int lives = 0;
        if (correctAnswerCount == 0) {
            lives = 1;
        } else {
            lives = correctAnswerCount;
        }

        JOptionPane.showMessageDialog(this, "<html><div style='text-align: center; font-size: 18px;'>Quiz finished!<br>You got " + correctAnswerCount + " of correct answes by facing " + questions.size() + " questions.<br> You got "+lives+" Lives to Play!!</div></html>", "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
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

        SwingQuest swingGame = new SwingQuest(lives);
        game.add(swingGame);
        game.pack();
        swingGame.requestFocus();
        game.setVisible(true);
    }
}
