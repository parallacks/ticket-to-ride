package com.ticket_to_ride.client.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.services.ClientChatService;
//import com.ticket_to_ride.client.services.MockServiceCaller;
import com.ticket_to_ride.common.model.ChatListM;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.GameHistoryM;
import com.ticket_to_ride.common.model.MessageM;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;


public class ChatFragment extends Fragment implements Observer {
    private IGameBoardActivity mListener;
    private RecyclerView mChatMessages;
    private ChatListAdapter mChatListAdapter;
    private EditText mChatInput;
    private Button mSendButton;
    private Button mCloseButton;
    private ClientM model = ClientM.get();

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        mChatMessages = (RecyclerView) v.findViewById(R.id.chat_recyclerView);
        mChatListAdapter = new ChatListAdapter(getActivity(), model.getChatList());
        mChatMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatMessages.setAdapter(mChatListAdapter);
        //mChatListAdapter.notifyDataSetChanged();
        ClientM.get().addObserver(this);

        mChatInput = (EditText)v.findViewById(R.id.chat_input);
        mChatInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendMessage();
                    mChatInput.setText("");
                    return true;
                }
                return false;
            }
        });

        mSendButton = (Button)v.findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                mChatInput.setText("");
            }
        });

        mCloseButton = (Button)v.findViewById(R.id.chat_hide_button);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.hideChat();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IGameBoardActivity) {
            mListener = (IGameBoardActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IGameBoardActivity");
        }
    }

    private void sendMessage() {
        String message = mChatInput.getText().toString();
        ChatM chat = new ChatM(model.getUser().getUsername(), LocalDateTime.now().toString(), message);
        ChatTask chatTask = new ChatTask();
        chatTask.execute(chat);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.getClass() == MessageM.class)
        {
            // For now, just recreate the adapter because it works this way
            mChatListAdapter = new ChatListAdapter(getActivity(), model.getChatList());
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatMessages.setAdapter(mChatListAdapter);
//                    mChatListAdapter.notifyDataSetChanged();
//                    mChatListAdapter.notifyItemInserted(model.getChatList().getChatList().size() - 1);
                    }
                });
            }
        }
    }

    public class ChatListAdapter extends RecyclerView.Adapter {
        private static final int VIEW_TYPE_CHAT_SENT = 1;
        private static final int VIEW_TYPE_CHAT_RECEIVED = 2;
        private static final int VIEW_TYPE_GAME_HISTORY = 3;

        private Context mContext;
        private ChatListM mChatList;

        public ChatListAdapter(Context context, ChatListM chatList) {
            mContext = context;
            mChatList = chatList;
        }

        @Override
        public int getItemViewType(int position) {
            MessageM message = (MessageM) mChatList.getChatList().get(position);

            if(message instanceof GameHistoryM)
            {
                return VIEW_TYPE_GAME_HISTORY;
            }
            else if(message.getAuthor().equals(model.getUser().getUsername())) {
                return VIEW_TYPE_CHAT_SENT;
            }
            else {
                return VIEW_TYPE_CHAT_RECEIVED;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View v;
            if(viewType == VIEW_TYPE_CHAT_SENT) {
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_message_sent, viewGroup, false);
                return new SentMessageHolder(v);
            }
            else if (viewType == VIEW_TYPE_CHAT_RECEIVED) {
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_message_received, viewGroup, false);
                return new ReceivedMessageHolder(v);
            }
            else if (viewType == VIEW_TYPE_GAME_HISTORY) {
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_game_history, viewGroup, false);
                return new GameHistoryHolder(v);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MessageM message = (MessageM) mChatList.getChatList().get(i);
            switch (viewHolder.getItemViewType()) {
                case VIEW_TYPE_CHAT_SENT:
                    ((SentMessageHolder) viewHolder).bind(message);
                    break;
                case VIEW_TYPE_CHAT_RECEIVED:
                    ((ReceivedMessageHolder) viewHolder).bind(message);
                    break;
                case VIEW_TYPE_GAME_HISTORY:
                    ((GameHistoryHolder) viewHolder).bind(message);
            }
        }

        @Override
        public int getItemCount() {
            return mChatList.getChatList().size();
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView mMessageText, mTimeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            mMessageText = (TextView) itemView.findViewById(R.id.chat_message_body);
            mTimeText = (TextView) itemView.findViewById(R.id.chat_message_time);        }

        void bind(MessageM message) {
            mMessageText.setText(message.getMessage());
            mTimeText.setText(message.getTimeStamp());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView mMessageText, mTimeText, mNameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            mMessageText = (TextView) itemView.findViewById(R.id.chat_message_body);
            mTimeText = (TextView) itemView.findViewById(R.id.chat_message_time);
            mNameText = (TextView) itemView.findViewById(R.id.chat_message_name);
        }

        void bind(MessageM message) {
            mMessageText.setText(message.getMessage());
            mTimeText.setText(message.getTimeStamp());
            mNameText.setText(message.getAuthor());
        }
    }

    private class GameHistoryHolder extends RecyclerView.ViewHolder {
        TextView mMessageText, mTimeText;

        GameHistoryHolder(View itemView) {
            super(itemView);
            mMessageText = (TextView) itemView.findViewById(R.id.chat_message_body);
            mTimeText = (TextView) itemView.findViewById(R.id.chat_message_time);
        }

        void bind(MessageM message) {
            mMessageText.setText(message.getMessage());
            mTimeText.setText(message.getTimeStamp());
        }
    }

    public class ChatTask extends AsyncTask<MessageM, Void, Void> {
        @Override
        protected Void doInBackground(MessageM... args) {
            ClientChatService clientChatService = new ClientChatService(args[0]);
            clientChatService.SendChat();
            return null;
        }
    }
}
