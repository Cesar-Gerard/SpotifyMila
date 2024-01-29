package com.example.spotify.Loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.spotify.R;

public class LoadingInsideArtist extends Dialog {
    public LoadingInsideArtist(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);

        // Configura la animación del GIF directamente
        ImageView imageView = findViewById(R.id.progressImageView);
        imageView.setImageResource(R.drawable.jake);

        // Si el GIF es una animación, inicia la animación
        if (imageView.getDrawable() instanceof AnimatedImageDrawable) {
            ((AnimatedImageDrawable) imageView.getDrawable()).start();
        }
    }


}
