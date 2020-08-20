package com.nsa.mhasite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VolunteerUser {
    //To be further populated once gather form from client.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long volunteeruserid;

    @Column(name = "user_id")
    private Long userid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "emergency_number")
    private String emergency_number;

    @Column(name = "mha_tennant")
    private Boolean mha_tennant;

    @Column(name = "previous_volunteer")
    private Boolean previous_volunteer;

    @Column(name = "volunteer_experience")
    private String volunteer_experience;

    @Column(name = "conditions")
    private Boolean conditions;

    @Column(name = "condition_details")
    private String condition_details;

    @Column(name = "medication")
    private Boolean medication;

    @Column(name = "medication_details")
    private String medication_details;

    @Column(name = "allergies")
    private Boolean allergies;

    @Column(name = "allergy_details")
    private String allergy_details;

    @Column(name = "current_balance")
    private Integer current_balance;

    @Column(name = "total_amount")
    private Integer total_amount;

    public Long getVolunteeruserid() {
        return volunteeruserid;
    }

    public Long getUserid() {
        return userid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmergency_number() {
        return emergency_number;
    }

    public Boolean getMha_tennant() {
        return mha_tennant;
    }

    public Boolean getPrevious_volunteer() {
        return previous_volunteer;
    }

    public String getVolunteer_experience() {
        return volunteer_experience;
    }

    public Boolean getConditions() {
        return conditions;
    }

    public String getCondition_details() {
        return condition_details;
    }

    public Boolean getMedication() {
        return medication;
    }

    public String getMedication_details() {
        return medication_details;
    }

    public Boolean getAllergies() {
        return allergies;
    }

    public String getAllergy_details() {
        return allergy_details;
    }

    public Integer getCurrent_balance() {
        return current_balance;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }
}
