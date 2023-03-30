/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;


@NoArgsConstructor
@AllArgsConstructor
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
