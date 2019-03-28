package com.example.yunxuan.menjin;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunxuan.menjin.adapter.HomeListAdapter;
import com.example.yunxuan.menjin.bean.NewData;
import com.example.yunxuan.menjin.utils.ChangeDataUtils;
import com.example.yunxuan.menjin.utils.HttpUtil;
import com.example.yunxuan.menjin.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends ListActivity {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.my_phone)
    TextView myPhone;
    @BindView(R.id.bind_click)
    Button bindClick;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String homelistUrl = "http://47.101.175.155:2280/door/queryBindings.itf?appId=YKSTEC_APP&mobile=";

    String usertoken = null;

    private boolean bindTag = false;
    public String loginhpone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashHandler.getInstance().init(this);//初始化全局异常管理
        ButterKnife.bind(this);
        //获取上一个Activity传过来的值
        Intent intent = getIntent();
        loginhpone = intent.getStringExtra("extra_data");
        //获取保存在本地的token值
        SharedPreferences preferences = getSharedPreferences("usertoken", Context.MODE_PRIVATE);
        usertoken = preferences.getString("token","");
        Log.i("------", usertoken);
        //根据usertoken，显示绑定信息
        checkPhone();
        //根据登录的手机号，重新组装homelistUrl
        String getdataUrl = homelistUrl+loginhpone;
        //初始化数据、加载数据
        initData(getdataUrl);
    }

    //初始化数据
    private void initData(final String getdataUrl) {
        //下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requesthomelist(getdataUrl);
            }
        });
        //初始化默认加载数据
        requesthomelist(getdataUrl);
    }

    /**
     * 绑定手机号操作
     */
    private void checkPhone() {
        if (!usertoken.isEmpty()) {//显示手机号、显示'解除绑定'按钮
            myPhone.setVisibility(View.VISIBLE);
            StringBuffer showphone = new StringBuffer(loginhpone);
            myPhone.setText("您登录的手机号："+showphone.replace(3,7,"****"));
            bindClick.setText("解除绑定");
            bindClick.setVisibility(View.VISIBLE);
        } else {//'绑定手机'按钮显示
            bindClick.setVisibility(View.VISIBLE);
            myPhone.setVisibility(View.GONE);
        }
    }

    /**
     * 请求数据
     *
     * @param homelistUrl url
     */
    private void requesthomelist(final String homelistUrl) {
        HttpUtil.sendOkHttpRequest(usertoken,homelistUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG,e.getMessage());
                        Log.e("错误信息",e.getMessage());
                        ToastUtil.showCustomLong("请求信息失败，请联系物业！", MainActivity.this);
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //创建空json接收返回
                JSONObject jsonObject = null;
                try {
                    //接收请求返回数据
                    jsonObject = new JSONObject(responseText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取key值为'code'的value值
                String homelist_code = jsonObject.optString("code");
                //根据code判断显示内容
                if(homelist_code.equals(1)){
                    //{"code":"1","detail":"非法请求"}
                    ToastUtil.showCustomLong("该设备没有绑定，请先绑定手机号！", MainActivity.this);
                }else{
                    //获取列表数据并展示
                    String bindings = jsonObject.optString("bindings");
                    final ArrayList<NewData> datalist = ChangeDataUtils.handlelistResponse(bindings);
                    //final ResponceData homelistdata = ChangeDataUtils.handleonlylistResponse(responseText);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (datalist != null){
                                //展示获取到的数据
                                HomeListAdapter adapter = new HomeListAdapter(MainActivity.this,datalist);
                                setListAdapter(adapter);
                            }else{
                                ToastUtil.showCustomLong("请求数据失败，请先绑定手机号！", MainActivity.this);
                            }
                            swipeRefresh.setRefreshing(false);//隐藏刷新进度条
                        }
                    });
                }

            }
        });

    }

    /**
     * 跳转至绑定手机
     */
    public void tobindphoneActivity(View view){
        //创建intent
        Intent intent = new Intent(this, BindPhoneActivity.class);
        //传值给下一个Activity
        if(usertoken.isEmpty()){
            //执行绑定手机
            intent.putExtra("bind_unbind", "");
        }else{
            //执行解除绑定
            intent.putExtra("bind_unbind", "unbind");
        }
        intent.putExtra("usertoken", usertoken);
        startActivity(intent);
    }


    /**
     * 绑定手机弹窗--暂时废弃
     * @param view
     */
    public void bindhponeview(final View view){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("绑定手机：")
                        .setView(R.layout.bindphone)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String inputPhone = view.findViewById(R.id.input_phone).toString();
                                final String bindcode = view.findViewById(R.id.bindcode).toString();
                                Log.i("-----", "bindhpone: "+inputPhone+"bindcode："+bindcode);
                                Toast.makeText(MainActivity.this, inputPhone+"这是确定按钮"+bindcode, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * 请求开门-暂时废弃
     *
     */
    public void opendoor(final String deviceId, final String useId) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder od_request = new StringBuilder("https://51guangjie.com.cn/access/wxapp/opendoor.srv");
                    od_request.append("?");
                    od_request.append("deviceId="+URLEncoder.encode(deviceId,"UTF-8")+"&");
                    od_request.append("useId="+URLEncoder.encode(useId,"UTF-8"));
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(od_request.toString())//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        JSONObject jsonObject = new JSONObject(message);
                        String opendoor_status = jsonObject.optString("status");
                        if (opendoor_status != null ) {
                            switch (opendoor_status) {
                                case "0":
                                    Log.i("---M---", opendoor_status);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.showCustomShort("yes",MainActivity.this);
                                        }
                                    });
                                    break;
                                case "1":
                                    ToastUtil.showCustomShort("开门失败，请联系物业！", MainActivity.this);
                                    break;
                            }
                        } else {
                            ToastUtil.showCustomShort("请求失败，请联系物业！", MainActivity.this);
                        }
                    }else{
                        ToastUtil.showCustomShort("请求失败，请联系物业！", MainActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}

