package com.example.whatsapp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp_android.R;
import com.example.whatsapp_android.adapters.ContactsAdapter;
import com.example.whatsapp_android.databinding.ActivityMainBinding;
import com.example.whatsapp_android.entities.Contact;
import com.example.whatsapp_android.listeners.ContactListener;
import com.example.whatsapp_android.utilities.Constants;
import com.example.whatsapp_android.utilities.PreferenceManager;
import com.example.whatsapp_android.viewmodels.ContactsViewModel;



public class MainActivity extends AppCompatActivity implements ContactListener {

    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private ContactsViewModel contactsViewModel;
    private int size = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        loadUserDetails();

        ContactsAdapter contactsAdapter = new ContactsAdapter(this);
        binding.conversationsRecyclerView.setAdapter(contactsAdapter);

        contactsViewModel = new ContactsViewModel(preferenceManager);
        contactsViewModel.get().observe(this, contacts -> {
            loading(true);

            binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

            contactsAdapter.setContacts(contacts);
            contactsAdapter.notifyItemRangeChanged(0,contactsAdapter.getItemCount() + size);
            size = contactsAdapter.getItemCount();

            loading(false);
        });
        setListeners();
    }

    private void setListeners() {
        binding.imageSignOut.setOnClickListener(v -> signOut());
        binding.btnNewChat.setOnClickListener(v -> addNewUser());
    }

    void addNewUser() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_user_dialog);
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        final EditText usernameET = dialog.findViewById(R.id.inputUsername);
        final EditText nameET = dialog.findViewById(R.id.inputNickname);
        final EditText serverET = dialog.findViewById(R.id.inputServer);

        TextView btn = dialog.findViewById(R.id.btnAdd);
        ImageView back = dialog.findViewById(R.id.imageBack);

        back.setOnClickListener(v -> dialog.hide());

        btn.setOnClickListener(v -> {
            String username = usernameET.getText().toString();
            String name = nameET.getText().toString();
            String server = serverET.getText().toString();

            //API
            Contact contact = new Contact(username,name, server);
            contactsViewModel.add(contact);
            dialog.hide();
        });

        dialog.show();
    }


    private void loadUserDetails()  {
        binding.textName.setText(preferenceManager.getString(Constants.KEY_NICKNAME));
        String image = preferenceManager.getString(Constants.KEY_IMAGE);
        if(image != null) {
            byte[] bytes = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            binding.imageProfile.setImageBitmap(bitmap);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        showToast("Signin out...");
        //Logout firebase
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        finish();
    }

    @Override
    public void onContactClicked(Contact contact) {
        preferenceManager.putString(Constants.KEY_CONTACT, contact.getId());
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_CONTACT, contact);
        startActivity(intent);
    }

    void loading(Boolean isLoading) {
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}