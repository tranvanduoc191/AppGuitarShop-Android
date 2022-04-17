package com.ssv.GuitarShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button buttonSignIn;
    private TextView textSignIn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        findView();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEditText(editEmail) && ktraaemail() && checkEditText(editPassword))
                {
                    mAuth.signInWithEmailAndPassword(
                            editEmail.getText().toString(),
                            editPassword.getText().toString()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                startActivity(intent);
//                             finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Sai Email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra lại thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findView() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        textSignIn = findViewById(R.id.textsignup);
    }

    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean ktraaemail()
    {
        if (editEmail.getText().toString().trim().matches(emailPattern))
            return true;
        else
            editEmail.setError("Email không đúng định dạng");
        return false;
    }
}
