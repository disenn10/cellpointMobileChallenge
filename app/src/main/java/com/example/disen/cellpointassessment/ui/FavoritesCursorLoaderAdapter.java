package com.example.disen.cellpointassessment.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disen.cellpointassessment.R;
import com.example.disen.cellpointassessment.data.RepositoryContract;

/**
 * Created by disen on 8/1/2018.
 */

public class FavoritesCursorLoaderAdapter extends android.support.v4.widget.CursorAdapter {
    public FavoritesCursorLoaderAdapter(Context context, Cursor c) {
        super(context, c);
    }
    public static class viewHolder{
        TextView textView_label;
        public viewHolder(View view){
            textView_label = view.findViewById(R.id.section);
        }

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.sections,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        viewHolder viewHolder = new viewHolder(view);
        int label = cursor.getColumnIndex(RepositoryContract.RepositoryEntry.ColumnRepository_name);
        String name = cursor.getString(label);
        viewHolder.textView_label.setText(name);
    }
}
