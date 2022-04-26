package com.econnect.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ShareManager {
    public static void shareText(String text, Context context) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        context.startActivity(shareIntent);
    }

    public static void shareTextAndImage(String text, Bitmap image, Context context) {
        //String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/share.png";

        final File outputDir = context.getCacheDir();
        final File outputFile;
        try {
            outputFile = File.createTempFile("export", ".png", outputDir);
            OutputStream out = new FileOutputStream(outputFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException("There has been an error while sharing: " + e.getMessage(), e);
        }
        Uri bmpUri = FileProvider.getUriForFile(context, "com.econnect.client.fileprovider", outputFile);

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("image/png");
        context.startActivity(shareIntent);
    }
}
