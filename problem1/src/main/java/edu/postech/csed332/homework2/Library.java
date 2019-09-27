package edu.postech.csed332.homework2;

import java.util.*;
import org.json.*;
import java.io.*;

/**
 * A container class for all collections (that eventually contain all
 * books). A library can be exported to or imported from a JSON file.
 */
public final class Library {
    private List<Collection> collections;

    /**
     * Builds a new, empty library.
     */
    public Library() {
        // TODO implement this
        collections = null;
    }

    /**
     * Builds a new library and restores its contents from a file.
     *
     * @param fileName the file from where to restore the library.
     */
    public Library(String fileName) {
        // TODO implement this
        try{
            StringBuilder stringBuilder = new StringBuilder();
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append('\n');
            }
            bufferedReader.close();
            String fileContent = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(fileContent);

            collections = new ArrayList<Collection>();
            JSONArray jsonCollections = jsonObject.getJSONArray("collections");
            for(int i = 0; i < jsonCollections.length(); i++){
                Collection newCollection = Collection.restoreCollection(jsonCollections.get(i).toString());
                collections.add(newCollection);
            }
        }catch(IOException e){
            System.out.println("IO error");
        }
        catch(JSONException e){
            System.out.println("json error");
        }
    }

    /**
     * Saves the contents of the library to the given file.
     *
     * @param fileName the file where to save the library
     */
    public void saveLibraryToFile(String fileName) {
        // TODO implement this
        try{
            File writeFile = new File(fileName);
            FileWriter fw = new FileWriter(writeFile);
            JSONStringer rst = new JSONStringer();
            rst.object();
            rst.key("collections");
            rst.array();
            for(Collection collection: collections){
                JSONObject collectionObject = new JSONObject(collection.getStringRepresentation());
                rst.value(collectionObject);
            }
            rst.endArray();
            rst.endObject();
            
            fw.write(rst.toString());
            fw.close();
        }catch(IOException | JSONException e){}
    }
    
    /**
     * Returns the set of all books that belong to the collections
     * of a given name. Note that different collections may have the
     * same name. Return null if there is no collection of the
     * given name, and the empty set of there are such collections but
     * all of them are empty. For example, suppose that
     * - "Computer Science" is a top collection.
     * - "Operating Systems" is a collection under "Computer Science".
     * - "Linux Kernel" is a book under "Operating System".
     * - "Software Engineering" is a collection under "Computer Science".
     * - "Software Design Methods" is a bool under "Software Engineering".
     * Then, the findBooks method for "Computer Science" returns the set
     * of two books "Linux Kernel" and "Software Design Methods".
     *
     * @param collection a collection name
     * @return a set of books
     */
    public Set<Book> findBooks(String collection) {
        // TODO implement this
        Set<Book> rst = new HashSet<Book>();
        if(collections == null) return null;
        for(Collection cur: collections){
            rst.addAll(cur.getTargetBooks(collection, false));
        }
        if(rst.size() > 0) return rst;
        else return null;
    }

    /**
     * Return the set of all books written by a given author in this
     * collection (including the sub-collections). Return the empty
     * set if there is no book written by the author. Note that a book
     * may involve multiple authors.
     *
     * @param author an author
     * @return Return the set of books written by the given author
     */
    public Set<Book> findBooksByAuthor(String author) {
        // TODO implement this
        Set<Book> rst = new HashSet<Book>();
        if(collections == null) return null;
        for(Collection cur: collections){
            rst.addAll(cur.getBooksByAuthor(author));
        }
        if(rst.size() > 0) return rst;
        else return null;
    }

    /**
     * Returns the collections contained in the library.
     *
     * @return library contained elements
     */
    public List<Collection> getCollections() {
        return collections;
    }
}
