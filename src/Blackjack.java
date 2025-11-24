import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Blackjack extends JFrame {
    private Deck deck;
    private Hand playerHand;
    private Hand opponentHand;
    private boolean opponentCardHidden;
    private Image backCardImage;
    
    private JPanel gamePanel;

    private JPanel buttonPanel;
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;
    private JLabel statusLabel;

    private final int CARD_WIDTH = 110;
    private final int CARD_HEIGHT = 154;

    public Blackjack() {
        setTitle("Blackjack Java Swing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        deck = new Deck();
        playerHand = new Hand();
        opponentHand = new Hand();

        try {
            backCardImage = ImageIO.read(new File("deck/back-deck.png"));
        } catch (IOException e) {
            System.err.println("Error loading back card image.");
            e.printStackTrace();
        }

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                g.setColor(new Color(34, 139, 34)); // Green table color
                g.fillRect(0, 0, getWidth(), getHeight());

                drawHand(g, opponentHand, 50, 50, true);

                drawHand(g, playerHand, 50, 350, false);
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                
                String opponentScoreText = "Opponent: ";
                if (opponentCardHidden) {
                    if (opponentHand.size() > 0) {
                         opponentScoreText += "?";
                    }
                } else {
                    opponentScoreText += opponentHand.getScore();
                }
                g.drawString(opponentScoreText, 50, 40);

                g.drawString("Player: " + playerHand.getScore(), 50, 340);
            }
        };

        buttonPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");
        statusLabel = new JLabel("Welcome to Blackjack!");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        hitButton.addActionListener(e -> hit());
        standButton.addActionListener(e -> stand());
        newGameButton.addActionListener(e -> startNewGame());

        buttonPanel.add(statusLabel);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);

        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        startNewGame();
    }

    private void drawHand(Graphics g, Hand hand, int x, int y, boolean isOpponent) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (isOpponent && i == 1 && opponentCardHidden) {
                if (backCardImage != null) {
                    g.drawImage(backCardImage, x + (i * 120), y, CARD_WIDTH, CARD_HEIGHT, this);
                } else {
                    g.setColor(new Color(139, 0, 0));
                    g.fillRoundRect(x + (i * 120), y, CARD_WIDTH, CARD_HEIGHT, 10, 10);
                    g.setColor(Color.WHITE);
                    g.drawRoundRect(x + (i * 120), y, CARD_WIDTH, CARD_HEIGHT, 10, 10);
                }
            } else {
                if (card.getImage() != null) {

                    g.drawImage(card.getImage(), x + (i * 120), y, CARD_WIDTH, CARD_HEIGHT, this);
                } else {

                    g.setColor(Color.WHITE);
                    g.fillRoundRect(x + (i * 120), y, CARD_WIDTH, CARD_HEIGHT, 10, 10);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect(x + (i * 120), y, CARD_WIDTH, CARD_HEIGHT, 10, 10);
                    g.drawString(card.toString(), x + (i * 120) + 10, y + 30);
                }
            }
        }
    }

    private void startNewGame() {
        deck = new Deck(); 
        deck.shuffle();
        playerHand.clear();
        opponentHand.clear();
        opponentCardHidden = true;

        // Deal initial cards
        playerHand.addCard(deck.dealCard());
        opponentHand.addCard(deck.dealCard()); // pra cima
        playerHand.addCard(deck.dealCard());
        opponentHand.addCard(deck.dealCard()); // baixo

        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        statusLabel.setText("Your move.");
        
        if (playerHand.getScore() == 21) {
             stand(); // automatico se 21
        }

        gamePanel.repaint();
    }

    private void hit() {
        playerHand.addCard(deck.dealCard());
        gamePanel.repaint();

        if (playerHand.getScore() > 21) {
            statusLabel.setText("Bust! You lose.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            opponentCardHidden = false;
            gamePanel.repaint();
        } else if (playerHand.getScore() == 21) {
            stand(); // automatico se 21
        }
    }

    private void stand() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        opponentCardHidden = false;
        gamePanel.repaint();

        // Pelo menos ate 17 o opponent deve ir, se n deve parar
        while (opponentHand.getScore() < 17) {
            opponentHand.addCard(deck.dealCard());
        }
        gamePanel.repaint();

        determineWinner();
    }

    private void determineWinner() {
        int playerScore = playerHand.getScore();
        int opponentScore = opponentHand.getScore();

        if (playerScore > 21) {
            statusLabel.setText("Too much! You lose.");
        } else if (opponentScore > 21) {
            statusLabel.setText("Opponent Busts! You Win!");
        } else if (playerScore > opponentScore) {
            statusLabel.setText("You Win!");
        } else if (playerScore < opponentScore) {
            statusLabel.setText("Opponent Wins.");
        } else {
            statusLabel.setText("Push (Tie).");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Blackjack().setVisible(true);
        });
    }
}
