package com.pbp_b_kelompok_1.ticketplease.UnitTesting;

public interface LoginView {
    
    String getUsername();
    String getPassword();

    void showUsernameError(String message);
    void showPasswordError(String message);

    void startMainLogin();

    void showLoginError(String message);

    void showErrorResponse(String message);
}
