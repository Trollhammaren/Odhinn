package com.trollhammaren.odhinn;

import java.io.File;
import java.io.FileInputStream;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFileFragment extends Fragment {

    public String ARG_PATH = "SELECTED_PATH";
    private File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            return inflater.inflate(R.layout.text_view, container, false);
        } catch(Exception e) {
            e.printStackTrace();
            Log.v("meh!!!!", "got here");
            return null;
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        String path = "/";
        if(args != null) {
            path = args.getString(this.ARG_PATH).replaceAll("//", "/");
        }
        this.file = new File(path);
        String out = "Unable to display contents.";
        try{
            FileInputStream in = new FileInputStream(this.file);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            out = "";
            for(byte b : buffer) {
                out += (char) b;
            }
            Log.v("contents", out);
        } catch(Exception e) {
              Log.v("meh!", "Unable to read file");
        }
        TextView view = (TextView) getActivity().findViewById(R.id.text_file);
        view.setMovementMethod(new ScrollingMovementMethod());
        view.setText(out);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);}
}
