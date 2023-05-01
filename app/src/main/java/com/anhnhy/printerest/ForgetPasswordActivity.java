package com.anhnhy.printerest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anhnhy.printerest.db.Database;
import com.anhnhy.printerest.model.User;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText txt_email, txt_password, txt_repassword;
    Button btn_updatePassword, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
        Database database = new Database(this);
        btn_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String repassword = txt_repassword.getText().toString();
                if (email == null || email.isEmpty() ||
                        password == null || password.isEmpty() ||
                        repassword == null || repassword.isEmpty()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
                } else {
                    User userUpdate = database.getUserByEmail(email.toLowerCase());
                    if (userUpdate == null) {
                        Toast.makeText(ForgetPasswordActivity.this, "Email chưa được đăng ký", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(repassword)) {
                            database.updatePassword(new User(userUpdate.getId(), userUpdate.getName(), userUpdate.getEmail(), password));
                            Toast.makeText(ForgetPasswordActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_repassword = findViewById(R.id.txt_re_password);
        btn_updatePassword = findViewById(R.id.btn_updatePassword);
        btn_back = findViewById(R.id.btn_back);
    }
}