package com.example.disen.cellpointassessment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.disen.cellpointassessment.ui.SectionsAdapter;
import com.example.disen.cellpointassessment.utils.GitUser;
import com.example.disen.cellpointassessment.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<GitUser>>,SectionsAdapter.OnsectionClickListener {
    RecyclerView recyclerView;
    ArrayList<GitUser> data_copy;
    String query_copy;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recy);
        //check connection
        Boolean internet = checkInternet();
        //check if user entered a user/organization
        String query = handleIntent(getIntent());
        //if device is connected
        if(internet){
            //but user hasn't typed user/organization tell user to look for one
            if(query == null){
                lookForRepository(2);
                //check the servers for user/organization
            }else{
            LoaderManager lod = getSupportLoaderManager();
            lod.initLoader(0,null,this);}
        }
        //if device is not connected to internet, let user know
        else{
            connectToInternet();
        }


    }

    @Override
    public Loader<ArrayList<GitUser>> onCreateLoader(int id, Bundle args) {
        String query = handleIntent(getIntent());
        return new DataAsyntaskLoader(this, query, 0, "");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<GitUser>> loader, ArrayList<GitUser> data) {
        if(data!= null){
            Collections.sort(data, GitUser.SectionListComparator);
            data_copy = data;
            updateUI(data);
        }else{
            lookForRepository(1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //go to saved data activity
            case R.id.save:
                Intent intent = new Intent(this,SavedDataActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    //update the UI with list of repositories
    private void updateUI(ArrayList<GitUser> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        SectionsAdapter sections_adapt = new SectionsAdapter(this,data,this,0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sections_adapt);
    }

    //create a request once the user type the search button
    private String handleIntent(Intent intent) {
        String query = null;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = Utils.createRequest(intent.getStringExtra(SearchManager.QUERY));
        }
        else {
            query = null;
        }
        query_copy = intent.getStringExtra(SearchManager.QUERY);
        return query;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<GitUser>> loader) {

    }
    //returns true if device is connected to internet otherwise returns false
    public Boolean checkInternet(){
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    //once item is clicked, go to list of repositories of the clicked section
    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this,ReposActivity.class);
        intent.putExtra("language",data_copy.get(position).getLanguage());
        intent.putExtra("query",query_copy);
        Boolean internet = checkInternet();
        if(internet){
        startActivity(intent);}
        else{
            connectToInternet();
        }
    }
    //inform the user to connect device to internet
    public void connectToInternet(){
        Snackbar snackbar = Snackbar.make(recyclerView,getString(R.string.connect),Snackbar.LENGTH_LONG);
        snackbar.show();

    }
    public void lookForRepository(int task){
        Snackbar snackbar = Snackbar.make(recyclerView,getString(R.string.notfound),Snackbar.LENGTH_LONG);
        switch (task){
            case 1:
                snackbar.show();
                break;
            case 2:
                snackbar.setText("Search for a repository").show();
                break;
        }

        snackbar.show();

    }
}
