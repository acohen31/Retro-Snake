import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    static final int DISPLAY_WIDTH = 600;
    static final int DISPLAY_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (DISPLAY_WIDTH * DISPLAY_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int [GAME_UNITS];
    final int y[] = new int [GAME_UNITS];
    int bodyParts = 6;
    int score;
    int foodX; // X coordinate of where food is located
    int foodY; // Y coordinate of where food is located
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    
    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        
    }
    public void startGame() {
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running) {
            g.setColor(Color.yellow);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
            for(int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.red);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }            
        }
        else gameOver(g);
    }
    public void newFood() {
        foodX = random.nextInt((int)(DISPLAY_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        foodY = random.nextInt((int)(DISPLAY_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction) {
        case 'U':
            y[0] -= UNIT_SIZE;
            break;
        case 'D':
            y[0] += UNIT_SIZE;
            break;
        case 'L':
            x[0] -= UNIT_SIZE;
            break;
        case 'R':
            x[0] += UNIT_SIZE;
            break;
        }
    }
    public void checkFood() {
        if(x[0] == foodX && y[0] == foodY) {
            bodyParts++;
            score++;
            newFood();
        }
    }
    public void checkCollisions() {
        // if the head collide with the body
        for(int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // if head touches left boundary
        if(x[0] < 0) {
            running = false;
        }
        // if head touches right boundary
        if(x[0] > DISPLAY_WIDTH) {
            running = false;
        }
        // if head touches top boundary
        if(y[0] < 0) {
            running = false;
        }
        // if head touches bottom boundary
        if(y[0] > DISPLAY_HEIGHT) {
            running = false;
        }
        if(!running) timer.stop();
    }
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (DISPLAY_WIDTH - metrics.stringWidth("Game Over!"))/2, DISPLAY_WIDTH/2);
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (DISPLAY_WIDTH - metrics2.stringWidth("Score: " + score))/2, g.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
        
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(direction != 'R') {
                    direction = 'L';                    
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(direction != 'L') {
                    direction = 'R';                    
                }
                break;
            case KeyEvent.VK_UP:
                if(direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(direction != 'U') {
                    direction = 'D';
                }
                break;
            }
        }
    }

}
