package com.example.texas_project_grant;

public class material {
    private int material_id;
    private String colour;
    private String  quality;
    private String description;
    private Double cost_price_per_m;
    private Double original_length;
    private Double current_length;
    private String date_purchased;
    private String shelf_code;
    private String country_of_origin;

    public material(int material_id, String colour, String quality, String description, Double cost_price_per_m, Double original_length, Double current_length, String date_purchased, String shelf_code, String country_of_origin) {
        this.material_id = material_id;
        this.colour = colour;
        this.quality = quality;
        this.description = description;
        this.cost_price_per_m = cost_price_per_m;
        this.original_length = original_length;
        this.current_length = current_length;
        this.date_purchased = date_purchased;
        this.shelf_code = shelf_code;
        this.country_of_origin = country_of_origin;
    }

    public int getId() {
        return material_id;
    }

    public String getColour() {
        return colour;
    }

    public String getQuality() {
        return quality;
    }

    public String getDescription() {
        return description;
    }

    public Double getCost_price_per_m() {
        return cost_price_per_m;
    }

    public Double getOriginal_length() {
        return original_length;
    }

    public Double getCurrent_length() {
        return current_length;
    }

    public String getDate_purchased() {
        return date_purchased;
    }

    public String getShelf_code() {
        return shelf_code;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }
}
