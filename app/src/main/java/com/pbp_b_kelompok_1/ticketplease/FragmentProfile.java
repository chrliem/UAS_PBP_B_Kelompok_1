package com.pbp_b_kelompok_1.ticketplease;

import static com.android.volley.Request.Method.GET;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.api.TicketEventApi;
import com.pbp_b_kelompok_1.ticketplease.api.UserApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEventResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;
import com.pbp_b_kelompok_1.ticketplease.models.UserResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class FragmentProfile extends Fragment {
    private UserResponse userResponse;
    private ImageButton btnSetting;
    private Button btnLogout;
    private TextView textNama, textUsername, textEmail, textAlamat;
    private User user;
    private UserPreferences userPreferences;
    private Long id;
    private ImageView ivProfile;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textNama = view.findViewById(R.id.tvNama);
        textUsername = view.findViewById(R.id.tvUsername);
        textEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnSetting = view.findViewById(R.id.btnSetting);
        ivProfile = view.findViewById(R.id.iv_profilePhoto);

        userPreferences = new UserPreferences(this.getContext());
        user = userPreferences.getUserLogin();

        if(getArguments() != null){
            id = getArguments().getLong("id");
            getUserbyId(id);
            Toast.makeText(getContext(),id+"Fragment Profile", Toast.LENGTH_LONG).show();
        }





        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("id",id);
                Fragment fragment = new FragmentProfileEdit();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout")
                        .setMessage("Are You Sure?")
                        .setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                userPreferences.logout();
                                Toast.makeText(getContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
                                checkLogin();
                            }
                        })
                        .show();
            }
        });

//        Toast.makeText(getContext(), " "+userPreferences.getUserLogin().getId(), Toast.LENGTH_SHORT).show();
    }

    private void checkLogin() {
//        Fungsi ini akan mengecek jika user login,
//        akan memunculkan toast jika tidak redirect ke login activity
        if (!userPreferences.checkLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "Heyy Kamu Sudah Login !!", Toast.LENGTH_SHORT).show();
        }
    }

//    public void changeFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layout_fragment, fragment)
//                .commit();
//    }

    private void getUserbyId(Long id){
        StringRequest stringRequest = new StringRequest(GET, UserApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response, UserResponse.class);
                textNama.setText(user.getFullName());
                textUsername.setText(user.getUsername());
                textEmail.setText(user.getEmail());
                Glide.with(getContext())
                        .load(user.getImgUrl())
                        .into(ivProfile);
                Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());
                return headers;
            }
        };            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


    }
}