package com.ticket_to_ride.common.model;

import com.ticket_to_ride.common.model.CardM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;

import java.util.ArrayList;
import java.util.Iterator;

public class HandM<T extends CardM>
{
    private ArrayList<T> hand;

    public HandM()
    {
        this.hand = new ArrayList<>();
    }

    public void addToHand(T card)
    {
        hand.add(card);
    }

    public ArrayList<TrainCardM> removeFromHand(TrainCardColor color, int amount)
    {
        if (hand.size() < amount)
            return null;
        if (amount < 0 || amount > 6)
            return null;

        ArrayList<TrainCardM> reqCards = new ArrayList<>();
        /* To fix concurrent mod error */
        HandM<T> temp = new HandM<>();
        for (T t : hand)
            temp.addToHand(t);
        Iterator<T> it = temp.getHand().iterator();
        int count = 0;
        while (it.hasNext())
        {
            T t = it.next();
            if (count == amount)
                break;
            TrainCardM card = (TrainCardM)t;
            if (card.getColor() == color) {
                hand.remove(card);
                reqCards.add(card);
                count++;
            }
        }
        if (count != amount)
        {
            it = temp.getHand().iterator();
            while (it.hasNext())
            {
                T t = it.next();
                if (count == amount)
                    break;
                TrainCardM card = (TrainCardM)t;
                if (card.getColor() == TrainCardColor.WILD) {
                    hand.remove(card);
                    reqCards.add(card);
                    count++;
                }
            }
        }
        if (count != amount)
        {
            for (TrainCardM card : reqCards)
                hand.add((T)card);
        }
        return reqCards;
        //TODO: Make discard deck in server, and send back that info to the server
    }

    public int getHandSize()
    {
        return hand.size();
    }

    public ArrayList<T> getHand()
    {
        return hand;
    }

    public void setHand(ArrayList<T> hand)
    {
        this.hand = hand;
    }
}
