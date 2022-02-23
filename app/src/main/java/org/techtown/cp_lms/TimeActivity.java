package org.techtown.cp_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class TimeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayAdapter adapter;
    private Spinner spinner;
    private ImageView imageView;
    ArrayAdapter<CharSequence> arrayAdapter;

    Integer[] classTime = {R.drawable.class1502,R.drawable.class1504,R.drawable.class1505,R.drawable.class1507};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        spinner = (Spinner) findViewById(R.id.timeSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.classNumber, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        imageView = (ImageView) findViewById(R.id.timeImage);

        spinner =(Spinner)findViewById(R.id.timeSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final Button time_homeButton = (Button) findViewById(R.id.time_homeButton);

        time_homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent time_homeintent = new Intent(TimeActivity.this, MainActivity.class);
                TimeActivity.this.startActivity(time_homeintent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String what;
        switch (parent.getId()){
            case R.id.timeSpinner:
                imageView.setImageResource(classTime[position]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
