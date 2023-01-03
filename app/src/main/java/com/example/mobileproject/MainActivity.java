package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.mobileproject.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager viewPager;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Set and define the tabs
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
          //      getSupportFragmentManager(), tabs.getTabCount());

        //viewPager = binding.viewPager;
        //viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        private final Context context;
        private int totalTabs;

        public SectionsPagerAdapter(@NonNull FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
            super(fm, behavior);
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    NotesFragment notesFragment = new NotesFragment();
                    return notesFragment;
                case 1:
                    ProfileFragment profileFragment = new ProfileFragment();
                    return profileFragment;
                default: return null;
            }

        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }
}