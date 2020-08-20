package com.nsa.mhasite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String fName;
    private String sName;
    private String address1;
    private String address2;
    private String city;
    private String postCode;
    private String phoneNumber;
    private String emergencyNumber;
    private String email;
    private String tenant;
    private String volunteered;
    private String volunteeredRole;
    private String disabilities;
    private String disabilityDetails;
    private String medications;
    private String medicationDetails;
    private String allergies;
    private String allergyDetails;
    private String checkedStatement;

    public RegisterForm(Date date, String fName, String sName, String address1,
                        String address2, String city, String postCode, String phoneNumber,
                        String emergencyNumber, String email, String tenant,
                        String volunteered, String volunteeredRole, String disabilities,
                        String disabilityDetails, String medications, String medicationDetails,
                        String allergies, String allergyDetails) {

        this.date = date;
        this.fName = fName;
        this.sName = sName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.emergencyNumber = emergencyNumber;
        this.email = email;
        this.tenant = tenant;
        this.volunteered = volunteered;
        this.volunteeredRole = volunteeredRole;
        this.disabilities = disabilities;
        this.disabilityDetails = disabilityDetails;
        this.medications = medications;
        this.medicationDetails = medicationDetails;
        this.allergies = allergies;
        this.allergyDetails= allergyDetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTenant() {
        if(tenant != null){
            if (tenant.equals("yes")) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Boolean getVolunteered() {
        if(volunteered != null){
        if(volunteered.equals("yes")) {
            return Boolean.TRUE;
        }
        else{return Boolean.FALSE;}
        }
        return null;
    }

    public void setVolunteered(String volunteered) {
        this.volunteered = volunteered;
    }

    public String getVolunteeredRole() {
        return volunteeredRole;
    }

    public void setVolunteeredRole(String volunteeredRole) {
        this.volunteeredRole = volunteeredRole;
    }

    public Boolean getDisabilities() {
        if(disabilities != null){
            if(disabilities.equals("yes")) {
                return Boolean.TRUE;
            }
            else{return Boolean.FALSE;}
        }
        return null;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }

    public String getDisabilityDetails() {
        return disabilityDetails;
    }

    public void setDisabilityDetails(String disabilityDetails) {
        this.disabilityDetails = disabilityDetails;
    }

    public Boolean getMedications() {
        if(medications != null){
            if(medications.equals("yes")) {
                return Boolean.TRUE;
            }
            else{return Boolean.FALSE;}
            }
        return null;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    public Boolean getAllergies() {
        if(allergies != null){
            if(allergies.equals("yes")) {
                return Boolean.TRUE;
            }
            else{return Boolean.FALSE;}
        }
        return null;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAllergyDetails() {
        return allergyDetails;
    }

    public void setAllergyDetails(String allergyDetails) {
        this.allergyDetails = allergyDetails;
    }

    public void setStatementAnswer(String value) {
        this.checkedStatement = value;
    }

    public String getStatementAnswer() {
        return this.checkedStatement;
    }

}
