package org.techtown.cp_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LightActivity extends AppCompatActivity {

    MyService myService;
    boolean isService = false;

    ToggleButton firstButton;
    ToggleButton secondButton;
    ToggleButton thirdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        serviceBind();


        firstButton = (ToggleButton) findViewById(R.id.firstButton);
        secondButton = (ToggleButton) findViewById(R.id.secondButton);
        thirdButton = (ToggleButton) findViewById(R.id.thirdButton);



        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstButton.isChecked()){
                    myService.sendMessage("5");
                    firstButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.on_light)
                    );


                } else if(!firstButton.isChecked()){
                    myService.sendMessage("6");
                    firstButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.off_light)
                    );
                    //light1 = "off";

                    //Log.e("확인", light1);

                }
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondButton.isChecked()){
                    myService.sendMessage("7");
                    secondButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.on_light)
                    );
                } else if(!secondButton.isChecked()){
                    myService.sendMessage("8");
                    secondButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.off_light)
                    );
                }
            }
        });

        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thirdButton.isChecked()){
                    myService.sendMessage("9");
                    thirdButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.on_light)
                    );
                } else if(!thirdButton.isChecked()){
                    myService.sendMessage("10");
                    thirdButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.off_light)
                    );
                }
            }
        });

        final Button light_homeButton = (Button) findViewById(R.id.light_homeButton);

        light_homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(LightActivity.this, MainActivity.class);
                LightActivity.this.startActivity(homeintent);
            }
        });


    }


    protected void onDestroy() {
        super.onDestroy();
        if(isService){
            unbindService(conn); // 서비스 종료
            isService = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }


    public void serviceBind(){
        Intent intent = new Intent(
                LightActivity.this, // 현재 화면
                MyService.class); // 다음넘어갈 컴퍼넌트

        bindService(intent, // intent 객체
                conn, // 서비스와 연결에 대한 정의
                Context.BIND_NOT_FOREGROUND);
        //처음 서비스를 시작하는 액티비티에서는 Context.BIND_AUTO_CREATE
        //다른 액티비티에서는 Context.BIND_NOT_FOREGROUND를 주어야합니다.
    }


    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            // 서비스와 연결되었을 때 호출되는 메서드
            // 서비스 객체를 전역변수로 저장
            MyService.MyBinder mb = (MyService.MyBinder) service;
            myService = mb.getService(); // 서비스가 제공하는 메소드 호출하여
            // 서비스쪽 객체를 전달받을수 있슴
            isService = true;
            Toast.makeText(getApplicationContext(),
                    "서비스 연결",
                    Toast.LENGTH_LONG).show();
        }

        public void onServiceDisconnected(ComponentName name) {
            // 서비스와 연결이 끊겼을 때 호출되는 메서드
            isService = false;
            Toast.makeText(getApplicationContext(),
                    "서비스 연결 해제",
                    Toast.LENGTH_LONG).show();
        }
    };
}
