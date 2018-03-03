package com.example.student.db2018030303;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        MyTask task = new MyTask();
        task.execute("https://www.mobile01.com/rss/news.xml");
    }
    class MyTask extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String str_url = params[0];
            URL url;
            String str;
            StringBuilder sb = new StringBuilder();
            try {
                url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream stream = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                while ((str = br.readLine()) != null)
                {
                    sb.append(str);
                }
                br.close();
                stream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String data = sb.toString();
            Log.d("NET", data);
            return data;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("TASK", "progress:" + values[0]);
            tv.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("TASK", "post:" + s);
            tv.setText(s);
        }
    }
}
