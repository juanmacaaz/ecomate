package com.ecomate;

import android.graphics.Bitmap;

public class ImageData {
    Bitmap bitmap;
    String text;

    public ImageData(Bitmap bitmap, String text) {
        this.bitmap = bitmap;
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getText() {
        return text;
    }
}