/*
 * Author : Mushib Khan
 * Date : 02-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cuisine {
    @Id
    private int cuisineId;
    private String cuisineName;
    private double cuisinePrice;
    private String cuisineImage;
    private String description;
}
