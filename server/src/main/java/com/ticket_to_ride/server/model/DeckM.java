package com.ticket_to_ride.server.model;

import com.ticket_to_ride.common.model.CardM;

import java.util.ArrayList;
import java.util.Collections;


public class DeckM<T extends CardM> {
    private ArrayList<T> deck;

    public DeckM () {
        this.deck = new ArrayList<T>();
    }

    public DeckM (ArrayList<T> deck) {
        this.deck = deck;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public T drawFromDeck(){
        if(deck.size() > 0) {
            T card = deck.get(0);
            deck.remove(0);
            return card;
        }else
            return null;
    }

    public void addToDeck(T card) {
        deck.add(card);
    }

    public int getDeckSize(){
        return deck.size();
    }

    public ArrayList<T> getDeck()
    {
        return deck;
    }

}
