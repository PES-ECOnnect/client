package com.econnect.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;

public class BitmapLoader {
    public static Bitmap fromURL(String URL, int height) {
        Bitmap image;
        try {
            java.net.URL url = new URL(URL);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException  e) {
            return null;
        }
        if (height > 0) {
            int scaledWidth = (height * image.getWidth()) / image.getHeight();
            image = Bitmap.createScaledBitmap(image, scaledWidth, height, true);
        }
        return image;
    }

    public static Bitmap fromURL(String URL) {
        return fromURL(URL, -1);
    }
}
