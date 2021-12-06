package com.pbp_b_kelompok_1.ticketplease.UnitTesting;

import com.pbp_b_kelompok_1.ticketplease.models.User;

public class LoginPresenter {
    private LoginView view;
    private LoginService service;
    private LoginCallback callback;
    private User user;

    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked() {

        if (view.getUsername().isEmpty()) {
            view.showUsernameError("Nama Depan tidak boleh kosong");
            return;
        } else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Nama Belakang tidak boleh kosong");
            return;
        } else if (view.getPassword().length() < 6) {
            view.showPasswordError("Tanggal Lahir tidak boleh kurang dari 6 digit");
            return;

        }
//        else {
//            service.(view, user, new LoginCallback() {
//                @Override
//                public void onSuccess(boolean value, Profil profil) {
//                    view.startMainProfil();
//                }
//
//                @Override
//                public void onError() {
//                }
//            });
//            return;
//        }
    }
}
