import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel {
    private JButton startButton;
    private Main mainGame;
    private Timer buttonAnimationTimer;
    private int buttonYOffset = 0;
    private Image titleImage, waterImage, grassImage, grassBorderImage;
    private static final int GRASS_RECT_WIDTH = 300; // Largura do retângulo
    private static final int GRASS_RECT_HEIGHT = 250; // Altura do retângulo

    public StartScreen(Main mainGame) {
        this.mainGame = mainGame;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 600));

        try {
            Image originalImage = ImageIO.read(new File("./img/botao.png"));
            waterImage = ImageIO.read(new File("./img/agua.png"));
            grassImage = ImageIO.read(new File("./img/grass2.png"));
            grassBorderImage = ImageIO.read(new File("./img/grassborder.png")); // Carrega a imagem da borda

            // Redimensiona a imagem do título
            double scale = Math.min(250.0 / originalImage.getWidth(null), 300.0 / originalImage.getHeight(null));
            int newWidth = (int) (originalImage.getWidth(null) * scale);
            int newHeight = (int) (originalImage.getHeight(null) * scale);
            titleImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel imageLabel = new JLabel(new ImageIcon(titleImage));

        startButton = new JButton("Start Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0x786dae));
                g2.fillRoundRect(0, buttonYOffset, getWidth() - 1, getHeight() - 1, 10, 10);

                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent() + buttonYOffset;
                g2.drawString(getText(), textX, textY);

                g2.dispose();
            }
        };
        startButton.setPreferredSize(new Dimension(100, 40));
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateButtonClick();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(imageLabel, gbc);

        gbc.gridy = 1;
        add(startButton, gbc);

        setOpaque(false);
    }

    private void animateButtonClick() {
        if (buttonAnimationTimer != null && buttonAnimationTimer.isRunning()) {
            return;
        }

        buttonAnimationTimer = new Timer(5, new ActionListener() {
            int steps = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (steps < 5) {
                    buttonYOffset += 1;
                } else if (steps < 10) {
                    buttonYOffset -= 1;
                } else {
                    ((Timer) e.getSource()).stop();
                    buttonYOffset = 0;
                    try {
                        mainGame.startGame();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(StartScreen.this,
                                "Error starting the game: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                startButton.repaint();
                steps++;
            }
        });
        buttonAnimationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Desenha a água como fundo
        for (int y = 0; y < height; y += waterImage.getHeight(null)) {
            for (int x = 0; x < width; x += waterImage.getWidth(null)) {
                g.drawImage(waterImage, x, y, this);
            }
        }

        // Calcula as dimensões do retângulo + borda
        int borderHeight = grassBorderImage.getHeight(null);
        int totalHeight = GRASS_RECT_HEIGHT + borderHeight;

        // Centraliza o retângulo e a borda
        int grassX = (width - GRASS_RECT_WIDTH) / 2;
        int grassY = (height - totalHeight) / 2;

        // Desenha o retângulo de grama
        for (int y = grassY; y < grassY + GRASS_RECT_HEIGHT; y += grassImage.getHeight(null)) {
            for (int x = grassX; x < grassX + GRASS_RECT_WIDTH; x += grassImage.getWidth(null)) {
                g.drawImage(grassImage, x, y, this);
            }
        }

        // Desenha a borda inferior
        for (int xOffset = 0; xOffset < GRASS_RECT_WIDTH; xOffset += grassBorderImage.getWidth(this)) {
            g.drawImage(grassBorderImage, grassX + xOffset, grassY + GRASS_RECT_HEIGHT, this);
        }
    }
}
