package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExpTest {

    @Test
    public void testParserOK() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        System.out.println("exp: "+exp);;
        System.out.println("hi");;
        System.out.println("exp: "+exp);;
        System.out.println((exp instanceof Conjunction));
        System.out.println((exp instanceof Constant));
        System.out.println((exp instanceof Disjunction));
        System.out.println((exp instanceof Negation));
        System.out.println((exp instanceof Variable));
        assertEquals(exp.toString(), "((p1 || (p2 && (! p3))) || true)");
    }

    @Test
    public void testParserError() {
        assertThrows(IllegalStateException.class, () -> {
            Exp exp = ExpParser.parse("p1 || p2 && ! p0 || true");
        });
    }

    @Test
    public void testVars() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        List<Integer> varsList = new ArrayList<Integer>(exp.vars());
        List<Integer> expectList = Arrays.asList(1, 2, 3);

        Collections.sort(varsList);
        System.out.println(varsList);
        System.out.println(expectList);
        assertEquals(expectList, varsList);
    }

    @Test
    public void testEvaluateTrue() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();
        assignment.put(1, true);
        assignment.put(2, true);
        assignment.put(3, true);

        assertTrue(exp.evaluate(assignment));
    }

    @Test
    public void testEvaluateFalse() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();
        assignment.put(1, false);
        assignment.put(2, false);
        assignment.put(3, false);

        assertFalse(exp.evaluate(assignment));
    }

    /*
     * TODO: add  test methods to achieve at least 80% branch coverage of the classes:
     *  Conjunction, Constant, Disjunction, Negation, Variable.
     * Each test method should have appropriate JUnit assertions to test a single behavior.
     * You should not add arbitrary code to test methods to just increase coverage.
     */
}
