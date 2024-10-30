package com.in.maurya.ecommerce.notification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository  extends MongoRepository<Notification, String> {
}
