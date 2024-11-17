import java.io.Serializable;

// Serializable allows objects of this class to be serialized (converted into bytes for storage or transmission)
public class Module implements Serializable {
    private static final long serialVersionUID = 1L; // Unique ID for serialization

    private String name; // Name of the module
    private int mark; // Mark obtained in the module

    // Constructor initializes module name and sets mark to 0
    public Module(String name) {
        this.name = name;
        this.mark = 0;
    }

    // Getter for module name
    public String getName() {
        return name;
    }

    // Getter for module mark
    public int getMark() {
        return mark;
    }

    // Setter for module mark with validation
    public void setMark(int mark) {
        if (mark >= 0 && mark <= 100) { // Check if mark is within valid range
            this.mark = mark;
        } else {
            System.out.println("Invalid mark. It should be between 0 and 100.");
        }
    }
}