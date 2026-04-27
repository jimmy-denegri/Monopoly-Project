package monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Deck {
    private Queue<Card> cards;     // cards waiting to be drawn
    private List<Card> discard;    // cards already used

    public Deck() {
        cards = new LinkedList<>();
        discard = new ArrayList<>();
    }

    // Adds a card to the deck
    public void addCard(Card card) {
        cards.offer(card);
    }

    // Draws the top card from the deck
    public Card draw() {
        if (cards.isEmpty()) {
            reshuffle();
        }

        if (cards.isEmpty()) {
            return null;
        }

        Card drawnCard = cards.poll();

        // Do NOT discard Get Out of Jail Free immediately
        if (drawnCard.getType() != CardType.GET_OUT_OF_JAIL_FREE) {
            discard.add(drawnCard);
        }

        return drawnCard;
    }
    // Shuffles discarded cards back into the main deck
    public void reshuffle() {
        // Only reshuffle if discard pile has cards
        if (!discard.isEmpty()) {
            Collections.shuffle(discard);

            for (Card card : discard) {
                cards.offer(card);
            }

            discard.clear();
        }
    }
    
    public void returnCard(Card card) {
        discard.add(card);
    }

    public Queue<Card> getCards() {
        return cards;
    }

    public List<Card> getDiscard() {
        return discard;
    }

    // number of cards left to draw
    public int size() {
        return cards.size();
    }
}