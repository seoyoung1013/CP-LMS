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

public class airActivity extends AppCompatActivity {

    MyService myService;
    boolean isService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);

        serviceBind();

        final ToggleButton airPowerButton = (ToggleButton) findViewById(R.id.airPowerButton);

        airPowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(airPowerButton.isChecked()){
                    myService.sendMessage("1");
                    airPowerButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.on)
                    );
                } else if(!airPowerButton.isChecked()){
                    myService.sendMessage("2");
                    airPowerButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.off)
                    );
                }
            }
        });

        final ToggleButton ventilationButton = (ToggleButton) findViewById(R.id.ventilationButton);

        ventilationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ventilationButton.isChecked()){
                    myService.sendMessage("3");
                    ventilationButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.on_fan)
                    );
                } else if(!ventilationButton.isChecked()){
                    myService.sendMessage("4");
                    ventilationButton.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.off_fan)
                    );
                }
            }
        });

        final Button homeButton = (Button) findViewById(R.id.shomeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent air_homeintent = new Intent(airActivity.this, MainActivity.class);
                airActivity.this.startActivity(air_homeintent);
            }
        });

    }

    public void serviceBind(){
        Intent intent = new Intent(
                airActivity.this, // 현재 화면
                MyService.class); // 다음넘어갈 컴퍼넌트

        bindService(intent, // intent 객체
                conn, // 서비스와 연결에 대한 정의
                Context.BIND_NOT_FOREGROUND);
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
