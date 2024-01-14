package com.example.spotify.dialogs;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.spotify.R;
import com.example.spotify.databinding.FragmentCustomDialogImagePickerBinding;

import java.io.ByteArrayOutputStream;


public class Custom_Dialog_Image_Picker extends DialogFragment {

    private static final int REQUEST_IMAGE_PICKER = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    FragmentCustomDialogImagePickerBinding b;




    private OnImageSelectedListener imageSelectedListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentCustomDialogImagePickerBinding.inflate(getLayoutInflater());
        View v = b.getRoot();

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        setUpImagePicker();
        setUpCamera();



        return v;

    }

    private void setUpCamera() {
        b.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }



        });

    }


    private void setUpImagePicker() {

        b.btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICKER);
            }
        });


    }







    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada
            Uri imageUri = data.getData();

            // Actualiza la imagen del ImageButton con la imagen seleccionada
            imageSelectedListener.onImageSelected(imageUri);

            this.dismiss();


        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView image = getActivity().findViewById(R.id.imgPickerReal);

            image.setImageBitmap(imageBitmap);


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), imageBitmap, "Title", null);
            imageSelectedListener.onImageSelected(Uri.parse(path));


            this.dismiss();
        }



    }


    public interface OnImageSelectedListener {
        void onImageSelected(Uri selectedImageUri);
    }


    public void setOnImageSelectedListener(OnImageSelectedListener listener) {
        imageSelectedListener = listener;
    }


}