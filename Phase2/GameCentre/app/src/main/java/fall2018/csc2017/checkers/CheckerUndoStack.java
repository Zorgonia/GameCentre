package fall2018.csc2017.checkers;

import java.io.Serializable;
import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.UndoStack;

public class CheckerUndoStack extends UndoStack implements Serializable {

    private ArrayList<Boolean> isKinged;

    private ArrayList<Integer> positions;

    private ArrayList<Integer> ids;

    private ArrayList<Boolean> isPrimedCaptures;

    public CheckerUndoStack(){
        super();
        super.setLimited(false);
        isKinged = new ArrayList<>();
        positions = new ArrayList<>();
        ids = new ArrayList<>();
        isPrimedCaptures = new ArrayList<>();

    }

    public void addCapture(int row, int col, int id){
        positions.add(row*8 + col);
        ids.add(id);

    }

    public void addIsPrimedCapture(boolean isPrimedCapture) {
        isPrimedCaptures.add(isPrimedCapture);
    }
    public void addKinged(boolean kinged){
        isKinged.add(kinged);
    }

    /**
     * Remove and return an two element array in order of most recent position, most recent id.
     * Return null if one or both lists are empty
     * @return two element array of position, id
     */
    public int[] removeCapture(){
        if (positions.size() > 0 && ids.size() > 0){
            int[] captureData = new int[2];
            captureData[0] = positions.remove(positions.size()-1);
            captureData[1] = ids.remove(ids.size()-1);
            return captureData;
        }
        return null;
    }

    public boolean getIsPrimedCapture(){
        if (isPrimedCaptures.size() > 0){
            return isPrimedCaptures.remove(isPrimedCaptures.size() - 1);
        }
        return false;
    }

    public boolean removeKinged(){
        if (isKinged.size() > 0){
            return isKinged.remove(isKinged.size() - 1);
        }
        return false;
    }


}
