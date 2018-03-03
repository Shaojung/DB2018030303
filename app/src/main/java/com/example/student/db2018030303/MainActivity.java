package com.example.student.db2018030303;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
            String str = "";
            try {
                url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int len;
                while ((len = is.read(b)) != -1)
                {
                    bos.write(b, 0, len);
                }

                is.close();
                str = new String(bos.toByteArray());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("NET", str);
            return str;
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
