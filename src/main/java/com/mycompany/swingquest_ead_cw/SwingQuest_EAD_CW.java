/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.swingquest_ead_cw;

import com.mycompany.swingquest_ead_cw.SwingQuestGame.SwingQuest;
import javax.swing.JFrame;

/**
 *
 * @author Lihindu Perera
 */
public class SwingQuest_EAD_CW {

    
    public static void main(String[] args) {
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

        SwingQuest swingGame = new SwingQuest();
        game.add(swingGame);
        game.pack();
        swingGame.requestFocus();
        game.setVisible(true);
    }
}
