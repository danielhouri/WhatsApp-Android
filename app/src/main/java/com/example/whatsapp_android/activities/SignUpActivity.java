package com.example.whatsapp_android.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.whatsapp_android.databinding.ActivitySignUpBinding;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private String encodedImage;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
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

        //Ok
        loading(false);
        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        preferenceManager.putString(Constants.KEY_COLLECTION_USERS, "");  //KEY COLLECTION USER
        preferenceManager.putString(Constants.KEY_USERNAME, binding.inputUsernameSignUp.toString());
        preferenceManager.putString(Constants.KEY_NICKNAME, binding.inputNicknameSignUp.toString());
        preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        //False
        //loading(false);
        //showToast("Error");
    }

    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

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

    private Boolean isValidSignUp() {
        /*if (encodedImage == null) {
            showToast("Select profile image ");
            return false;
        }
        else if (binding.inputUsernameSignUp.getText().toString().trim().isEmpty()) {
            showToast("Enter username");
            return false;
        }
        else if (binding.inputNicknameSignUp.getText().toString().trim().isEmpty()) {
            showToast("Enter username");
            return false;
        }
        else if (binding.inputPasswordSignUp.getText().toString().trim().isEmpty() ||
                binding.inputConfirmPasswordSignUp.getText().toString().trim().isEmpty() ||
                !Pattern.matches("^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", binding.inputPasswordSignUp.getText().toString())) {
            showToast("Enter a valid password (must contain letters and digits)");
            return false;
        }
        else if(!binding.inputPasswordSignUp.getText().toString().equals(binding.inputConfirmPasswordSignUp.getText().toString())) {
            showToast("The passwords do not match. Try again.");
            return false;
        }*/
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