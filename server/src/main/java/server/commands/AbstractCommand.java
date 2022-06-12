package server.commands;

public abstract class AbstractCommand implements Command {
    private String name;
    private String description;
    private String usage;


    AbstractCommand(String name, String usage, String description) {
        this.name = name;
        this.description = description;
        this.usage = usage  ; 
    }
    /**
     * @return Name and usage way of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Description of the command.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @return usage of the command.
     */
    public String getUsage() {
        return usage;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
}