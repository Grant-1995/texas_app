package com.example.texas_project_grant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import java.lang.reflect.Type;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class upload extends AppCompatActivity {
    private AutoCompleteTextView edtcol;
    String col;
    private EditText edtqual;
    int qual;
    String desc;
    Double cost;
    Double origi;
    Double curr;
    String Date;
    String shelf;
    String country;
    private AutoCompleteTextView edtdesc;
    private EditText edtcost;
    private EditText edtorigi;
    private EditText edtcurr;
    private EditText edtdate;
    private AutoCompleteTextView edtshelf;
    private AutoCompleteTextView edtcountry;
    private PostmanAPI PostmanAPI;
    private Button btn_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        btn_enter = (Button)findViewById(R.id.btn_enter);
        edtcol = (AutoCompleteTextView)findViewById(R.id.edtcol);
        edtdesc = (AutoCompleteTextView)findViewById(R.id.edtdesc);
        edtcost = (EditText)findViewById(R.id.edtcost);
        edtcountry = (AutoCompleteTextView)findViewById(R.id.edtcountry);
        edtshelf = (AutoCompleteTextView)findViewById(R.id.edtshelf);
        edtdate = (EditText)findViewById(R.id.edtdate);
        edtcurr = (EditText)findViewById(R.id.edtcurr);
        edtorigi = (EditText)findViewById(R.id.edtorigi);
        edtqual = (EditText)findViewById(R.id.edtqual);


















        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://texas-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        PostmanAPI = retrofit.create(PostmanAPI.class);

        distmat();
       btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                col = edtcol.getText() + "";
        desc = edtdesc.getText() + "";
        country = edtcountry.getText() + "";
        Date = edtdate.getText() + "";
        shelf = edtshelf.getText() + "";

        cost = Double.parseDouble(edtcost.getText() + "");
        curr = Double.parseDouble(edtcurr.getText() + "");
        origi = Double.parseDouble(edtorigi.getText() + "");
        qual = Integer.parseInt(edtqual.getText() + "");
                Report r = new Report();
                r.creatematerial(PostmanAPI, col,qual, desc ,  cost, origi,curr,Date, shelf, country);
            }
        });


    }

    public void distmat()
    {
        final Call<List<String[]>> listCall = PostmanAPI.readJson();
        listCall.enqueue(new Callback<List<String[]>>() {
            @Override
            public void onResponse(Call<List<String[]>> call, Response<List<String[]>> response) {
                if (!response.isSuccessful()){
                    System.out.println("un");
                    return;
                }
                List<String[]> distinct = response.body();


                String[] discription = distinct.get(0);
                String[] colour = distinct.get(1);
                String[] shelf_code = distinct.get(2);
                String[] country = distinct.get(3);



                ArrayAdapter<String> adapter_disc =  new ArrayAdapter<String>(upload.this,
                        android.R.layout.simple_list_item_1,discription);
                edtdesc.setAdapter(adapter_disc);

                ArrayAdapter<String> adapter_col =  new ArrayAdapter<String>(upload.this,
                        android.R.layout.simple_list_item_1,colour);
                edtcol.setAdapter(adapter_col);

                ArrayAdapter<String> adapter_shelf =  new ArrayAdapter<String>(upload.this,
                        android.R.layout.simple_list_item_1,shelf_code);
                edtshelf.setAdapter(adapter_shelf);

                ArrayAdapter<String> adapter_country =  new ArrayAdapter<String>(upload.this,
                        android.R.layout.simple_list_item_1,country);
                edtcountry.setAdapter(adapter_country);
            }

            @Override
            public void onFailure(Call<List<String[]>> call, Throwable t) {

            }
        });
    }

    public void distmataterial()
    {

        Call<List<material>> call = PostmanAPI.getallmaterial();

        //   Call<List<Post>> call2 = PostmanAPI.getPosts("Josh","id","desc");
        call.enqueue(new Callback<List<material>>() {

            @Override
            public void onResponse(Call<List<material>> call, Response<List<material>> response) {
                if (!response.isSuccessful()){


                    return;

                }
                List<material> materials = response.body();
                String colour[] = new String[materials.size()];
                final String description[] = new String[materials.size()];
                final String[] shelf_code = new String[materials.size()];
                final String country_of_origin[] = new String[materials.size()];
                int i = 0;


                for(material Material : materials){

                 //    String colour[] = new String[];
          //          final String description[] = new String[materials.size()];
         //           final String country_of_origin[] = new String[materials.size()];

                    colour[i] = Material.getColour() + "";
                    description[i] = Material.getDescription();
                    shelf_code[i] = Material.getShelf_code() + "";
                    country_of_origin[i] = Material.getCountry_of_origin();



                    i++;





                }

               String[] Countries = new String[]{"fsfsd","dsada","fsfs","dsf"};
                ArrayAdapter<String> adapter =  new ArrayAdapter<String>(upload.this,
                        android.R.layout.simple_list_item_1,shelf_code);
                edtshelf.setAdapter(adapter);
                ArrayAdapter<String> adapter_col =  new ArrayAdapter<String>(upload.this,
                        android.R.layout.simple_list_item_1,Countries);
                edtcol.setAdapter(adapter_col);



            }




            @Override
            public void onFailure(Call<List<material>> call, Throwable t) {

            }
        });
    }



}
