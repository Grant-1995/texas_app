package com.example.texas_project_grant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

public class Report extends AppCompatActivity {
    private TextView txt_res;
    private EditText edt_desc;
    private Button Btn_enter;
    private PostmanAPI PostmanAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder().serializeNulls().create();

        setContentView(R.layout.activity_report);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        edt_desc = findViewById(R.id.edt_desc);
        Btn_enter = findViewById(R.id.Btn_enter);
        txt_res = findViewById(R.id.txt_res);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://texas-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


         PostmanAPI = retrofit.create(PostmanAPI.class);
    //    getposts();

        Btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmataterial();
            }
        });



       // updateLength();



    }
    private void getmataterial()
    {
        String desc = edt_desc.getText() + "";
        Call<List<material>> call = PostmanAPI.getmaterial(desc);

        //   Call<List<Post>> call2 = PostmanAPI.getPosts("Josh","id","desc");
        call.enqueue(new Callback<List<material>>() {
            @Override
            public void onResponse(Call<List<material>> call, Response<List<material>> response) {
                if (!response.isSuccessful()){
                    txt_res.setText("Code" + response.code());
                    return;

                }
                List<material> materials = response.body();

                for(final material Material : materials){
                    ForegroundColorSpan fcsGray =  new ForegroundColorSpan(Color.GRAY);
                    String content = "";

                    String id = "id: "+ Material.getId() + "\n";
                    content +=id;
                    content += "colour: " + Material.getColour() + "\n";
                    content += "quality: " +Material.getQuality() + "\n";
                    content += "description: " +Material.getDescription() + "\n";
                    content += "cost_price_per_m: " +Material.getCost_price_per_m() + "\n";
                    content += "original_length: " +Material.getOriginal_length() + "\n";
                    content += "current_length: " +Material.getCurrent_length() + "\n";
                    content += "date_purchased: " +Material.getDate_purchased() + "\n";
                    content += "shelf_code: " +Material.getShelf_code() + "\n";
                    content += "country_of_origin: " +Material.getCountry_of_origin() + "\n\n";

                    SpannableString ss = new SpannableString(content);
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Intent scanInfo = new Intent(getBaseContext(),scan.class);
                            String id = Material.getId() + "";
                            scanInfo.putExtra("scan results", id);
                            startActivity(scanInfo);

                        }
                    };
                    ss.setSpan(fcsGray, 3,id.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(clickableSpan,3,id.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                    txt_res.append(ss);


                }
                txt_res.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onFailure(Call<List<material>> call, Throwable t) {
                txt_res.setText(t.getMessage());
            }
        });
    }


    public void creatematerial(PostmanAPI PostmanAPI, String col, int qual, String desc, Double cost, Double original, Double current, String date, String shelf, String country)
        {
         //   material Material = new material();
      //      Map<String, String> fields = new HashMap<>();
        //    fields.put("id","100");
       //     fields.put("first_name", "J");
//        Call<Post> call = PostmanAPI.createPost(fields);
            Call<material> call = PostmanAPI.creatematerial(col,qual,desc,cost,original,  current, date,shelf,country);
            call.enqueue(new Callback<material>() {
                @Override
                public void onResponse(Call<material> call, Response<material> response) {
                    if (!response.isSuccessful()){

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



                }

                @Override
                public void onFailure(Call<material> call, Throwable t) {


                }
            });
        }

    private void setPost()
    {
        Post post = new Post(5,"Josh","B",111010101,"1996-01-21","2020-02-02");
        Map<String, String> fields = new HashMap<>();
        fields.put("id","100");
        fields.put("first_name", "J");
//        Call<Post> call = PostmanAPI.createPost(fields);
        Call<Post> call = PostmanAPI.createPost(5,"Joshoa","B",101101,"1996-01-21","2020-02-02" );
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    txt_res.setText("Code" + response.code());
                    return;

                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " +response.code() + "\n";
                content += "id: "+ postResponse.getId() + "\n";
                content += "first_name: " +postResponse.getFirst_name() + "\n";
                content += "last_name: " +postResponse.getLast_name() + "\n";
                content += "phone_number: " +postResponse.getPhone_number() + "\n";
                content += "date_of_birth: " +postResponse.getDate_of_birth() + "\n";
                content += "employment_date: " +postResponse.getEmployment_date() + "\n\n";

                txt_res.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                txt_res.append(t.getMessage());

            }
        });
    }


    private void getposts()
    {
        Call<List<Post>> call = PostmanAPI.getPosts(7,"id","desc");
  //   Call<List<Post>> call2 = PostmanAPI.getPosts("Josh","id","desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    txt_res.setText("Code" + response.code());
                    return;

                }
                List<Post> posts = response.body();

                for(Post post : posts){
                    String content = "";
                    content += "id: "+ post.getId() + "\n";
                    content += "first_name: " + post.getFirst_name() + "\n";
                    content += "last_name: " +post.getLast_name() + "\n";
                    content += "phone_number: " +post.getPhone_number() + "\n";
                    content += "date_of_birth: " +post.getDate_of_birth() + "\n";
                    content += "employment_date: " +post.getEmployment_date() + "\n\n";

                    txt_res.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                txt_res.setText(t.getMessage());
            }
        });

    }


    public void updateLength(int id, Double length,final TextView txt)
    {
        System.out.println(id+"");
        System.out.println(length+ "");

        Call<material>call = PostmanAPI.updateLength(id,length);
        call.enqueue(new Callback<material>() {
            @Override
            public void onResponse(Call<material> call, Response<material> response) {
                if (!response.isSuccessful()){
                   txt.setText("Code" + response.code());
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


               txt.append(content);
            }

            @Override
            public void onFailure(Call<material> call, Throwable t) {
             txt.append(t.getMessage());

            }
        });
    }

    /*
    private void getposts()
    {
        Call<List<Post>> call = PostmanAPI.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    txt_res.setText("Code" + response.code());
                    return;

                }
                List<Post> posts = response.body();

                for(Post post : posts){
                    String content = "";
                    content += "id: "+ post.getId() + "\n";
                    content += "first_name: " +post.getFirst_name() + "\n";
                    content += "last_name: " +post.getLast_name() + "\n";
                    content += "phone_number: " +post.getPhone_number() + "\n";
                    content += "date_of_birth: " +post.getDate_of_birth() + "\n";
                    content += "employment_date: " +post.getEmployment_date() + "\n\n";

                    txt_res.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                txt_res.setText(t.getMessage());
            }
        });
    }
*/

    public void distmataterial(AutoCompleteTextView AC_colour, AutoCompleteTextView AC_shelf_code, AutoCompleteTextView AC_description,AutoCompleteTextView AC_country_of_origin)
    {
        String colour[] = new String[]{};
        String description[] = new String[]{};
        String shelf_code[] = new String[]{};
        String country_of_origin[] = new String[]{};
        Call<List<material>> call = PostmanAPI.getallmaterial();

        //   Call<List<Post>> call2 = PostmanAPI.getPosts("Josh","id","desc");
        call.enqueue(new Callback<List<material>>() {

            @Override
            public void onResponse(Call<List<material>> call, Response<List<material>> response) {
                if (!response.isSuccessful()){

                    return;

                }
                List<material> materials = response.body();


                for(material Material : materials){
                    String colour[] = new String[]{};
                    String description[] = new String[]{};
                    String shelf_code[] = new String[]{};
                    String country_of_origin[] = new String[]{};
                    int i = 0;
                   colour[i] = Material.getColour() ;
                   description[i] = Material.getDescription();
                    shelf_code[i] = Material.getShelf_code();
                    country_of_origin[i] = Material.getCountry_of_origin();
                    i++;



                }


            }


            @Override
            public void onFailure(Call<List<material>> call, Throwable t) {
                txt_res.setText(t.getMessage());
            }
        });
    }

}
