package com.example.spotify.Albums;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.spotify.R;
import com.example.spotify.Songs.llista_cansons;
import com.example.spotify.databinding.FragmentAlbumCreationBinding;
import com.example.spotify.dialogs.Custom_Dialog_Image_Picker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.example.spotify.model.classes.Album;
import com.example.spotify.model.formatters.DateUtils;

public class Album_Creation extends Fragment implements Custom_Dialog_Image_Picker.OnImageSelectedListener {

    FragmentAlbumCreationBinding b;
    DatePickerDialog picker;

    Custom_Dialog_Image_Picker customDialog;

    String pathImage;

    private Handler handler = new Handler();
    private Runnable inputFinishChecker;

    public static Album entrada=null;

    private boolean control = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentAlbumCreationBinding.inflate(getLayoutInflater());
        View v = b.getRoot();

        setUpDialogDatePicker();
        setUpImagePicker();
        setUpTextController();
        setUpCreationButton();







        if(entrada!=null){
            setUpAlbum();
        }else{
            netejarcamps();
        }




        return v;

    }



    private void netejarcamps() {

        b.edtAlbumTitle.setText("");
        b.edtAuthorName.setText("");
        setUpDialogDatePicker();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_creation);
        b.imgPickerReal.setImageBitmap(bitmap);

    }

    private void setUpAlbum() {

        b.edtAlbumTitle.setText(entrada.getName());
        b.edtAuthorName.setText(entrada.getAuthor());
        b.editText.setText(DateUtils.formatDateToDayMonthYear(entrada.getDate()));

        b.imgPickerReal.setImageBitmap(entrada.getBitmap());

        control = true;


    }

    private void setUpCreationButton() {

        b.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (entrada == null) {
                    Bitmap bitmap=null;

                    if(pathImage!=null) {
                        File file = new File(pathImage);
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    }else{
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music_creation);
                    }

                    Album nou = new Album(Album.getNewId(), b.edtAlbumTitle.getText().toString(), bitmap, b.edtAuthorName.getText().toString(), DateUtils.parseDayMonthYear(b.editText.getText().toString()));

                    Album.list_albums.add(nou);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Album enregistrat amb exit")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    b.edtAlbumTitle.setText("");
                                    b.edtAuthorName.setText("");
                                    b.editText.setText("");
                                    b.imgPickerReal.setImageResource(R.drawable.music_creation);
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    MyMusic.adapter.notifyDataSetChanged();


                }else{

                    int posicio = Album.list_albums.indexOf(entrada);



                    Album.list_albums.get(posicio).setName(b.edtAlbumTitle.getText().toString());
                    Album.list_albums.get(posicio).setAuthor(b.edtAuthorName.getText().toString());
                    Album.list_albums.get(posicio).setDate(DateUtils.parseDayMonthYear(b.editText.getText().toString()));

                    Bitmap bitmap = BitmapFactory.decodeFile(pathImage);

                    float scale = Album_Creation.this.getContext().getResources().getDisplayMetrics().density;

                    int widthInPx = (int) (120 * scale);
                    int heightInPx = (int) (140 * scale);

                    if(bitmap!=null){
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, widthInPx, heightInPx, true);
                        Album.list_albums.get(posicio).setBitmap(scaledBitmap);
                    }

                    Album.list_albums.get(posicio).setSelected(false);




                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Album actualitzat")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    b.edtAlbumTitle.setText("");
                                    b.edtAuthorName.setText("");
                                    b.editText.setText("");
                                    b.imgPickerReal.setImageResource(R.drawable.music_creation);
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();



                    MyMusic.adapter.notifyDataSetChanged();

                    //Netegem el path de la imatge per tenir en compte de que es pot no posar imatge
                    pathImage=null;

                    if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE && llista_cansons.entrada!=null){

                        llista_cansons.setUpAlbumInfo();

                        Navigation.findNavController(Album_Creation.this.getActivity(),R.id.nav_host_fragment).navigate(R.id.action_global_myMusic);

                    }


                }



                getActivity().getSupportFragmentManager().popBackStack();



            }




        });



    }


    private void setUpTextController() {



        b.edtAlbumTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {




            }

            @Override
            public void afterTextChanged(Editable editable) {

                handler.removeCallbacks(inputFinishChecker);

                inputFinishChecker = new Runnable() {
                    @Override
                    public void run() {
                        // Verifica el texto después del retraso
                        if (!Album.Album_Realesed(b.edtAlbumTitle.getText().toString(),entrada)) {
                            verificarYActualizarBoton();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Nom ja registrat")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            b.edtAlbumTitle.setText("");
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                };

                // Inicia el temporizador después de 1000 ms (1 segundo)
                handler.postDelayed(inputFinishChecker, 400);




            }
        });


        b.edtAuthorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYActualizarBoton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYActualizarBoton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setUpImagePicker() {
        b.imgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 customDialog = new Custom_Dialog_Image_Picker();

                customDialog.show(getFragmentManager(), "CustomDialogFragment");


                customDialog.setOnImageSelectedListener(new Custom_Dialog_Image_Picker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri selectedImageUri) {
                        // Actualitzar el amb la imatge seleccionada

                        File folder = getContext().getFilesDir();
                        File arxiu = new File(folder, "nom"+MyMusic.imagenumber+".png");
                        Bitmap mImageBitmap = null;
                        MyMusic.imagenumber++;

                        try {
                            InputStream inputStream = getContext().getContentResolver().openInputStream(selectedImageUri);
                            mImageBitmap = BitmapFactory.decodeStream(inputStream);
                            FileOutputStream outputStream = new FileOutputStream(arxiu);
                            mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            outputStream.close();

                            pathImage = arxiu.getAbsolutePath();


                            Bitmap bitmap = BitmapFactory.decodeFile(pathImage);

                            b.imgPickerReal.setImageBitmap(bitmap);


                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });





            }
        });
    }

    private void setUpDialogDatePicker() {

        b.btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        R.style.DatePickerSpinner,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                b.editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }


    @Override
    public void onImageSelected(Uri selectedImageUri) {
        b.imgPicker.setImageURI(selectedImageUri);
    }


    private void verificarYActualizarBoton() {


        String texto1 = b.edtAuthorName.getText().toString();
        String texto2 = b.edtAlbumTitle.getText().toString();
        String texto3 = b.editText.getText().toString();

        boolean habilitarBoton = !texto1.isEmpty() && !texto2.isEmpty() && !texto3.isEmpty();
        b.fab.setEnabled(habilitarBoton);
    }


    @Override
    public void onDetach() {
        super.onDetach();

        entrada=null;

    }






}