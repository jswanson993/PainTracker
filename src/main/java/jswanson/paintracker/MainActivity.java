package jswanson.paintracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DbHandler myDbHandler;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDbHandler = new DbHandler(this);
        ImageButtonListener();
        ButtonListener();

    }

    public void ImageButtonListener(){
        button = (ImageButton)findViewById(R.id.imageButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimeClicked(v);
            }
        });
    }

    public void ButtonListener(){
        Button b = (Button)findViewById(R.id.calendar_button);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(MainActivity.this, Calandar.class);
                startActivity(intent);
            }
        });

    }

    public void addTimeClicked(View view) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int date = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        String time = hour + ":" + minute;
        myDbHandler.addTime(year, month, date, time);
    }

}
