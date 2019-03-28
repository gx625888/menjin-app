package com.example.yunxuan.menjin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yunxuan.menjin.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_phone)
    EditText login_phone;

    String usertoken = null;
    String bindphone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取保存在本地的token值
        SharedPreferences preferences = getSharedPreferences("usertoken", Context.MODE_PRIVATE);
        usertoken = preferences.getString("token","");
        bindphone = preferences.getString("mobilephone","");
        dologin(usertoken);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);


//        SharedPreferences preferences=getSharedPreferences("usertoken",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.clear().commit();
    }


    /**
     * 验证是否需要登录
     * @param user_token
     */
    public void dologin(String user_token){
        if(!user_token.isEmpty()){//如果token为空需要登录
            //创建intent
            Intent intent = new Intent(this, MainActivity.class);
            //传值给下一个Activity
            intent.putExtra("extra_data", bindphone);
            startActivity(intent);
        }
    }

    public void tohomelist(final View view){
        //创建intent
        Intent intent = new Intent(this, MainActivity.class);
        //获取登录手机号
        String loginphone = login_phone.getText().toString();
        //验证手机号是否填写
        if (loginphone.isEmpty()){//未填写
            ToastUtil.showShort("请填写手机号！",LoginActivity.this);
        }else {//已填写
            //传值给下一个Activity
            intent.putExtra("extra_data", loginphone);
            startActivity(intent);
        }
    }
}
