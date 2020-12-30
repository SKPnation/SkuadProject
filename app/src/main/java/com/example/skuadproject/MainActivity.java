package com.example.skuadproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import java.util.List;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://raw.githubusercontent.com/SKPnation/Skuadjson/master/results_list.json";

    List<Result> listItems;
    ArrayList<Boolean> itemOpen = new ArrayList<>();
    //List<OpeningHours> openingHours;

    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_restaurants);
        recyclerView.setHasFixedSize(true);
        //recyclerView Configuration
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        //openingHours = new ArrayList<>();

        loadRecyclerViewData();

        //getRestaurantResponse();
    }

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

                            //JSONObject openingHours = restaurantDetail.getJSONObject("opening_hours");

                            Result result = new Result();
                            result.setIcon(restaurantDetail.getString("icon"));
                            result.setName(restaurantDetail.getString("name"));

                            listItems.add(result);
                        }

                        customAdapter = new CustomAdapter(listItems, getApplicationContext());
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