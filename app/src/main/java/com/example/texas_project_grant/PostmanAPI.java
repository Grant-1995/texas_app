package com.example.texas_project_grant;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public  interface PostmanAPI {
    @GET("Server/api/getemployee")
    Call<List<Post>> getPosts(
            @Query("id") int id,
            @Query("_sort") String sort,
            @Query("_order") String order
    );
    @GET("Server/api/material")
    Call<List<material>>getmaterial(
            @Query("text") String text
    );

    @POST("Server/api/getemployee")
    Call<Post>CreatePost(@Body Post post);

    @FormUrlEncoded
    @POST("Server/api/getemployee")
    Call<Post>createPost(
         @Field("id") int id,
         @Field("first_name") String first_name,
         @Field("last_name") String last_name,
         @Field("phone_number") int phone_number,
         @Field ("date_of_birth") String date_of_birth,
         @Field("employment_date") String employment_date


    );

    @FormUrlEncoded
    @POST("Server/api/material")
    Call<material>creatematerial(
            @Field("colour") String colour,
            @Field("quality") int quality,
            @Field("description") String description,
            @Field ("cost_price_per_m") Double cost_price_per_m,
            @Field("original_length") Double original_length,
            @Field("current_length") Double current_length,
            @Field("date_purchased") String date_purchased,
            @Field("shelf_code") String shelf_code,
            @Field("country_of_origin") String country_of_origin
    );

    @FormUrlEncoded
    @POST("Server/api/getemployee")
    Call<Post>createPost(@FieldMap Map<String, String> fields);


    @PUT("Server/api/material")
    Call<material> putmaterial(@Body material Material);

    @PATCH("Server/api/material")
    Call<material> patchPost(@Body material Material);

}
