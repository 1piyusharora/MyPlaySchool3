package com.auribisesmyplayschool.myplayschool.bean;

import java.io.Serializable;

public class StudentBean implements Serializable {
    private int studentId,branchId,enqType,approved,subAutoStuId,branCourId, enqStatus,userId,userType,
            admissionId,batchId,courseFees,admStatus,admType,admApproved,submit_fees,bal_fee,
            zero,one,two,totalEnquiries,enqid,feeCategorySelected,accountAmount,totalSellingCost,totalCreditAmount;

    private String  regId, name, father_name, dob, gender, address, email, password, phone, parentPhone, image,
            device_id, curDate, reference, enqdesc, nextVisit,referenceBy, courseName,time,
            userName,coursetime,date,batch_title,courseDuration,branCourFees,branchName,transferDate,
            nextBranchName,admComment,fdName,joinDate,description,startingDate,finishDate,remarks;
    private String father_email,father_phone,mother_name,motherPhone,motherEmail,monthlyIncome,father_qualification,father_occupation,
            father_company_name,fOfficeAddress,mQualification,mOccupation,mCompanyName,mOfficeAddress,recommendSomeone,
            siblingDetails,tStartingDate,tEndingDate;
    private boolean isMarkAttendance=true;

    public StudentBean() {
        this.image="";
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiblingDetails() {
        return siblingDetails;
    }

    public void setSiblingDetails(String siblingDetails) {
        this.siblingDetails = siblingDetails;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getEnqType() {
        return enqType;
    }

    public void setEnqType(int enqType) {
        this.enqType = enqType;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getSubAutoStuId() {
        return subAutoStuId;
    }

    public void setSubAutoStuId(int subAutoStuId) {
        this.subAutoStuId = subAutoStuId;
    }

    public int getBranCourId() {
        return branCourId;
    }

    public void setBranCourId(int branCourId) {
        this.branCourId = branCourId;
    }

    public int getEnqStatus() {
        return enqStatus;
    }

    public void setEnqStatus(int enqStatus) {
        this.enqStatus = enqStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getCourseFees() {
        return courseFees;
    }

    public void setCourseFees(int courseFees) {
        this.courseFees = courseFees;
    }

    public int getAdmStatus() {
        return admStatus;
    }

    public void setAdmStatus(int admStatus) {
        this.admStatus = admStatus;
    }

    public int getAdmType() {
        return admType;
    }

    public void setAdmType(int admType) {
        this.admType = admType;
    }

    public int getAdmApproved() {
        return admApproved;
    }

    public void setAdmApproved(int admApproved) {
        this.admApproved = admApproved;
    }

    public int getSubmit_fees() {
        return submit_fees;
    }

    public void setSubmit_fees(int submit_fees) {
        this.submit_fees = submit_fees;
    }

    public int getBal_fee() {
        return bal_fee;
    }

    public void setBal_fee(int bal_fee) {
        this.bal_fee = bal_fee;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getTotalEnquiries() {
        return totalEnquiries;
    }

    public void setTotalEnquiries(int totalEnquiries) {
        this.totalEnquiries = totalEnquiries;
    }

    public int getEnqid() {
        return enqid;
    }

    public void setEnqid(int enqid) {
        this.enqid = enqid;
    }

    public int getFeeCategorySelected() {
        return feeCategorySelected;
    }

    public void setFeeCategorySelected(int feeCategorySelected) {
        this.feeCategorySelected = feeCategorySelected;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEnqdesc() {
        return enqdesc;
    }

    public void setEnqdesc(String enqdesc) {
        this.enqdesc = enqdesc;
    }

    public String getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(String nextVisit) {
        this.nextVisit = nextVisit;
    }

    public String getReferenceBy() {
        return referenceBy;
    }

    public void setReferenceBy(String referenceBy) {
        this.referenceBy = referenceBy;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCoursetime() {
        return coursetime;
    }

    public void setCoursetime(String coursetime) {
        this.coursetime = coursetime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBatch_title() {
        return batch_title;
    }

    public void setBatch_title(String batch_title) {
        this.batch_title = batch_title;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getBranCourFees() {
        return branCourFees;
    }

    public void setBranCourFees(String branCourFees) {
        this.branCourFees = branCourFees;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getNextBranchName() {
        return nextBranchName;
    }

    public void setNextBranchName(String nextBranchName) {
        this.nextBranchName = nextBranchName;
    }

    public String getAdmComment() {
        return admComment;
    }

    public void setAdmComment(String admComment) {
        this.admComment = admComment;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }


    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public String getMotherEmail() {
        return motherEmail;
    }

    public void setMotherEmail(String motherEmail) {
        this.motherEmail = motherEmail;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getFather_email() {
        return father_email;
    }

    public void setFather_email(String father_email) {
        this.father_email = father_email;
    }

    public String getFather_phone() {
        return father_phone;
    }

    public void setFather_phone(String father_phone) {
        this.father_phone = father_phone;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
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

    public String getFather_company_name() {
        return father_company_name;
    }

    public void setFather_company_name(String father_company_name) {
        this.father_company_name = father_company_name;
    }

    public String getfOfficeAddress() {
        return fOfficeAddress;
    }

    public void setfOfficeAddress(String fOfficeAddress) {
        this.fOfficeAddress = fOfficeAddress;
    }

    public String getmQualification() {
        return mQualification;
    }

    public void setmQualification(String mQualification) {
        this.mQualification = mQualification;
    }

    public String getmOccupation() {
        return mOccupation;
    }

    public void setmOccupation(String mOccupation) {
        this.mOccupation = mOccupation;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public String getmOfficeAddress() {
        return mOfficeAddress;
    }

    public void setmOfficeAddress(String mOfficeAddress) {
        this.mOfficeAddress = mOfficeAddress;
    }

    public String getRecommendSomeone() {
        return recommendSomeone;
    }

    public void setRecommendSomeone(String recommendSomeone) {
        this.recommendSomeone = recommendSomeone;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(int accountAmount) {
        this.accountAmount = accountAmount;
    }

    public int getTotalSellingCost() {
        return totalSellingCost;
    }

    public void setTotalSellingCost(int totalSellingCost) {
        this.totalSellingCost = totalSellingCost;
    }

    public int getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public void setTotalCreditAmount(int totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    public boolean isMarkAttendance() {
        return isMarkAttendance;
    }

    public void setMarkAttendance(boolean markAttendance) {
        isMarkAttendance = markAttendance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String gettStartingDate() {
        return tStartingDate;
    }

    public void settStartingDate(String tStartingDate) {
        this.tStartingDate = tStartingDate;
    }

    public String gettEndingDate() {
        return tEndingDate;
    }

    public void settEndingDate(String tEndingDate) {
        this.tEndingDate = tEndingDate;
    }


}
