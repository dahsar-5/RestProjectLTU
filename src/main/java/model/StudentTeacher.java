package model;

public class StudentTeacher {

    private String personnr;
    private String studentId;
    private String fName;
    private String lName;
    private String email;
    private String status;

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
}
