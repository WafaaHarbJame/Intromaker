package com.kinetic.sh.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterMainTab extends FragmentStatePagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public void setTitles(String str, Fragment fragment) {
        this.titles.add(str);
        this.fragments.add(fragment);
    }

    public AdapterMainTab(FragmentManager fragmentManager, int i) {
        super(fragmentManager, i);
    }

    @NonNull
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    public int getCount() {
        return this.titles.size();
    }

    public CharSequence getPageTitle(int i) {
        return this.titles.get(i);
    }
}
