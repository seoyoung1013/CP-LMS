package org.techtown.cp_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

public class seatActivity extends AppCompatActivity {

    MyService myService;
    boolean isService = false;

    ImageView seat1;
    ImageView seat2;
    ImageView seat3;
    ImageView seat4;

    BitmapDrawable on;
    BitmapDrawable no;
    BitmapDrawable off;
    BitmapDrawable no_seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        seat1 = (ImageView) findViewById(R.id.seat1);
        seat2 = (ImageView) findViewById(R.id.seat2);
        seat3 = (ImageView) findViewById(R.id.seat3);
        seat4 = (ImageView) findViewById(R.id.seat4);

        on = (BitmapDrawable) getResources().getDrawable(R.drawable.seat_on);
        no = (BitmapDrawable) getResources().getDrawable(R.drawable.seat_no);
        off = (BitmapDrawable) getResources().getDrawable(R.drawable.seat_off);
        no_seat = (BitmapDrawable) getResources().getDrawable(R.drawable.no_seat);

        serviceBind();

        final Button shomeButton = (Button) findViewById(R.id.shomeButton);
        final Button returnButton = (Button) findViewById(R.id.returnButton);


        shomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seatintent = new Intent(seatActivity.this, MainActivity.class);
                seatActivity.this.startActivity(seatintent);
            }
        });

        seat_input();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myService.sendMessage("21");
                seat_input();
            }
        });
    }

    public void seat_input(){
           String str = MyService.response;
            String seat_s[] = str.split("/");

            if (seat_s[0].equals("on")) {
                Log.e("1번", "on");
                seat1.setImageDrawable(on);
            }

            if (seat_s[0].equals("off")) {
                Log.e("1번", "off");
                seat1.setImageDrawable(off);
            }

            if (seat_s[1].equals("on")) {
                Log.e("2번", "on");
                seat2.setImageDrawable(on);
            }

            if (seat_s[1].equals("off")) {
                Log.e("2번", "off");
                seat2.setImageDrawable(no);
            }

            if (seat_s[2].equals("on")) {
                Log.e("3번", "on");
                seat3.setImageDrawable(on);
            }

            if (seat_s[2].equals("off")) {
                Log.e("3번", "off");
                seat3.setImageDrawable(off);
            }

            if (seat_s[3].equals("on")) {
                Log.e("4번", "on");
                seat4.setImageDrawable(on);
            }

            if (seat_s[3].equals("off")) {
                Log.e("4번", "off");
                seat4.setImageDrawable(no);
            }

            if(seat_s[0].equals("on")&&seat_s[2].equals("on")){
                seat1.setImageDrawable(on);
                seat2.setImageDrawable(no);
                seat3.setImageDrawable(on);
                seat4.setImageDrawable(no);
                if(seat_s[1].equals("on")){
                    seat2.setImageDrawable(no_seat);
                }
                if(seat_s[3].equals("on")){
                    seat4.setImageDrawable(no_seat);
                }
            }

        if(seat_s[1].equals("on")&&seat_s[3].equals("on")){
            seat1.setImageDrawable(no);
            seat2.setImageDrawable(on);
            seat3.setImageDrawable(no);
            seat4.setImageDrawable(no);
            if(seat_s[0].equals("on")){
                seat1.setImageDrawable(no_seat);
            }
            if(seat_s[2].equals("on")){
                seat3.setImageDrawable(no_seat);
            }
        }

    }

    public void serviceBind(){
        Intent intent = new Intent(
                seatActivity.this, // 현재 화면
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
