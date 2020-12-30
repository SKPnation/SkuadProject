package com.example.skuadproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skuadproject.Models.OpeningHours;
import com.example.skuadproject.Models.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CustomAdapter.CustomAdapterListener {

    private static final String URL_DATA = "https://raw.githubusercontent.com/SKPnation/Skuadjson/master/results_list.json";

    List<Result> listItems;
    ArrayList<Boolean> itemOpen = new ArrayList<>();
    //List<OpeningHours> openingHours;

    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_restaurants);
        recyclerView.setHasFixedSize(true);
        //recyclerView Configuration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchBar);

        listItems = new ArrayList<>();
        //openingHours = new ArrayList<>();

        loadRecyclerViewData();

        //getRestaurantResponse();

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                customAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadRecyclerViewData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                response -> {
                    try {
                        //Getting JSON Object from endpoint
                        JSONObject jsonObject = new JSONObject(response);

                        //FETCH JSONArray from RESULTS
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        //Implementation of loop for getting results list data
                        for (int i=0; i<jsonArray.length(); i++){

                            //Creating a JSONObject for fetching single restaurant data
                            JSONObject restaurantDetail = jsonArray.getJSONObject(i);

                            Result result = new Result();
                            result.setName(restaurantDetail.getString("name"));
                            result.setIcon(restaurantDetail.getString("icon"));
                            JSONObject jo = restaurantDetail.optJSONObject("opening_hours");
                            if (jo != null) result.setOpeningHours(jo.getString("open_now"));

                            JSONArray jowArray = restaurantDetail.getJSONArray("photos");
                            JSONObject jow = (JSONObject) jowArray.get(0);
                            result.setHtmlAttributions(jow.getString("html_attributions"));
                            result.setBusinessStatus(restaurantDetail.getString("business_status"));
                            JSONArray typesArray = restaurantDetail.getJSONArray("types");
                            result.setItemType(typesArray.join(", "));
                            result.setItemAddress(restaurantDetail.getString("vicinity"));
                            result.setRating(restaurantDetail.getString("rating"));

                            listItems.add(result);
                        }

                        customAdapter = new CustomAdapter(listItems, getApplicationContext(), this);
                        recyclerView.setAdapter(customAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), error.getMessage()+"",Toast.LENGTH_LONG).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(Result result) {
        Toast.makeText(getApplicationContext(), "Selected: " + result.getHtmlAttributions(), Toast.LENGTH_LONG).show();
    }

//    private void getRestaurantResponse() {
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RequestInteface requestInteface=retrofit.create(RequestInteface.class);
//        Call<List<Result>> call = requestInteface();
//
//        call.enqueue(new Callback<List<Result>>() {
//            @Override
//            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Result>> call, Throwable t) {
//
//            }
//        });
//    }
}