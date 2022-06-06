package com.example.whatsapp_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp_android.api.WhatsAppAPI;
import com.example.whatsapp_android.databinding.ActivitySigninBinding;
import com.example.whatsapp_android.entities.User;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;
    private PreferenceManager preferenceManager;
    private WhatsAppAPI whatsAppAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        whatsAppAPI = new WhatsAppAPI();
        setListeners();
    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.btnSignIn.setOnClickListener(v -> {
            if(isValidSignIn()) {
                signIn();
            }
        });
    }

    private void signIn() {
        loading(true);

        User user = new User(binding.inputUsernameSignIn.getText().toString(), "","" ,
                binding.inputPasswordSignIn.getText().toString());
        Call<String> call = whatsAppAPI.signIn(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                // Ok
                if(response.code() == 200) {
                    preferenceManager.putString(Constants.KEY_TOKEN, response.body());
                    loading(false);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_COLLECTION_USERS, "");  //KEY COLLECTION USER
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                //Error
                else {
                    loading(false);
                    showToast("Can't register, try again!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                loading(false);
                showToast("Can't register, try again!");
            }
        });


    }

    void loading(Boolean isLoading) {
        if (isLoading){
            binding.btnSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnSignIn.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignIn() {
        if (binding.inputPasswordSignIn.getText().toString().trim().isEmpty()) {
            showToast("Enter username");
            return false;
        }
        else if (binding.inputPasswordSignIn.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        }
        return true;
    }

}