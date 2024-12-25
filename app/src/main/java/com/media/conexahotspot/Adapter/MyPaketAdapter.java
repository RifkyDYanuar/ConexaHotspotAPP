package com.media.conexahotspot.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.media.conexahotspot.CNXFragment;
import com.media.conexahotspot.HotspotFragment;

public class MyPaketAdapter extends FragmentStateAdapter {


    public MyPaketAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==1){
            return  new HotspotFragment();
        }
        return new CNXFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
