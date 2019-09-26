package edu.postech.csed332.homework2;

import java.util.*;

// import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

/**
 * A book contains the title and the author(s), each of which is
 * represented as a string. A book can be exported to and import
 * from a string representation in JSON (https://www.json.org/).
 * Using the string, you should be able to construct a book object.
 */
public final class Book extends Element {
    private String title;
    private List<String> authors;

    /**
     * Builds a book with the given title and author(s).
     *
     * @param title   the title of the book
     * @param authors the author(s) of the book
     */
    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
        // TODO write more code if necessary
        this.setParentCollection(null);
    }

    /**
     * Builds a book from the string representation in JSON, which
     * contains the title and author(s) of the book.
     *
     * @param stringRepr the JSON string representation
     */
    public Book(String stringRepr) {
        // TODO implement this
        try{
            JSONObject json = new JSONObject(stringRepr);
            this.title = json.getString("title");
            JSONArray jsonAuthors = json.getJSONArray("authors");
            this.authors = new ArrayList<String>();
            for(int i = 0; i < jsonAuthors.length(); i++){
                this.authors.add(jsonAuthors.getString(i));
            }
            this.setParentCollection(null);
        } catch(JSONException e){}
    }

    /**
     * Returns the JSON string representation of this book. The string
     * representation contains the title and author(s) of the book.
     *
     * @return the string representation
     */
    public String getStringRepresentation() {
        // TODO implement this
        try{
            JSONStringer rst = new JSONStringer();
            rst.object();
            rst.key("title").value(this.title);
            rst.key("authors").value(this.authors);
            rst.endObject();
            return rst.toString();
        } catch(JSONException e){}
        return null;
    }

    /**
     * Returns all the collections that this book belongs to directly
     * and indirectly, starting from the bottom-level collection.
     * <p>
     * For example, suppose that "Computer Science" is a top collection,
     * "Operating Systems" is a collection under "Computer Science", and
     * "Linux Kernel" is a book under "Operating System". Then, this
     * method for "The Linux Kernel" returns the list of the collections
     * (Operating System, Computer Science), starting from the bottom.
     *
     * @return the list of collections
     */
    public List<Collection> getContainingCollections() {
        // TODO implement this
        List<Collection> rst = new ArrayList<Collection>();
        Collection parent = this.getParentCollection();
        while(parent != null){
            rst.add(parent);
            parent = parent.getParentCollection();
        }
        return rst;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author(s) of the book.
     *
     * @return the author(s)
     */
    public List<String> getAuthors() {
        return authors;
    }
}