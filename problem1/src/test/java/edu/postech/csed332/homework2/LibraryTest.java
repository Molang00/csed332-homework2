package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import org.json.*;
import java.io.*;

public class LibraryTest {

    @Test
    public void testfindBooksNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooks("any"));
    }

    @Test
    public void testfindBooksByAuthorNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooksByAuthor("Linus"));
    }

    @Test
    public void testMakeLibrarywithFile() {
        Library lib = new Library("json/LibraryTestRead.json");
        List<Collection> collections = lib.getCollections();
        String target = "{\"name\":\"Software\",\"subcollections\":[{\"subcollections\":[],\"books\":[{\"title\":\"WebDevelopment\",\"authors\":[\"front\",\"back\"]}],\"name\":\"FrontEnd\"},{\"subcollections\":[],\"books\":[],\"name\":\"BackEnd\"}],\"books\":[]}";
        for(Collection collection: collections){
            Assertions.assertEquals(collection.getStringRepresentation(), target);
        }
    }

    @Test
    public void testSaveLibraryToFile() {
        Library src = new Library("json/LibraryTestRead.json");
        src.saveLibraryToFile("json/LibraryTestWrite.json");
        
        try{
            StringBuilder stringBuilderSrc = new StringBuilder();
            FileReader fileReaderSrc = new FileReader("json/LibraryTestRead.json");
            BufferedReader bufferedReaderSrc = new BufferedReader(fileReaderSrc);
            String lineSrc;
            while((lineSrc = bufferedReaderSrc.readLine()) != null){
                stringBuilderSrc.append(lineSrc).append('\n');
            }
            bufferedReaderSrc.close();
            String fileContentSrc = stringBuilderSrc.toString();
            JSONObject jsonObjectSrc = new JSONObject(fileContentSrc);

            StringBuilder stringBuilderTarget = new StringBuilder();
            FileReader fileReaderTarget = new FileReader("json/LibraryTestWrite.json");
            BufferedReader bufferedReaderTarget = new BufferedReader(fileReaderTarget);
            String lineTarget;
            while((lineTarget = bufferedReaderTarget.readLine()) != null){
                stringBuilderTarget.append(lineTarget).append('\n');
            }
            bufferedReaderTarget.close();
            String fileContentTarget = stringBuilderTarget.toString();
            JSONObject jsonObjectTarget = new JSONObject(fileContentTarget);
            
            Assertions.assertEquals(jsonObjectTarget.toString(), jsonObjectSrc.toString());
        }catch(IOException e){}
    }

    @Test
    public void testfindBooks() {
        Library lib = new Library("json/LibraryTestFindBooks.json");
        Set<Book> finded = lib.findBooks("Computer Science");
        // Book linuxKernel = new Book("Linux Kernel", Arrays.asList("Linus", "Benedict", "Torvalds"));
        // Book softwareDesignMethods = new Book("Software Design Methods", Arrays.asList("Agile", "Waterfall"));

        Assertions.assertEquals(finded.size(), 2);
        for(Book cur: finded){
            Assertions.assertTrue((cur.getTitle().equals("Linux Kernel")) || (cur.getTitle().equals("Software Design Methods")));
        }
    }

    @Test
    public void testfindBooksByAuthor() {
        Library lib = new Library("json/LibraryTestFindBooks.json");
        Set<Book> finded = lib.findBooksByAuthor("Linus");
        // Book linuxKernel = new Book("Linux Kernel", Arrays.asList("Linus", "Benedict", "Torvalds"));
        // Book softwareDesignMethods = new Book("Software Design Methods", Arrays.asList("Agile", "Waterfall"));

        Assertions.assertEquals(finded.size(), 1);
        for(Book cur: finded){
            Assertions.assertTrue(cur.getTitle().equals("Linux Kernel"));
            Assertions.assertTrue(cur.getAuthors().contains("Linus"));
        }
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Library.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
