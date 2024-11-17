import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Module[] modules;

    public Student(String id) {
        this.id = id;
        this.name = "";
        this.modules = new Module[]{new Module("Module 1"), new Module("Module 2"), new Module("Module 3")};
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    public void setModuleMarks(int module1Mark, int module2Mark, int module3Mark) {
        if (isValidMark(module1Mark) && isValidMark(module2Mark) && isValidMark(module3Mark)) {
            this.modules[0].setMark(module1Mark);
            this.modules[1].setMark(module2Mark);
            this.modules[2].setMark(module3Mark);
        } else {
            System.out.println("Marks should be between 0 and 100.");
        }
    }

    private boolean isValidMark(int mark) {
        return mark >= 0 && mark <= 100;
    }

    public int getModuleMark(int moduleIndex) {
        return modules[moduleIndex].getMark();
    }

    public double calculateAverage() {
        int total = 0;
        for (Module module : modules) {
            total += module.getMark();
        }
        return total / 3.0;
    }

    public String calculateGrade() {
        double average = calculateAverage();
        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }

    public String completeReport() {
        StringBuilder report = new StringBuilder();
        report.append("Student ID: ").append(id).append(", Name: ").append(name);
        for (int i = 0; i < modules.length; i++) {
            report.append(", ").append(modules[i].getName()).append(": ").append(modules[i].getMark());
        }
        report.append(", Total: ").append(modules[0].getMark() + modules[1].getMark() + modules[2].getMark());
        report.append(", Average: ").append(calculateAverage());
        report.append(", Grade: ").append(calculateGrade());
        return report.toString();
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name +
                ", Module 1: " + modules[0].getMark() +
                ", Module 2: " + modules[1].getMark() +
                ", Module 3: " + modules[2].getMark();
    }

    public String toCSV() {
        return "id - " + id + "," + "name - " + name + "," +
                "Module 1 marks - " + modules[0].getMark() + "," +
                "Module 2 marks - " + modules[1].getMark() + "," +
                "Module 3 marks - " + modules[2].getMark();
    }

    public static Student fromCSV(String csv) {
        String[] parts = csv.split(",");
        String id = parts[0].trim().substring(5);  // Adjusted index
        String name = parts[1].trim().substring(7);  // Adjusted index
        int module1Mark = Integer.parseInt(parts[2].trim().substring(17));
        int module2Mark = Integer.parseInt(parts[3].trim().substring(17));
        int module3Mark = Integer.parseInt(parts[4].trim().substring(17));

        Student student = new Student(id);
        student.setName(name);
        student.setModuleMarks(module1Mark, module2Mark, module3Mark);
        return student;
    }
}