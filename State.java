/**
 * @author Amirhossein Alibakhshi (id: 9731096)
 */
public class State {
    //fields:
    private String title;
    private boolean isStarsState;
    private boolean IsFinalState;

    //constructor:
    public State(String title){
        this.title = title;
        //initial values:
        this.isStarsState = false;
        this.IsFinalState = false;
    }

    //setters and getters:
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean isStarsState() {
        return isStarsState;
    }
    public void setStarsState(boolean starsState) {
        isStarsState = starsState;
    }
    public boolean isFinalState() {
        return IsFinalState;
    }
    public void setFinalState(boolean finalState) {
        IsFinalState = finalState;
    }
}
