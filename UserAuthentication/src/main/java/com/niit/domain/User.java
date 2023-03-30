/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    //Instance variables of User
    @Id
    private String userEmailId;
    private String password;
    private String typeOfUser;
}
