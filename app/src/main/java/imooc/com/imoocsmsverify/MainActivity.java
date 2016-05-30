package imooc.com.imoocsmsverify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {

    Button mBtnBindPhone;
    String APPKEY = "135462848f6e8";
    String APPSECRET="03dcae5ddf36abca9dc8e5313036085c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化sdk
        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        //配置信息

        mBtnBindPhone = (Button) findViewById(R.id.btn_bind_phone);
        mBtnBindPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            // 提交用户信息
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(MainActivity.this);
            }
        });
    }

    /**
     * 提交用户信息,在监听中返回*/
    private void registerUser(String country, String phone) {
        Random r = new Random();

        String uid = Math.abs(r.nextInt())+"";
        String nickName = "IMOOC";

        SMSSDK.submitUserInfo(uid,nickName,null, country, phone);
    }


}
