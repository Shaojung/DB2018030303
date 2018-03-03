package com.example.student.db2018030303;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    TextView tv2;
    ImageView img;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        tv2 = findViewById(R.id.textView2);
        pb = findViewById(R.id.progressBar);
        MyTask task = new MyTask();
        task.execute("https://static1.squarespace.com/static/523b823ce4b0c90f4f169867/t/584f4d00e3df2821594ce4a6/1481592081752/");
    }
    class MyTask extends AsyncTask<String, Integer, Bitmap>
    {
        int totalLength;
        @Override
        protected Bitmap doInBackground(String... params) {
            String str_url = params[0];
            URL url;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int sum = 0;
            try {
                url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                totalLength = conn.getContentLength();
                InputStream is = conn.getInputStream();
                byte[] b = new byte[128];
                int len;
                while ((len = is.read(b)) != -1)
                {
                    sum += len;
                    publishProgress(sum);
                    bos.write(b, 0, len);

                }

                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bmp = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());
            return bmp;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("TASK", "progress:" + values[0]);
            tv2.setText(String.valueOf(values[0]) + "/" + totalLength);
            pb.setProgress(100 * values[0] / totalLength);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }
}
