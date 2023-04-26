/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Vendor {
    @Transient
    private String SEQUENCE_NAME = "restaurant_sequence";
    @Id
    private String emailId;
    private String name;
    private String password;
    private String typeOfUser;
    private Restaurant restaurant;
    private String image;
}
