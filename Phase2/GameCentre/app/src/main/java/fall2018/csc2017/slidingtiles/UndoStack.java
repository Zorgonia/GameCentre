package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;

public class UndoStack implements Serializable {
    /**
     * Number of moves that will be saved on the stack at a time
     */
    private int size = 3;
    /**
     * Stack containing saved moves
     */
    private ArrayList<Move> stack;

    /**
     * If false, stack will have no maximum size
     */
    private boolean limited = true;

    /**
     * Initialize a new stack.
     */
    public UndoStack() {
        stack = new ArrayList<>();
    }

    /**
     * Remove and return the most recent element in the stack. Return null if stack is empty
     *
     * @return The most recent element in the stack.
     */
    public Move remove() {
        if (stack.size() > 0) {
            return stack.remove(stack.size() - 1);
        }
        return null;
    }

    /**
     * Add move to the stack. If stack is full, add move and remove the oldest object.
     */
    public void add(Move m) {
        if (stack.size() >= size && limited) { //if stack is full and undo has a limit
            stack.remove(0); //remove the oldest move
        }
        stack.add(m);

    }

    /**
     * Add increment to size
     *
     * @param increment: Value to add to size
     */
    public void incrementSize(int increment) {
        size += increment;
    }


    /**
     * Set this.limited to limited.
     *
     * @param limited value to set this.limited to
     */
    public void setLimited(boolean limited) {
        this.limited = limited;
    }

}
