package com.ecomate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class CarouselFragment extends Fragment {

    private ArrayList<ImageData> images;

    public CarouselFragment(ArrayList<ImageData> images) {
        this.images = images;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        viewPager2.setAdapter(new CarouselAdapter(this));

        return view;
    }

    private class CarouselAdapter extends FragmentStateAdapter {
        public CarouselAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // you can use custom fragment for image and text
            ImageFragment fragment = new ImageFragment(images.get(position));
            return fragment;
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }
}
