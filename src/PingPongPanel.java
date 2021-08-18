import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PingPongPanel extends JPanel implements ActionListener {
    private final int WINDOWS_WIDTH = 600;
    private final int WINDOWS_HEIGHT = 600;
    private Timer time;
    private int DELEY=1;
    private  int lengthDesk =150;
    private int xDeskPlayerOne =WINDOWS_WIDTH/2- lengthDesk /2;
    private int yDeskPlayerOne = WINDOWS_HEIGHT-50;
    private int xDeskPlayerTwo =WINDOWS_WIDTH/2- lengthDesk /2;
    private int yDeskPlayerTwo = 50;
    private int xBall=WINDOWS_HEIGHT/2;
    private int yBall=WINDOWS_WIDTH/2;
    private int sizeBall=15;
    private int heightDesk=15;
    private int xSpeedBall =0;
    private int ySpeedBall =5;
    private int upAndDownWay =1;
    private int score = 0;
    private boolean isPlaying = true;

    PingPongPanel(){
        setPreferredSize(new Dimension(WINDOWS_WIDTH,WINDOWS_HEIGHT));
        setSize(new Dimension(WINDOWS_WIDTH,WINDOWS_HEIGHT));
        setFocusable(true);
        addKeyListener(new MyAdapterKeyOne());
        addMouseListener(new MyMouseAdapter());
        setBackground(Color.BLACK);
        startGame();
    }

    private void startGame() {
        time = new Timer(DELEY,this);
        time.start();
    }

    private void moveBall() {
        if(xBall>= xDeskPlayerOne &&xBall<= xDeskPlayerOne +lengthDesk && yBall-sizeBall> yDeskPlayerOne -heightDesk-3 &&yBall-sizeBall< yDeskPlayerOne -heightDesk+3) {
            upAndDownWay=-1;
            score++;
            System.out.println(score);
        }
        theWayOfMovingBall();
    }

    private void theWayOfMovingBall() {

        deskHit(yDeskPlayerOne,xDeskPlayerOne,-1,1);
        deskHit(yDeskPlayerTwo,xDeskPlayerTwo,1, -1);

        if(xBall-sizeBall/2<0){ //odbija od lewej ściany
            xSpeedBall*=-1;
        }else if(xBall+sizeBall/2>WINDOWS_WIDTH){ //odbija od prawej ściany
            xSpeedBall*=-1;
        }
        if(yBall<0) isPlaying =false;
        if(yBall>WINDOWS_HEIGHT) isPlaying =false;
        xBall+=xSpeedBall;
        yBall+=ySpeedBall;
    }

    private void deskHit(int yDesk,int xDesk, int upAndDown,int leftRight) {

        /*
        * Metoda ta służy do tego by odbijać piłkę
        * deska została podzielona na kilka części
        * @parm upAndDown służy do okreslenia czy piłka ma lecieć w dół czy górę
        * @parm leftRight określa czy piłka ma lecieć w lewo czy w prawo w zależności od tego
        * czy ma do czynienia z playerem górnnym lub dolnym
        * */

        if(yBall-3<yDesk && yBall+3 >yDesk){
            if (xBall- xDesk <0 || xBall- xDesk >lengthDesk){
                //piłka mineła deskę
            }else if (xBall- xDesk >=0.4*lengthDesk && xBall- xDesk <=0.6*lengthDesk){
                ySpeedBall=5*upAndDown;
                xSpeedBall=0;
            }else if (xBall- xDesk >=0.2*lengthDesk && xBall- xDesk <0.4*lengthDesk){
                ySpeedBall=4*upAndDown;
                xSpeedBall=1*upAndDown*leftRight;
            }else if (xBall- xDesk >=0.1*lengthDesk && xBall- xDesk <0.2*lengthDesk){
                ySpeedBall=2*upAndDown;
                xSpeedBall=3*upAndDown*leftRight;
            }else if (xBall- xDesk <0.1*lengthDesk &&xBall- xDesk >0){
                ySpeedBall=1*upAndDown;
                xSpeedBall=4*upAndDown*leftRight;
            }else if (xBall- xDesk >0.6*lengthDesk && xBall- xDesk <=0.8*lengthDesk){
                ySpeedBall=4*upAndDown;
                xSpeedBall=-1*upAndDown*leftRight;
            }else if (xBall- xDesk >0.8*lengthDesk && xBall- xDesk <=0.9*lengthDesk){
                ySpeedBall=3*upAndDown;
                xSpeedBall=-2*upAndDown*leftRight;
            }else if (xBall- xDesk >0.9*lengthDesk){
                ySpeedBall=1*upAndDown;
                xSpeedBall=-4*upAndDown*leftRight;
            }
        }
    }

    public void paintComponent(Graphics g){
        if(isPlaying){
                super.paintComponent(g); // dzięki tej komendzie można zmieniać np. tło
                draw(g);
        }else gameOver(g);
    }

    private void draw(Graphics g) {
        drawDeskPlayerOne(g);
        drawDeskPlayerTwo(g);

        drawBall(g);
    }

    private void drawDeskPlayerOne(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(xDeskPlayerOne, yDeskPlayerOne, lengthDesk,heightDesk);
    }
    private void drawDeskPlayerTwo(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(xDeskPlayerTwo, yDeskPlayerTwo-heightDesk, lengthDesk,heightDesk);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(xBall-sizeBall/2,yBall-sizeBall/2,sizeBall,sizeBall);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBall();
        repaint();
    }

    private void gameOver(Graphics g) {
        g.setFont(new Font("Times New Roman",Font.BOLD,55));
        g.setColor(Color.CYAN);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(WINDOWS_WIDTH-metrics.stringWidth("Game Over"))/2,WINDOWS_HEIGHT/2);
    }

    private class MyAdapterKeyOne extends KeyAdapter{
        private final Set<Integer> pressedKeys = new HashSet<>();
        @Override
        public synchronized void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());

                if(!pressedKeys.isEmpty()){
                    for (Iterator<Integer> it=pressedKeys.iterator();it.hasNext();){
                        switch (it.next()){
                            case KeyEvent.VK_D:
                                xDeskPlayerTwo+=5;
                                break;
                            case KeyEvent.VK_RIGHT:
                                xDeskPlayerOne+=5;
                                break;
                            case KeyEvent.VK_LEFT:
                                xDeskPlayerOne-=5;
                                break;
                            case KeyEvent.VK_A:
                                xDeskPlayerTwo-=5;
                                break;
                        }
                    }
                }
            }
        public synchronized void keyReleased(KeyEvent e){
                pressedKeys.remove(e.getKeyCode());
            }
        public void keyTyped(KeyEvent e) {

        }
        }


    public class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            isPlaying=true;
            xBall=WINDOWS_HEIGHT/2;
            yBall=WINDOWS_WIDTH/2;
            xDeskPlayerOne =WINDOWS_WIDTH/2- lengthDesk /2;
            yDeskPlayerOne = WINDOWS_HEIGHT-50;
            xDeskPlayerTwo =WINDOWS_WIDTH/2- lengthDesk /2;
            yDeskPlayerTwo = 50;
            xSpeedBall=0;
            ySpeedBall=5*upAndDownWay;
        }
    }
}