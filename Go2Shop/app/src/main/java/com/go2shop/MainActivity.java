package com.go2shop;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.go2shop.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        ListView listView = findViewById(R.id.ListView);
        ArrayList<String> productList = new ArrayList<>();
        getCatalogueBySearch(new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ProductDTO[] products = gson.fromJson(response, ProductDTO[].class);
                for (ProductDTO p : products) {
                    Log.i("Product: ", p.toString());
                    productList.add(p.getDescription());
                    createView(productList, listView);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.i("Error on response: ",error.toString());
            }
        });
    }

    private void createView(ArrayList<String> productList, ListView listView) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private interface VolleyResponseListener{
        void onResponse(String response);
        void onError(VolleyError error);
    }

    private void getCatalogueBySearch(final VolleyResponseListener volleyResponseListener) {
        // Initial an API call
        queue = Volley.newRequestQueue(this);
        // API URL
        String URL = "http://3.138.195.123:8770/api/catalogueService/catalogue/catalogue/search?page=0&size=100";
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", String.valueOf(0));
        params.put("size", String.valueOf(1));
        JSONObject jsonBody = new JSONObject();

        ArrayList<String> stringList = new ArrayList<>();

        StringRequest getCatalogueProducts = new StringRequest(
                // Api Call Method
                Request.Method.POST,
                // Api URL
                URL,
                // Successful Response
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("***** Rest Response: ", response);

                        /* To be remove */
                        ArrayList<String> simpleList = new ArrayList<>();
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");
                        simpleList.add("product1");

                        volleyResponseListener.onResponse(response);
                    }
                },
                // Response Error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("***** Rest Response: ", error.toString());
                        volleyResponseListener.onError(error);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("page", String.valueOf(0));
                params.put("size", String.valueOf(1));
                Log.i("**** Check param: ", params.toString());
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
//                    jsonBody.put("searchTerm", "cry");
                    final String requestBody = jsonBody.toString();
                    Log.i("**** Check Body: ", requestBody);
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        // Call
        getCatalogueProducts.setShouldCache(false);
        queue.add(getCatalogueProducts);
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    throw new IllegalArgumentException(
                            String.format(
                                    "Request#getParams() or Request#getPostParams() returned a map "
                                            + "containing a null key or value: (%s, %s). All keys "
                                            + "and values must be non-null.",
                                    entry.getKey(), entry.getValue()));
                }
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
}