import java.util.List;

/**
 * [ s1 , s2 , ... ] --- alphabet ---> [ e1 , e2 , ... ]
 * @author Amirhossein Alibakhshi (id: 9731096)
 */
public class PathForDFA {
    //fields:
    private List<State> startState;
    private String inputAlphabet;
    private List<State> endState;

    //constructor:
    public PathForDFA(List<State> startState, String inputAlphabet, List<State> endState) {
        this.startState = startState;
        this.inputAlphabet = inputAlphabet;
        this.endState = endState;
    }

    @Override
    public String toString() {
        return "" + printStateInPath(startState) + "\t\t ----- " + inputAlphabet + " -----> " + printStateInPath(endState) + "" ;
    }

    public String printStateInPath(List<State> set){
        String result = "[ ";
        for (int i = 0 ; i < set.size(); i++){
            result = result.concat(set.get(i).getTitle());
            if (i != set.size() - 1){
                result = result.concat(" , ");
            }
        }
        result = result.concat(" ]");
        return result;
    }

    //setters and getters:
    public List<State> getStartState() {
        return startState;
    }
    public void setStartState(List<State> startState) {
        this.startState = startState;
    }
    public String getInputAlphabet() {
        return inputAlphabet;
    }
    public void setInputAlphabet(String inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }
    public List<State> getEndState() {
        return endState;
    }
    public void setEndState(List<State> endState) {
        this.endState = endState;
    }
}
