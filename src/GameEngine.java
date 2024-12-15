import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    private DynamicSprite hero;
    private DynamicSprite enemy;
    private boolean gameOver = false;
    private Main mainGame;

    // Updated constructor to accept hero, enemy, and mainGame parameters
    public GameEngine(DynamicSprite hero, DynamicSprite enemy, Main mainGame) {
        this.hero = hero;
        this.enemy = enemy;
        this.mainGame = mainGame;
    }

    @Override
    public void update() {
        if (!gameOver) {
            checkCollision();
        }
    }

    // Checks the collision between hero and enemy
    private void checkCollision() {
        if (hero.getHitBox().intersects(enemy.getHitBox())) {
            gameOver = true;
            showGameOverScreen(); // Displays the Game Over screen
        }
    }

    private void showGameOverScreen() {
        GameOverScreen gameOverScreen = new GameOverScreen(mainGame);
        mainGame.displayZoneFrame.getContentPane().removeAll();
        mainGame.displayZoneFrame.getContentPane().add(gameOverScreen);
        mainGame.displayZoneFrame.revalidate();
        mainGame.displayZoneFrame.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return; 

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_W:
                enemy.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_S:
                enemy.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_A:
                enemy.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_D:
                enemy.setDirection(Direction.EAST);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
