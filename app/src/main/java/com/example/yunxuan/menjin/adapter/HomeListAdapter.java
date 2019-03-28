package com.example.yunxuan.menjin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.yunxuan.menjin.MainActivity;
import com.example.yunxuan.menjin.R;
import com.example.yunxuan.menjin.bean.NewData;
import com.example.yunxuan.menjin.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.net.URLEncoder;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeListAdapter extends BaseAdapter {
    //声明一个上下文对象
    private Context mcontext;
    //声明一个信息队列
    private ArrayList<NewData>  mhomelist;

    private String TmpPwd;

    private MainActivity mainActivity;

    //适配器的构造器函数，传入上下文和信息队列
    public HomeListAdapter(Context context,ArrayList<NewData> home_list){
        mainActivity = new MainActivity();
        mcontext = context;
        mhomelist = home_list;
    }

    /**
     * 获取临时密码
     *
     */
    public void getTmpPwd(final String deviceId, final String useId) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder buf = new StringBuilder("https://51guangjie.com.cn/access/wxapp/getTmpPwd.srv");
                    buf.append("?");
                    buf.append("deviceId="+URLEncoder.encode(deviceId,"UTF-8")+"&");
                    buf.append("useId="+URLEncoder.encode(useId,"UTF-8"));
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(buf.toString())//请求接口。如果需要传参拼接到接口后面。
                            .addHeader("token","bFkzZ0RNcklBbzE=")
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        JSONObject jsonObject = new JSONObject(message);
                        TmpPwd = jsonObject.optString("pwd");
                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(mcontext)
                                        .setTitle("提示：")
                                        .setMessage(TmpPwd)
                                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /*
         * 因为是GET请求，所以需要将请求参数添加到URL后，并且还需要进行URL编码
         * URL = http://192.168.0.103:8080/Server/PrintServlet?name=%E6%88%91&age=20
         * 此处需要进行URL编码因为浏览器提交时自动进行URL编码
         * */
//        StringBuilder buf = new StringBuilder("https://51guangjie.com.cn/access/wxapp/getTmpPwd.srv");
//        buf.append("?");
//        buf.append("deviceId="+URLEncoder.encode(deviceId,"UTF-8")+"&");
//        buf.append("useId="+URLEncoder.encode(useId,"UTF-8"));
//        URL url = new URL(buf.toString());
//
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        Log.i("-----", url.toString());
//        Log.i("-----", String.valueOf(conn.));
//        if(conn.getResponseCode()==200){
//            new AlertDialog.Builder(mcontext)
//                    .setTitle("临时密码：")
//                    .setMessage("介绍...")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    })
//                    .show();
//        }
//        else Toast.makeText(mcontext, "GET提交失败", Toast.LENGTH_SHORT).show();



        /*
         * 如果是POST请求，则请求参数放在请求体中，
         * name=%E6%88%91&age=12
         *
         * */
//        StringBuilder buf = new StringBuilder();
//        buf.append("deviceId="+URLEncoder.encode(deviceId,"UTF-8")+"&");
//        buf.append("useId="+URLEncoder.encode(useId,"UTF-8"));
//        byte[]data = buf.toString().getBytes("UTF-8");
//        URL url = new URL("https://51guangjie.com.cn/access/wxapp/getTmpPwd.srv");
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("POST");
//        Log.i("------", url.toString());
//        conn.setDoOutput(true);	//如果要输出，则必须加上此句
//        OutputStream out = conn.getOutputStream();
//        out.write(data);
//        Log.i("------", data.toString());
//        if(conn.getResponseCode()==200){
//            Toast.makeText(mcontext, "GET提交成功", Toast.LENGTH_SHORT).show();
//        }
//        else Toast.makeText(mcontext, "GET提交失败", Toast.LENGTH_SHORT).show();

    }

    /**
     * 请求开门
     *
     */
    public void getopendoor(final String deviceId, final String useId) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder buf = new StringBuilder("https://51guangjie.com.cn/access/wxapp/opendoor.srv");
                    buf.append("?");
                    buf.append("deviceId="+URLEncoder.encode(deviceId,"UTF-8")+"&");
                    buf.append("useId="+URLEncoder.encode(useId,"UTF-8"));
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(buf.toString())//请求接口。如果需要传参拼接到接口后面。
                            .addHeader("token","bFkzZ0RNcklBbzE=")
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        JSONObject jsonObject = new JSONObject(message);
                        String opendoor_status = jsonObject.optString("status");
                        Looper.prepare();
                        if (opendoor_status != null ) {
                            switch (opendoor_status) {
                                case "0":
                                    ToastUtil.showCustomShort("开门成功！",mcontext);
                                    break;
                                case "1":
                                    ToastUtil.showCustomShort("开门失败，请联系物业！", mcontext);
                                    break;
                            }
                        } else {
                            ToastUtil.showCustomShort("请求失败，请联系物业！", mcontext);
                        }
                        Looper.loop();
                    }else{
                        ToastUtil.showCustomShort("请求失败，请联系物业！", mcontext);
                        Looper.loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    //获取列表项的个数
    public int getCount(){
        return mhomelist.size();
    }

    //获取列表项的数据
    public Object getItem(int arg0) {
        return mhomelist.get(arg0);
    }

    //获取列表项的编号
    public long getItemId(int arg0){
        return arg0;
    }

    //获取指定位置的列表项视图
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView == null){//转换视图为空
            holder = new ViewHolder();//创建一个新的视图持有者
            //根据布局文件****.xml生成转换视图对象
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.home_list,null);
            //根据id获取视图对象中的imageview、textview等视图，添加到视图持有者中
            holder.residentname = convertView.findViewById(R.id.residentname);
            holder.buildname = convertView.findViewById(R.id.buildname);
            holder.unitname = convertView.findViewById(R.id.unitname);
            holder.housename = convertView.findViewById(R.id.housename);
            holder.getTmpPwd = convertView.findViewById(R.id.getTmpPwd);
            holder.opendoor = convertView.findViewById(R.id.opendoor);
            //将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        }else{//转换视图非空
            //从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) convertView.getTag();
        }
        final NewData home_information = mhomelist.get(position);
        holder.residentname.setText(home_information.getResidentName());
        holder.buildname.setText(home_information.getBuildName());
        holder.unitname.setText(home_information.getUnitName());
        holder.housename.setText(home_information.getHouseName());
        holder.getTmpPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getTmpPwd(home_information.getDeviceId(), String.valueOf(home_information.getPersonId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.opendoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getopendoor(home_information.getDeviceId(), String.valueOf(home_information.getPersonId()));
                    //mainActivity.opendoor(home_information.getDeviceId(), String.valueOf(home_information.getPersonId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }

}
