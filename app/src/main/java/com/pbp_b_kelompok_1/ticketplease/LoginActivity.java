package com.pbp_b_kelompok_1.ticketplease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.pbp_b_kelompok_1.ticketplease.api.UserApi;
import com.pbp_b_kelompok_1.ticketplease.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextInputLayout textUsername, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername = findViewById(R.id.inputLoginUsername);
        textPassword = findViewById(R.id.inputLoginPassword);

        TextView textLink = findViewById(R.id.textLink);
        textLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = textUsername.getEditText().getText().toString();
                String password = textPassword.getEditText().getText().toString();

                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    if (username.trim().isEmpty()) {
                        textUsername.setError("Username cannot be Empty");
                    }
                    if (password.trim().isEmpty()) {
                        textPassword.setError("Password cannot be Empty");
                    }
                } else {
                    login(textUsername.getEditText().getText().toString().trim(),
                            textPassword.getEditText().getText().toString().trim());
                    Toast.makeText(LoginActivity.this, "Berhasil Login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void login(String username, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserApi.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);

                    if (!object.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONObject userObject = object.getJSONObject("user");
                        User user = new User(
                                userObject.getInt("id"),
                                userObject.getString("name"),
                                userObject.getString("email"),
                                userObject.getString("username"),
                                userObject.getString("password")
                        );
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}