
package modelApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Artistmatches {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist;

    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();


    public List<Artist> getArtist() {
        return artist;
    }


    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
