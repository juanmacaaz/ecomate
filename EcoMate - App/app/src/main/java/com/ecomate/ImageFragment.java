package com.ecomate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {
    private ImageData imageData;

    public ImageFragment(ImageData imageData) {
        this.imageData = imageData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        imageView.setImageBitmap(imageData.getBitmap());
        textView.setText(imageData.getText());

        return view;
    }
}