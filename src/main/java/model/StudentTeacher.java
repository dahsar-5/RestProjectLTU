package model;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
public class StudentTeacher {

    private String personnr;
    private String studentId;
    private String fName;
    private String lName;
    private String email;
    private String status;
    private boolean registered;

    // Grade selected for registration in Ladok
    private String ladokGrade;

    // Examination date for Ladok registration
    private LocalDate examinationDate;

    // Student selection
    private final BooleanProperty selected =
            new SimpleBooleanProperty(false);

    public StudentTeacher() {
    }

    public StudentTeacher(
            String personnr,
            String studentId,
            String fName,
            String lName,
            String email,
            String status
    ) {
        this.personnr = personnr;
        this.studentId = studentId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.status = status;
    }

    public String getPersonnr() {
        return personnr;
    }

    public void setPersonnr(String personnr) {
        this.personnr = personnr;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLadokGrade() {
        return ladokGrade;
    }

    public void setLadokGrade(String ladokGrade) {
        this.ladokGrade = ladokGrade;
    }
    public LocalDate getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(LocalDate examinationDate) {
        this.examinationDate = examinationDate;
    }
    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }
    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
