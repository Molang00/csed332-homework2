package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(book.getTitle(), "Unit Testing");
    }


    @Test
    public void testGetTitlewithJSON() {
        String str = "{\"title\":\"UnitTesting\", \"authors\": [\"Name1\", \"Name2\"]}";
        Book book = new Book(str);
        Assertions.assertEquals(book.getTitle(), "UnitTesting");
        Assertions.assertEquals(book.getAuthors(), Arrays.asList("Name1", "Name2"));
    }

    @Test
    public void testGetStringRepr() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        String str = "{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}";
        Assertions.assertEquals(book.getStringRepresentation(), str);
    }

    @Test
    public void testGetContainingCollections() {
        Book reactjs = new Book("React", Arrays.asList("facebook", "javascript"));
        Collection col_master = new Collection("Software");
        Collection col_slave = new Collection("FrontEnd");
        col_master.addElement(col_slave);
        col_slave.addElement(reactjs);
        List<Collection> listCollection = Arrays.asList(col_slave, col_master);
        Assertions.assertEquals(reactjs.getContainingCollections(), listCollection);
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Book.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
