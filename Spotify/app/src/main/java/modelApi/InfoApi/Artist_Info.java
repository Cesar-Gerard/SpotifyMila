
package modelApi.InfoApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Artist_Info {

    @SerializedName("bio")
    @Expose
    private Bio bio;

    public Bio getBio() {
        return bio;
    }

    public void setBio(Bio bio) {
        this.bio = bio;
    }

}
