package com.example.student.db2018030303;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyTask task = new MyTask();
        task.execute(5);
    }
    class MyTask extends AsyncTask<Integer, Integer, String>
    {
        @Override
        protected String doInBackground(Integer... integers) {
            int i;
            for (i=1;i<=5;i++)
            {
                try {
                    Thread.sleep(500);
                    Log.d("TASK", "R:" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
