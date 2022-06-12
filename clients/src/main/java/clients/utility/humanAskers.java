package clients.utility;

import java.util.NoSuchElementException;
import java.util.Scanner;


import clients.app;
import common.data.*;
import common.exceptions.IncorrectInput;
import common.exceptions.IncorrectInputInScript;
import common.exceptions.MustBeNotEmpty;
import common.exceptions.NotInDeclaredLimit;
import common.utility.outPuter;



public class humanAskers {
	private final int MAX_X = 352;
	private Scanner UserinputReader ;
	private boolean fileMode;
	
	public humanAskers (Scanner UserinputReader) {
		this.UserinputReader = UserinputReader ;
		fileMode = false ;
	}
	/**
     * Sets a InputStream to scan user input.
     * 
     * @param inputReader InputStreamReader to set.
     */
	public void setUserInputReader(Scanner UserinputReader) {
		this.UserinputReader = UserinputReader ;
	}
	 /**
     * Get InputStreamReaderr when interacting with Console.
     * @return InputStreamReaderr, which uses for user input.
     */
	public Scanner getUserInputReader() {
		return UserinputReader ;
	}
	
	public void setFileMode () {
		fileMode = true;
	}
	
	public void setUserMode () {
		fileMode = false;
	}
	
	public long askID() throws IncorrectInputInScript {
		String StrID ;
		Long ID;
        while (true) {
            try {
            	outPuter.println("Enter ID:");
            	outPuter.print(app.PS2);
                StrID = UserinputReader.nextLine().trim();
                if (StrID.equals(""))
                    throw new MustBeNotEmpty();
                if (fileMode)
                	outPuter.println(StrID);
                    ID = Long.parseLong(StrID);
                if (ID < 1) throw new IncorrectInput();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The ID is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (MustBeNotEmpty exception) {
            	outPuter.printerror("The ID cannot be empty!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NumberFormatException exception) {
            	outPuter.printerror("ID must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch (IncorrectInput exception) {
            	outPuter.printerror("Input must be bigger than 0!");
                if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return ID;
	}
	
	
	/**
     * Asks a user the humanbeing's name.
     * 
     * @return Humanbeing's name.
     * @throws IncorrectInputInScriptException iff script is running and something
     *                                         goes wrong.
     */
	public String askName() throws IncorrectInputInScript {
        String name;
        while (true) {
            try {
            	outPuter.println("Enter name:");
            	outPuter.print(app.PS2);
                name = UserinputReader.nextLine().trim();
                if (fileMode)
                	outPuter.println(name);
                if (name.equals(""))
                    throw new MustBeNotEmpty();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The name is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (MustBeNotEmpty exception) {
            	outPuter.printerror("The name cannot be empty!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }
	/**
     * Asks a user the organization's X coordinate.
     * 
     * @return Organization's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something
     *                                         goes wrong.
     */
    public int askX() throws IncorrectInputInScript {
        String strX;
        int x;
        while (true) {
            try {
            	outPuter.println("Enter X coordinate:");
            	outPuter.print(app.PS2);
                strX = UserinputReader.nextLine().trim();
                if (strX.equals("")) throw new MustBeNotEmpty();
                if (fileMode)
                	outPuter.println(strX);
                x = Integer.parseInt(strX);
                if (x > MAX_X)
                    throw new NotInDeclaredLimit();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The X coordinate is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The X coordinate must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NotInDeclaredLimit exception) {
            	outPuter.printerror("The X coordinate cannot exceed " + MAX_X + "!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input for X can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return x;
    }
    /**
     * Asks a user the organization's Y coordinate.
     * 
     * @return Organization's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something
     *                                         goes wrong.
     */
    public Integer askY() throws IncorrectInputInScript {
        String strY;
        Integer y;
        while (true) {
            try {
            	outPuter.println("Enter Y coordinate: ");
            	outPuter.print(app.PS2);
                strY = UserinputReader.nextLine().trim();
                if (strY.equals("")) throw new MustBeNotEmpty();
                if (fileMode)
                	outPuter.println(strY);
                y = Integer.parseInt(strY);
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The Y coordinate is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();       
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The Y coordinate must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input for Y can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return y;
    }
    /**
     * Asks a user the humanbeing's coordinates.
     * @return Humanbeing's coordinates.
     * @throws IncorrectInput If script is running and something goes wrong.
     */
    public coordinates askCoordinates() throws IncorrectInputInScript {
        int x;
        Integer y;
        x = askX();
        y = askY();
        return new coordinates(x, y);
    }
    
    public Double askImpactSpeed() throws IncorrectInputInScript {
    	String StrImpactSpeed ;
    	Double ImpactSpeed ;
    	while (true) {
            try {
            	outPuter.println("Enter impact speed: ");
            	outPuter.print(app.PS2);
                StrImpactSpeed = UserinputReader.nextLine().trim();
                if (StrImpactSpeed.equals("")) throw new MustBeNotEmpty();
                if (fileMode)
                	outPuter.println(StrImpactSpeed);
                ImpactSpeed = Double.parseDouble(StrImpactSpeed);
                if (ImpactSpeed < 0) throw new IncorrectInput();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The Impact Speed is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();  
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The Impact Speed must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
                
            } catch (IncorrectInput exception) {
            	outPuter.printerror("The input must be at least equal to 0 or bigger!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input for Impact Speed can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return ImpactSpeed;  	
    }
    
    public Integer askMinutesOfWaiting() throws IncorrectInputInScript {
    	String StrMinutesOfWaiting;
    	Integer MinutesOfWaiting ;
    	while (true) {
            try {
            	outPuter.println("Enter minutes of waiting: ");
            	outPuter.print(app.PS2);
                StrMinutesOfWaiting = UserinputReader.nextLine().trim();
                if (StrMinutesOfWaiting.equals("")) throw new MustBeNotEmpty();
                if (fileMode) outPuter.println(StrMinutesOfWaiting);
                MinutesOfWaiting = Integer.parseInt(StrMinutesOfWaiting);
                if (MinutesOfWaiting < 0 ) throw new IncorrectInput() ;
                break;
                
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The minutes of waiting is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The minutes of waiting must be represented by a number!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch (IncorrectInput exception) {
            	outPuter.printerror("The input must be at least equal to 0 or bigger ");
                if (fileMode) throw new IncorrectInputInScript();
            }  catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input for Minutes Of Waiting can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return MinutesOfWaiting;  	
    }
    
    public weapontype askWeaponType() throws IncorrectInputInScript {
    	String StrWeaponType;
    	weapontype weaponType;
    	 while (true) {
             try {
            	 outPuter.println("List of weapon type - "  + weapontype.nameList());
            	 outPuter.println("Enter Humanbeing's weapon type:");
            	 outPuter.print(app.PS2);
                 StrWeaponType = UserinputReader.nextLine().trim();
                 if (StrWeaponType.equals("")) throw new MustBeNotEmpty();
                 if (fileMode) outPuter.println(StrWeaponType);
                 weaponType = weapontype.valueOf(StrWeaponType.toUpperCase());
                 break;
             } catch (NoSuchElementException exception) {
            	 outPuter.printerror("That weapon type is not recognized!");
                 if (fileMode) throw new IncorrectInputInScript();
             } catch (IllegalArgumentException exception) {
            	 outPuter.printerror("That weapon type is not listed!");
                 if (fileMode) throw new IncorrectInputInScript();    
             } catch (IllegalStateException exception) {
            	 outPuter.printerror("Unexpected error!");
                 System.exit(0);
             } catch(MustBeNotEmpty exception) {
            	 outPuter.printerror("The input for Weapon Type can not be empty") ;
             	if (fileMode) throw new IncorrectInputInScript();
             } 
         }
         return weaponType;
     }

    public mood askMood() throws IncorrectInputInScript {
    	String StrMood ;
    	mood Mood ;
    	while (true) {
            try {
            	outPuter.println("List of mood - "  + mood.nameList());
            	outPuter.println("Enter Humanbeing's mood:");
            	outPuter.print(app.PS2);
                StrMood = UserinputReader.nextLine().trim();
                if (StrMood.equals("")) throw new MustBeNotEmpty();
                if (fileMode) outPuter.println(StrMood);
                Mood = mood.valueOf(StrMood.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("That mood is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalArgumentException exception) {
            	outPuter.printerror("That mood is not listed!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input for Mood can not be empty") ;
             	if (fileMode) throw new IncorrectInputInScript();
             }
        }
        return Mood; 	
    }
    
    public boolean askHero () throws IncorrectInputInScript {
    	String StrHero ;
    	boolean Hero ;
    	while (true) {
            try {
            	String s1 = "YES" ;
            	String s2 = "NO" ;
            	outPuter.println("Do this humanbeing a hero? (Yes/No) ");
            	outPuter.print(app.PS2);
                StrHero = UserinputReader.nextLine().trim();
                if (StrHero.equals("")) throw new MustBeNotEmpty();
                if (fileMode) outPuter.println(StrHero);
                if (StrHero.toUpperCase().compareTo(s1) == 0) {
                    Hero = true;                	
                }else if (StrHero.toUpperCase().compareTo(s2) == 0) {
                	Hero = false;
                }else {
                	throw new NumberFormatException();                       	   	
                }
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The input is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();     
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The input must be yes or no!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            }  catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return Hero ;
    	
    }
    
    public Boolean askToothpick () throws IncorrectInputInScript {
    	String StrToothpick ;
    	Boolean Toothpick ;
    	while (true) {
            try {
            	String s1 = "YES" ;
            	String s2 = "NO" ;
            	outPuter.println("Do this humanbeing has a toothpick? (Yes/No) ");
            	outPuter.print(app.PS2);
                StrToothpick = UserinputReader.nextLine().trim();
                if (StrToothpick.equals("")) throw new MustBeNotEmpty();
                if (fileMode) outPuter.println(StrToothpick);
                if (StrToothpick.toUpperCase().compareTo(s1) == 0) {
                	Toothpick = true;                	
                }else if (StrToothpick.toUpperCase().compareTo(s2) == 0) {
                	Toothpick = false;
                }else {
                	throw new NumberFormatException();                       	   	
                }
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The input is not recognized!");
                if (fileMode) throw new IncorrectInputInScript();       
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The input must be yes or no!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return Toothpick ;	
    }
    
    public boolean askCool() throws IncorrectInputInScript {
    	String StrCool ;
    	Boolean Cool ;
    	while (true) {
            try {
            	String s1 = "YES" ;
            	String s2 = "NO" ;
            	outPuter.println("Do this humanbeing have a cool car? (Yes/No) ");
            	outPuter.print(app.PS2);
                StrCool = UserinputReader.nextLine().trim();
                if (StrCool.equals("")) throw new MustBeNotEmpty();
                if (fileMode) outPuter.println(StrCool);
                if (StrCool.toUpperCase().compareTo(s1) == 0) {
                	Cool = true;                	
                }else if (StrCool.toUpperCase().compareTo(s2) == 0) {
                	Cool = false;
                }else {
                	throw new NumberFormatException();                       	   	
                }
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("The input is not recognized!");    
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NumberFormatException exception) {
            	outPuter.printerror("The input must be yes or no!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NullPointerException | IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return Cool ;
    }
    
    public car askCar() throws IncorrectInputInScript {
        boolean cool;
        cool = askCool();
        return new car(cool);
    }
    
    public boolean askQuestion(String question) throws IncorrectInputInScript {
        String finalQuestion = question + " (Y/N):";
        String answer;
        while (true) {
            try {
            	outPuter.println(finalQuestion);
            	outPuter.print(app.PS2);
                answer = UserinputReader.nextLine().trim().toUpperCase();
                if (answer.equals("")) throw new MustBeNotEmpty();
                if (fileMode) outPuter.println(answer);
                if (!answer.equals("Y") && !answer.equals("N"))
                    throw new NotInDeclaredLimit();
                break;
            } catch (NoSuchElementException exception) {
            	outPuter.printerror("Answer not recognized!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (NotInDeclaredLimit exception) {
            	outPuter.printerror("The answer must be represented by 'Y' or 'N'!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
            	outPuter.printerror("Unexpected error!");
                System.exit(0);
            } catch(MustBeNotEmpty exception) {
            	outPuter.printerror("The input can not be empty") ;
            	if (fileMode) throw new IncorrectInputInScript();
            }
        }
        return (answer.equals("Y")) ? true : false;
    }

    	
    @Override
    public String toString() {
        return "HumanAsker (helper class for queries to the user)";
    }
}

