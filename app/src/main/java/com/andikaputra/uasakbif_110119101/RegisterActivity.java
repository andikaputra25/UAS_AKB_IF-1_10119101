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

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    TextView signin;
    private EditText editName, editEmail, editPassword, editPasswordConf;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Inisialisasi variabel
        signin = findViewById(R.id.signin);
        editName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editPasswordConf = findViewById(R.id.confirmpassword);
        btnRegister = findViewById(R.id.btnRegister);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.setCancelable(false);

        //Action back to Sigin
        signin = (TextView) findViewById(R.id.signin);
        signin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        //Action Register
        btnRegister.setOnClickListener(v -> {
            if (editName.getText().length()>0 && editEmail.getText().length()>0 && editPassword.getText().length()>0 && editPasswordConf.getText().length()>0){
                if (editPassword.getText().toString().equals(editPasswordConf.getText().toString())){
                    register(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Silahkan masukkan password yang sama!", Toast.LENGTH_SHORT).show();
                }
              }else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String name ,String email, String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful() && task.getResult()!=null){
                   // Jika Registrasi success
                   Toast.makeText(RegisterActivity.this, "Registrasi Sukses", Toast.LENGTH_LONG).show();
                   finish();
                   FirebaseUser firebaseUser = task.getResult().getUser();
                   if (firebaseUser!=null){
                       UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                               .setDisplayName(name)
                               .build();
                       firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                                reload();
                           }
                       });
                   }else {
                       Toast.makeText(getApplicationContext(), "Regitrasi Gagal!", Toast.LENGTH_SHORT).show();
                   }
               }else {
                   Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
               }
               progressDialog.dismiss();
            }
        });
    }

    private void reload(){
        startActivity  (new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}