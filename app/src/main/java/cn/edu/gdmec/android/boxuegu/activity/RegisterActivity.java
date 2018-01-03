package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

/**
 * Created by student on 17/12/27.
 */
public class RegisterActivity extends AppCompatActivity {
    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private Button btn_register;//注册按钮
    private EditText et_user_name,et_psw,et_psw_again;//账号，密码，再次输入的密码的控件
    private String userName,psw,pswAgain;//账号，密码，再次输入的控件的获取值
    private RelativeLayout rl_title_bar;//标题布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);//设置页面布局
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);//从main_title_bar.xml页面布局中获得对应的UI控件
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        btn_register = (Button) findViewById(R.id.btn_register);//从activity_register.xml页面布局中获取对应的控件
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_psw_again = (EditText) findViewById(R.id.et_psw_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditString();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if (!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this,"输入两次的密码不一样",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if (isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this,"此账户名已经存在",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this,"注册成功",
                            Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(userName,psw);//把账号。密码和账号标识保存到sp里面
                    Intent data = new Intent();//注册成功后把账号传递到LogActivity.java中
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();
                }
            }
            //保存账号密码到SharePreference中
            private void saveRegisterInfo(String userName, String psw) {
                String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
                SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor  =sp.edit();
                editor.putString(userName,md5Psw);
                editor.commit();
            }
            //从SharePreferences中读取输入的用户名，判断SharePreences中是否由此用户名
            private boolean isExistUserName(String userName) {
                boolean has_userName = false;
                SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
                String spPsw = sp.getString(userName,"");
                if (!TextUtils.isEmpty(spPsw)){
                    has_userName=true;
                }
                return has_userName;
            }
            //获取控件字符串
            private void getEditString() {
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                pswAgain = et_psw_again.getText().toString().trim();
            }
        });
    }
}