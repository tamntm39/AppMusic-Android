package com.example.musicapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.musicapp.Activity.MainActivity;

import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.google.gson.Gson;



public class LoginActivity extends AppCompatActivity {
    Button btLogin, btRegister;
    static EditText edUserNameC;
    static EditText edPasswordC;
    SharedPreferences.Editor editor;
    private final Gson gson = new Gson();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        //
        anhxa();
        //
        sharedPreferences = getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        ;
        //
        taosukien();

    }

    private void taosukien() {
        btLogin.setOnClickListener(v -> checkUserLogin());
        btRegister.setOnClickListener(funRegister());
    }

    private void anhxa() {
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        edUserNameC = findViewById(R.id.edUserName);
        edPasswordC = findViewById(R.id.edPassword);

    }




    @NonNull
    private View.OnClickListener funRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        };
    }
    private void checkUserLogin()
    {
        String userPref = sharedPreferences.getString(Utils.KEY_USER, null);
        User user = gson.fromJson(userPref , User.class);
        //user = null có nghĩa là chưa co user đăng ký.
        if(user == null)
        {
            return ;
        }
//Kiễm tra user name và password có trùm với  đối tượng user trong share preference không .
        boolean isValid = edUserNameC.getText().toString().trim().equals(user.getUserName()) && edPasswordC.getText().toString().trim().equals(user.getPassword());
        //NẾU KẾT QUẢ trùng với user đã đăng ký thì start main activity.
        if(isValid)
        {
            Intent intent = new Intent(this, MainActivity.class);
            //khởi tạo bundle để truyền user data qua cho MainActivity
            Bundle bundle = new Bundle();
            //vì user là object nên dùng putSerializable
            bundle.putSerializable(Utils.KEY_USER_PROFILE, user);


            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}