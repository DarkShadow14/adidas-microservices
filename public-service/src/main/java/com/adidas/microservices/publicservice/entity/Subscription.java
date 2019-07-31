package com.adidas.microservices.publicservice.entity;

import com.adidas.microservices.publicservice.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Subscription {
    @NotNull(message = "Id of campaign must be defined")
    private Long campaignId;
    @NotNull(message = "Date of birth must be defined in dd.MM.yyyy format")
    @Past(message = "Entered date of birth must be set in the past")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private String firstName;
    private Gender gender;
    @NotNull(message = "Email must be defined in format email@host")
    @Email(message = "Email format is not valid. Please use format email@host")
    private String email;
    @AssertTrue(message = "You must consent to terms to be able to receive newsletter")
    private boolean consent;
}
