
package modelApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;


public class Results {

    @SerializedName("artistmatches")
    @Expose
    private Artistmatches artistmatches;


    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Artistmatches getArtistmatches() {
        return artistmatches;
    }


    public void setArtistmatches(Artistmatches artistmatches) {
        this.artistmatches = artistmatches;
    }



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
