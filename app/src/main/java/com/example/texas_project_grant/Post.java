package com.example.texas_project_grant;

import java.time.LocalDate;

public class Post {
    private int id;
    private String first_name;
    private String  last_name;
    private int phone_number;
    private String date_of_birth;
    private String employment_date;

    public Post(int id,String first_name, String last_name, int phone_number, String date_of_birth, String employment_date) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.employment_date = employment_date;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getEmployment_date() {
        return employment_date;
    }


}
