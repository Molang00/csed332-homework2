package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExpTest {

    @Test
    public void testParserOK() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
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
        assertEquals(expectList, varsList);
    }

    @Test
    public void testEvaluateVariableTrue() {
        Exp exp = ExpParser.parse("p1");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();
        assignment.put(1, true);

        assertTrue(exp.evaluate(assignment));
    }


    @Test
    public void testEvaluateConstantTrue() {
        Exp exp = ExpParser.parse("true");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();

        assertTrue(exp.evaluate(assignment));
    }


    @Test
    public void testEvaluateNegationTrue() {
        Exp exp = ExpParser.parse("! p1");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();
        assignment.put(1, false);

        assertTrue(exp.evaluate(assignment));
    }

    @Test
    public void testEvaluateNegationFalse() {
        Exp exp = ExpParser.parse("! p1");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();
        assignment.put(1, true);

        assertFalse(exp.evaluate(assignment));
    }

    @Test
    public void testEvaluateTrue() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3");
        Map<Integer, Boolean> assignment = new HashMap<Integer, Boolean>();
        assignment.put(1, false);
        assignment.put(2, true);
        assignment.put(3, false);
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

    @Test
    public void testDisjunctionSimplify() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3");
        exp.simplify();
    }

    /*
     * TODO: add  test methods to achieve at least 80% branch coverage of the classes:
     *  Conjunction, Constant, Disjunction, Negation, Variable.
     * Each test method should have appropriate JUnit assertions to test a single behavior.
     * You should not add arbitrary code to test methods to just increase coverage.
     */
}
