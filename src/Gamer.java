import java.util.List;

public class Gamer {
    private String name;
    private List<Card> hand; //карты в руках 6 штук
    private List<Card> cardDeck; //колода 9 штук

    public Gamer(String name, List<Card> hand, List<Card> cardDeck) {
        this.name = name;
        this.hand = hand;
        this.cardDeck = cardDeck;
    }

    public boolean isWinner() {
        return hand.isEmpty() && cardDeck.isEmpty();
    }
    /*Функция проверяет, может ли побить игрок карту на сервере или нет
    Для этого я бегу по циклу карт в руке и пробую передавать каждую карту
    в метод getHit обьекта lastUnbrokenCard

    Если я могу побить эту карту, то я отправляю обьект побившей карты на сервер (делая ее там последней)
    и удаляю эту карту из колоды в руке.

    Если я не могу побить карту колодой в руке, я беру карту из запасной колоды
    и опять пробую побить эту карту с сервера. Если я побиваю эту карту, то я отправляю эту карту
    на сервер и делаю ее там последней, и удаляю ее из запасной колоды.
    */
    public Card tryHit(Card lastUnbrokenCard) {
        //пробуем побить тем, что находится в руке
        for (int i = 0; i < hand.size(); i++) {
            if (lastUnbrokenCard.getHitted(hand.get(i))) {
                //удаляем карту из руки
                //и отправляем ее на сервер и делаем ее там последней
                return hand.remove(i);
            }
        }

        //здесь не получилось побить тем, что в руке, или рука оказалась пуста, поэтому выбираем последнюю карту из запасной колоды
        //и бью ей
        Card auxCard = cardDeck.get(cardDeck.size() - 1);
        if (lastUnbrokenCard.getHitted(auxCard) || hand.isEmpty()) {
            Card returnCard = auxCard;
            cardDeck.remove(auxCard);
            return returnCard;
        } else {
            hand.add(auxCard);
            cardDeck.remove(auxCard);
        }

        if (hand.size()==0) {
            hand.add(cardDeck.get(0));
        }

        //Игроку не удалось побить карту
        return null;
    }

    public String getName() {
        return this.name;
    }
}

