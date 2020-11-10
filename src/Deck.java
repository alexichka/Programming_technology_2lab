import java.util.ArrayList;


//Класс, тасующий карты и раздающий их трем игрокам
public class Deck {
    private static final int DECK_SIZE = 50;
    private static final int GAMERS_NB = 3;
    private static final int CARDS_ON_HANDS = 15;

    private Card[] cardDeck;

    public void createAndMixCards() {
        cardDeck = new Card[DECK_SIZE];
        for (int i = 0; i < Card.nums.length; i++) {
            for (int j = 0; j < Card.colours.length; j++) {
                cardDeck[Card.colours.length * i + j] = new Card(Card.colours[j], Card.nums[i]);
            }

        }

        for (int i = 0; i < DECK_SIZE; i++) {
            int r = i + (int) (Math.random() * (DECK_SIZE - i)); // случайная карта в колоде
            Card temp = cardDeck[r];
            cardDeck[r] = cardDeck[i];
            cardDeck[i] = temp;
        }
    }

    //метод создает игроков, раздает им карты и возвращает их
    public ArrayList<Gamer> createGamers() {
        ArrayList<Card> hand = new ArrayList<Card>();
        ArrayList<Card> deck = new ArrayList<Card>();
        ArrayList<Gamer> gamers = new ArrayList<Gamer>();

        int handCount = 1; //6
        int gamerDeckCount = 1; //15
        int gamerCount = 1; //счетчик игроков

        //у нас 45 карт, мы их раздаем трем игрокам по 6 на руку и 9 в запасную колоду
        for (int i = 0; i < (GAMERS_NB * CARDS_ON_HANDS) + 3; i++) {
            //если это одна из первых 6 карт в каждой из 15 карт, мы заполняем руку игрока (список)
            if (handCount <= 6) {
                handCount++;
                hand.add(cardDeck[i]);
                continue;
            }

            if (gamerDeckCount <= 9) {
                gamerDeckCount++;
                //заполняем запасную колоду
                deck.add(cardDeck[i]);
                continue;
            }

            //создаем игрока
            if (gamerCount == 1) {
                gamers.add(new Gamer("Саша", new ArrayList<Card>(hand), new ArrayList<Card>(deck)));
            } else if (gamerCount == 2) {
                gamers.add(new Gamer("Родион", new ArrayList<Card>(hand), new ArrayList<Card>(deck)));
            } else {
                gamers.add(new Gamer("Кристиан", new ArrayList<Card>(hand), new ArrayList<Card>(deck)));
            }

            gamerCount++;
            handCount = 1;
            gamerDeckCount = 1;
            hand.clear();
            deck.clear();
        }

        return gamers;
    }
}
