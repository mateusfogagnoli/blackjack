import java.awt.Image;

public class Card {
    private String rank;
    private String suit;
    private int value;
    private Image image;

    public Card(String rank, String suit, int value, Image image) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public String getRank() {
        return rank;
    }

    public Image getImage() {
        return image;
    }

    public boolean isAce() {
        return rank.equals("Ace");
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
