package common.data;

import java.io.Serializable;

public class coordinates implements Serializable {
	private int x;
    private Integer y;
    
    
    public coordinates(int x, Integer y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return X-coordinate.
     */
    public int getX() {
        return x;
    }
    /**
     * @return Y-coordinate.
     */
    public Integer getY() {
        return y;
    }
    @Override
    public String toString() {
        return "X:" + x + " Y:" + y;
    }  
    @Override
    public int hashCode() {
        return y.hashCode() + (int) x;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof coordinates) {
            coordinates coordinatesObj = (coordinates) obj;
            return (x == coordinatesObj.getX()) && y.equals(coordinatesObj.getY());
        }
        return false;
    }
}

