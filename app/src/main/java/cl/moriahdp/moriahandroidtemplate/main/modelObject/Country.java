package cl.moriahdp.moriahandroidtemplate.main.modelObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by edwinperaza on 2/13/18.
 */

public class Country {

    @SerializedName("iso")
    @Expose
    private String iso;

    @SerializedName("name")
    @Expose
    private String name;

    public Country(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
