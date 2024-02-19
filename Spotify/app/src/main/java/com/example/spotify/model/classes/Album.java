package com.example.spotify.model.classes;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.spotify.MainActivity;
import com.example.spotify.model.formatters.BitmapUtils;
import com.example.spotify.model.formatters.DateUtils;
import com.example.spotify.modelApi.AlbumApi.Image;
import com.example.spotify.modelApi.ArtistApi.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import javax.annotation.processing.Generated;
@Entity
@Generated("jsonschema2pojo")
public class Album implements Serializable {


    //region atributs

    @PrimaryKey(autoGenerate = true)
    long id;


    @ColumnInfo(name = "albumName")
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "albumUrl")
    String ImageUrl;

    @ColumnInfo(name = "albumPath")
    String imagepath;


    @SerializedName("image")
    @Expose
    @Ignore
    private List<Image> image;

    @SerializedName("artist")
    @Expose
    @Ignore
    private Object artist;

    @ColumnInfo(name = "artistname")
    String artistname;

    @ColumnInfo(name = "albumDateCreation")
    Date date;


    @Ignore
    boolean selected;

    @SerializedName("tracks")
    @Expose
    @Ignore
    private Songs llista_cansons;

    @Ignore
     List<Song> consons_Album;



    @Ignore
     Bitmap imageBitmap;


     @Ignore
    public static List<Album> list_albums=null;


     @ColumnInfo(name = "Downloaded")
     boolean download;

     //endregion

    //#region Constuctors

    public Album(String name, String imageUrl, String author, Date date) {
        this.name = name;
        ImageUrl = imageUrl;
        this.artistname = author;
        this.date = date;
        this.consons_Album= new ArrayList<>();
        this.imagepath=null;
    }


    public Album() {}


    public Album(String string, Bitmap bitmap,String imagepath, String string1, Date date) {
        this.name = string;
        this.artistname = string1;
        this.date = date;
        this.imagepath=imagepath;
        this.imageBitmap = bitmap;
        this.consons_Album= new ArrayList<>();
        this.ImageUrl= null;
    }


    public Album(Integer id,String string, Bitmap bitmap,String imagepath,String imageUrl, String string1, Date date, String date_string) {
        this.id=id;
        this.name = string;
        this.artistname = string1;
        this.date = date;
        this.imagepath=imagepath;
        this.imageBitmap = bitmap;
        this.consons_Album= new ArrayList<>();
        this.ImageUrl= null;
        this.ImageUrl= imageUrl;
    }





    //#endregion

    //#region Getters i Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }


    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Artist getArtist() {
        if (artist instanceof LinkedTreeMap) {
            LinkedTreeMap<String, Object> artistMap = (LinkedTreeMap<String, Object>) artist;

            // Aquí deberías tener las claves adecuadas para mapear el objeto LinkedTreeMap a un objeto Artist
            String name = (String) artistMap.get("name");
            // Obtener otros campos necesarios del LinkedTreeMap

            // Construir y devolver un nuevo objeto Artist con los valores obtenidos
            Artist newArtist = new Artist();
            newArtist.setName(name);
            // Setear otros campos necesarios en el objeto Artist

            return newArtist;

        }

        Artist resposta = new Artist();
        resposta.setName(artistname);
        return resposta;
    }

    public void setArtist(Object artist) {
        this.artist = artist;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        this.ImageUrl = null;

    }

    public List<Song> getConsons_Album() {
        return consons_Album;
    }

    public void setConsons_Album(List<Song> consons_Album) {
        this.consons_Album = consons_Album;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Songs getLlista_cansons() {
        return llista_cansons;
    }

    public void setLlista_cansons(Songs llista_cansons) {
        this.llista_cansons = llista_cansons;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    //#endregion

    //#region Metodes


    //Creació dels albums predeterminats de la app
    public static List<Album> createList(){

        if(list_albums==null){

            list_albums = new ArrayList<>();
            list_albums.add(new Album("21", "https://i1.sndcdn.com/artworks-000168814903-4vrfjc-t500x500.jpg","Adele", DateUtils.parseDayMonthYear("21/07/2003")));
            list_albums.add(new Album("Greatest Hits", "https://i1.sndcdn.com/artworks-0594d6b29566c9686580e8d3560d253d1bc868d3-0-t500x500.jpg","Queen",DateUtils.parseDayMonthYear("08/09/2014")));
            list_albums.add(new Album("Dancing Queen", "https://i1.sndcdn.com/artworks-HklzvIa2mO1dlBcF-OXKJqw-t500x500.jpg","ABBA",DateUtils.parseDayMonthYear("21/07/2003")));
            list_albums.add(new Album("Coinicidir", "https://i1.sndcdn.com/artworks-suT8zKas8erE-0-t500x500.jpg","Macaco",DateUtils.parseDayMonthYear("21/07/2003")));
            list_albums.add(new Album("Semells Like Teen Spirit", "https://i1.sndcdn.com/artworks-gojRubnOqDOn-0-t500x500.jpg","Nirvana",DateUtils.parseDayMonthYear("21/07/2003")));
            list_albums.add(new Album("Life is Strange Soundtrack", "https://i1.sndcdn.com/artworks-000130535674-a4utz2-t500x500.jpg","Dolkins",DateUtils.parseDayMonthYear("21/07/2003")));


            return list_albums;
        }else{
            return list_albums;
        }


    }



    //Retorna un nou id per la creació de una canço
    public  int getNewSongId(){
        if(this.consons_Album.size()>0) {

            Song last = this.consons_Album.get(consons_Album.size() - 1);

            return last.getId() + 1;
        }else{
            return 0;
        }
    }

    //Afegeix la cançon seleccionada al album seleccionat
    public static void addNewSong(Song entrada,Album a){

        int posicio = Album.list_albums.indexOf(a);

           list_albums.get(posicio).getConsons_Album().add(entrada);


    }


    //Comprova si la canço existeix o no
    public static boolean songInAlbumRealesed(String entrada,Album a, Song original){
        if(a.consons_Album.size()>0){
            for(Song n : a.consons_Album){
                if(n.getName().equals(entrada)){

                        int id = getSongPosition(original,a);
                        if (n.getId() == id) {
                            return false;
                        }else{
                            return true;
                        }


                }
            }

            return false;
        }else{
            return false;
        }


    }


    //Comprova si el album existeix o no
    public static boolean Album_Realesed(String entrada,Album a){

        for(Album n : list_albums){

            if(n.getName().equals(entrada)){

                if(a !=null) {

                    long id = getAlbumPosition(a);
                    if (n.getId() == id) {
                        return false;
                    }
                }
                return true;
            }

        }

        return false;


    }

    //Retorna la posicio del album que coincideixi amb el nom del album passat per parametre
    public static long getAlbumPosition(Album entrada) {
        for (int i = 0; i < list_albums.size(); i++) {
            Album album = list_albums.get(i);

            if (album.getName().equals(entrada.getName())) {
                return album.getId();
            }
        }
        return -1;
    }


    //Retorna la posicio de la canço que coincideixi amb el nom de la canço passada per parametre
    public static int getSongPosition(Song entrada,Album a) {
        if(entrada != null) {

            for (int i = 0; i <  a.getConsons_Album().size(); i++) {
                Song song = a.getConsons_Album().get(i);

                if (song.getName().equals(entrada.getName())) {
                    return song.getId();
                }
            }
            return -1;
        }else{
            return -1;
        }
    }


    public void procesarArtist() {
         if (artist instanceof String) {
            // Es una cadena de texto
            artistname = (String) artist;

        }
    }

    //#endregion



}
