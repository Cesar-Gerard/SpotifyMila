
package modelApi.ArtistApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Artist {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("streamable")
    @Expose
    private String streamable;
    @SerializedName("image")
    @Expose
    private List<Image> image;

    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getListeners() {
        return listeners;
    }


    public void setListeners(String listeners) {
        this.listeners = listeners;
    }


    public String getMbid() {
        return mbid;
    }


    public void setMbid(String mbid) {
        this.mbid = mbid;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getStreamable() {
        return streamable;
    }


    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }


    public List<Image> getImage() {
        return image;
    }


    public void setImage(List<Image> image) {
        this.image = image;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }



}
