package com.example.disen.cellpointassessment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.disen.cellpointassessment.data.RepositoryContract;
import com.example.disen.cellpointassessment.ui.FavoritesCursorLoaderAdapter;

public class SavedDataActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    FavoritesCursorLoaderAdapter cursorAdapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);
        listview = (ListView) findViewById(R.id.saved_list);
        listview.setEmptyView(findViewById(R.id.emptyview));
        cursorAdapter = new FavoritesCursorLoaderAdapter(this, null);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{RepositoryContract.RepositoryEntry.ColumnID, RepositoryContract.RepositoryEntry.ColumnDesc,
                RepositoryContract.RepositoryEntry.ColumnUpdated, RepositoryContract.RepositoryEntry.ColumnCreated,
                RepositoryContract.RepositoryEntry.ColumnRepository_name};
        return new CursorLoader(this, RepositoryContract.Content_Uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            //Toast.makeText(this, ""+data.getCount(), Toast.LENGTH_SHORT).show();
            updateUI(data);
        } else {
            //set empty view
            Toast.makeText(this, "NULL!!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(Cursor data) {
        cursorAdapter.swapCursor(data);
        listview.setAdapter(cursorAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Cursor item = (Cursor) cursorAdapter.getItem(i);
                int dataColumnCreated = item.getColumnIndex(RepositoryContract.RepositoryEntry.ColumnCreated);
                int dataColumnName = item.getColumnIndex(RepositoryContract.RepositoryEntry.ColumnRepository_name);
                int dataColumnUpdated = item.getColumnIndex(RepositoryContract.RepositoryEntry.ColumnUpdated);
                int dataColumnDesc = item.getColumnIndex(RepositoryContract.RepositoryEntry.ColumnDesc);
                String created = item.getString(dataColumnCreated);
                String name = item.getString(dataColumnName);
                String updated = item.getString(dataColumnUpdated);
                String desc = item.getString(dataColumnDesc);

                Intent intent = new Intent(getApplicationContext(), SavedDataDetailsActivity.class);
                intent.putExtra("created", created);
                intent.putExtra("desc", desc);
                intent.putExtra("updated", updated);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
