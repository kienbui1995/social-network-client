package com.joker.thanglong.Learning.Entity;

/**
 * Created by joker on 6/12/17.
 */

public class Subject {
    String id;
    String subjectId;
    String subjectName;
    int creadit;
    int factor;

    public Subject(String id, String subjectId, String subjectName, int creadit, int factor) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.creadit = creadit;
        this.factor = factor;
    }

    public Subject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCreadit() {
        return creadit;
    }

    public void setCreadit(int creadit) {
        this.creadit = creadit;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
}
