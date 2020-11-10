public class Card {
    private String colour;
    private String num;
    transient public static String[] colours = {"yellow", "red", "green", "blue", "multicolour"};
    transient public static String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public Card(String colour, String num) {
        this.colour = colour;
        this.num = num;
    }

    public boolean getHitted(Card card) {

        if (this.colour.equals("multicolour") || card.colour.equals("multicolour")) {
            return true;
        }

        if (card.colour.equals(this.colour)) {
            return true;
        }

        return card.num.equals(this.num);
    }

    public @Override String toString()
    {
        return this.num + " " + this.colour;
    }
}
