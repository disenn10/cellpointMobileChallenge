package com.example.disen.cellpointassessment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ReposActivity extends AppCompatActivity implements ReposFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = new Bundle();
        String language = getIntent().getStringExtra("language");
        String query = getIntent().getStringExtra("query");
        bundle.putString("language", language);
        bundle.putString("query", query);
        ReposFragment reposFragment = new ReposFragment();
        ReposDetailsFragment reposdetails = new ReposDetailsFragment();
        reposFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, reposFragment, "reposfragment").commit();
        int screenSize = getResources().getConfiguration().smallestScreenWidthDp;
        if (screenSize >= 600) {
            FragmentManager sec_fragmentManager = getSupportFragmentManager();
            sec_fragmentManager.beginTransaction().replace(R.id.second_container, reposdetails, "details").commit();
        }
    }


    @Override
    public void sendDetails(String name, String desc, String url, String created, String updated) {
        ReposDetailsFragment reposDetailsFragment = (ReposDetailsFragment) getSupportFragmentManager().findFragmentByTag("details");
        if (reposDetailsFragment != null) {
            //update its content
            reposDetailsFragment.updateView(name, desc, url, created, updated);
        } else {
            ReposDetailsFragment new_reposDetailsFragment = new ReposDetailsFragment();
            Bundle b = new Bundle();
            b.putString("name", name);
            b.putString("created", created);
            b.putString("desc", desc);
            b.putString("updated", updated);
            b.putString("url", url);
            new_reposDetailsFragment.setArguments(b);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, new_reposDetailsFragment, "reposdetailsfragment").addToBackStack(null).commit();

        }


    }
}
