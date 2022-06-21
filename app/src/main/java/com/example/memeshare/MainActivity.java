package com.example.memeshare;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    String currenturl = null;
    ProgressBar progress_circular;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress_circular = findViewById(R.id.progress_circular);

        loadMeme();
    }

    public  void loadMeme(){




        ImageView imageView = findViewById(R.id.imageView);
        String url ="https://meme-api.herokuapp.com/gimme";



// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {
            
                    try {
                         currenturl= response.getString("url");

                        Glide.with(this).load(currenturl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progress_circular.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progress_circular.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(imageView);

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                    

                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }
public void share() {
    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
    Uri imageUri = Uri.parse(currenturl);
    sharingIntent.setType("image/*");
    sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
    startActivity(sharingIntent);

}






    public void next(View view) {
        loadMeme();
    }

}