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
        task.execute(10);
    }
    class MyTask extends AsyncTask<Integer, Integer, String>
    {
        @Override
        protected String doInBackground(Integer... params) {
            int i;
            for (i=1;i<=params[0];i++)
            {
                try {
                    Thread.sleep(500);
                    // Log.d("TASK", "R:" + i);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Okay";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("TASK", "progress:" + values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("TASK", "post:" + s);
        }
    }
}
