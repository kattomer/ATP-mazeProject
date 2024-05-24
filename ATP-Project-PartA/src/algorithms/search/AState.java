package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    private Object state;
    private int cost;
    private AState parent;

    public AState(AState prevState, Object currentState, int cost) {
        this.parent = prevState;
        this.state = currentState;
        this.cost = cost;
    }

    public AState(){
        this.state = null;
        this.parent = null;
        this.cost = 0;
    }

    // Getters & Setters
    public AState getFrom() {
        return parent;
    }

    public Object getCurrentState() {
        return state;
    }

    public int getCost() {
        return cost;
    }

    public void setFrom(AState from){
        this.parent = from;
    }

    public void setCurrentState(Object pos){
        this.state = pos;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AState aState = (AState) obj;
        return this.state.equals(aState.state);
    }

    @Override
    public String toString() {
        return this.state.toString();
    }

}
