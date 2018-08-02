package com.example.disen.cellpointassessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.disen.cellpointassessment.utils.Utils;

import java.text.ParseException;

public class SavedDataDetailsActivity extends AppCompatActivity {
    TextView name_view;
    TextView created_view;
    TextView updated_view;
    TextView desc_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_details);
        name_view = (TextView) findViewById(R.id.name);
        created_view = (TextView) findViewById(R.id.created);
        updated_view = (TextView) findViewById(R.id.updated);
        desc_view = (TextView) findViewById(R.id.desc);
        Intent intent = getIntent();
        if(intent!= null){
            String name = intent.getStringExtra("name");
            String created = intent.getStringExtra("created");
            String updated = intent.getStringExtra("updated");
            String desc = intent.getStringExtra("desc");
            name_view.setText(name);
            try {
                created_view.setText(Utils.convertDate(created));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                updated_view.setText(Utils.convertDate(updated));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            desc_view.setText(desc);
        }
    }
}
