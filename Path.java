/**
 * ( start ) --- alphabet ---> ( end )
 * @author Amirhossein Alibakhshi (id: 9731096)
 */
public class Path {
    //fields:
    private String startState;
    private String inputAlphabet;
    private String endState;

    //constructor:
    public Path(String startState, String inputAlphabet, String endState) {
        this.startState = startState;
        this.inputAlphabet = inputAlphabet;
        this.endState = endState;
    }

    @Override
    public String toString() {
        return "( " + startState + " ) ----- " + inputAlphabet + " -----> ( " + endState + " )" ;
    }


    //setters and getters:
    public String getStartState() {
        return startState;
    }
    public void setStartState(String startState) {
        this.startState = startState;
    }
    public String getInputAlphabet() {
        return inputAlphabet;
    }
    public void setInputAlphabet(String inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }
    public String getEndState() {
        return endState;
    }
    public void setEndState(String endState) {
        this.endState = endState;
    }
}
