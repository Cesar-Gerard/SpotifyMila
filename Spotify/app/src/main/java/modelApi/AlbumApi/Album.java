
package modelApi.AlbumApi;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

import Interface.AlbumInterface;
import modelApi.TopAlbums.Artist;

@Generated("jsonschema2pojo")
public class Album implements Serializable, AlbumInterface {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private List<Image> image;
    @SerializedName("mbid")
    @Expose
    private String mbid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public Artist getArtist_() {
        return null;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }


    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

}
