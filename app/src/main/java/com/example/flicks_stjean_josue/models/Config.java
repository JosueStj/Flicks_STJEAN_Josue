package com.example.flicks_stjean_josue.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    // The base url for loading  images
    String imageBaseUrl;
    //the poster size to use when fetching image, part of the url
    String posterSize;
    // the backdrop size to use when fetching images , part  of the Url
    String backdropSize;

    public Config(JSONObject object) throws JSONException {

        JSONObject images = object.getJSONObject("images");
        imageBaseUrl = images.getString("secure_base_url");
        //get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // Use the option at index 3  or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "342");
        // parse the backdrop sizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOption = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOption.optString(1, "w780");
    }
    // helper method for creation urls
    public String getImageUrl(String size,String path) {
        return String.format("%s%s%s",imageBaseUrl, size,path);
        //concatenate all tree
    }
    public String getImageBaseUrl(){
        return imageBaseUrl;
    }
    public String getPosterSize() {
        return posterSize;
    }
 }
