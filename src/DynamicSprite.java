import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.EAST;
    protected double speed = 5; // Default speed
    private double timeBetweenFrame = 250;
    private final int spriteSheetNumberOfColumn = 4;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        double newX = x;
        double newY = y;

        if (direction == null) {
            return false; // Does not move if there is no direction
        }

        // Calculate the new position depending on the direction
        switch (direction) {
            case EAST:
                newX += speed;
                break;
            case WEST:
                newX -= speed;
                break;
            case NORTH:
                newY -= speed;
                break;
            case SOUTH:
                newY += speed;
                break;
        }

        moved.setRect(newX, newY, width, height);

        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (((SolidSprite) s).intersect(moved)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (direction != null && isMovingPossible(environment)) {
            move();
        }
    }

    private void move() {
        if (direction != null) {
            switch (direction) {
                case NORTH:
                    y -= speed;
                    break;
                case SOUTH:
                    y += speed;
                    break;
                case EAST:
                    x += speed;
                    break;
                case WEST:
                    x -= speed;
                    break;
         }
        }
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);
        g.drawImage(image,
                (int) x, (int) y,
                (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height),
                null);
    }
}
