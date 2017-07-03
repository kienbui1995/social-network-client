package com.joker.thanglong.Learning.Entity;

import java.util.Date;

/**
 * Created by joker on 6/12/17.
 */

public class Student {
    private int id;
    private String studentId;
    private String first_name;
    private String last_name;
    private Date birthday;

    public Student(int id, String studentId, String first_name, String last_name, Date birthday) {
        this.id = id;
        this.studentId = studentId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthday = birthday;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
