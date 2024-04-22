package com.example.creteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.data.PostDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class create_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.createposts, container, false);
        TextInputLayout titleid = v.findViewById(R.id.textInputLayouttitleid);
        TextInputEditText title = v.findViewById(R.id.titleid);
        TextInputLayout bodyid = v.findViewById(R.id.textInputLayoutbodyid);
        TextView createposthead = v.findViewById(R.id.createpostheading);
        TextInputEditText body = v.findViewById(R.id.bodyid);
        Button create = v.findViewById(R.id.createpost);
        PostRepository repo = new PostRepository(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int myInt = bundle.getInt("uid", 0);
            String stats = bundle.getString("stat");
            String titlepost = bundle.getString("title");
            String bodypost = bundle.getString("body");
            int myID = bundle.getInt("id", 0);
            title.setText(titlepost);
            body.setText(bodypost);
            create.setText("Update Post");
            createposthead.setText("Update Post");
            Log.d("CHECKING IDS", String.valueOf(myID));
            create.setOnClickListener(
                    view ->
                    {
                        Log.d("CHECKING IDS IN BUTTON", String.valueOf(myID));
                        Log.d("Check titles", title.getText().toString());
                        Log.d("Checking bodys", body.getText().toString());
                        Log.d("Update", "I AM PRESSED");
                        if(stats == "")
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()) //set icon
                                    .setIcon(android.R.drawable.ic_delete) //set title
                                    .setTitle("Update Post") //set message
                                    .setMessage("Are you sure you want to update post?") //set positive button
                                    .setPositiveButton
                                            (android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which)    {
                                                    Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_LONG).show();
                                                    repo.updateposts(myID, title.getText().toString(), body.getText().toString());
                                                }
                                            })
                                    //set negative button
                                    .setNegativeButton (android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which)    {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();

                        }
                        else
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()) //set icon
                                    .setIcon(android.R.drawable.ic_delete) //set title
                                    .setTitle("Update Post") //set message
                                    .setMessage("Are you sure you want to update post?") //set positive button
                                    .setPositiveButton
                                            (android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which)    {
                                                    postpage.Post posts = new postpage.Post(myInt, title.getText().toString(), body.getText().toString());
                                                    Call<postpage.Post> call=  RetrofitClient2.getInstance().getMyApi().updatepost("Bearer a24969d8efe8fb877ff52802e48a129b531291e80163f35597e996d315a826b8",myID, posts);
                                                    call.enqueue(new Callback<postpage.Post>() {
                                                        @Override
                                                        public void onResponse(Call<postpage.Post> call, Response<postpage.Post> response) {
                                                            Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_LONG).show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<postpage.Post> call, Throwable throwable) {
                                                            Toast.makeText(getActivity(), "Some thing went wrong", Toast.LENGTH_LONG).show();

                                                        }

                                                    });
                                                }
                                            })
                                    //set negative button
                                    .setNegativeButton (android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which)    {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();

                        }
                    }
            );
        }
        else
        {
            create.setOnClickListener(
                    view ->
                    {
                        if (!isnamevalid(title.getText().toString())) {
                            titleid.setError("Invalid title for the post");
                        } else {
                            titleid.setError(null);
                        }
                        bodyid.setError(null);
                        if(titleid.getError() == null && bodyid.getError() == null)
                        {
                            InternetConnectivity check = new InternetConnectivity(getActivity().getApplicationContext());
                            if(check.checkinternet(getContext()))
                            {
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext()) //set icon
                                        .setIcon(android.R.drawable.ic_delete) //set title
                                        .setTitle("Add User") //set message
                                        .setMessage("Are you sure you want to add post?") //set positive button
                                        .setPositiveButton
                                                (android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which)    {
                                                        Integer d = getActivity().getIntent().getIntExtra("user_id", 0);
                                                        Call<postpage.Post> call=  RetrofitClient2.getInstance().getMyApi().postUser("Bearer a24969d8efe8fb877ff52802e48a129b531291e80163f35597e996d315a826b8",d, title.getText().toString(), body.getText().toString());
                                                        Log.d("TESTER", "CALL STARTED");
                                                        call.enqueue(new Callback<postpage.Post>() {
                                                            @Override
                                                            public void onResponse(Call<postpage.Post> call, Response<postpage.Post> response) {
                                                                Log.d("CHECK RESPONSE BY API", "API CALL");
                                                                    List<postpage.Post> list = Collections.singletonList(response.body());
                                                                    Log.d("TAG", "response title" + response.body().getTitle());
                                                                    Log.d("TAG", "response body" + response.body().getBody());
                                                                    Log.d("TAG", "response user id" + response.body().getUser_id());
                                                                    Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_LONG).show();
                                                            }

                                                            @Override
                                                            public void onFailure(Call<postpage.Post> call, Throwable throwable) {
                                                                Toast.makeText(getActivity(), "Some thing went wrong", Toast.LENGTH_LONG).show();
                                                            }

                                                        });
                                                    }
                                                })
                                        //set negative button
                                        .setNegativeButton (android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which)    {
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();

                            }
                            else
                            {
                                Integer d = getActivity().getIntent().getIntExtra("user_id", 0);
                                AlertDialog alertDialog = new AlertDialog.Builder(getContext()) //set icon
                                        .setIcon(android.R.drawable.ic_delete) //set title
                                        .setTitle("Add User") //set message
                                        .setMessage("Are you sure you want to add post?") //set positive button
                                        .setPositiveButton
                                                (android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which)    {
                                                        postpage.Post posts = new postpage.Post(d,title.getText().toString(),body.getText().toString());
                                                        repo.insertposts(posts);
                                                        Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                        //set negative button
                                        .setNegativeButton (android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which)    {

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
                            }

                        } }
            );
        }


        return v;
    }

    public boolean isnamevalid(String s){

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number for which we create an object of
        // Pattern class
        Pattern p = Pattern.compile(
                "[a-zA-Z]+"
        );

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression by creating an object of
        // Matcher class
       Matcher m = p.matcher(s);

        // Returns boolean value
        return m.matches();
    }

}
