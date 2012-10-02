package com.trollhammaren.odhinn;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FolderFragment extends ListFragment {
    // properties
    public String ARG_PATH = "SELECTED_PATH";
    private OnFileSelected listener;
    private File folder;
    
    // methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
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
        String[] items = new String[0];
        if(this.folder.isDirectory()) {
            items = this.folder.list();
            for(String j : items) {
                Log.v("dir", j);
            }
        }
        this.setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, items));
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(this.folder.isDirectory()) {
            File selected = this.folder.listFiles()[position];
            Log.v("filename", selected.getName());
            if(selected.isDirectory()) {
                this.listener.directorySelected(position);
            } else {
                
                
                String[] split = selected.getName().split("[.]+");
                String ext = "" + split.length;
                if(split.length > 1) {
                    ext = split[split.length - 1];
                }
                Log.v("extension", ext);
                String mime = null;
                try{
                    mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
                } catch(Exception e){}
                if(mime == null) {
                    mime = "Unknown";
                } else if(mime.startsWith("text/")) {
                    this.listener.textFileSelected(position);
                } else {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(selected), mime);
                        this.startActivity(intent);
                    } catch (Exception e) {
                        this.listener.textFileSelected(position);
                    }
                }
                Log.v("mimetype", mime);
            }
        }
    }
    
    /**
     * Set the select listener of the main activity.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.listener = (OnFileSelected) activity;
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
