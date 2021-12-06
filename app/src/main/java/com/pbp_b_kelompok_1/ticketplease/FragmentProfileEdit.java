package com.pbp_b_kelompok_1.ticketplease;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

//import com.pbp_b_kelompok_1.ticketplease.Database.Database;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.api.TicketEventApi;
import com.pbp_b_kelompok_1.ticketplease.api.UserApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEventResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.models.UserResponse;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FragmentProfileEdit extends Fragment {

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_PICTURE = 1;

    private ImageButton btnEditNama, btnEditUsername, btnEditEmail, btnEditAlamat;
    private Button btnCancel, btnConfirm, btnUpdate;
    private EditText tvUsername, tvEmail, tvAlamat;
    private EditText tvNama;
    private ImageView photoProfile;
    private FloatingActionButton btnCamera;
    private EditText textEdit;
    private UserPreferences userPreferences;
    private User user;
    private Bitmap bitmap = null;
    private UserResponse userResponse;
    private Long id;

    public FragmentProfileEdit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCancel = view.findViewById(R.id.btnCancel);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnCamera = view.findViewById(R.id.btnCamera);
        photoProfile = view.findViewById(R.id.photoProfile);
        tvNama = view.findViewById(R.id.tvNamaEdit);
        tvUsername = view.findViewById(R.id.tvUsernameEdit);
        tvEmail = view.findViewById(R.id.tvEmailEdit);

        userPreferences = new UserPreferences(this.getContext());
        user = userPreferences.getUserLogin();
        if(getArguments() != null){
            id = getArguments().getLong("id");
            getUserbyId(id);
            Toast.makeText(getContext(),id+" Fragment Edit Profile", Toast.LENGTH_LONG).show();
        }

        tvNama.setText(user.getFullName());
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
        Glide.with(getContext())
                .load(user.getImgUrl())
                .into(photoProfile);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(id);
                onBackPressed();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View selectMediaView = layoutInflater.inflate(R.layout.layout_media_select, null);

                final android.app.AlertDialog alertDialog = new AlertDialog.Builder(selectMediaView.getContext()).create();

                Button btnKamera = selectMediaView.findViewById(R.id.btn_kamera);
                Button btnGaleri = selectMediaView.findViewById(R.id.btn_galeri);

                btnKamera.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA};
                            requestPermissions(permission, PERMISSION_REQUEST_CAMERA);
                        } else {
                            // Membuka kamera
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }

                        alertDialog.dismiss();
                    }
                });

                btnGaleri.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Membuka galeri
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_PICTURE);

                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(selectMediaView);
                alertDialog.show();
            }
        });
    }
//    KAMERA
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Membuka kamera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            Uri selectedImage = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            bitmap = (Bitmap) data.getExtras().get("data");
        }

        bitmap = getResizedBitmap(bitmap, 512);
        photoProfile.setImageBitmap(bitmap);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }
//    ===================================================================================
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    private void getUserbyId(long id){
        StringRequest stringRequest = new StringRequest(GET, UserApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response, UserResponse.class);

                User user = userResponse.getUser();

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

    private void updateUser(Long id){

        User user = new User(
                null,
                tvNama.getText().toString(),
                tvEmail.getText().toString(),
                tvUsername.getText().toString(),
                null,
                bitmapToBase64(bitmap)
        );

        StringRequest stringRequest = new StringRequest(PUT, UserApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response, UserResponse.class);

                Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                getActivity().setResult(RESULT_OK, returnIntent);
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getContext().getApplicationContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
//                headers.get(userPreferences.getUserLogin().getAccessToken());
                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());  //nanti ini token ambil dari userPreference
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(user);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}