package com.example.roomandjobservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.text.DateFormat.SHORT;

public class MainActivity extends AppCompatActivity {

    private final int jobId = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button butCancel = findViewById(R.id.butt_cancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelJob();
            }
        });

        final Button butSchedule = (Button) findViewById(R.id.butt_shedule);
        butSchedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scheduleJob();
            }
        });

        final Button butReadDb = findViewById(R.id.butt_read);
        butReadDb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClimateRoomDatabase appDb = ClimateRoomDatabase.getInstance(getApplicationContext());
                List<ClimateItem> items = appDb.climateDao().getLastN(10);
                long cTime;
                //String sTime = "";
                int tAir = 0;
                int tBat = 0;
                int Press = 0;

                Calendar cal = Calendar.getInstance();

                if ((items != null) && (items.size() > 0)) {
                    StringBuilder builder = new StringBuilder();
                    for (ClimateItem details : items) {
                        cTime = details.measureTime;
                        tAir = details.tempAir;
                        tBat = details.tempBattery;
                        Press = details.Pressure;

                        cal.setTimeInMillis(cTime);
                        Date d = cal.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

                        builder.append(sdf.format(d)).append(", ").append(tAir).append(", ").append(tBat).append(", ").append(Press).append("\n");
                        //builder.append(cTime).append(", ").append(tAir).append(", ").append(tBat).append(", ").append(Press).append("\n");
                    }

                    TextView tva = findViewById(R.id.textAir);
                    tva.setText(getString(R.string.temp_air) + Integer.toString(tAir));

                    TextView tvb = findViewById(R.id.textBat);
                    tvb.setText(getString(R.string.temp_bat) + Integer.toString(tBat));

                    TextView tvp = findViewById(R.id.textPress);
                    tvp.setText(getString(R.string.pressure) + Integer.toString(Press));

                    TextView tv = findViewById(R.id.textViewDB);
                    tv.setText(builder.toString());

                } else {
                    Log.d("RoomAndJobService", "Database is empty");
                }
            }
        });

    }


    private void scheduleJob() {

        ComponentName name = new ComponentName(this, DbUpdateJobService.class);
        JobInfo info = new JobInfo.Builder(jobId, name)
                .setPersisted(true)
                .setMinimumLatency(30 * 1000)
                .setOverrideDeadline(20 * 1000)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setPeriodic(60 * 1000).build();

        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int response = scheduler.schedule(info);

    }

    private void cancelJob() {
        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(jobId);
    }
}
