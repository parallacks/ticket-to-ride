package com.ticket_to_ride.client.view;

//Jayden Olsen

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    private Button mLoginButton;
    private EditText mHostField;
    private EditText mUserField;
    private EditText mPasswordField;
    private TextView mRegisterLink;
    private String mHostName;
    private String mUserName;
    private String mPassword;
    private boolean mHostInput = false;
    private boolean mUserInput = false;
    private boolean mPasswordInput = false;
    private IRegisterLoginActivity mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IRegisterLoginActivity) {
            mListener = (IRegisterLoginActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IRegisterLoginActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginButton = (Button)v.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.login(mHostName, mUserName, mPassword);
                }
            }
        });
        mLoginButton.setEnabled(false);

        mUserField = (EditText)v.findViewById(R.id.login_edit_username);
        mUserField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserName = s.toString();
                mUserInput = (s.length() != 0);

                if(mUserInput && mPasswordInput && mHostInput)
                {
                    mLoginButton.setEnabled(true);
                }
                else
                {
                    mLoginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordField = (EditText)v.findViewById(R.id.login_edit_password);
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
                mPasswordInput = (s.length() != 0);

                if(mUserInput && mPasswordInput && mHostInput)
                {
                    mLoginButton.setEnabled(true);
                }
                else
                {
                    mLoginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mHostField = (EditText)v.findViewById(R.id.login_host_name);
        mHostField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHostName = s.toString();
                mHostInput = (s.length() != 0);

                if(mUserInput && mPasswordInput && mHostInput)
                {
                    mLoginButton.setEnabled(true);
                }
                else
                {
                    mLoginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRegisterLink = (TextView)v.findViewById(R.id.register_link);
        mRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.switchToRegisterFragment();
                }
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
