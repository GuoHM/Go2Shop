package com.go2shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.go2shop.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.scottyab.rootbeer.RootBeer;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RootBeer rootBeer = new RootBeer(this);
        if (rootBeer.isRooted()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alert)
                    .setMessage(R.string.rooted_message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }

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
                Log.i("Error on response: ", error.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
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
        if (id == R.id.action_payment) {
            this.startActivity(new Intent(this, PaymentActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
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

    private interface VolleyResponseListener {
        void onResponse(String response);

        void onError(VolleyError error);
    }

    private SSLSocketFactory getSSLSocketFactory() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = getResources().openRawResource(R.raw.server);

        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("192.168.0.159", session);
            }
        };
    }

    private void getCatalogueBySearch(final VolleyResponseListener volleyResponseListener) {
        HurlStack hurlStack = new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                try {
                    httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
                    httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }
        };
        // Initial an API call
        queue = Volley.newRequestQueue(this, hurlStack);
        // API URL
        String URL = "https://192.168.0.159:8770/api/catalogueService/catalogue/catalogue/search?page=0&size=100";
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
            protected Map<String, String> getParams() {
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