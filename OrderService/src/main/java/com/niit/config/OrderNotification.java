/*
 *  Author : Hanamagouda Goudar
 *  Date : 04-04-2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.niit.config;

import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderNotification {
    @Id
    private String emailId;
    private String message;

    private JSONObject orderId;
}
