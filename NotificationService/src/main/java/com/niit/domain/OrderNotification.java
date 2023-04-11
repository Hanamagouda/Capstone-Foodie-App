/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderNotification {
    JSONObject itemNames;
    @Id
    private String emailId;
    private String message;
}
