package com.ssv.GuitarShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editPassword2;
    private CheckBox checkAgree;
    private Button buttonSignUp;
    private FirebaseAuth mAuth;
    private TextView textlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        FindViews();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (checkEditText(editEmail) && ktraaemail() && checkEditText(editUsername) && checkEditText(editPassword) && checkEditText(editPassword2) &&checkAgree.isChecked() && checkrepass())
                {
                    mAuth.createUserWithEmailAndPassword(
                            editEmail.getText().toString(),
                            editPassword.getText().toString()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Vui lòng kiểm tra lại thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void FindViews() {
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editPassword2 = findViewById(R.id.editPassword2);
        checkAgree = findViewById(R.id.editcheckbox);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textlogin = findViewById(R.id.textlogin);
    }

    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }
    private boolean checkrepass(){
        String str1 = editPassword.getText().toString();
        String str2 = editPassword2.getText().toString();
        if (str1.equals(str2))
        {
            return true;
        }
        else{
            editPassword2.setError("Mật khẩu không khớp");
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