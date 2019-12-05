package com.example.roomandjobservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.util.Random;

public class DbUpdateJobService extends JobService {

    private boolean isJobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        dbAddNewRow(jobParameters);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("DbUpdateJobService", "Job Cancelled");
        isJobCancelled = true;
        return false;
    }

    private void dbAddNewRow(final  JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("DbUpdateJobService", "Service running");
                if (isJobCancelled) {
                    return;
                }

                ClimateRoomDatabase appDb = ClimateRoomDatabase.getInstance(getApplicationContext());
                ClimateItem item = new ClimateItem();

                Random r = new Random();
                item.measureTime = System.currentTimeMillis();
                item.Pressure = r.nextInt(10) + 10;
                item.tempBattery = r.nextInt(30) + 10;
                item.tempAir = r.nextInt(10) + 18;
                appDb.climateDao().insert(item);

                Log.d("DbUpdateJobService", "Row added");

                jobFinished(params, false);
            }
        }).start();
    }
}
