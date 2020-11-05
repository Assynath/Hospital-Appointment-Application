package com.example.hospie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerPage extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextFullName, editTextAge, editTextMail, editTextGender, editTextPassword;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView)findViewById(R.id.textView5);
        banner.setOnClickListener(this);

        registerUser = (Button)findViewById(R.id.button2);
        registerUser.setOnClickListener(this);

        editTextFullName= (EditText)findViewById(R.id.editTextTextPersonName2);
        editTextAge = (EditText)findViewById(R.id.editTextTextPersonName3);
        editTextMail = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        editTextGender = (EditText)findViewById(R.id.editTextTextPersonName4);
        editTextPassword = (EditText)findViewById(R.id.editTextTextPassword2);

        progressbar = (ProgressBar)findViewById(R.id.progressBar2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView5:
                startActivity(new Intent(this, loginPage.class));
                break;
            case R.id.button2:
                registerUser();
                break;
        }

    }


    private void registerUser(){
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("Full Name is Required");
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()){
            editTextAge.setError("Age is Required");
            editTextAge.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextMail.setError("Email is Required");
            editTextMail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextMail.setError("Please provide valid email address");
            editTextMail.requestFocus();
            return;

        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6) {
            editTextPassword.setError("Minimum password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (gender.isEmpty()){
            editTextGender.setError("Gender is Required");
            editTextGender.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullName, age, email, gender);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(registerPage.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.VISIBLE);
                            } else{
                                Toast.makeText(registerPage.this, "Failed to register! Try again!!", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    });

                }else {
                    Toast.makeText(registerPage.this, "Failed to register! Try again!!", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}