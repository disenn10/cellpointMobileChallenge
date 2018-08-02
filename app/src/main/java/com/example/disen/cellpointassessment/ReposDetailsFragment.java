package com.example.disen.cellpointassessment;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disen.cellpointassessment.data.RepositoryContract;
import com.example.disen.cellpointassessment.utils.Utils;

import java.text.ParseException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReposDetailsFragment extends Fragment {
    TextView name_view;
    ImageButton url_view;
    TextView desc_view;
    TextView updated_view;
    TextView created_view;
    String name;
    String url;
    String created;
    String updated;
    String desc;
    FloatingActionButton fab;
    FrameLayout root;


    public ReposDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repos_details, container, false);
        root = view.findViewById(R.id.frag_details);
        name_view = view.findViewById(R.id.name);
        url_view = view.findViewById(R.id.link);
        desc_view = view.findViewById(R.id.desc);
        updated_view = view.findViewById(R.id.updated);
        created_view = view.findViewById(R.id.created);
        fab = view.findViewById(R.id.save);

        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            url = bundle.getString("url");
            created = bundle.getString("created");
            updated = bundle.getString("updated");
            desc = bundle.getString("desc");

            name_view.setText(name);
            desc_view.setText(desc);
            try {
                updated_view.setText(Utils.convertDate(updated));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                created_view.setText(Utils.convertDate(created));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            url_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });
            //save data if download icon is clicked
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveData(name, desc, created, updated);
                }
            });

        }
        return view;
    }

    //update view when the screen device is at least 600 dp
    public void updateView(final String name, final String desc, final String url, final String created, final String updated) {
        name_view.setText(name);
        desc_view.setText(desc);
        try {
            updated_view.setText(Utils.convertDate(updated));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            created_view.setText(Utils.convertDate(created));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        url_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(name, desc, created, updated);
            }
        });
    }

    //save data to database
    public void saveData(String name, String desc, String created, String updated) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RepositoryContract.RepositoryEntry.ColumnCreated, created);
        contentValues.put(RepositoryContract.RepositoryEntry.ColumnDesc, desc);
        contentValues.put(RepositoryContract.RepositoryEntry.ColumnUpdated, updated);
        contentValues.put(RepositoryContract.RepositoryEntry.ColumnRepository_name, name);
        getContext().getContentResolver().insert(RepositoryContract.Content_Uri, contentValues);
        Snackbar snackbar = Snackbar.make(root,"Saved!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
