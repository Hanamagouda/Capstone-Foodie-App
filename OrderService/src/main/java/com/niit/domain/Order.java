/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    @Id
    private String orderId;
    private int quantity;
    private double price;
    private List<Cuisine> orderedItems;
}
