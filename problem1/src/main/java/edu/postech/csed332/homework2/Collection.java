package edu.postech.csed332.homework2;

import java.util.*;
import org.json.*;

/**
 * The Collection class represents a single collection, which contains
 * a name of the collection and also has a list of references to every
 * book and every subcollection in the collection. A collection can
 * also be exported to and imported from a JSON string representation.
 */
public final class Collection extends Element {
    private List<Element> elements;
    private String name;

    /**
     * Creates a new collection with the given name.
     *
     * @param name the name of the collection
     */
    public Collection(String name) {
        this.name = name;
        // TODO write more code if necessary
        this.elements = new ArrayList<Element>();
        this.setParentCollection(null);
    }

    /**
     * Restores a collection from its string representation in JSON
     *
     * @param stringRepr the string representation
     */
    public static Collection restoreCollection(String stringRepr) {
        // TODO implement this
        try{
            JSONObject json = new JSONObject(stringRepr);
            String colName = json.getString("name");
            Collection rst = new Collection(colName);

            JSONArray jsonBooks = json.getJSONArray("books");
            for(int i = 0; i < jsonBooks.length(); i++){
                Book newBook = new Book(jsonBooks.getJSONObject(i).toString());
                rst.addElement(newBook);
            }
            
            JSONArray jsonSubcollections = json.getJSONArray("subcollections");
            for(int i = 0; i < jsonSubcollections.length(); i++){
                JSONObject jsonSubcollection = jsonSubcollections.getJSONObject(i);
                Collection newCollection = restoreCollection(jsonSubcollection.toString());
                rst.addElement(newCollection);
            }
            return rst;
        } catch(JSONException e){};
        return null;
    }

    /**
     * Returns the JSON string representation of this collection, which
     * contains the name of this collection and all elements (books and
     * subcollections) contained in the collection.
     *
     * @return string representation of this collection
     */
    public String getStringRepresentation() {
        // TODO implement this
        try{
            JSONStringer rst = new JSONStringer();
            rst.object();
            rst.key("name").value(this.name);
            rst.key("subcollections");
            rst.array();
            for(Element cur: elements){
                if(cur instanceof Collection){
                    JSONObject elementObject = new JSONObject(((Collection)cur).getStringRepresentation());
                    rst.value(elementObject);
                }
            }
            rst.endArray();
            rst.key("books");
            rst.array();
            for(Element cur: elements){
                if(cur instanceof Book){
                    JSONObject elementObject = new JSONObject(((Book)cur).getStringRepresentation());
                    rst.value(elementObject);
                }
            }
            rst.endArray();
            rst.endObject();
            return rst.toString();
        }catch(JSONException e){}
        return null;
    }

    /**
     * Adds an element to this collection, if the element has no parent
     * collection yet. Otherwise, this method returns false, without
     * actually adding the element to this collection.
     *
     * @param element the element to add
     * @return true on success, false on fail
     */
    public boolean addElement(Element element) {
        // TODO implement this
        element.setParentCollection(this);
        return elements.add(element);
    }

    /**
     * Deletes an element from the collection. Returns false if the
     * collection does not have this element. Hint: do not forget to
     * clear parentCollection of given element.
     *
     * @param element the element to remove
     * @return true on success, false on fail
     */
    public boolean deleteElement(Element element) {
        // TODO implement this
        element.setParentCollection(null);
        return elements.remove(element);
    }

    /**
     * Returns the name of the collection.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of elements.
     *
     * @return the list of elements
     */
    public List<Element> getElements() {
        return elements;
    }
}
