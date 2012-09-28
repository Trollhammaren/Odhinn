package com.trollhammaren.odhinn;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements OnFolderSelected {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    @Override
    public void click(int position) {
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
}
