package algorithms.search;

import java.io.Serializable;

// Abstract class representing a state in a search algorithm
public abstract class AState implements Serializable {
    private Object state;  // The current state represented as an Object
    private int cost;      // The cost associated with reaching this state
    private AState parent; // The parent state from which this state was reached

    // Constructor initializing the state with a parent, current state, and cost
    public AState(AState prevState, Object currentState, int cost) {
        this.parent = prevState;
        this.state = currentState;
        this.cost = cost;
    }

    // Default constructor initializing the state with null values and zero cost
    public AState(){
        this.state = null;
        this.parent = null;
        this.cost = 0;
    }

    // Getters and Setters
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

    // Override equals method to compare states based on the 'state' field
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false;  // Check for type compatibility
        AState aState = (AState) obj;
        return this.state.equals(aState.state);  // Compare the state values
    }

    // Override toString method to return the string representation of the state
    @Override
    public String toString() {
        return this.state.toString();
    }
}
