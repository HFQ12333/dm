package com.example.myorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    String user;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View e){
        RadioGroup rg;
        EditText e1 = findViewById(R.id.editText);
        EditText e2 = findViewById(R.id.editText2);
        user = e1.getText().toString();
        pass = e2.getText().toString();

        rg = findViewById(R.id.rad);
        switch(rg.getCheckedRadioButtonId()){
            case R.id.radioButton:
                if(user.equals("15535972571")){
                    if(pass.equals("87654321")){
                        Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                        onUser(e);
                    }else{
                        Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"账号不存在",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioButton2:
                if(user.equals("13453930644")){
                    if(pass.equals("12345678")){
                        Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                        onAdmin(e);
                    }else{
                        Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"账号不存在",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void onAdmin(View e){
        intent = new Intent();
        intent.putExtra("extra_admin",user);
        intent.setClass(MainActivity.this,com.example.myorder.Activity_admin.class);
        startActivity(intent);
    }
    public void onUser(View e){
        intent = new Intent();
        intent.putExtra("extra_user",user);
        intent.setClass(MainActivity.this,com.example.myorder.Actitvity_User.class);
        startActivity(intent);
    }
}
