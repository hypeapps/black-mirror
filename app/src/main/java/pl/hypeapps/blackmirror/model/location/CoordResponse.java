package pl.hypeapps.blackmirror.model.location;


import java.util.List;

/**
 * Model reprezentujący dane długości koordynatów.
 */
public class CoordResponse {

    public List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
