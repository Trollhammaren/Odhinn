package com.trollhammaren.odhinn;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements OnFileSelected {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            this.setContentView(R.layout.folder_view);
            FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.folder_fragment, new FolderFragment());
            transaction.commit();
        }
    }

    @Override
    public void directorySelected(int position) {
        FolderFragment old = (FolderFragment) this.getSupportFragmentManager().findFragmentById(R.id.folder_fragment);
        FolderFragment selected = new FolderFragment();
        Bundle args = new Bundle();
        args.putString(selected.ARG_PATH, old.getFolderName() + "/" + old.getChildFileName(position));
        selected.setArguments(args);
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.folder_fragment, selected);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    
    @Override
    public void textFileSelected(int position) {
        FolderFragment old = (FolderFragment) this.getSupportFragmentManager().findFragmentById(R.id.folder_fragment);
        TextFileFragment selected = new TextFileFragment();
        Bundle args = new Bundle();
        args.putString(selected.ARG_PATH, old.getFolderName() + "/" + old.getChildFileName(position));
        selected.setArguments(args);
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.folder_fragment, selected);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
