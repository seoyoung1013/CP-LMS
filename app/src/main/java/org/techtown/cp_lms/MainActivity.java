package org.techtown.cp_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyService myService;
    boolean isService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceBind();

        final Button airButton = (Button) findViewById(R.id.airButton);
        final Button lightButton = (Button) findViewById(R.id.lightButton);
        final Button timeButton = (Button) findViewById(R.id.timeButton);
        final Button seatButton = (Button) findViewById(R.id.seatButton);



        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LightActivity.class);
                startActivity(intent);
            }
        });


        airButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent airIntent = new Intent(getApplicationContext(), airActivity.class);
                startActivity(airIntent);
            }
        });


        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeIntent = new Intent(getApplicationContext(), TimeActivity.class);
                startActivity(timeIntent);
            }
        });

        seatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seatIntent = new Intent(getApplicationContext(), seatActivity.class);
                startActivity(seatIntent);
            }
        });
    }


    public void serviceBind(){
        Intent intent = new Intent(
                MainActivity.this, // 현재 화면
                MyService.class); // 다음넘어갈 컴퍼넌트

        bindService(intent, // intent 객체
                conn, // 서비스와 연결에 대한 정의
                Context.BIND_AUTO_CREATE);

        //처음 서비스를 시작하는 액티비티에서는 Context.BIND_AUTO_CREATE
        //다른 액티비티에서는 Context.BIND_NOT_FOREGROUND를 주어야합니다.
    }

    protected void onDestroy() {
        super.onDestroy();
        if(isService){
            unbindService(conn); // 서비스 종료
            isService = false;
        }
    }

    protected void onResume() {
        super.onResume();

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
