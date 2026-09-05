import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePlay1 extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private boolean paused = false;
    private boolean gameStarted = false;

    private int score = 0;
    private int highScore = 0;
    private int lives = 3;
    private int level = 1;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;

    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator1 map;

    public GamePlay1() {

        map = new MapGenerator1(3,7);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay,this);
        timer.start();
    }

    @Override
    public void paint(Graphics g){

        super.paint(g);

        if(!gameStarted){

            g.setColor(Color.BLACK);
            g.fillRect(0,0,700,600);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial",Font.BOLD,38));
            g.drawString("BRICK BREAKER DELUXE",120,180);

            g.setFont(new Font("Arial",Font.PLAIN,24));

            g.drawString("Press SPACE to Start",190,260);
            g.drawString("Arrow Keys : Move Paddle",170,310);
            g.drawString("P : Pause",170,350);
            g.drawString("R : Resume",170,390);

            return;
        }

        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        map.draw((Graphics2D)g);

        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,18));

        g.drawString("Score : "+score,520,25);
        g.drawString("Lives : "+lives,20,25);
        g.drawString("Level : "+level,260,25);
        g.drawString("High : "+highScore,500,50);

        g.setColor(Color.GREEN);
        g.fillRect(playerX,550,100,8);

        g.setColor(Color.WHITE);
        g.fillOval(ballPosX,ballPosY,20,20);

        if(paused){

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial",Font.BOLD,30));
            g.drawString("GAME PAUSED",210,300);
        }        // WIN
        if (totalBricks <= 0) {
		SoundPlayer.play("Sounds/Win.wav");
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("LEVEL COMPLETED!", 180, 260);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press ENTER for Next Level", 180, 310);

            if (score > highScore)
                highScore = score;
        }

        // GAME OVER
        if (lives == 0) {
		SoundPlayer.play("Sounds/Gameover.wav");
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER", 220, 260);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press ENTER to Restart", 200, 310);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play && !paused) {

            // Paddle Collision
            if (new Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new Rectangle(playerX, 550, 100, 8))) {

                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {

                for (int j = 0; j < map.map[0].length; j++) {

                    if (map.map[i][j] > 0) {

                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect =
                                new Rectangle(brickX, brickY,
                                        brickWidth, brickHeight);

                        Rectangle ballRect =
                                new Rectangle(ballPosX, ballPosY,
                                        20, 20);

                        if (ballRect.intersects(brickRect)) {

                            map.setBrickValue(0, i, j);

                            totalBricks--;
                            score += 10;
				SoundPlayer.play("Sounds/brick.wav");

                            if (score > highScore)
                                highScore = score;

                            if (ballPosX + 19 <= brickRect.x
                                    || ballPosX + 1 >= brickRect.x + brickRect.width)

                                ballXdir = -ballXdir;
                            else
                                ballYdir = -ballYdir;
				SoundPlayer.play("Sounds/paddle.wav");

                            break A;
                        }
                    }
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if (ballPosX < 0)
                ballXdir = -ballXdir;

            if (ballPosY < 0)
                ballYdir = -ballYdir;

            if (ballPosX > 670)
                ballXdir = -ballXdir;

            if (ballPosY > 570) {

                lives--;

                if (lives > 0) {

                    play = false;

                    ballPosX = 120;
                    ballPosY = 350;

                    ballXdir = -1;
                    ballYdir = -2;

                    playerX = 310;
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            gameStarted = true;
            play = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            if (!paused) {

                play = true;

                playerX += 20;

                if (playerX > 590)
                    playerX = 590;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            if (!paused) {

                play = true;

                playerX -= 20;

                if (playerX < 10)
                    playerX = 10;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P)
            paused = true;

        if (e.getKeyCode() == KeyEvent.VK_R)
            paused = false;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (lives == 0) {
                lives = 3;
                level = 1;
                score = 0;
            } else if (totalBricks == 0) {

                level++;

                if (delay > 3)
                    delay--;

                timer.setDelay(delay);
            }

            totalBricks = 21;

            ballPosX = 120;
            ballPosY = 350;

            ballXdir = -1;
            ballYdir = -2;

            playerX = 310;

            map = new MapGenerator1(3, 7);

            play = true;
            paused = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}