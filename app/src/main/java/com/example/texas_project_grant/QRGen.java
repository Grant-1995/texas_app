package com.example.texas_project_grant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QRGen extends AppCompatActivity {
    TextView txt_result;
    EditText EditQR;
    Button BtnGen;
    ImageView imQR;
    String text2Qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_gen);
        txt_result = (TextView) findViewById(R.id.txt_result);
        EditQR = (EditText)findViewById(R.id.EditQR);
        BtnGen = (Button)findViewById(R.id.BtnGen);
        imQR = (ImageView)findViewById(R.id.imQR);

        OkHttpClient client = new OkHttpClient();

        String url = "https://texas-server.herokuapp.com/Server/api/getemployee";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    QRGen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt_result.setText(myResponse);
                        }
                    });
                }
            }
        });


        BtnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2Qr = EditQR.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imQR.setImageBitmap(bitmap);
                }
                catch (WriterException e)
                {
                    e.printStackTrace();
                }


            }
        });



    }
}
