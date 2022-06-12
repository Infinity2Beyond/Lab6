package common.interaction;

import common.data.*;

import java.io.Serializable;

public class humanRaw implements Serializable {
    private String name; 
    private coordinates coordinates; 
    private Boolean realHero;
    private Boolean hasToothpick; 
    private Double impactSpeed; 
    private Integer minutesOfWaiting; 
    private weapontype weaponType; 
    private mood mood;
    private car car;  
    
    public humanRaw (String name, coordinates coordinates, Boolean realHero,
    	Boolean hasToothpick, Double impactSpeed, Integer minutesOfWaiting, weapontype weaponType, mood mood, car car ) {
    	this.name = name ;
    	this.coordinates = coordinates ;
    	this.realHero = realHero ;
    	this.hasToothpick = hasToothpick ;
    	this.impactSpeed = impactSpeed ;
    	this.minutesOfWaiting = minutesOfWaiting ;
    	this.weaponType = weaponType ;
    	this.mood = mood;
    	this.car = car;
    	
    }
    
    public String getName() {
 	   return name;
    }
    public coordinates getCoordinates() {
        return coordinates;
    }
    public Boolean getHero() {
 	   return realHero;
    }
    public Boolean getToothpick () {
 	   return hasToothpick;
    }
    public Double getSpeed () {
 	   return impactSpeed ;   
    }
    public Integer getMinutesWaiting () {
 	   return minutesOfWaiting ;
    }
    public weapontype getWeaponType () {
 	   return weaponType ;
    }
    public mood getMood () {
 	   return mood ;
    }  
    public car getCar() {
 	   return car;
    }
    
    @Override
    public String toString() {
        String info = "";
        info += "\n Name: " + name;
        info += "\n Coordinates: " + coordinates;
        if (realHero == true) {
     	   info += "\n " + name +" is a true hero" ;
 	   }
 	   else {
 		   info += "\n " + name +" is not a true hero" ;
 	   }   
        if (hasToothpick == true) {
     	   info += "\n " + name +" has a toothpick";	   }
 	   else {
 		   info += "\n " + name +" doesn't have a toothpick";	   }   
        info += "\n " + name + "'s impact Speed: " + impactSpeed;
        info += "\n " + name + " has been wating for " + minutesOfWaiting +" minutes";
        info += "\n " + name +"'s weapon type: " + weaponType;
        info += "\n " + name +"'s mood: " + mood;
        info += "\n " + name + car.toString();
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + + impactSpeed.hashCode() + (Integer) minutesOfWaiting 
     		   + weaponType.hashCode() + mood.hashCode() + car.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof humanbeing) {
     	   humanbeing humanObj = (humanbeing) obj;
            return name.equals(humanObj.getName()) && coordinates.equals(humanObj.getCoordinates()) &&
                   (realHero == humanObj.getHero()) && (hasToothpick == humanObj.getToothpick()) &&
                   (impactSpeed == humanObj.getSpeed()) && (minutesOfWaiting == humanObj.getMinutesWaiting()) &&
                   (weaponType == humanObj.getWeaponType()) && (mood == humanObj.getMood()) &&
                   car.equals(humanObj.getCar());
        }
        return false;
    }
    
}
