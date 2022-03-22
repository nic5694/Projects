package snakegame.snakegame2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts=1;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 39;
    boolean running = false;
    Timer timer;
    Random random;


    public GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();


    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running) {
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("INK FREE",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten*5, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }

    }
    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 37:
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 38:
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 39:
                x[0]=x[0]+UNIT_SIZE;
                break;
            case 40:
                y[0]=y[0]+UNIT_SIZE;
                break;

        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        for(int i=bodyParts;i>0;i--){
            if((x[0] == x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        if(x[0]<0)
            running=false;
        if(x[0]>SCREEN_WIDTH)
            running=false;
        if(y[0]<0)
            running=false;
        if(y[0]>SCREEN_HEIGHT)
            running=false;
        if(!running)
            timer.stop();

    }
    public void gameOver(Graphics g){g.setColor(Color.RED);
        g.setFont(new Font("INK FREE",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten*5, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
        g.setColor(Color.RED);
        g.setFont(new Font("INK FREE",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over Loser", (SCREEN_WIDTH-metrics2.stringWidth("Game Over Loser"))/2,
                SCREEN_HEIGHT/2);



    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case 37:
                    if(direction!=39)
                        direction=37;
                    break;
                case 38:
                    if(direction!=40)
                        direction=38;
                    break;
                case 39:
                    if(direction!=37)
                        direction=39;
                    break;
                case 40:
                    if(direction!=38)
                        direction=40;
                    break;
            }
        }
    }
}
