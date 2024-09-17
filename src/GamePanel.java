import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1920;
    static final int SCREEN_HEIGHT = 1080;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT/UNIT_SIZE);
    static final int DELAY = 40;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    JButton restartButton;
    JButton closeButton;

    //Setting up game-panel before starting the game
    GamePanel(){
        random = new Random();
        restartButton = new JButton("Restart");
        closeButton = new JButton("Close");
        this.add(closeButton);
        this.add(restartButton);
        this.setLayout(null);
        closeButton.setBounds(SCREEN_WIDTH/2-75,SCREEN_HEIGHT/2+200,150,50);
        closeButton.setVisible(false);
        restartButton.setBounds(SCREEN_WIDTH/2-75,SCREEN_HEIGHT/2+100,150,50);
        restartButton.setVisible(false);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        //Action listener for restart button
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeButton.setVisible(false);
                System.exit(0);
            }
        });
        startGame();
    }
    //Game running function, starts the game and calls all functions
    public void startGame(){
        closeButton.setVisible(false);
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }
    //Corresponds to the graphics function
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    //Draws all the graphics
    public void draw(Graphics g){
        if(running){
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for(int i = 0;i<bodyParts;i++){
                if(i==0){
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else if (i==(bodyParts-1)) {
                    g.setColor(Color.RED);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    //g.setColor(new Color(45,180,0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(STR."Score: \{appleEaten}",(SCREEN_WIDTH-metrics.stringWidth(STR."Score: \{appleEaten}"))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    //Creates new apples each time an apple is eaten by the snake
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    //Moves the snake, responds to the arrow keys
    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }
    //Checks whether an apple has been eaten
    public void checkApple(){
            if((x[0] == appleX) && (y[0] == appleY)) {
                bodyParts++;
                appleEaten++;
                newApple();
            }
    }
    //Checks whether the snake has collided with a wall, or with its own body
    public void checkCollision(){
        //Checks if the head collides with the body
        for(int i = bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        //Checks if the head touches the left border
        if(x[0]<0){
            running=false;
        }
        //Checks if the head touches the right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //Checks if head touches top border
        if(y[0]<0){
            running=false;
        }
        //Checks if the head touches the bottom border
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running)
            timer.stop();
    }
    //Game over screen, print "Game Over", and the user's score
    public void gameOver(Graphics g){
        //Game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 150));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!",(SCREEN_WIDTH-metrics.stringWidth("Game Over!"))/2, SCREEN_HEIGHT/2);
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 100));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString(STR."Score: \{appleEaten}",(SCREEN_WIDTH-metrics1.stringWidth(STR."Score: \{appleEaten}"))/2, g.getFont().getSize());
        restartButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        restartButton.setText("Restart");
        restartButton.setForeground(Color.black);
        restartButton.setBackground(Color.red);
        restartButton.setBorder(BorderFactory.createLineBorder(Color.black,0));
        restartButton.setFocusPainted(false);
        restartButton.setVisible(true);
        closeButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        closeButton.setText("Exit");
        closeButton.setForeground(Color.black);
        closeButton.setBackground(Color.red);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.black,0));
        closeButton.setFocusPainted(false);
        closeButton.setVisible(true);
    }
    //Checks every action that happens in real-time, and repaints the board
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    //Restart game if player failed
    public void restartGame(){
        timer.stop();
        bodyParts=6;
        appleEaten=0;
        direction='R';
        running=false;
        for(int i =0 ; i<bodyParts;i++){
            x[i]=0;
            y[i]=0;
        }
        newApple();
        restartButton.setVisible(false);
        startGame();
    }
    //A class dedicated to user-input, which gets translated into directions
    public class  MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                        direction='L';
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D')
                        direction='U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                        direction='D';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                        direction='R';
                    break;
            }
        }
    }
}