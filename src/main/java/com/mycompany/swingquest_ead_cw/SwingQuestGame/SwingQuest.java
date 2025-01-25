/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swingquest_ead_cw.SwingQuestGame;
import com.mycompany.swingquest_ead_cw.MainMenuFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author Lihindu Perera
 */

public class SwingQuest extends JPanel implements ActionListener , KeyListener{
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startingPointX;
        int startingPointY;

        char direction = 'U';
        int velocityX = 0;
        int velocityY = 0;

        Block (Image image, int x , int y , int width , int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startingPointX = x;
            this.startingPointY = y;
        }
        void updateDirection(char direction) {
            char previousDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = previousDirection;
                    updateVelocity();
                }
            }
        }
        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -tileSize/4;
            }
            else if (this.direction == 'D'){
                this.velocityX = 0;
                this.velocityY = tileSize/4;
            }
            else if (this.direction == 'L') {
                this.velocityX = -tileSize/4;
                this.velocityY = 0;
            }
            else if (this.direction == 'R') {
                this.velocityX = tileSize/4;
                this.velocityY = 0;
            }
        }

        void reset() {
            this.x = this.startingPointX;
            this.y = this.startingPointY;
        }
    }

    int rowCount = 21;
    int columnCount = 19;
    int tileSize = 32;
    int boardWidth = columnCount * tileSize;
    int boardHeight = rowCount * tileSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image redGhostImage;
    private Image whiteGhostImage;
    private Image yellowGhostImage;

    private Image playerLeftImage;
    private Image playerRightImage;

    private Image gemImage;

    Timer gameLoop;
    char[] directions = {'U','D','L','R'}; //for ghosts onlyyy
    Random random = new Random();
    int gemScore = 0;
    int lives = 0;
    boolean gameOver = false;
    
    private String[] tileMap = {
    "XXXXXXXXXXXXXXXXXXX",
    "X        X        X",
    "X XX XXX X XXX XX X",
    "X                 X",
    "X XX X XXXXX X XX X",
    "X    X   X   X    X",
    "XXXX XXXX XXXX XXXX",
    "OOOX X       X XOOO",
    "XXXX X XXrXX X XXXX",
    "X       bwy       X",
    "XXXX X XXXXX X XXXX",
    "OOOX X       X XOOO",
    "XXXX X XXXXX X XXXX",
    "X        X        X",
    "X XX XXX X XXX XX X",
    "X  X     P     X  X",
    "XX X X XXXXX X X XX",
    "X    X   X   X    X",
    "X XXXXXX X XXXXXX X",
    "X                 X",
    "XXXXXXXXXXXXXXXXXXX"
};
    HashSet <Block> walls;
    HashSet <Block> gems;
    HashSet <Block> ghosts;
    Block player;

    public SwingQuest (int livesYouGot) {
        lives += livesYouGot;
        setPreferredSize(new Dimension(boardWidth , boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/BlueGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/RedGhost.png")).getImage();
        whiteGhostImage = new ImageIcon(getClass().getResource("/WhiteGhost.png")).getImage();
        yellowGhostImage = new ImageIcon(getClass().getResource("/YellowGhost.png")).getImage();

        playerLeftImage = new ImageIcon(getClass().getResource("/GirlLeft.png")).getImage();
        playerRightImage = new ImageIcon(getClass().getResource("/GirlRight.png")).getImage();

        gemImage = new ImageIcon(getClass().getResource("/Gem.png")).getImage();

        loadMap();
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }

        gameLoop = new Timer (50, this); //20fps (1000/50)
        gameLoop.start();

        System.out.println(walls.size());
        System.out.println(ghosts.size());
        System.out.println(gems.size());
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        gems = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r=0; r<rowCount; r++) {
            for (int c=0; c<columnCount; c++) {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;

                if (tileMapChar == 'X') {
                    Block wall = new Block(wallImage, x , y , tileSize , tileSize);
                    walls.add(wall);
                }
                else if (tileMapChar == 'b') {
                    Block ghost = new Block (blueGhostImage , x ,y ,tileSize , tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'w') {
                    Block ghost = new Block (whiteGhostImage, x ,y ,tileSize , tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'y') {
                    Block ghost = new Block (yellowGhostImage , x ,y ,tileSize , tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'r') {
                    Block ghost = new Block (redGhostImage , x ,y ,tileSize , tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'P') {
                    player = new Block(playerRightImage,x,y,tileSize,tileSize);
                }
                else if (tileMapChar == ' ') {
                    Block gem = new Block (gemImage,x+10,y+10,13,13);
                    gems.add(gem);
                }
            }
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g) {
        g.drawImage(player.image, player.x, player.y, player.width, player.height, null);
    
        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x , ghost.y , ghost.width , ghost.height, null);
        }
    
        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
    
        for (Block gem : gems) {
            g.drawImage(gem.image, gem.x, gem.y, gem.width, gem.height, null);
        }
    
        g.setFont(new Font("Arial", Font.PLAIN, 18));
    
        // Determine the width and height of the text to create a box around it
        String scoreText;
        if (gameOver) {
            scoreText = "Game Over: " + String.valueOf(gemScore);
        } else {
            scoreText = "x" + String.valueOf(lives) + " Score: " + String.valueOf(gemScore);
        }
    
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(scoreText);
        int textHeight = metrics.getHeight();
    
        // Draw a white background rectangle behind the text
        int boxX = tileSize / 2 - 10; // Padding around the text
        int boxY = tileSize / 2 - textHeight / 2 - 5; // Vertical centering of the box
        int boxWidth = textWidth + 20; // Add padding to the box width
        int boxHeight = textHeight + 10; // Add padding to the box height
        g.setColor(Color.WHITE);
        g.fillRect(boxX, boxY, boxWidth, boxHeight);
    
        // Adjust vertical position to ensure text is centered within the box
        int textY = tileSize / 2 + textHeight / 4; // Adjust Y position for centering
    
        // Draw the score text over the white box
        g.setColor(Color.BLACK); // Text color (black)
        g.drawString(scoreText, tileSize / 2, textY);
    }
    

    public void move() {
        player.x += player.velocityX;
        player.y += player.velocityY;
        
        //check wall collisions
        for (Block wall : walls) {
            if (collision(player, wall)) {
                player.x -= player.velocityX;
                player.y -= player.velocityY;
                break;
            }
        }

        for (Block ghost : ghosts) {
            if (collision(ghost, player)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }

            if (ghost.y == tileSize*9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }

        Block gemCollected = null;
        for (Block gem : gems) {
            if (collision(player, gem)) {
                gemCollected = gem;
                gemScore ++;
            }
        }
        gems.remove(gemCollected);

        if (gems.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }

    public boolean collision(Block a, Block b){
        return  a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    public void resetPositions() {
        player.reset();
        player.velocityX = 0;
        player.velocityY = 0;
        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            JOptionPane.showMessageDialog(this, "<html><div style='text-align: center; font-size: 18px;'>Game Over!!!!<br> you got "+gemScore+" of Gems</div></html>", "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        MainMenuFrame mainMenu2 = new MainMenuFrame();
        mainMenu2.setLocationRelativeTo(null);
        mainMenu2.setResizable(false);
        mainMenu2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu2.setVisible(true);
        
        Window window = SwingUtilities.windowForComponent(this);
        if (window != null) {
            window.dispose();
        }
        
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            loadMap();
            resetPositions();
            lives = 3;
            gemScore = 0;
            gameOver = false;
            gameLoop.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.updateDirection('U');
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.updateDirection('D');
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.updateDirection('L');
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.updateDirection('R');
        }

        if (player.direction == 'U') {
            player.image = playerLeftImage;
        }
        else if (player.direction == 'D') {
            player.image = playerRightImage;
        }
        else if (player.direction == 'L') {
            player.image = playerLeftImage;
        }
        else if (player.direction == 'R') {
            player.image = playerRightImage;
        }
    }
}

