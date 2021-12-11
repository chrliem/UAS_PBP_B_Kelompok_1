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
import com.android.volley.DefaultRetryPolicy;
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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout textNama, textEmail, textUsername, textPassword;
    private Button btnRegister;
    private UserPreferences userPreferences;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userPreferences = new UserPreferences(RegisterActivity.this);

        textNama = findViewById(R.id.inputRegisName);
        textEmail = findViewById(R.id.inputRegisEmail);
        textUsername = findViewById(R.id.inputRegisUsername);
        textPassword = findViewById(R.id.inputRegisPassword);
        TextView textLink = findViewById(R.id.textLink);
        btnRegister = findViewById(R.id.btnRegister);

        textLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = textNama.getEditText().getText().toString();
                email = textEmail.getEditText().getText().toString();
                String username = textUsername.getEditText().getText().toString();
                password = textPassword.getEditText().getText().toString();

                if(nama.trim().isEmpty() || email.trim().isEmpty() || username.trim().isEmpty()
                        || password.trim().isEmpty()){
                    if(nama.trim().isEmpty()){
                        textNama.setError("Nama must be filled with text");
                    }
                    if(email.isEmpty()){
                        textEmail.setError("Email must be filled with text");
                    }
                    if(username.isEmpty()){
                        textUsername.setError("Username must be filled with text");
                    }
                    if(password.isEmpty()){
                        textPassword.setError("Password must be filled with text");
                    }
                }
                else{
                    register();
                }
            }
        });
    }

    public void register(){
        User user = new User(
                null,
                null,
                textNama.getEditText().getText().toString(),
                textEmail.getEditText().getText().toString(),
                textUsername.getEditText().getText().toString(),
                textPassword.getEditText().getText().toString(),
                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
        );

        StringRequest stringRequest = new StringRequest(POST, UserApi.REGISTER_URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response, UserResponse.class);

                Toast.makeText(RegisterActivity.this, userResponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(RegisterActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this, e.getMessage(),
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}