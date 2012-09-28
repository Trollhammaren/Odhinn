package com.trollhammaren.odhinn;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FolderFragment extends ListFragment {
    // properties
    public String ARG_PATH = "SELECTED_PATH";
    private OnFolderSelected listener;
    private File folder;
    
    // methods
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        String path = "/";
        if(args != null) {
            path = args.getString(this.ARG_PATH).replaceAll("//", "/");
        }
        this.folder = new File(path);
        Log.v("path", path);
        String[] items = this.folder.list();
        for(String j : items) {
            Log.v("dir", j);
        }
        this.setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, items));
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.listener.click(position);
    }
    
    /**
     * Set the select listener of the main activity.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.listener = (OnFolderSelected) activity;
        } catch (ClassCastException e) {
        }
    }
    
    public File getFile() {
        return this.folder;
    }
    
    public String getFolderName() {
        return this.folder.getPath();
    }
    
    public String getChildFileName(int position) {
        if(this.folder.isDirectory()) {
            return this.folder.list()[position];
        }
        return "";
    }
}
