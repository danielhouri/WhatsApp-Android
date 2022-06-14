package com.example.whatsapp_android.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp_android.api.WhatsAppAPI;
import com.example.whatsapp_android.databinding.ActivitySignUpBinding;
import com.example.whatsapp_android.entities.User;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String encodedImage;
    private PreferenceManager preferenceManager;
    private WhatsAppAPI whatsAppAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        // Set up the API connection
        whatsAppAPI = new WhatsAppAPI();

        // Config the btn listeners
        setListeners();
    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(v -> onBackPressed());
        binding.btnSignUp.setOnClickListener(v -> {
            if (isValidSignUp()) {
                signUp();
            }
        });
        binding.layoutImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signUp() {
        loading(true);

        //API - SIGNUP
        User user = new User(binding.inputUsernameSignUp.getText().toString(),
                binding.inputNicknameSignUp.getText().toString(),
                encodedImage,
                binding.inputPasswordSignUp.getText().toString());
        Call<String> call = whatsAppAPI.signUp(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                // Ok
                if(response.code() == 200) {

                    // Add user info to the preferenceManager
                    preferenceManager.putString(Constants.KEY_TOKEN, response.body());
                    preferenceManager.putString(Constants.KEY_USERNAME, binding.inputUsernameSignUp.getText().toString());
                    preferenceManager.putString(Constants.KEY_NICKNAME, binding.inputNicknameSignUp.getText().toString());
                    preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);

                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignUpActivity.this, instanceIdResult -> {
                        String newToken = instanceIdResult.getToken();
                        whatsAppAPI.sendToken(response.body(), newToken);
                    });

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
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                loading(false);
                showToast("Can't register, try again!");
            }
        });
    }

    /**
     * This function change the image to a string.
     * @param bitmap the image
     * @return the string
     */
    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * Open the pickup image box and encode the image to Base64.
     */
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if(result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageProfile.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);
                        }
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    /**
     * Validity check for the user credentials.
     * @return true - valid, false - invalid
     */
    private Boolean isValidSignUp() {
        if (binding.inputUsernameSignUp.getText().toString().trim().isEmpty()) {
            showToast("Enter username");
            return false;
        }
        else if (encodedImage == null) {
            showToast("Select profile image");
            return false;
        }
        else if (binding.inputNicknameSignUp.getText().toString().trim().isEmpty()) {
            showToast("Enter your nickname");
            return false;
        }
        else if (binding.inputPasswordSignUp.getText().toString().trim().isEmpty() ||
                binding.inputConfirmPasswordSignUp.getText().toString().trim().isEmpty() ||
                !Pattern.matches("^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", binding.inputPasswordSignUp.getText().toString())) {
            showToast("Password must contain letters and digits");
            return false;
        }
        else if(!binding.inputPasswordSignUp.getText().toString().equals(binding.inputConfirmPasswordSignUp.getText().toString())) {
            showToast("The passwords do not match. Try again.");
            return false;
        }
        return true;
    }

    void loading(Boolean isLoading) {
        if (isLoading){
            binding.btnSignUp.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnSignUp.setVisibility(View.VISIBLE);
        }
    }
}