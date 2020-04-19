package com.example.texas_project_grant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private Button BtnScan;
    private Button BtnLookup;
    private Button BtnUpload;
    private Button Btn_report; //For now using it for HTTP get and pull request using retrofit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnLookup = (Button)findViewById(R.id.BtnLookup);
        Btn_report = (Button)findViewById(R.id.Btn_report);
        BtnScan = (Button) findViewById(R.id.BtnScan);
        BtnUpload = (Button) findViewById(R.id.BtnUpload);
        final Activity activity = this;
        BtnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_lookup();

            }
        });

        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_qrGen();
            }
        });

        Btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_report();
            }
        });

        BtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("scan");
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
              if(result!=null) {
                  if (result.getContents() == null) {
                      Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                  } else {
                      Intent scanInfo = new Intent(getBaseContext(),scan.class);
                      scanInfo.putExtra("scan results",result.getContents());
                      startActivity(scanInfo);

                  }
              }
              else
                  {
                      super.onActivityResult(requestCode, resultCode, data);
                  }

              }
              public void openActivity_lookup()
              {
                  Intent intent = new Intent(this,Lookup.class);
                  startActivity(intent);
              }

    public void openActivity_qrGen()
    {
        Intent intent = new Intent(this,QRGen.class);
        startActivity(intent);
    }
    public void openActivity_report()
    {
        Intent intent = new Intent(this,Report.class);
        startActivity(intent);
    }
}
