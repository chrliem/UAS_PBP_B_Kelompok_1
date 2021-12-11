package com.pbp_b_kelompok_1.ticketplease;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.api.UserApi;
import com.pbp_b_kelompok_1.ticketplease.models.User;
import com.pbp_b_kelompok_1.ticketplease.models.UserResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextInputLayout textUsername, textPassword;
    private UserPreferences userPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPreferences = new UserPreferences(LoginActivity.this);

        textUsername = findViewById(R.id.inputLoginUsername);
        textPassword = findViewById(R.id.inputLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        checkLogin();

        TextView textLink = findViewById(R.id.textLink);
        textLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
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
                    login();
                }
            }
        });
    }

    public void login() {
        final String username = textUsername.getEditText().getText().toString().trim();
        final String password = textPassword.getEditText().getText().toString().trim();

        User user = new User(null, null, null, null,
                username, password, null);
        StringRequest stringRequest = new StringRequest(POST, UserApi.LOGIN_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response, UserResponse.class);

                Toast.makeText(LoginActivity.this, userResponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
                userPreferences.setLogin(
                        userResponse.getUser().getId(),
                        userResponse.getAccess_token(),
                        userResponse.getUser().getFullName(),
                        userResponse.getUser().getEmail(),
                        userResponse.getUser().getUsername(),
                        userResponse.getUser().getPassword(),
                        userResponse.getUser().getImgUrl());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                        Toast.makeText(LoginActivity.this, errors.getString("message"),
                                Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError{
                Gson gson = new Gson();
                String requestBody = gson.toJson(user);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType(){
                return "application/json";
            }

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void checkLogin(){
        if(userPreferences.checkLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

}