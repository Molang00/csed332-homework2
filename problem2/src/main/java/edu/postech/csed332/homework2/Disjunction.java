package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is || (or).
 */
public class Disjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a disjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Disjunction(Exp... exps) {
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
        Boolean rst = false;
        for(int i = 0; i < subexps.size(); i++){
            rst = rst | (subexps.get(i).evaluate(assignment));
        }
        return rst;
    }

    @Override
    public Exp simplify() {
        // TODO: implement this
        List<Integer> variableList = new ArrayList(this.vars());
        List<Map<Integer, Integer> > truthTable = makeTruthMap(variableList, 0);
        List<Map<Integer, Integer> > trueValues = new ArrayList<Map<Integer, Integer> >();
        List<Map<Integer, Integer> > pastValues = null;
        List<Map<Integer, Integer> > resultList = new ArrayList<Map<Integer, Integer> >();
        Boolean checkChange = true;
        for(int i = 0;i < truthTable.size(); i++){
            Map<Integer, Integer> cur = truthTable.get(i);
            Map<Integer, Boolean> forEvaluate = new HashMap<Integer, Boolean>();
            for(int j = 0; j < variableList.size(); j++){
                forEvaluate.put(variableList.get(j), (cur.get(variableList.get(j))==1));
            }
            if(evaluate(forEvaluate)) trueValues.add(cur);
        }

        while(checkChange){
            pastValues = new ArrayList<Map<Integer, Integer> >(trueValues);
            trueValues = new ArrayList<Map<Integer, Integer> >();
            checkChange = false;

            for(int i = 0; i < pastValues.size(); i++){
                Boolean isFinded = false;
                Map<Integer, Integer> compareA = pastValues.get(i);
                for(int j = 0; j < pastValues.size(); j++){
                    if(i == j) continue;
                    Map<Integer, Integer> compareB = pastValues.get(j);
                    int cnt = 0;
                    int index = 0;
                    for(int k = 0; k < variableList.size(); k++){
                     Integer integerA = compareA.get(variableList.get(k));
                     Integer integerB = compareB.get(variableList.get(k));
                        if(integerA != integerB){
                            cnt++;
                            index = variableList.get(k);
                        }
                    }
                    if(cnt == 1){
                        Map<Integer, Integer> newValue = new HashMap<Integer, Integer>(compareA);
                        newValue.put(index, 2);
                        trueValues.add(newValue);
                        isFinded = true;
                    }
                }
                if(!isFinded)
                {
                    resultList.add(compareA);
                }
                else{
                    checkChange = true;
                }
            }
        }
        
        return null;
    }

    public List<Map<Integer, Integer> > makeTruthMap(List<Integer> variableList, Integer id){
        List<Map<Integer, Integer> > truthTable = new ArrayList<Map<Integer, Integer> >();
        if(id == variableList.size()-1){
            Map<Integer, Integer> giveTrue = new HashMap<Integer, Integer>();
            giveTrue.put(variableList.get(id), 0);
            Map<Integer, Integer> giveFalse = new HashMap<Integer, Integer>();
            giveFalse.put(variableList.get(id), 1);
            truthTable.add(giveTrue);
            truthTable.add(giveFalse);
            return truthTable;
        }
        else {
            List<Map<Integer, Integer> > nextStates = this.makeTruthMap(variableList, id+1);
            for(int i = 0; i < nextStates.size(); i++){
                Map<Integer, Integer> giveTrue = new HashMap<Integer, Integer>(nextStates.get(i));
                giveTrue.put(variableList.get(id), 0);
                truthTable.add(giveTrue);
                Map<Integer, Integer> giveFalse = new HashMap<Integer, Integer>(nextStates.get(i));
                giveFalse.put(variableList.get(id), 1);
                truthTable.add(giveFalse);
            }
            return truthTable;
        }
    }

    @Override
    public String toString() {
        return "(" + subexps.stream().map(i -> i.toString()).collect(Collectors.joining(" || ")) + ")";
    }
}