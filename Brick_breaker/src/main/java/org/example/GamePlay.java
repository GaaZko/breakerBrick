package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int total_bricks = 21;
    private Timer timer;
    private int delay = 9;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private MapGenerator mapGenerator;

    public GamePlay() {
        mapGenerator = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paintComponent(g);
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        mapGenerator.draw((Graphics2D)g);


        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(" " + score, 590,30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //The ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        if (total_bricks <= 0) {
            play = false;
            ballYDir = 0;
            ballXDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You won: " + score, 230,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press enter to restart", 230,350);
        }

        if(ballPosY > 570){
            play = false;
            ballYDir = 0;
            ballXDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game over, Score: " + score, 230,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press enter to restart", 230,350);

        }

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYDir = -ballYDir;
            }

           A: for (int i = 0; i <mapGenerator.map.length ; i++) {
                for (int j = 0; j < mapGenerator.map[0].length; j++) {
                    if (mapGenerator.map[i][j]> 0){
                        int brickX = j* mapGenerator.brickWidth + 80;
                        int brickY = i* mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)){
                            mapGenerator.setBrickValue(0,i,j);
                            total_bricks --;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 > brickRect.x + brickRect.width ){
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }
                    }
                }
            }


                ballPosX += ballXDir;
            ballPosY += ballYDir;
            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }
        }


        repaint();
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                total_bricks = 21;
                mapGenerator = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
