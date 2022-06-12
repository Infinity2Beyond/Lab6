package common.data;

import java.io.Serializable;

public class car implements Serializable {
	private Boolean cool ;
	public car(Boolean cool) {
		this.cool = cool;
	}
	@Override
    public String toString() {
		if (cool == true) {
			return (" has a cool car");
		}
		else {
			return (" doesn't have a cool car");
		}   
    }
    

}
