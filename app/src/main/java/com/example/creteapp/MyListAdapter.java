package com.example.creteapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    List<postpage.Post> listdata;
    Context context;
    FragmentActivity activity;
    View v;
    String stat;
    public MyListAdapter(List<postpage.Post> listdata, Context context,  FragmentActivity  activity, View v, String stat) {
        this.activity = activity;
        this.context = context;
        this.listdata = listdata;
        this.v = v;
        this.stat = stat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.postui, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titletextview.setText(listdata.get(position).getTitle());
        holder.bodytextview.setText(listdata.get(position).getBody());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = null;
                f = new create_fragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", listdata.get(position).getTitle());
                bundle.putString("body", listdata.get(position).getBody());
                bundle.putInt("uid", listdata.get(position).getUser_id());
                bundle.putInt("id", listdata.get(position).getId());
                bundle.putString("stat", stat);
                f.setArguments(bundle);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, f).addToBackStack(null).commit();
                v.setVisibility(view.GONE);
            }
        }
        );
    }


    public int getItemCount() {
        return listdata.size();
    }


    public Integer getUID(int position)
    {
        return listdata.get(position).id;
    }

    public void removed(int position)
    {
        listdata.remove(position);
        notifyDataSetChanged();
    }


    public void undo()
    {
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titletextview;
        public TextView bodytextview;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.titletextview = (TextView) itemView.findViewById(R.id.titletextview);
            this.bodytextview = (TextView) itemView.findViewById(R.id.bodytextview);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);

        }
    }
}
