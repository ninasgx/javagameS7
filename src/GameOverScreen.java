import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameOverScreen extends JPanel {
    private JButton restartButton;
    private Main mainGame;
    private Image waterImage; 
    private Image gameOverImage; 
    private Image scaledGameOverImage; 

    public GameOverScreen(Main mainGame) {
        this.mainGame = mainGame;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 600));

        try {
            waterImage = ImageIO.read(new File("./img/agua.png"));
            gameOverImage = ImageIO.read(new File("./img/gameover.png"));
            
            int newWidth = 204;
            int newHeight = 48;
            scaledGameOverImage = gameOverImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }

        restartButton = new JButton("Back to Start Screen") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0x786dae)); 
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), textX, textY);

                g2.dispose();
            }
        };

        restartButton.setPreferredSize(new Dimension(200, 50));
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setFocusPainted(false);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGame.showStartScreen();  
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0; 
        add(restartButton, gbc);

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Draws the water as background
        for (int y = 0; y < height; y += waterImage.getHeight(null)) {
            for (int x = 0; x < width; x += waterImage.getWidth(null)) {
                g.drawImage(waterImage, x, y, this);
            }
        }

        // Draws the resized "Game Over" image, further from the button
        if (scaledGameOverImage != null) {
            int imageWidth = scaledGameOverImage.getWidth(null);
            int imageHeight = scaledGameOverImage.getHeight(null);

            // Calculates the position to center the image, adjusting the distance from the button
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2 - 50;  // Increases the distance of the image from the button

            g.drawImage(scaledGameOverImage, x, y, this);  // Draws the "Game Over" image
        }
    }
}
