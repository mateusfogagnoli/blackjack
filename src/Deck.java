import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        buildDeck();
    }

    private void buildDeck() {
        // Lembrar que JACK eh valete 
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value;
                String filenameRank;
                String filenameSuit = suit.toLowerCase();
                String suffix = "";
                if (rank.equals("Ace")) {
                    value = 11;
                    filenameRank = "ace";
                } else if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
                    value = 10;
                    filenameRank = rank.toLowerCase();
                    suffix = "2"; // Face cards have '2' suffix in this specific deck
                } else {
                    value = Integer.parseInt(rank);
                    filenameRank = rank;
                }

                String filename = filenameRank + "_of_" + filenameSuit + suffix + ".png";
                String path = "deck/" + filename;
                
                Image img = null;
                try {
                    img = ImageIO.read(new File(path));
                } catch (IOException e) {
                    System.err.println("Error loading image: " + path);
                    e.printStackTrace();
                }

                Card card = new Card(rank, suit, value, img);
                cards.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }
    
    public int remainingCards() {
        return cards.size();
    }
}
