package com.faresa.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout Email,Password;
    Button btnMasuk,regis;
    private FirebaseDatabase mDatabase;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        btnMasuk = findViewById(R.id.bt_login);
        regis = findViewById(R.id.bt_signup);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

     
        btnMasuk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!validateForm()) {
                    return;
                }

                String email = Email.getEditText().getText().toString();
                String password = Password.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(String.valueOf(task), "signIn:onComplete:" + task.isSuccessful());
                                //hideProgressDialog();

                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Sign In Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }


    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(Email.getEditText().getText().toString())) {
            Email.setError("Pastikan Memasukan Alamat Email Yang benar");
            result = false;
        } else {
            Email.setError(null);
        }

        if (TextUtils.isEmpty(Password.getEditText().getText().toString())) {
            Password.setError("Masukan Password Yang Benar");
            result = false;
        } else {
            Password.setError(null);
        }

        return result;
    }


}
