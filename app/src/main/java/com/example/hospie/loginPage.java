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

public class loginPage extends AppCompatActivity implements View.OnClickListener{


     private TextView register, forgotPassword;
     private EditText editTextEmail, editTextPassword;
     private Button signIn;

     private FirebaseAuth mAuth;
     private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        register = (TextView)findViewById(R.id.textView4);
        register.setOnClickListener(this);

        signIn = (Button)findViewById(R.id.btnsignin);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.edtEmail);
        editTextPassword = (EditText)findViewById(R.id.edtPassword);

        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);

        forgotPassword = (TextView)findViewById(R.id.textView3);
        forgotPassword.setOnClickListener((this));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView4:
                startActivity(new Intent(this, registerPage.class));
                break;
            case R.id.btnsignin:
                userLogin();
                break;
            case R.id.textView3:
                startActivity(new Intent(this, forgotPassword.class));
                break;
        }
    }


    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
             editTextEmail.setError("Email is required!");
             editTextEmail.requestFocus();
             return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum characters should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(loginPage.this, homePage.class));
                }else {
                    Toast.makeText(loginPage.this, "Failed to login!! Please check your credentials", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}
