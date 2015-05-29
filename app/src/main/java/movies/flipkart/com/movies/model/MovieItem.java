package movies.flipkart.com.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manish.patwari on 5/29/15.
 */
public class MovieItem {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("imdbID")
    private String id;
    @SerializedName("Type")
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
