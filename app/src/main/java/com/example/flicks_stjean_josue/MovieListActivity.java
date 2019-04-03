/*************************ST JEAN JOSUE ***************************/
package com.example.flicks_stjean_josue;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.flicks_stjean_josue.models.Config;
import com.example.flicks_stjean_josue.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
/*************************ST JEAN JOSUE ***************************/

public class MovieListActivity extends Activity {
    //constant
    // the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // the parameter name for the API key
    public final static  String API_KEY_PARAM = "api_key";

    // The api key -- TODO  move to a secure location
    public final static String API_key = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

   // Tag for logging from the Activity
   public final static String TAG = "MovieListActivity";

    // instance fields
    AsyncHttpClient client;
    // the list of currently playing movies
    ArrayList<Movie> movies;
    // the recycler view
    RecyclerView rvMovies;
    // the adapter wired to the recycler view
    MovieAdapter adapter;
    // image config
    Config config;

/*  private String message;
    private Throwable error;
    private Boolean alertUser;
*/
/*************************ST JEAN JOSUE ***************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //Initialize the client
        client = new AsyncHttpClient();
        // Initialize the list of the movies
        movies = new ArrayList<>();
        // initialize movie adapter -- movie Array connot be reinitialize after this point
        adapter = new MovieAdapter(movies);
        // resolve the recycler view and connect a layout manager and the adapter
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        // get the configuration on app creation
        getConfiguration();
        // get the now playing movie list
        getNowPlaying();
    }
    // get the list of currently playing movies from the API
    private void getNowPlaying(){
        // Create the URL
        String url = API_BASE_URL + "/movie/now_playing";
        // Set the request Parameters
        RequestParams params = new RequestParams();
        // API  key, always required
        params.put(API_KEY_PARAM,getString(R.string.api_key));
        // execute a Get  request expecting a JSON object response
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        // Load the results into movie list
                try {
                    JSONArray results = response.getJSONArray("results");
                    // Iterate through result set and create movie object
                    for (int i = 0; i < results.length(); i++){
                     Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                // Notify adapter that a row was added
                adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                  logError();
                }
            }
        @Override
        public void onFailure
        (int statusCode, Header[] headers, String responseString, Throwable throwable) {
        logError();

            }
        });
    }
    private void logError() {
    }

    // Get the configuration from the API
    private void  getConfiguration(){
    // Create the URL
        String url = API_BASE_URL + "/configuration";
    // Set the request Parameters
        RequestParams params = new RequestParams();
    // API  key, always required
        params.put(API_KEY_PARAM, getString(R.string.api_key));
    // execute a Get  request expecting a JSON object response
    client.get(url,params, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // get the image base url
            try {
                config = new Config(response);
                Log.i(TAG,String.format
                        ("Loaded configuration with imageBaseUrl %s and posterSize",
                        config.getImageBaseUrl(),
                        config.getPosterSize()));
                // pass config to adapter
                adapter.setConfig(config);
            } catch (JSONException e) {
                logError("Failed parsing configuration", e, true);
            }
        }
        @Override
        public void onFailure
                (int statusCode, Header[] headers, String responseString, Throwable throwable) {
            logError("Failed getting configuration", throwable, true);
        }
        //Handle errors , log and Alert user
        private void logError(String message,Throwable error, Boolean alertUser) {
            // Always log the Error
            Log.e(TAG, message, error);
            // Alert the user  to avoid silent  errors
            if (alertUser) {
                // Show a long toast  with the error message
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    });
  }
}
/*************************ST JEAN JOSUE ***************************/

