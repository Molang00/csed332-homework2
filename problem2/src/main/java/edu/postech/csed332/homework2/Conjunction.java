package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is && (and).
 */
public class Conjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a conjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Conjunction(Exp... exps) {
        subexps = Arrays.asList(exps);
    }

    /**
     * Returns the immediate sub-expressions of this expression.
     *
     * @return a list of sub-expressions
     */
    public List<Exp> getSubexps() {
        return subexps;
    }

    @Override
    public Set<Integer> vars() {
        // TODO: implement this
        Set<Integer> rst = new HashSet<Integer>();
        for(Exp subexp: subexps){
            rst.addAll(subexp.vars());
        }
        return rst;
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        // TODO: implement this
        Boolean rst = true;
        for(int i = 0; i < subexps.size(); i++){
            rst = rst & (subexps.get(i).evaluate(assignment));
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
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" && ")) + ")";
    }
}