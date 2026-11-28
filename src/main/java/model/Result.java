package model;

public class Result {

    private String personnr;
    private String finalGrade;
    private String examDate;
    private boolean registered;

    public Result() {
    }

    public Result(String personnr, String finalGrade,
                  String examDate, boolean registered) {

        this.personnr = personnr;
        this.finalGrade = finalGrade;
        this.examDate = examDate;
        this.registered = registered;
    }

    public String getPersonnr() {
        return personnr;
    }

    public void setPersonnr(String personnr) {
        this.personnr = personnr;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}