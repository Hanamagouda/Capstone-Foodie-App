/*
 * Author : Mushib Khan
 * Date : 05-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant {
    @Id
    private String restaurantId;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantImage;
    private List<Cuisine> cuisineList;
}
