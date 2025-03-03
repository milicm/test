package com.production.app.service;

import com.production.app.model.Notification;
import com.production.app.model.Order;

/**
 *
 * @author Milos
 */
public interface OrderService {

    Order saveOrder(Order order);

    String sendNotification(Notification notification);
}
