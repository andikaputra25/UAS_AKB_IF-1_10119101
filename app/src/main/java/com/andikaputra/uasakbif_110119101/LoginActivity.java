package com.andikaputra.uasakbif_110119101;

/*
* NIM       : 10119101
* Nama      : Andika Putra
* Kelas     : IF-1
 * **/

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    TextView reg, forgotpassword;
    EditText inputEmail, inputPassword;
    Button btn_signin;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    private ProgressDialog progressDialog;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnGoogle;

    public LoginActivity() throws ApiException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inisialisasi edittext dan button
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btn_signin = findViewById(R.id.btn_signin);
        btnGoogle = findViewById(R.id.btn_google);


        //inisialisasi Firebase Auth dan Firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //Progres Dialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Tunggu Sebentar");
        progressDialog.setCancelable(false);

        //untuk lupa password
        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        forgotpassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgot);
            }
        });

        //Untuk membuat akun baru Register (Sign Up)
        reg = (TextView)  findViewById(R.id.create_account);
        reg.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(create);
            }
        });

        //Action button login (SignIn)
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cek inputan Login (Email dan Password)
               if (inputEmail.getText().length()>0 && inputPassword.getText().length()>0){
                   Login(inputEmail.getText().toString(), inputPassword.getText().toString());
               }else {
                   Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
               }
            }
        });

        //Action Buttoon Sigin With Google
        btnGoogle.setOnClickListener(v -> {
            googlesignIn();
        });

        //Configurasi Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

        int RC_SIGN_IN = 1000;
        private void googlesignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result returned from launching the intent from gooogle Sign in
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign in Was Succesfull
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GOOGLE SIGN IN", "firebaseAuthWithGoogle:" + account.getId());
                Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //Google Sign in Failed
                Log.w("GOOGLE SIGN IN", "Google SignIn Failed", e);
            }
        }
    }

    //Validasi login (Email dan Password)
    private void Login(String email, String password) {
        //Coding Login
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null){
                    Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_LONG).show();
                    if (task.getResult().getUser()!=null){
                        reload();
                    }else {
                        Toast.makeText(getApplicationContext(), "Email dan Password Tidak terdaftar!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Login Gagal, Silahkan coba lagi!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void reload(){
        startActivity (new Intent(getApplicationContext(), MainActivity.class));
    }



    private void firebaseAuthWithGoogle(String idToken){
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("GOOGLE SIGN IN", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Users users = new Users();
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
                            reload();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("GOOGLE SIGN IN", "SignInWithCredential:failure", task.getException());
                           Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                           reload();
                        }
                        progressDialog.dismiss();
                    }
                });

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