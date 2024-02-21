package com.example.spotify.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.API.LastFMManager;
import com.example.spotify.InsideArtist;
import com.example.spotify.Loading.LoadingInsideArtist;
import com.example.spotify.R;
import com.example.spotify.model.classes.Album;
import com.example.spotify.modelApi.ArtistApi.Artist;
import com.example.spotify.modelApi.SongsAlbum.SongsAlbum;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Image_Album_adapter extends RecyclerView.Adapter<Image_Album_adapter.ViewHolder>{

    List<Album> albums;
    List<Album> top;
    Context context;

    InsideArtist inside;

    boolean isTop;



    private LoadingInsideArtist loadingDialog;

    public Image_Album_adapter(List<Album> albums, Context context) {
        this.albums=albums;
        this.context=context;
        isTop=false;
    }

    public Image_Album_adapter(List<Album> album, InsideArtist clase,Context context, boolean isTop) {
        top=album;
        inside= clase;
        this.context=context;
        loadingDialog= new LoadingInsideArtist(inside.getContext());
        this.isTop=isTop;

    }

    @NonNull
    @Override
    public Image_Album_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(!isTop){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_image_item,parent,false);

        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_image_item2,parent,false);

        }



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Image_Album_adapter.ViewHolder holder, int position) {

        if(albums!=null) {


            Album item = albums.get(position);


            CarregarImatge(holder, position, item, 120);
        }else if(top != null){

            Album itemTop = top.get(position);
            itemTop.setArtistname(itemTop.getArtist().getName());
            CarregarImatge(holder, position, itemTop, 150);



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(itemTop.isSelected()){
                        itemTop.setSelected(false);
                    }else if(!itemTop.isSelected()){
                        itemTop.setSelected(true);
                        ((AppCompatActivity)inside.getContext()).startSupportActionMode(inside.actionModeCallback);
                    }




                }
            });




        }


    }

    private void CarregarImatge(@NonNull ViewHolder holder, int position, Album item, int escala) {
        if (item.getImageBitmap() != null) {

            if (holder.getAdapterPosition() == position) {
                holder.imagen.setImageBitmap(item.getImageBitmap());
            }


        } else {


            if (item.getImage().get(3).getText().equals("")) {


                int resourceId = R.drawable.music_not_found;

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

                float scale = context.getResources().getDisplayMetrics().density;

                int widthInPx = (int) (escala * scale);
                int heightInPx = (int) (escala * scale);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, widthInPx, heightInPx, true);
                item.setImageBitmap(scaledBitmap);

                holder.imagen.setImageBitmap(scaledBitmap);


            } else {


                ImageLoader iL = ImageLoader.getInstance();

                iL.loadImage(item.getImage().get(3).getText(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        int resourceId = R.drawable.music_not_found;

                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

                        float scale = context.getResources().getDisplayMetrics().density;

                        int widthInPx = (int) (escala * scale);
                        int heightInPx = (int) (escala * scale);

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, widthInPx, heightInPx, true);
                        item.setImageBitmap(scaledBitmap);

                        holder.imagen.setImageBitmap(scaledBitmap);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        float scale = context.getResources().getDisplayMetrics().density;

                        int widthInPx = (int) (escala * scale);
                        int heightInPx = (int) (escala * scale);

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(loadedImage, widthInPx, heightInPx, true);
                        item.getImage().get(1).setImageBitmap(scaledBitmap);

                        holder.imagen.setImageBitmap(scaledBitmap);


                        item.setImageBitmap(scaledBitmap);


                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        Log.e("Imatge cancelada", item.getName());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
       if(albums!= null){
           return albums.size();
       }else if(top!=null){
           return top.size();
       }else{
           return 0;
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.ImageAlbum);
        }
    }





    public void showLoading() {
        loadingDialog.show();
    }

    // Método para ocultar la pantalla de carga
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    public void startLoading() {
        showLoading();

        // Simula la carga de datos con un retraso
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                hideLoading();
            }
        }, 0);
    }


}
