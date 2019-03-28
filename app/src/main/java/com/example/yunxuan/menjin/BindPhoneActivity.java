package com.example.yunxuan.menjin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yunxuan.menjin.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BindPhoneActivity extends AppCompatActivity {

    @BindView(R.id.input_phone)
    EditText inputphone;
    @BindView(R.id.bindcode)
    EditText bindcode;
    @BindView(R.id.bindbutton)
    Button bindbutton;

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final String GET_BINDCODE_URL = "http://47.101.175.155:2280/door/querySmsCode.itf";//获取验证码的URL
    private static final String BIND_PHONE = "http://47.101.175.155:2280/door/bind.itf";//绑定手机的URL
    private static final String UNBIND_PHONE = "http://47.101.175.155:2280/door/unbind.itf";

    //用于判断'绑定 or 解绑'操作
    String bind_unbind = "";
    String usertoken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        //获取上一个Activity传过来的值
        Intent intent = getIntent();
        bind_unbind = intent.getStringExtra("bind_unbind");
        usertoken = intent.getStringExtra("usertoken");
        if(!bind_unbind.isEmpty()){
            bindbutton.setText("解除绑定");
        }
    }

    /**
     * 手机绑定成功后跳转至列表页面
     */
    public void toMainActivity(){
        //创建intent
        Intent intent = new Intent(this, MainActivity.class);
        //传值给下一个Activity
        intent.putExtra("extra_data", inputphone.getText().toString());
        startActivity(intent);
    }


    /**
     * 获取验证码
     * @param view
     */
    public void getbindcode(final View view){
        //获取填写手机号
        final String bindphone = inputphone.getText().toString();
        if (bindphone.isEmpty()){
            ToastUtil.showCustomShort("请填写手机号！",BindPhoneActivity.this);
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //生成参数,创建一个请求实体对象 RequestBody
                        RequestBody params = new FormBody.Builder()
                                .add("appId", "YKSTEC_APP")
                                .add("mobile",bindphone)
                                .build();
                        //创建OkHttpClient对象
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(GET_BINDCODE_URL)
                                .post(params)
                                .build();//创建Request 对象
                        Response response = client.newCall(request).execute();//得到Response 对象
                        if (response.isSuccessful()) {
                            Looper.prepare();
                            ToastUtil.showCustomLong("验证码已发送，请注意查收短信！", BindPhoneActivity.this);
                            Looper.loop();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 绑定/解绑手机
     * @param view
     */
    public void bindphone(final View view) {
        //获取填写手机号
        final String bindphone = inputphone.getText().toString();
        //获取填写验证码
        final String binc = bindcode.getText().toString();
        //判断调用'绑定'or'解除'URL
        final String  binurl;
        if(bind_unbind.isEmpty()){
            binurl = BIND_PHONE;
        }else{
            binurl = UNBIND_PHONE;
        }
        if(!bindphone.isEmpty() && !binc.isEmpty()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //生成参数,创建一个请求实体对象 RequestBody
                        RequestBody params = new FormBody.Builder()
                                .add("appId", "YKSTEC_APP")
                                .add("mobile",bindphone)
                                .add("verifyCode",binc)
                                .build();
                        //创建OkHttpClient对象
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(binurl)
                                .post(params)
                                .addHeader("token",usertoken)
                                .build();//创建Request 对象
                        Response response = null;
                        response = client.newCall(request).execute();//得到Response 对象
                        if (response.isSuccessful()) {
                            //创建SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("usertoken",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            if(bind_unbind.isEmpty()){//绑定操作-本地写入token、mobilephone
                                //向SharedPreferences写入数据
                                String token = response.header("token");
                                editor.putString("token", token);
                                editor.putString("mobilephone", bindphone);
                                editor.commit();
                            }else{//解绑操作-删除本地数据
                                //删除SharedPreferences已写入数据
                                editor.clear().commit();
                            }
                            //处理成功，返回列表页面
                            toMainActivity();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            Looper.prepare();
            if(bindphone.isEmpty())
                ToastUtil.showCustomShort("请填写手机号！", BindPhoneActivity.this);
            if(binc.isEmpty())
                ToastUtil.showCustomShort("请填写验证码！", BindPhoneActivity.this);
            Looper.loop();
        }
    }
}
