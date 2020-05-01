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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout Email,Password,pwulang;
    ProgressBar progressBar;
    Button btnMasuk,regis;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = FirebaseDatabase.getInstance();
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        pwulang = findViewById(R.id.et_password2);
        btnMasuk = findViewById(R.id.bt_login);
        regis = findViewById(R.id.bt_signup);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        regis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!validateForm()) {
                    return;
                }
                String email = Email.getEditText().getText().toString();
                String password = Password.getEditText().getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(String.valueOf(task), "signIn:onComplete:" + task.isSuccessful());
                                //hideProgressDialog();

                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(Register.this,Login.class));
                                } else {

                                    Toast.makeText(Register.this, "Sign In Failed",
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

            }
        });
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(Email.getEditText().getText().toString())||Email.getEditText().length() < 6) {
            Email.setError("Periksa Kembali Email Anda Minimal 6 huruf");
            result = false;
        } else {
            Email.setError(null);
        }

        if (TextUtils.isEmpty(Password.getEditText().getText().toString())||Password.getEditText().length() < 6||isValidPassword(Password.getEditText().getText().toString())) {
            Password.setError("Password Minimal 6 huruf dan Terdapat Huruf , Angka dan simbol");
            result = false;
        } else {
            Password.setError(null);
        }

        if (TextUtils.isEmpty(pwulang.getEditText().getText().toString())||pwulang.getEditText().length() < 6) {
            if (!pwulang.equals(Password))
            {
                Toast.makeText(Register.this, "Password do not match", Toast.LENGTH_SHORT).show();
            }
            pwulang.setError("Periksa Kembali Password Anda Minimal 6 huruf");
            result = false;
        } else {
            pwulang.setError(null);
        }

        return result;
    }
}
