package com.ticket_to_ride.client.view;

//Jayden Olsen

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.UserM;

import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import com.ticket_to_ride.client.services.ClientLoginService;
import com.ticket_to_ride.client.services.ClientRegisterService;

public class LoginRegisterActivity extends AppCompatActivity implements IRegisterLoginActivity, Observer {
    private ClientM model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = (LoginFragment)fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container,
                    fragment).commit();
        }
        model = ClientM.get();
        model.addObserver(this);
    }

    @Override
    public void switchToRegisterFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new RegisterFragment();
        fm.beginTransaction().replace(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }

    @Override
    public void switchToLoginFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new LoginFragment();
        fm.beginTransaction().replace(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }

    @Override
    public void login(String hostname, String username, String password) {
        new LoginTask(this).execute(hostname, username, password);
    }

    @Override
    public void register(String hostname, String username, String password, String passwordConfirm) {
        if (password.equals(passwordConfirm))
        {
            new RegisterTask(this).execute(hostname, username, password);
        }
        else
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (model == o)
        {
            if(arg instanceof String){
                final String error = (String)arg;
                final Context context = this;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
            else if(arg instanceof UserM) {
                Intent intent = new Intent(this, GameWaitingActivity.class);
                startActivity(intent);
            }
        }
    }

    public static class LoginTask extends AsyncTask<String, Void, Boolean>
    {
        private Context mContext;

        LoginTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                ServerProxy.init(strings[0]);
            } catch (UnknownHostException e) {
                return false;
            } catch (Exception e) {
                //YOU DID THE BAD THING
                e.printStackTrace();
                return false;
            }
            ClientLoginService loginService = new ClientLoginService(strings[1], strings[2]);
            loginService.Login();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                Toast.makeText(mContext, "Host error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class RegisterTask extends AsyncTask<String, Void, Boolean>
    {
        private Context mContext;

        RegisterTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                ServerProxy.init(strings[0]);
            } catch (UnknownHostException e) {
                return false;
            } catch (Exception e) {
                //YOU DID THE BAD THING
                e.printStackTrace();
                return false;
            }
            ClientRegisterService registerService = new ClientRegisterService(strings[1], strings[2]);
            registerService.Register();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result) {
                Toast.makeText(mContext, "Host error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
