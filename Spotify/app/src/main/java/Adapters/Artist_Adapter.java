package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Download_Albums;
import com.example.spotify.InsideArtist;
import com.example.spotify.R;
import com.example.spotify.databinding.FragmentInsideArtistBinding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.List;

import Loading.LoadingArtist;
import modelApi.ArtistApi.Artist;

public class Artist_Adapter extends RecyclerView.Adapter<Artist_Adapter.ViewHolder> {


    List<Artist> artistes;
    Download_Albums context;

    private LoadingArtist loadingDialog;


    //region Constructor
    public Artist_Adapter(List<Artist> entrada_artistes, Download_Albums context) {
        artistes = entrada_artistes;
        this.context = context;
        loadingDialog = new LoadingArtist(context.getContext());
    }

    //endregion



    @NonNull
    @Override
    public Artist_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Artist_Adapter.ViewHolder holder, int position) {
        Artist item = artistes.get(position);


        holder.name.setText(item.getName());


        if(item.getImage().get(1).getImageBitmap()!=null){
            holder.image.setImageBitmap(item.getImage().get(2).getImageBitmap());
        }else {


            holder.image.setImageResource(R.drawable.loading);
            ImageLoader iL = ImageLoader.getInstance();

            iL.loadImage(item.getImage().get(2).getText(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.image.setImageResource(R.drawable.not_found);
                    hideLoading();
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.image.setImageBitmap(loadedImage);
                    item.getImage().get(1).setImageBitmap(loadedImage);
                    hideLoading();
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }


        //Anem a mostrar els albums del artista

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsideArtist fragmentoDestino = new InsideArtist();

                // Crear un Bundle para pasar el objeto Album
                Bundle bundle = new Bundle();
                bundle.putSerializable("album", item.getName());

                // Asignar el Bundle al fragmento de destino
                fragmentoDestino.setArguments(bundle);

                // Realizar la transacción de fragmentos
                FragmentTransaction transaction = context.requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragmentoDestino);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });




    }

    @Override
    public int getItemCount() {

        if(artistes!= null){
            return artistes.size();
        }else{
            return 0;
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.ArtistName);
            image= itemView.findViewById(R.id.ArtistImage);
        }
    }

    // Método para mostrar la pantalla de carga
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
        }, 3000); // Ajusta este valor al tiempo real que tome cargar los datos
    }

}
