package com.example.musicapp.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.google.gson.Gson;



public class RegisterActivity extends AppCompatActivity {
    private static EditText edUserNameC;
    private static EditText edPasswordC;
    private static EditText edConfirmPasswordC;
    private EditText edEmailC;
    private EditText edPhoneNumberC;
    private RadioGroup rbSex;
    private Button btnRegister;
    private ImageButton imbBack;
    private SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    private final Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        //Khai báo share Pre
        sharedPreferences = getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //lâ dữ liệu
        anhxadulieu();
        taosukien();
    }
    void anhxadulieu()
    {
        edUserNameC = findViewById(R.id.edUserNameRe);
        edPasswordC = findViewById(R.id.edPasswordRe);
        edConfirmPasswordC = findViewById(R.id.edPasswordRe);
        edEmailC = findViewById(R.id.edEmail);
        edPhoneNumberC = findViewById(R.id.edPhone);
        rbSex = findViewById(R.id.rgSex);
        btnRegister = findViewById(R.id.btRegister);
        imbBack = findViewById(R.id.imbBack);
    }
    void taosukien()
    {
        btnRegister.setOnClickListener(v -> sukienRegister());
        imbBack.setOnClickListener(v -> finish());
    }
    void sukienRegister()
    {
        String userName = edUserNameC.getText().toString().trim();
        String password = edPasswordC.getText().toString().trim();
        String confirmPassword = edConfirmPasswordC.getText().toString().trim();
        String email = edEmailC.getText().toString().trim();
        String phone = edPhoneNumberC.getText().toString().trim();
        //nếu sex = 1 là nam , = 0 là nữ
        int sex = 1;
        boolean isValid = checkUserName(userName) && checkPassword(password , confirmPassword);
        if(isValid) {
            // nếu dữ liệu hợp lệ , tạo dối tượng user để lưu vào share preference.
            User userNew = new User();
            userNew.setUserName(userName);
            userNew.setPassword(password);
            userNew.setEmail(email);
            userNew.setPhoneNumber(phone);
            //lấy radio button id đang được checked
            int sexSelected = rbSex.getCheckedRadioButtonId();
            if(sexSelected == R.id.rbFemale)
            {
                sex = 0;
            }
            userNew.setSex(sex);
            //vì user là object nên convert qua string với format là json để lưu vào share preference
            String userStr = gson.toJson(userNew);
            editor.putString(Utils.KEY_USER, userStr);
            editor.commit();
            //dùng Toast để show thông báo đăng ký thành công
            Toast.makeText(RegisterActivity.this , "Đăng ký tài khoản thành công ",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    static boolean checkUserName(String username)
    {
      if (username.isEmpty())
      {
          edUserNameC.setError("Vui lòng nhập tên đăng nhập");
          return false;
      }
      if(username.length() <= 5 )
      {
          edUserNameC.setError("Ít nhất 6 ký tự");
          return false;
      }
      return true;
    }
    static boolean checkPassword(String password , String confirmPassword)
    {
        if(password.isEmpty())
        {
            edPasswordC.setError("Vui lòng nhập");
            return false;
        }
        if(password.length() <= 5 )
        {
            edPasswordC.setError("Ít nhất 6 ký tự");
        }
        if (!password.equals(confirmPassword))
        {
            edConfirmPasswordC.setError("xác nhận mật khẩu không khớp");
            return false;
        }
        return true;
    }
}