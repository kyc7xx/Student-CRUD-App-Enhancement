/*
The Student class is a model that represents a student record in the system. 
Its purpose is to store and organize student data—such as ID, name, course, year, and age—so it can be used 
in CRUD operations and displayed in the UI.
 */
package studentinfosyscrudapp;

public class Student {

    private int id;
    private String name;
    private String course;
    private int year;
    private int age;  // NEW FIELD ADDED

    public Student() {
    }

    public Student(int id, String name, String course, int year, int age) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.year = year;
        this.age = age;
    }

    public Student(String name, String course, int year, int age) {
        this.name = name;
        this.course = course;
        this.year = year;
        this.age = age;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }    

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
