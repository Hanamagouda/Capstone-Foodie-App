package com.niit.service;

import com.niit.config.OrderDTO;
import com.niit.domain.OrderNotification;

public interface OrderNotificationService {
    public OrderNotification getNotification(String emailId);

    public void saveNotification(OrderDTO orderDTO);
}
