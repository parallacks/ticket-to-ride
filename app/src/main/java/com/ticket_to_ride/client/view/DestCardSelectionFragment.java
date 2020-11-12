package com.ticket_to_ride.client.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ticket_to_ride.client.presenter.DestCardSelectionPresenter;
import com.ticket_to_ride.common.model.CityM;
import com.ticket_to_ride.common.model.DestinationCardM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DestCardSelectionFragment extends Fragment implements IDestCardSelectionView {

    private IGameBoardActivity mListener;
    private Button submitBtn;
    private HashMap<Button, DestinationCardM> destCardBtns;
    private Button firstDestCardBtn;
    private Button secondDestCardBtn;
    private Button thirdDestCardBtn;
    private DestCardSelectionPresenter destCardSelectionPresenter;
    private TextView selectionInstructions;
    private ArrayList<DestinationCardM> destCardChoices;
    private ArrayList<DestinationCardM> selectedDestCards;
    private int minNumDestCardsToChoose;
    private ArrayList<DestinationCardM> toDiscard;

    public DestCardSelectionFragment() {
        // Required empty public constructor
        destCardBtns = new HashMap<>();
        selectedDestCards = new ArrayList<>();
        destCardChoices = new ArrayList<>();
        toDiscard = new ArrayList<>();
    }

    public static DestCardSelectionFragment newInstance(int minNumDestCards) {
        DestCardSelectionFragment fragment = new DestCardSelectionFragment();
        Bundle args = new Bundle();
        args.putInt("minNumDestCards", minNumDestCards);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dest_card_selection,
                container, false);

        submitBtn = view.findViewById(R.id.submitBtn);
        submitBtn.setEnabled(false);

        firstDestCardBtn = view.findViewById(R.id.firstDestCardBtn);
        secondDestCardBtn = view.findViewById(R.id.secondDestCardBtn);
        thirdDestCardBtn = view.findViewById(R.id.thirdDestCardBtn);
        selectionInstructions = view.findViewById(R.id.selectionInstructions);
        destCardSelectionPresenter = new DestCardSelectionPresenter(this);
        Bundle args = getArguments();
        minNumDestCardsToChoose = args.getInt("minNumDestCards");
        if (minNumDestCardsToChoose == 2) {
            destCardSelectionPresenter.setFirstTimePickingDestCards(true);
        }
        else {
            destCardSelectionPresenter.setFirstTimePickingDestCards(false);
        }
        setInstructionText(minNumDestCardsToChoose);

        setButtonListeners();

        return view;
    }

    private void setButtonListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destCardSelectionPresenter.respondSubmitBtnPress(selectedDestCards,
                        getSelectedDestCardPositions(), toDiscard);
            }
        });

        for (Button btn : destCardBtns.keySet()) {
            setDestCardBtnListener(btn);
        }
    }

    private void toggleSubmitBtn() {
        if(selectedDestCards.size() >= minNumDestCardsToChoose) {
            submitBtn.setEnabled(true);
        }
        else {
            submitBtn.setEnabled(false);
        }
    }

    private void setDestCardBtnListener(Button destCardBtn) {
        DestCardBtnListener destCardBtnListener = new DestCardBtnListener();
        destCardBtn.setOnTouchListener(destCardBtnListener);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IGameBoardActivity) {
            mListener = (IGameBoardActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setBtnText(DestinationCardM destCard, Button btn) {
        Resources res = getResources();
        String text = String.format(res.getString(R.string.DestCardBtnText),
                destCard.getCityM1().getName(), destCard.getCityM2().getName(),
                destCard.getPoints());
        btn.setText(text);
    }

    private void setInstructionText(int minNumDestToChoose) {
        Resources res = getResources();
        String text = String.format(res.getString(R.string.destCardInstructionText),
                minNumDestToChoose);
        selectionInstructions.setText(text);

    }

    private ArrayList<Integer> getSelectedDestCardPositions() {
        ArrayList<Integer> positions = new ArrayList<>();
        for (DestinationCardM destCard : selectedDestCards) {
            for (int i = 0; i < destCardChoices.size(); i++) {
                if (destCard.equals(destCardChoices.get(i))) {
//                    System.out.printf("card to keep: " + selectedDestCards.get(i).getCityM1() +
//                            " to " + selectedDestCards.get(i).getCityM2() + "\t pos: %d\n", i);
                    positions.add(i);
                }
            }
        }
        return positions;
    }

    @Override
    public void updateDestCards(List<DestinationCardM> destCards) {
        selectedDestCards.clear();
        destCardChoices.clear();
        destCardBtns.clear();
        toDiscard.clear();
        if (destCards.size() == 3) {
            destCardBtns.put(firstDestCardBtn, destCards.get(0));
            destCardBtns.put(secondDestCardBtn, destCards.get(1));
            destCardBtns.put(thirdDestCardBtn, destCards.get(2));

            destCardChoices.add(destCards.get(0));
            destCardChoices.add(destCards.get(1));
            destCardChoices.add(destCards.get(2));
        }
        else if (destCards.size() == 2) {
            destCardBtns.put(firstDestCardBtn, destCards.get(0));
            destCardBtns.put(secondDestCardBtn, destCards.get(1));

            destCardChoices.add(destCards.get(0));
            destCardChoices.add(destCards.get(1));

            thirdDestCardBtn.setVisibility(View.INVISIBLE);
        }
        else if (destCards.size() == 1) {
            destCardBtns.put(firstDestCardBtn, destCards.get(0));
            destCardChoices.add(destCards.get(0));

            secondDestCardBtn.setVisibility(View.INVISIBLE);
            thirdDestCardBtn.setVisibility(View.INVISIBLE);
        }
        else {
            System.out.println("Error: No destination cards available");
            System.out.println("DestCardSelectionFragment");
        }

        // Set text information for cards
        for (Map.Entry<Button, DestinationCardM> cardBtnPair : destCardBtns.entrySet()) {
            setBtnText(cardBtnPair.getValue(), cardBtnPair.getKey());
            toDiscard.add(cardBtnPair.getValue());
        }
    }

    @Override
    public void closeView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListener.closeDestCardSelectionView();
            }
        });
    }

    @Override
    public void enableSubmitBtn(boolean enable) {
        final boolean Enable = enable;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submitBtn.setEnabled(Enable);
            }
        });
    }

    @Override
    public void displayToast(String toDisplay) {
        final String message = toDisplay;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private class DestCardBtnListener implements View.OnTouchListener {

        private long mLastClickTime = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                return true;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            v.performClick();
            if (v.isPressed()) {
                v.setPressed(false);
                selectedDestCards.remove(destCardBtns.get(v));
                toDiscard.add(destCardBtns.get(v));
                toggleSubmitBtn();
            } else {
                v.setPressed(true);
                selectedDestCards.add(destCardBtns.get(v));
                toDiscard.remove(destCardBtns.get(v));
                toggleSubmitBtn();
            }
            return true;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /*
                            TEST FUNCTIONS
     */

    private void testDisplay() {
        //TODO: CREATE destination cards and display them
        String[] cityStrings = {"Spanish Fork", "Provo", "Las Vegas", "LA",
                "New York", "Denver"};
        ArrayList<CityM> cities = new ArrayList<>();
        for (int i = 0; i < cityStrings.length; i++) {
            CityM city = new CityM(cityStrings[i]);
            cities.add(city);
        }

        ArrayList<DestinationCardM> cards = new ArrayList<>();
        for (int i = 0; i < cities.size(); i+=2) {
            DestinationCardM card = new DestinationCardM(cities.get(i), cities.get(i+1),
                    (i+1)*4);
            cards.add(card);
        }
        updateDestCards(cards);
    }

}
