/*
 * Author : Mushib Khan
 * Date : 11-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.repo;

import com.niit.domain.OrderNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNotificationRepo extends MongoRepository<OrderNotification, String> {
}
