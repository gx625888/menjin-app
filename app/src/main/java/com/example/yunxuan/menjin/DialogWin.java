package com.example.yunxuan.menjin;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DialogWin extends AppCompatActivity {
    /**
     * 弹窗提示
     *
     * @param message 消息
     */
    private void ShowDialogWin(Context context,String message) {
        final Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_code, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT; //设置宽度 dialog.getWindow().setAttributes(lp);
        lp.height = (int) (display.getHeight() * 0.65);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);

        TextView messageT = (TextView) view.findViewById(R.id.product_qr_code);//message
        TextView tv_no = (TextView) view.findViewById(R.id.message_reject);//取消
//        TextView prompt = (TextView) view.findViewById(R.id.warm_prompt);//温馨提示

        messageT.setText(message);
//        prompt.setText("确认");
        tv_no.setText("关闭");

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
