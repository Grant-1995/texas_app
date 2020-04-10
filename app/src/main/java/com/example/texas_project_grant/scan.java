package com.example.texas_project_grant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        String result = getIntent().getStringExtra("scan results");
        TextView txtAt1 = (TextView)findViewById(R.id.txtAt1);
        TextView txtAt2 = (TextView)findViewById(R.id.txtAt2);
        TextView txtAt3 = (TextView)findViewById(R.id.txtAt3);
        String[] dateSplit = result.split(";");
        txtAt1.setText(dateSplit[0] + "");
        txtAt2.setText(dateSplit[1] + "");
        txtAt3.setText(dateSplit[2] + "");
      //  Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
    }
}
