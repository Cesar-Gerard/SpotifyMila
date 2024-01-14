
package modelApi.ArtistApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Image {

    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("size")
    @Expose
    private String size;

    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public String getSize() {
        return size;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }




}
