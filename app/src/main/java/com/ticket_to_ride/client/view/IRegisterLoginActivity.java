package com.ticket_to_ride.client.view;

public interface IRegisterLoginActivity {
    void switchToRegisterFragment();

    void switchToLoginFragment();

    void login(String hostname, String username, String password);

    void register(String hostname, String username, String password, String passwordConfirm);
}
