package com.example.creteapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creteapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class view_fragment extends Fragment {
    List<postpage.Post> EmptyList = Collections.<postpage.Post>emptyList();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycle_layout, container, false);
        PostRepository repo = new PostRepository(getActivity());


        String stat = "Internet";
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        Integer d = getActivity().getIntent().getIntExtra("user_id", 0);
        Log.d("CHECKING IDS", d.toString());
        Call<List<postpage.Post>> call=  RetrofitClient2.getInstance().getMyApi().getpostbyid("Bearer a24969d8efe8fb877ff52802e48a129b531291e80163f35597e996d315a826b8",d);
        ProgressBar spinners= v.findViewById(R.id.progressBar1);
        TextView txt1 = v.findViewById(R.id.nothingtext);
        spinners.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<postpage.Post>>() {
            @Override
            public void onResponse(Call<List<postpage.Post>> call, Response<List<postpage.Post>> response) {
                if(response.isSuccessful())
                {
                    EmptyList = response.body();
                    if(response.body().isEmpty())
                    {
                        txt1.setText("NOTHING TO SHOW");
                    }
                    spinners.setVisibility(View.GONE);
                    RecyclerView.Adapter adapter = new MyListAdapter(response.body(), v.getContext(), (FragmentActivity) getContext(), v, stat);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    swipelistviewJSON(recyclerView, v);
                }
            }

            @Override
            public void onFailure(Call<List<postpage.Post>> call, Throwable throwable) {
                Toast.makeText(getActivity(), "Showing data from local database", Toast.LENGTH_LONG).show();
                Log.d("Check", d.toString());
                spinners.setVisibility(View.GONE);
                if(repo.getpostbyid(d).isEmpty())
                {
                    txt1.setText("NOTHING TO SHOW");
                }
                Log.d("Data", repo.getpostbyid(d).toString());
                RecyclerView.Adapter adapter = new MyListAdapter(repo.getpostbyid(d), v.getContext(), (FragmentActivity) getContext(),v, "");
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                swipelistview(recyclerView,repo,v);

            }

        });
        return v;
    }
    private void swipelistview(RecyclerView recyclerView, PostRepository repos, View v)
    {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                Integer swipedPosition = viewHolder.getAdapterPosition();
                MyListAdapter myListAdapter = (MyListAdapter) recyclerView.getAdapter();
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()) //set icon
                            .setIcon(android.R.drawable.ic_delete) //set title
                            .setTitle("Delete User") //set message
                            .setMessage("Are you sure you want to delete user?") //set positive button
                            .setPositiveButton
                (android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)    {
                        repos.deletebyid(myListAdapter.getUID(swipedPosition));
                        myListAdapter.removed(swipedPosition);
                        Toast.makeText(getActivity(), "Data deleted successfully", Toast.LENGTH_LONG).show();
                        TextView txt1 = v.findViewById(R.id.nothingtext);
                        if(repos.getallposts().isEmpty())
                        {
                            txt1.setText("NOTHING TO SHOW");
                            txt1.setVisibility(View.VISIBLE);
                        }

                    }
                })
              //set negative button
                            .setNegativeButton (android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)    {
                                   myListAdapter.undo();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void swipelistviewJSON(RecyclerView recyclerView, View v)
    {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                MyListAdapter myListAdapter = (MyListAdapter) recyclerView.getAdapter();
                Integer swipedPosition = viewHolder.getAdapterPosition();
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()) //set icon
                        .setIcon(android.R.drawable.ic_delete) //set title
                        .setTitle("Delete User") //set message
                        .setMessage("Are you sure you want to delete user?") //set positive button
                        .setPositiveButton
                                (android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)    {
                                        Log.d("CHECLONG INDEX", myListAdapter.getUID(swipedPosition).toString());
                                        Call<postpage.Post> call=  RetrofitClient2.getInstance().getMyApi().deleteItem("Bearer a24969d8efe8fb877ff52802e48a129b531291e80163f35597e996d315a826b8",myListAdapter.getUID(swipedPosition));
                                        call.enqueue(new Callback<postpage.Post>() {
                                            @Override
                                            public void onResponse(Call<postpage.Post> call, Response<postpage.Post> response) {
                                                myListAdapter.removed(swipedPosition);
                                                Toast.makeText(getActivity(), "Data deleted successfully", Toast.LENGTH_LONG).show();
                                                TextView txt1 = v.findViewById(R.id.nothingtext);
                                                if(EmptyList.isEmpty())
                                                {
                                                    txt1.setText("NOTHING TO SHOW");
                                                    txt1.setVisibility(View.VISIBLE);
                                                }
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
                                myListAdapter.undo();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}