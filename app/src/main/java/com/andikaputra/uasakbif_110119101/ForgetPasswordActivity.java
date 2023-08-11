package com.andikaputra.uasakbif_110119101;

/**
 *
 * NIM      : 10119101
 * Nama     : Andika Putra
 * Kelas    : IF-1
 *
 * **/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText inputanEmail;
    private Button btnreset;
    private TextView btnlogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //Inisialisasi Variabel diatas
        inputanEmail = (EditText) findViewById(R.id.email);
        btnreset = (Button) findViewById(R.id.btn_reset);
        btnlogin = (TextView) findViewById(R.id.btn_log);
        auth = FirebaseAuth.getInstance();

        //Progress Dialog
        progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.setCancelable(false);

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputanEmail.getText().toString();
                if (TextUtils.isEmpty(email)){
                    inputanEmail.setError("Email tidak boleh kosong! harus diisi!");
                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(ForgetPasswordActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPasswordActivity.this, "reset password berhasil, silahkan cek email!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this, "password gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        // Kembali ke Login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
            }
        });

    }


}