package edu.postech.csed332.homework2;

import java.util.*;

/**
 * A Boolean expression whose top-level operator is ! (not).
 */
public class Negation implements Exp {
    private final Exp subexp;

    /**
     * Builds a negated expression of a given Boolean expression.
     *
     * @param exp a Boolean expression
     */
    public Negation(Exp exp) {
        subexp = exp;
    }

    /**
     * Returns the immediate sub-expression of this expression.
     *
     * @return a sub-expression
     */
    public Exp getSubexp() {
        return subexp;
    }

    @Override
    public Set<Integer> vars() {
        // TODO: implement this
        return subexp.vars();
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        Set<Integer> value = subexp.vars();
        Iterator<Integer> it = value.iterator();
        Boolean rst = false;
        while(it.hasNext()){
            rst = !assignment.get(it.next());
        }
        return rst;
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        return null;
    }

    @Override
    public String toString() {
        return "(! " + subexp + ")";
    }
}
