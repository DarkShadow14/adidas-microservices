package com.adidas.microservices.subscriptionservice.entity;

import com.adidas.microservices.subscriptionservice.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Id of campaign must be defined")
    private Long campaignId;
    @NotNull(message = "Date of birth must be defined")
    @Past(message = "Entered date of birth must be set in the past")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;
    private String firstName;
    @Enumerated
    private Gender gender;
    @NotNull(message = "Email must be defined in format email@host")
    @Email(message = "Email format is not valid. Please use format email@host")
    private String email;
    @AssertTrue(message = "You must consent to terms to be able to receive newsletter")
    private boolean consent;
    private boolean newsletterSent = false;
}
