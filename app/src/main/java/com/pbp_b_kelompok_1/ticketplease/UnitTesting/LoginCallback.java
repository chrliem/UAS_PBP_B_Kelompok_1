package com.pbp_b_kelompok_1.ticketplease.UnitTesting;

import com.pbp_b_kelompok_1.ticketplease.models.User;

public interface LoginCallback {
    void onSuccess(boolean value, User user);
    void onError();
}
