package com.example.creteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.roomandroidexample.Converters;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class postpage  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        configureNavigationDrawer();
        configureToolbar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("");
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer() {
        androidx.drawerlayout.widget.DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigation_view);
        RelativeLayout lin = findViewById(R.id.viewfragmentscreen);
        TextView texts =  findViewById(R.id.toolbartitle);
        lin.setVisibility(View.VISIBLE);
        navView.setNavigationItemSelectedListener(menuItem -> {
            Fragment f = null;
            int itemId = menuItem.getItemId();
            if(itemId == R.id.view)
            {
                lin.setVisibility(View.GONE);
                texts.setText("View Post");
                f = new view_fragment();
            }
            else if (itemId == R.id.create) {
                lin.setVisibility(View.GONE);
                texts.setText("Create Post");
                f = new create_fragment();
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this) //set icon
                        .setIcon(android.R.drawable.ic_delete) //set title
                        .setTitle("Log Out") //set message
                        .setMessage("Are you sure you want to Logout?") //set positive button
                        .setPositiveButton
                                (android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)    {
                                        sharepreference.cleardata(getApplicationContext());
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        //set negative button
                        .setNegativeButton (android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)    {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
            if (f != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, f);
                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        androidx.drawerlayout.widget.DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
    public interface ApiInterface {

        @GET("posts")
        Call<List<Post>> getAllResponse();

        @GET("users/{user_id}/posts")
        Call<List<Post>> getpostbyid(
                @Header("Authorization") String token,
                @Path("user_id") int user_id);

        @POST("posts")
        @FormUrlEncoded
        Call<Post> postUser(
                @Header("Authorization") String token,
                             @Field("user_id") long userId,
                             @Field("title") String title,
                             @Field("body") String body);

        @DELETE("posts/{id}")
        Call<Post> deleteItem(
                @Header("Authorization") String token,
                @Path("id") int id);

        @PATCH("posts/{id}")
        Call<Post> updatepost(
                @Header("Authorization") String token,
                @Path("id") int id, @Body Post posts);

    }


    @Entity(tableName = "posts")
    public static class Post{
        @PrimaryKey(autoGenerate = true)
        public int id;
        private int user_id;
        private String title;
        private String body;
    
        public Post(int user_id, String title, String body) {
            this.user_id = user_id;
            this.title = title;
            this.body = body;
        }

        public int getId() {
            return id;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }


    }

}
