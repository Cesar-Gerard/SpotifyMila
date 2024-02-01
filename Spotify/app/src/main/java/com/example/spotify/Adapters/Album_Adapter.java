package com.example.spotify.Adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Albums.MyMusic;
import com.example.spotify.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.util.List;

import com.example.spotify.model.AlbumClickerListener;
import com.example.spotify.model.classes.Album;
import com.example.spotify.model.formatters.DateUtils;

public class Album_Adapter extends RecyclerView.Adapter<Album_Adapter.ViewHolder>{

    private List<Album> albums;
    MyMusic context;
    AlbumClickerListener listener;


    //#region Constructor
    public Album_Adapter(List<Album> albums, MyMusic a, AlbumClickerListener listener) {
        this.albums = albums;
        context=a;
        this.listener=listener;
    }
    //#endregion

    @NonNull
    @Override
    public Album_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Album_Adapter.ViewHolder holder, int position) {
        Album a = albums.get(position);

        //Configurem la info del item que anem a mostrar
        holder.titlealbum.setText(a.getName());
        holder.authoralbum.setText(a.getArtistname());
        holder.datealbum.setText(DateUtils.formatDateToDayMonthYear(a.getDate()));
        holder.numsongs.setText(String.valueOf(a.getConsons_Album().size()));



        //#region ImageLoader

        if(a.getImageBitmap()!=null){

            //#region Escalem el bitmap de la imatge
            float scale = context.getResources().getDisplayMetrics().density;

            int widthInPx = (int) (120 * scale);
            int heightInPx = (int) (140 * scale);


            Bitmap scaledBitmap = Bitmap.createScaledBitmap(a.getImageBitmap(), widthInPx, heightInPx, true);

            a.setImageBitmap(scaledBitmap);


            holder.imgalbum.setImageBitmap(scaledBitmap);

            //#endregion

        }else{



            //Imatge per defecte del imageview
            holder.imgalbum.setImageResource(R.drawable.loading);
            ImageLoader iL = ImageLoader.getInstance();
            iL.loadImage(a.getImageUrl(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    //En el cas de que no es carregui la imatge determinem aquesta imatge
                    holder.imgalbum.setImageResource(R.drawable.not_found);


                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {


                    //Si s'ha pogut completar la cerca de la imatge, la establim com imatge del imageview
                    a.setImageBitmap(loadedImage);
                    holder.imgalbum.setImageBitmap(loadedImage);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }

            });

        }
        //#endregion


        //#region clickListeners

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Enllaç de la ContextualActionBar amb el seu equivalent de
                a.setSelected(true);
                ((AppCompatActivity)context.getContext()).startSupportActionMode(context.actionModeCallback);

                return true;
            }

        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.AlbumClicked(a);

            }


        });


        //#endregion



    }




    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgalbum;
        public TextView titlealbum;
        public TextView authoralbum;
        public TextView datealbum;
        public TextView numsongs;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgalbum=itemView.findViewById(R.id.AlbumImage);
            titlealbum=itemView.findViewById(R.id.AlbumName);
            authoralbum=itemView.findViewById(R.id.AuthorName);
            datealbum=itemView.findViewById(R.id.RealeseDate);
            numsongs=itemView.findViewById(R.id.txvSongs);

        }
    }





}
