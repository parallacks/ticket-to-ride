package com.ticket_to_ride.client.model;

import com.ticket_to_ride.common.model.CardM;
import com.ticket_to_ride.common.model.TrainCardM;

import java.util.ArrayList;

/**
 * Created by jared
 */

// Should this be different from the Server Deck in functionality?
public class DeckM {
    private ArrayList<CardM> deck;

    public DeckM (ArrayList<CardM> deck) {
        this.deck = deck;
    }

    public DeckM() {
        deck = new ArrayList<>();
    }

    public void addToDeck(CardM cardM) {
        deck.add(cardM);
    }

    public void removeFromDeck(CardM cardM) {
        deck.remove(cardM);
    }

    public int getDeckSize(){
        return deck.size();
    }

    public ArrayList<CardM> getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (CardM card : deck) {
            strBuilder.append(card.toString());
            strBuilder.append("\n\n");
        }
        return strBuilder.toString();
    }

}

