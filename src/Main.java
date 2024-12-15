import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;
    StartScreen startScreen;
    JPanel gamePanel;

    public Main() throws Exception {
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        startScreen = new StartScreen(this);
        displayZoneFrame.getContentPane().add(startScreen);
        displayZoneFrame.setVisible(true);
    }

    public void showStartScreen() {
        displayZoneFrame.getContentPane().removeAll();
        StartScreen startScreen = new StartScreen(this); 
        displayZoneFrame.getContentPane().add(startScreen);
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();
    }

    public void startGame() throws Exception {
        displayZoneFrame.getContentPane().removeAll();
    
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/basiccharakterspritesheet.png")), 48, 50);
    
        DynamicSprite enemy = new DynamicSprite(200, 200,
                ImageIO.read(new File("./img/basiccharakterspritesheet2.png")), 48, 50);
    
        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, enemy, this); // Pass the mainGame to the GameEngine
    
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());
    
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();
    
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();
    
        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(enemy);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.addToMovingSpriteList(enemy);
        physicEngine.setEnvironment(level.getSolidSpriteList());
    
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.requestFocus();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }
}

