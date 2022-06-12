package common.data;

import java.io.Serializable;
import java.time.LocalDateTime;


public class humanbeing implements Comparable<humanbeing>, Serializable  {
	private Long id ;
    private String name; 
    private coordinates coordinates; 
    private LocalDateTime creationDate; 
    private boolean realHero;
    private Boolean hasToothpick; 
    private Double impactSpeed; 
    private Integer minutesOfWaiting; 
    private weapontype weaponType; 
    private mood mood;
    private car car;  
    public humanbeing () {
    	super ();
    }
    public humanbeing (Long id, String name, coordinates coordinates, LocalDateTime creationDate, boolean realHero,
    	Boolean hasToothpick, Double impactSpeed, Integer minutesOfWaiting, weapontype weaponType, mood mood, car car ) {
    	super () ;
    	this.id = id ;
    	this.name = name ;
    	this.coordinates = coordinates ;
    	this.creationDate = creationDate;
    	this.realHero = realHero ;
    	this.hasToothpick = hasToothpick ;
    	this.impactSpeed = impactSpeed ;
    	this.minutesOfWaiting = minutesOfWaiting ;
    	this.weaponType = weaponType ;
    	this.mood = mood;
    	this.car = car;
    	
    }
   public void setId (Long id) {
	   this.id = id;
   }
   public Long getId() {
	   return id ;
   }
   public String getName() {
	   return name;
   }
   public coordinates getCoordinates() {
       return coordinates;
   }
   public LocalDateTime getCreationDate() {
       return creationDate;
   }
   public boolean getHero() {
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
   
   
   public int compareTo(humanbeing human) {
       return id.compareTo(human.getId());
   }
   @Override
   public String toString() {
       String info = "";
       info += "HumanBeing's ID" + id;
       info += " (Added " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")";
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
