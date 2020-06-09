import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Amirhossein Alibakhshi (id: 9731096)
 */
public class DFAfromNFA {
    //fields:
    private List<List<State>> accepterStates;
    private ArrayList<String> accepterAlphabet;
    private ArrayList<PathForDFA> accepterPaths;
    private Accepter nfa;

    //constructors + print the status
    public DFAfromNFA(Accepter nfa) {
        try {
            this.nfa = nfa;

            System.out.println("---------------------------------------");
            System.out.println("///////////////// DFA /////////////////");
            System.out.println("---------------------------------------");

            //creating alphabets ... same as the NFA ( plus " λ " ):
            System.out.println(">>> ALPHABETS:");
            this.accepterAlphabet = nfa.getAccepterAlphabet();
            this.accepterAlphabet.add("λ");
            for (String alphabet : accepterAlphabet)
                if (!alphabet.equals("λ"))
                    System.out.println("   >>> " + alphabet);

            //creating states:
            System.out.println("---------------------------------------");
            System.out.println(">>> ALL OF THE STATES:");
            accepterStates = nfa.createAPowerSetOfTheStates();
            printNewStatesForASetForStatus(accepterStates);

            //start states:
            System.out.println("---------------------------------------");
            System.out.println(">>> START STATES: ");
            System.out.println("   >>> [ " + getStartState().getTitle() + " ]");

            //final states:
            System.out.println("---------------------------------------");
            System.out.println(">>> FINAL STATES:");
            printNewStatesForASetForStatus(getFinalStates());


            //creating new paths:
            System.out.println("---------------------------------------");
            System.out.println(">>> PATHS:");
            System.out.println("* * * * * * * * * * * * * * * * * * * *");
            System.out.println("*  format:                            *");
            System.out.println("* |  >>> starting_state               *");
            System.out.println("* |  |  >>> input_alphabet            *");
            System.out.println("* |  |  |  >>> destination_state      *");
            System.out.println("*                                     *");
            System.out.println("* * * * * * * * * * * * * * * * * * * *");
            this.accepterPaths = new ArrayList<>();
            for (List<State> ls : this.accepterStates){
                boolean goToNullWithAllOfAlphabets = true;
                for (String alphabet : nfa.getAccepterAlphabet()){
                    if (ls.size()!=0 && !nfa.findPathByInitialStateAndInputAlphabet(ls.get(0).getTitle(), alphabet).getEndState().equals("")){
                        goToNullWithAllOfAlphabets = false;
                        break;
                    }
                }
                if (!printNewStatesForASetAsString(ls).equals("[]") || !goToNullWithAllOfAlphabets){
                    if (ls.size()==1 && nfa.findPathByInitialStateAndInputAlphabet(ls.get(0).getTitle(), "λ")!= null ){
                        String stateAfterLandaString =  nfa.findPathByInitialStateAndInputAlphabet(ls.get(0).getTitle(), "λ").getEndState();
                        List<String> stateAfterLandaStringList = new ArrayList<>();
                        stateAfterLandaStringList.add(stateAfterLandaString);
                        List<State> stateAfterLanda = getDFAStatesBySetOfStrings(stateAfterLandaStringList);
                        System.out.println("|  >>>  " + printNewStatesForASetAsString(ls));
                        for (String alphabet : accepterAlphabet){
                            if (!alphabet.equals("λ")){
                                if (findDestinationsByInitialStateAndInputAlphabet(stateAfterLandaString, alphabet) != null){
                                    Set<String> set = new HashSet<>();
                                    for (State state : stateAfterLanda) {
                                        set.addAll(findDestinationsByInitialStateAndInputAlphabet(state.getTitle(), alphabet));
                                    }
                                    List<String> destination = new ArrayList<>(set);
                                    if (getDFAStatesBySetOfStrings(destination).size() != 0) {
                                        accepterPaths.add(new PathForDFA(ls, alphabet, getDFAStatesBySetOfStrings(destination)));
                                        System.out.println("|  |  >>> " + alphabet);
                                        System.out.println("|  |  |  >>> " + printNewStatesForASetAsStringFromString(destination));
                                    }
                                }
                            }
                        }
                    }else {
                        System.out.println("|  >>>  " + printNewStatesForASetAsString(ls));
                        for (String alphabet : accepterAlphabet) {
                            if (!alphabet.equals("λ")) {
                                Set<String> set = new HashSet<>();
                                for (State state : ls) {
                                    set.addAll(findDestinationsByInitialStateAndInputAlphabet(state.getTitle(), alphabet));
                                }
                                List<String> destination = new ArrayList<>(set);
                                if (getDFAStatesBySetOfStrings(destination).size() != 0) {
                                    accepterPaths.add(new PathForDFA(ls, alphabet, getDFAStatesBySetOfStrings(destination)));
                                    System.out.println("|  |  >>> " + alphabet);
                                    System.out.println("|  |  |  >>> " + printNewStatesForASetAsStringFromString(destination));
                                }
                            }
                        }
                    }
                    System.out.println("| - - - - - - - - - - - - - - - - - - -");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("///////////////////////////////////////");
            System.out.println("//          WRONG INPUTS!!!          //");
            System.out.println("///////////////////////////////////////");
        }
    }

    //finding the start state:
    public State getStartState(){
        return nfa.getStartState();
    }

    //finding final state(s):
    public List<List<State>> getFinalStates(){
        List<List<State>> finalStates = new ArrayList<>();
        for (List<State> listOfStates : accepterStates){
            for (State state : listOfStates){
                if (state.isFinalState()){
                    finalStates.add(listOfStates);
                    break;
                }
            }
        }
        return finalStates;
    }

    //a method for printing states in this format: [ Qa , Qb , ... , Qn ]
    public void printNewStatesForASetForStatus(List<List<State>> set){
        for (List<State> ls : set){
            System.out.print("   >>> [ " );
            for (int i = 0 ; i < ls.size(); i++){
                System.out.print(ls.get(i).getTitle()+ " ");
                if (i != ls.size() - 1){
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    //a method for printing states in this format: [ Qa , Qb , ... , Qn ]
    public String printNewStatesForASetAsString(List<State> set){
        String result = "[";
        for (int i = 0 ; i < set.size(); i++){
            result = result.concat(set.get(i).getTitle());
            if (i != set.size() - 1){
                result = result.concat(",");
            }
        }
        result = result.concat("]");
        return result;
    }

    //a method for printing states in this format: [ Qa , Qb , ... , Qn ]
    public String printNewStatesForASetAsStringFromString(List<String> set){
        String result = "[";
        for (int i = 0 ; i < set.size(); i++){
            result = result.concat(set.get(i));
            if (i != set.size() - 1){
                result = result.concat(",");
            }
        }
        result = result.concat("]");
        return result;
    }

    //finding a path with its start state and input alphabet:
    public List<String> findDestinationsByInitialStateAndInputAlphabet (String initialState , String input){
        List<String> result = new ArrayList<>();
        for (Path p : nfa.getAccepterPaths()){
            if ( p.getStartState().equals(initialState) && p.getInputAlphabet().equals(input)){
                result.add(p.getEndState());
            }
        }
        return result;
    }

    //get new states by set of old states:
    public List<State> getDFAStatesBySetOfStrings(List<String> stringList){
        List<State> result = new ArrayList<>();
        for (List<State> ls : accepterStates){
            if (ls.size() == stringList.size()) {
                boolean flag = true;
                for (int i = 0; i < ls.size(); i++) {
                    if (!stringList.contains(ls.get(i).getTitle())) {
                        flag = false;
                    }
                }
                if (flag) {
                    result = ls;
                    break;
                }
            }
        }
        return result;
    }

    //a multiline string for the text file:
    @Override
    public String toString() {
        String result = "";
        //
        for (String s : accepterAlphabet){
            if (!s.equals("λ")){
                result = result.concat(s + " ");
            }
        }
        result = result.concat("\n");
        //
        for (List<State> ls : accepterStates){
            result = result.concat(printNewStatesForASetAsString(ls) + " ");
        }
        result = result.concat("\n");
        //
        result = result.concat("["+getStartState().getTitle()+"]\n");
        //
        for (List<State> s : getFinalStates()){
            result = result.concat(printNewStatesForASetAsString(s) + " ");
        }
        result = result.concat("\n");
        //
        for (PathForDFA p : accepterPaths){
            result = result.concat(printNewStatesForASetAsString(p.getStartState()));
            result = result.concat(" ");
            result = result.concat(p.getInputAlphabet());
            result = result.concat(" ");
            result = result.concat(printNewStatesForASetAsString(p.getEndState()));
            result = result.concat("\n");
        }
        return result;
    }

    //writing on a text file:
    public void writeDFAOnFile(String filePath){
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(this.toString());
            myWriter.close();
            System.out.println("SUCCESSFULLY WROTE ON THE TEXT FILE");
        } catch (IOException e) {
            System.out.println("ERROR WHILE WRITING ON THE FILE!");
            e.printStackTrace();
        }
    }
}
