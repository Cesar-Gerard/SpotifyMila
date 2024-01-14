
package modelApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;


public class SearchArtist {

    @SerializedName("results")
    @Expose
    private Results results;


    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();


    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
