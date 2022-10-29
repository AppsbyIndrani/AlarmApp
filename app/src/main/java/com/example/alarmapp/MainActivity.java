package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnalarm;
    Long time;
    PendingIntent pendingIntent;
    SimpleDateFormat simpleDateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnalarm=findViewById(R.id.btnAlarm);

        createNotificationChannel();


        simpleDateFormat = new SimpleDateFormat("hh:mm");

        time=System.currentTimeMillis();


//        String[] s=currentTime.split(":");
//        String currentHour=s[0];
//        String currentMin=s[1];

        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

//                Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
//                intent.putExtra(AlarmClock.EXTRA_HOUR,currentHour);
//                intent.putExtra(AlarmClock.EXTRA_MINUTES,currentMin);
//                startActivity(intent);


                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent intent=new Intent(MainActivity.this,AlarmReciever.class);

                pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
                Date date = new Date(time);
                String time2 = simpleDateFormat.format(date);
                Toast.makeText(MainActivity.this,"Alarm set Successfully at "+time2,Toast.LENGTH_LONG).show();
                time += (5 * 60 * 1000);

            }
        });


    }



    private void createNotificationChannel()
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="alarmReminderChannel";
            String description="Channel for Alarm Manager";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("alarm",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


}