package com.example.whatsapp_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp_android.R;
import com.example.whatsapp_android.api.WhatsAppAPI;
import com.example.whatsapp_android.databinding.ActivitySigninBinding;
import com.example.whatsapp_android.entities.User;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.example.whatsapp_android.utilities.WhatsApp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

        // preferenceManager keep all user information needed
        preferenceManager = new PreferenceManager(getApplicationContext());

        // Set up the API connection
        whatsAppAPI = new WhatsAppAPI();
        preferenceManager.putString(Constants.KEY_SERVER, WhatsApp.context.getString(R.string.BaseUrl));

        // Config the btn listeners
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
        binding.settingsImage.setOnClickListener(v -> settingsDialog());
    }

    /**
     * In this dialog box the user can change the API address.
     */
    void settingsDialog() {
        // Create the dialog box
        final Dialog dialog = new Dialog(SignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.settings_dialog);
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        final EditText serverET = dialog.findViewById(R.id.inputServerChange);
        TextView btn = dialog.findViewById(R.id.btnChangeServer);
        ImageView back = dialog.findViewById(R.id.imageBackSetting);

        // Config the listeners
        back.setOnClickListener(v -> dialog.hide());
        btn.setOnClickListener(v -> {
            preferenceManager.putString(Constants.KEY_SERVER, serverET.getText().toString());
            dialog.hide();
        });

        // Start the dialog box
        dialog.show();
    }

    /**
     * The function send to the API the user credentials, if the user is registered the API send a
     * token and the user information. otherwise the API return message code 400.
     */
    private void signIn() {
        loading(true);

        User loginCredentials = new User(binding.inputUsernameSignIn.getText().toString(), "","" ,
                binding.inputPasswordSignIn.getText().toString());
        Call<JsonObject> call = whatsAppAPI.signIn(loginCredentials);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                // Ok
                if(response.code() == 200) {
                    assert response.body() != null;

                    // Deserialize user info and add it to the preferenceManager
                    String token = response.body().get("token").getAsString();
                    preferenceManager.putString(Constants.KEY_TOKEN, token);
                    Gson gson = new Gson();
                    User user = gson.fromJson(response.body().getAsJsonObject("data"), User.class);
                    preferenceManager.putString(Constants.KEY_USERNAME, user.getUsername());
                    preferenceManager.putString(Constants.KEY_IMAGE, user.getImage());
                    preferenceManager.putString(Constants.KEY_NICKNAME, user.getName());

                    loading(false);
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
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                loading(false);
                showToast("Can't register, try again!");
            }
        });
    }

    void loading(Boolean isLoading) {
        if (isLoading){
            binding.btnSignIn.setVisibility(View.INVISIBLE);
            binding.textCreateNewAccount.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.textCreateNewAccount.setVisibility(View.VISIBLE);
            binding.btnSignIn.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Validity check for the user credentials.
     * @return true - valid, false - invalid
     */
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