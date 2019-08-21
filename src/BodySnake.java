import java.awt.*;

public class BodySnake {

    private int xCoor;
    private int yCoor;
    private int width;
    private int height;

    public BodySnake(int xCoor, int yCoor, int tileSize) {
        this.yCoor = yCoor;
        this.xCoor = xCoor;
        this.width = tileSize;
        this.height = tileSize;
    }

    public int getxCoor() {
        return xCoor;
    }

    public void setxCoor(int xCoor) {
        this.xCoor = xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }

    public void tick() {
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(xCoor * width, yCoor * height, width, height);
    }
}
