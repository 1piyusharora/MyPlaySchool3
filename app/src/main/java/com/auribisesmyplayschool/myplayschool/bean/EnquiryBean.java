package com.auribisesmyplayschool.myplayschool.bean;

import java.io.Serializable;

/**
 * Created by Amandeep on 28-Sep-16.
 */
public class EnquiryBean implements Serializable {
int enqid;
    int branCourId;
    int branchId;
    int type;



    int userId;
    String enqphone;
    String name;
    String gender;
    String email;
    String enqdesc;
    String refre;
    String nameprefix;
    String date;
    String time;
    String datetime;
    String courseName;
    String nextVisit;
    String visitDates;
    String mother_name;
    String father_name;
    String father_phone;
    String father_email;
    String father_qualification;
    String father_occupation;
    String father_company_name;
    String userName;
    String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFamily_monthly_income() {
        return family_monthly_income;
    }

    public void setFamily_monthly_income(String family_monthly_income) {
        this.family_monthly_income = family_monthly_income;
    }

    String family_monthly_income;

    public String getFather_office_address() {
        return father_office_address;
    }

    public void setFather_office_address(String father_office_address) {
        this.father_office_address = father_office_address;
    }

    String father_office_address;

    public String getFather_company_name() {
        return father_company_name;
    }

    public void setFather_company_name(String father_company_name) {
        this.father_company_name = father_company_name;
    }

    String address;
    String dob;

    public EnquiryBean() {
        this.enqid=0;
        this.branCourId=0;
        this.name="";
        this.gender="";
        this.email="";
        this.enqdesc="";
        this.refre="";
        this.branchId=0;
        this.courseName="";
        this.nextVisit="";
        this.type = 0;
        this.visitDates = "";
    }

    public String getFather_qualification() {
        return father_qualification;
    }

    public void setFather_qualification(String father_qualification) {
        this.father_qualification = father_qualification;
    }

    public String getFather_occupation() {
        return father_occupation;
    }

    public void setFather_occupation(String father_occupation) {
        this.father_occupation = father_occupation;
    }

    public EnquiryBean(int enqid, int branCourId, int branchId, String enqphone, String name, String gender, String email, String enqdesc, String refre,
                       String nameprefix, String date, String time, String datetime, String courseName, String nextVisit, int type, String visitDates,
                       String mother_name, String father_name, String father_phone, String father_email, String address, String dob, String father_qualification,String father_occupation) {
        this.enqid = enqid;
        this.branCourId = branCourId;
        this.branchId = branchId;
        this.enqphone = enqphone;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.enqdesc = enqdesc;
        this.refre = refre;
        this.nameprefix = nameprefix;
        this.date = date;
        this.time = time;
        this.datetime = datetime;
        this.courseName = courseName;
        this.nextVisit= nextVisit;
        this.type = type;
        this.visitDates = visitDates;
        this.mother_name=mother_name;
        this.father_name=father_name;
        this.father_phone=father_phone;

        this.father_email=father_email;
        this.address=address;
        this.dob=dob;
        this.father_qualification=father_qualification;
        this.father_occupation=father_occupation;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getFather_phone() {
        return father_phone;
    }

    public void setFather_phone(String father_phone) {
        this.father_phone = father_phone;
    }

    public String getFather_email() {
        return father_email;
    }

    public void setFather_email(String father_email) {
        this.father_email = father_email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getVisitDates() {
        return visitDates;
    }

    public void setVisitDates(String visitDates) {
        this.visitDates = visitDates;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getEnqid() {
        return enqid;
    }

    public void setEnqid(int enqid) {
        this.enqid = enqid;
    }

    public String getEnqphone() {
        return enqphone;
    }

    public void setEnqphone(String enqphone) {
        this.enqphone = enqphone;
    }

    public int getBranCourId() {
        return branCourId;
    }

    public void setBranCourId(int branCourId) {
        this.branCourId = branCourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnqdesc() {
        return enqdesc;
    }

    public void setEnqdesc(String enqdesc) {
        this.enqdesc = enqdesc;
    }

    public String getRefre() {
        return refre;
    }

    public void setRefre(String refre) {
        this.refre = refre;
    }

    public String getNameprefix() {
        return nameprefix;
    }

    public void setNameprefix(String nameprefix) {
        this.nameprefix = nameprefix;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(String nextVisit) {
        this.nextVisit = nextVisit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EnquiryBean{" +
                "enqid=" + enqid +
                ", branCourId=" + branCourId +
                ", branchId=" + branchId +
                ", type=" + type +
                ", enqphone='" + enqphone + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", enqdesc='" + enqdesc + '\'' +
                ", refre='" + refre + '\'' +
                ", nameprefix='" + nameprefix + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", datetime='" + datetime + '\'' +
                ", courseName='" + courseName + '\'' +
                ", nextVisit='" + nextVisit + '\'' +
                ", visitDates='" + visitDates + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", father_name='" + father_name + '\'' +
                ", father_phone='" + father_phone + '\'' +
                ", father_email='" + father_email + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
