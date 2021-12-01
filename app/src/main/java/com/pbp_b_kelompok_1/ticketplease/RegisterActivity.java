package com.pbp_b_kelompok_1.ticketplease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout textNama, textEmail, textUsername, textPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                String email = textEmail.getEditText().getText().toString();
                String username = textUsername.getEditText().getText().toString();
                String password = textPassword.getEditText().getText().toString();

                if(nama.trim().isEmpty() || email.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()){
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
                    register(textNama.getEditText().getText().toString(),
                            textEmail.getEditText().getText().toString().trim(),
                            textUsername.getEditText().getText().toString().trim(),
                            textPassword.getEditText().getText().toString().trim());
                    Toast.makeText(RegisterActivity.this, "Berhasil Register Akun !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void register(String nama, String email, String username, String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserApi.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    if(!object.getBoolean("error")){
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
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", nama);
                params.put("email", email);
                params.put("password", username);
                params.put("gender", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}