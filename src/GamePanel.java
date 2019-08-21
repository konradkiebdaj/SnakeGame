import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private Thread thread;
    private boolean running;

    private BodySnake bodySnake;
    private ArrayList<BodySnake> snake;

    private Food food;
    private ArrayList<Food> foods;

    private Random random;

    private int xCoor = 10;
    private int yCoor = 10;
    private int size = 5;

    private int ticks = 0;

    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    public GamePanel() {
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        snake = new ArrayList<BodySnake>();
        foods = new ArrayList<Food>();

        random = new Random();

        start();
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        if (snake.size() == 0) {
            bodySnake = new BodySnake(xCoor, yCoor, 10);
            snake.add(bodySnake);
        }
        ticks++;
        if (ticks > 350000) {
            if (right) xCoor++;
            if (left) xCoor--;
            if (up) yCoor--;
            if (down) yCoor++;

            ticks = 0;

            bodySnake = new BodySnake(xCoor, yCoor, 10);
            snake.add(bodySnake);

            if (snake.size() > size) {
                snake.remove(0);
            }
        }
        if (foods.size() == 0){
            int xCoor = random.nextInt(49);
            int yCoor = random.nextInt(49);

            food = new Food(xCoor, yCoor, 10);
            foods.add(food);
        }
        for (int i = 0; i <foods.size() ; i++) {
            if (xCoor == foods.get(i).getxCoor() && yCoor == foods.get(i).getyCoor()){
                size++;
                foods.remove(i);
                i++;
            }
        }
        for (int i = 0; i < snake.size(); i++) {
            if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
                if (i != snake.size() - 1) {
                    System.out.println("Game over!");
                    stop();
                }
            }
        }
        if (xCoor == 0 || xCoor > 49 || yCoor == 0 || yCoor > 49){
            System.out.println("Game over, loser!");
            stop();
        }
    }

    public void paint(Graphics g) {

        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }
        for (int i = 0; i < HEIGHT / 10; i++) {
            g.drawLine(0, i * 10, HEIGHT, i * 10);
        }
        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }
        for (int i = 0; i < foods.size(); i++) {
            foods.get(i).draw(g);
        }

    }

    @Override
    public void run() {
        while (running) {
            tick();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }
        if (key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }
        if (key == KeyEvent.VK_UP && !down) {
            up = true;
            right = false;
            left = false;
        }
        if (key == KeyEvent.VK_DOWN && !up) {
            down = true;
            right = false;
            left = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
