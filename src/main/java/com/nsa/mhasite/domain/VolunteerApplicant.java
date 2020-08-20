package com.nsa.mhasite.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "volunteer_application")
public class VolunteerApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long volunteerapplicantid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "emergency_number")
    private String emergency_number;

    @Column(name = "address_line_1")
    private String address_line_one;

    @Column(name = "address_line_2")
    private String address_line_two;

    @Column(name = "city")
    private String city;

    @Column(name = "postcode")
    private String postcode;

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

}
