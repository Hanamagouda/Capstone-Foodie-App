/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

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
