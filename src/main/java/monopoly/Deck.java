package monopoly;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Deck {
    private Queue<Card> cards;
    private List<Card> discard;

    public Deck() {
        this.cards = new LinkedList<>();
        this.discard = new ArrayList<>();
        // TODO: initialize cards
    }

    public Card draw() {
        // TODO: draw top card
        return null;
    }

    public void reshuffle() {
        // TODO: reshuffle discard into cards
    }

    public Queue<Card> getCards() {
        return cards;
    }

    public List<Card> getDiscard() {
        return discard;
    }
}