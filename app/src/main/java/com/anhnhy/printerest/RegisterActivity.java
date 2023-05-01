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

public class RegisterActivity extends AppCompatActivity {

    EditText txt_email, txt_name, txt_password, txt_repassword;
    Button btn_register, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        Database database = new Database(this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                String name = txt_name.getText().toString();
                String password = txt_password.getText().toString();
                String repassword = txt_repassword.getText().toString();
                if (email == null || email.isEmpty() ||
                        password == null || password.isEmpty() ||
                        name == null || name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
                } else {
                    User userRegister = database.getUserByEmail(email.toLowerCase());
                    if (userRegister != null) {
                        Toast.makeText(RegisterActivity.this, "Email đã được đăng ký", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(repassword)) {
                            database.createSuperUser(new User(email, name, password));
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        txt_email = findViewById(R.id.txt_email);
        txt_name = findViewById(R.id.txt_name);
        txt_password = findViewById(R.id.txt_password);
        txt_repassword = findViewById(R.id.txt_re_password);
        btn_register = findViewById(R.id.btn_register);
        btn_back = findViewById(R.id.btn_back);
    }
}