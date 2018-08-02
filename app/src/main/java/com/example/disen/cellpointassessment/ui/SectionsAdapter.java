package com.example.disen.cellpointassessment.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disen.cellpointassessment.R;
import com.example.disen.cellpointassessment.utils.GitUser;

import java.util.ArrayList;

/**
 * Created by disen on 7/23/2018.
 */

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.languageAdapter> {
    Context context;
    ArrayList<GitUser> sections;
    OnsectionClickListener onsectionClickListener;
    int type;

    public SectionsAdapter(Context context, ArrayList<GitUser> sections,OnsectionClickListener onsectionClickListener,int type){
        this.context = context;
        this.sections = sections;
        this.onsectionClickListener = onsectionClickListener;
        this.type = type;
    }

    public interface OnsectionClickListener{
        public void onItemClicked(int position);
    }
    @Override
    public SectionsAdapter.languageAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.sections,parent,false);
        return new languageAdapter(view);
    }

    @Override
    public void onBindViewHolder(SectionsAdapter.languageAdapter holder, int position) {
        switch (type){
            case 0:
                holder.section.setText(sections.get(position).getLanguage());
                holder.repos.setText(String.valueOf(sections.get(position).getNoLanguages()));
                break;
            case 1:
                holder.icon.setVisibility(View.VISIBLE);
                holder.section.setText(sections.get(position).getName());
                holder.repos.setText(String.valueOf(sections.get(position).getWatchers()));
        }

    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public class languageAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView section;
        TextView repos;
        ImageView icon;
        public languageAdapter(View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.section);
            repos = itemView.findViewById(R.id.norepos);
            icon = itemView.findViewById(R.id.watch_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onsectionClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
