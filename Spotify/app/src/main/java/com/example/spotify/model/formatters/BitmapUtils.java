package com.example.spotify.model.formatters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;

import com.example.spotify.MainActivity;
import com.example.spotify.model.classes.Album;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class BitmapUtils {


    public static File saveBitmapToGallery(Bitmap bitmap, Album entrada) {

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        File file = new File(directory, String.valueOf(Calendar.getInstance().getTimeInMillis())+".jpg");

        try {

            FileOutputStream outputStream = new FileOutputStream(file);


            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);


            outputStream.close();


            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFilePath(File file) {
        return file.getAbsolutePath();
    }


}
