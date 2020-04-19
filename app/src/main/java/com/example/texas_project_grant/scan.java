package com.example.texas_project_grant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class scan extends AppCompatActivity {
    private PostmanAPI PostmanAPI;
    private TextView textView7;
    private EditText edt_len;
    private Button btn_enterrrr;
    public Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder().serializeNulls().create();

        setContentView(R.layout.activity_scan);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        btn_enterrrr = findViewById(R.id.btn_enter);
        Button bt2 = (Button) findViewById(R.id.bt2);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://texas-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        PostmanAPI = retrofit.create(PostmanAPI.class);
        setContentView(R.layout.activity_scan);
        final String result = getIntent().getStringExtra("scan results");
        final int id = Integer.parseInt(result);

        textView7 = findViewById(R.id.textView7);
        getmataterial_by_id(id);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_len = findViewById(R.id.edt_len);
                System.out.println(id);
                Log.d("HELLO", "Register Button Clicked");
                System.out.println(edt_len.getText() + "");
                Report rep = new Report();
                final String result = getIntent().getStringExtra("scan results");
                int id = Integer.parseInt(result);

                Double len = Double.parseDouble(edt_len.getText() + "");

                Call<material>call = PostmanAPI.updateLength(id,len);
                call.enqueue(new Callback<material>() {
                    @Override
                    public void onResponse(Call<material> call, Response<material> response) {
                        if (!response.isSuccessful()){
                            textView7.setText("Code" + response.code());
                            return;

                        }

                        material materialResponse = response.body();

                        String content = "";
                        content += "Code: " +response.code() + "\n";
                        content += "id: "+ materialResponse.getId() + "\n";
                        content += "colour: " + materialResponse.getColour() + "\n";
                        content += "quality: " +materialResponse.getQuality() + "\n";
                        content += "description: " +materialResponse.getDescription() + "\n";
                        content += "cost_price_per_m: " +materialResponse.getCost_price_per_m() + "\n";
                        content += "original_length: " +materialResponse.getOriginal_length() + "\n";
                        content += "current_length: " +materialResponse.getCurrent_length() + "\n";
                        content += "date_purchased: " +materialResponse.getDate_purchased() + "\n";
                        content += "shelf_code: " +materialResponse.getShelf_code() + "\n";
                        content += "country_of_origin: " +materialResponse.getCountry_of_origin() + "\n\n";


                        textView7.append(content);
                    }

                    @Override
                    public void onFailure(Call<material> call, Throwable t) {
                        textView7.append(t.getMessage());

                    }
                });
            }
        });



        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HELLO", "Register Button Clicked");
                System.out.println("hekjfdsklfjs");
                textView7 = (TextView) findViewById(R.id.textView7);
                textView7.setText("dsffds");
         //       Report rep = new Report();
       //         Double len = Double.parseDouble(edt_len.getText() + "");
        //        rep.updateLength(id,len,textView7);
            }
        });

      //  Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

    }
    private void getmataterial_by_id(int material_id)
    {
        Call<List<material>> call = PostmanAPI.getid(material_id);

        //   Call<List<Post>> call2 = PostmanAPI.getPosts("Josh","id","desc");
        call.enqueue(new Callback<List<material>>() {
            @Override
            public void onResponse(Call<List<material>> call, Response<List<material>> response) {
                if (!response.isSuccessful()){
                    textView7.setText("Code" + response.code());
                    return;

                }
                List<material> materials = response.body();

                for(material Material : materials){
                    String content = "";
                   content += "id: "+ Material.getId() + "\n";
                    content += "colour: " + Material.getColour() + "\n";
                    content += "quality: " +Material.getQuality() + "\n";
                    content += "description: " +Material.getDescription() + "\n";
                    content += "cost_price_per_m: " +Material.getCost_price_per_m() + "\n";
                    content += "original_length: " +Material.getOriginal_length() + "\n";
                    content += "current_length: " +Material.getCurrent_length() + "\n";
                    content += "date_purchased: " +Material.getDate_purchased() + "\n";
                    content += "shelf_code: " +Material.getShelf_code() + "\n";
                    content += "country_of_origin: " +Material.getCountry_of_origin() + "\n\n";
                    System.out.println(content);
                    textView7 = (TextView) findViewById(R.id.textView7);

                    textView7.setText(content);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<material>> call, Throwable t) {
                textView7.setText(t.getMessage());
                return;
            }
        });
    }
}
