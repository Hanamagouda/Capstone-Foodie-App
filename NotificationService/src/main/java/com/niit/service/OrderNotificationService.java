package com.niit.service;

import com.niit.config.OrderDTO;
import com.niit.domain.OrderNotification;

public interface OrderNotificationService {
    public OrderNotification orderDetails(OrderNotification orderNotification);

    void saveNotifications(OrderDTO orderDTO);
}
