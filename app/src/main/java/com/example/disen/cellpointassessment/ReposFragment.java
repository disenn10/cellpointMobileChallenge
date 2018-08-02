package com.example.disen.cellpointassessment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.disen.cellpointassessment.ui.SectionsAdapter;
import com.example.disen.cellpointassessment.utils.GitUser;
import com.example.disen.cellpointassessment.utils.Utils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReposFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReposFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<GitUser>>, SectionsAdapter.OnsectionClickListener {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<GitUser> data_copy;
    String language;
    String query;

    public ReposFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        recyclerView = view.findViewById(R.id.repo_recy);
        LoaderManager loaderManager = getLoaderManager();
        language = getArguments().getString("language");
        query = getArguments().getString("query");
        loaderManager.initLoader(0, null, this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    /**
     * public void onButtonPressed(Uri uri) {
     * if (mListener != null) {
     * mListener.sendDetails();
     * }
     * }
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<ArrayList<GitUser>> onCreateLoader(int id, Bundle args) {
        return new DataAsyntaskLoader(getContext(), Utils.createRequest(query, language), 1);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<GitUser>> loader, ArrayList<GitUser> data) {
        if (data != null) {
            data_copy = data;
            updateUI(data);
        }
    }

    private void updateUI(ArrayList<GitUser> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        SectionsAdapter sections_adapt = new SectionsAdapter(getContext(), data, this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sections_adapt);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<GitUser>> loader) {

    }

    @Override
    public void onItemClicked(int position) {
        String name = data_copy.get(position).getName();
        String created = data_copy.get(position).getCreated();
        String desc = data_copy.get(position).getDescription();
        String updated = data_copy.get(position).getUpdated();
        String url = data_copy.get(position).getUrl();
        mListener.sendDetails(name, desc, url, created, updated);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void sendDetails(String name, String desc, String url, String created, String updated);
    }
}
