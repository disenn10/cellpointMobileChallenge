package com.example.disen.cellpointassessment;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.disen.cellpointassessment.utils.GitUser;
import com.example.disen.cellpointassessment.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by disen on 7/23/2018.
 */

public class DataAsyntaskLoader extends AsyncTaskLoader<ArrayList<GitUser>> {
    String request;
    int request_type;

    public DataAsyntaskLoader(Context context, String request, int request_type) {
        super(context);
        this.request = request;
        this.request_type = request_type;
    }

    @Override
    public ArrayList<GitUser> loadInBackground() {
        switch (request_type) {
            case 0:
                try {

                    return Utils.fetchNumberofLanguages(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    return Utils.fetchData(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
