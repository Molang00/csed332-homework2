package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import org.json.*;

public class CollectionTest {

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    @Test
    public void testGetStringRepr() {
        Collection col = new Collection("Software");
        String str = "{\"name\":\"Software\",\"books\":[],\"subcollections\":[]}";
        JSONObject target = new JSONObject(str);
        JSONObject src = new JSONObject(col.getStringRepresentation());
        Assertions.assertEquals(src.toString(), target.toString());
    }

    @Test
    public void testAddElementsGetStringRepr() {
        Book webdevelopment = new Book("WebDevelopment", Arrays.asList("front", "back"));
        Collection colMaster = new Collection("Software");
        Collection colSlave1 = new Collection("FrontEnd");
        Collection colSlave2 = new Collection("BackEnd");

        Assertions.assertEquals(colMaster.addElement(colSlave1), true);
        Assertions.assertEquals(colMaster.addElement(colSlave2), true);
        Assertions.assertEquals(colSlave1.addElement(webdevelopment), true);

        String str = "{\"name\":\"Software\",\"subcollections\":[{\"subcollections\":[],\"books\":[{\"title\":\"WebDevelopment\",\"authors\":[\"front\",\"back\"]}],\"name\":\"FrontEnd\"},{\"subcollections\":[],\"books\":[],\"name\":\"BackEnd\"}],\"books\":[]}";
        JSONObject target = new JSONObject(str);
        JSONObject src = new JSONObject(colMaster.getStringRepresentation());
        Assertions.assertEquals(src.toString(), target.toString());
    }

    @Test
    public void testAddElements() {
        Book webdevelopment = new Book("WebDevelopment", Arrays.asList("front", "back"));
        Collection colMaster = new Collection("Software");
        Collection colSlave1 = new Collection("FrontEnd");
        Collection colSlave2 = new Collection("BackEnd");

        Assertions.assertEquals(colMaster.addElement(colSlave1), true);
        Assertions.assertEquals(colMaster.addElement(colSlave2), true);
        Assertions.assertEquals(colMaster.addElement(webdevelopment), true);
    }

    @Test
    public void testDeleteElements() {
        Collection colMaster = new Collection("Software");
        Collection colSlave1 = new Collection("FrontEnd");
        Collection colSlave2 = new Collection("BackEnd");

        String str = "{\"name\":\"Software\",\"subcollections\":[],\"books\":[]}";
        Assertions.assertEquals(colMaster.addElement(colSlave1), true);
        Assertions.assertEquals(colMaster.addElement(colSlave2), true);
        Assertions.assertEquals(colMaster.deleteElement(colSlave1), true);
        Assertions.assertEquals(colMaster.deleteElement(colSlave2), true);
        Assertions.assertEquals(colMaster.getStringRepresentation(), str);
    }

    @Test
    public void testDeleteElementsFalse() {
        Collection colMaster = new Collection("Software");
        Collection colSlave = new Collection("FrontEnd");

        Assertions.assertEquals(colMaster.addElement(colSlave), true);
        Assertions.assertEquals(colMaster.deleteElement(colSlave), true);
        Assertions.assertEquals(colMaster.deleteElement(colSlave), false);
    }

    @Test
    public void testRestoreCollection() {
        Book webdevelopment = new Book("WebDevelopment", Arrays.asList("front", "back"));
        Collection colMaster = new Collection("Software");
        Collection colSlave1 = new Collection("FrontEnd");
        Collection colSlave2 = new Collection("BackEnd");

        colMaster.addElement(colSlave1);
        colMaster.addElement(colSlave2);
        colMaster.addElement(webdevelopment);

        String stringMaster = colMaster.getStringRepresentation();
        Collection colTest = Collection.restoreCollection(stringMaster);
        Assertions.assertEquals(colTest.getStringRepresentation(), colMaster.getStringRepresentation());
    }
    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Collection.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
