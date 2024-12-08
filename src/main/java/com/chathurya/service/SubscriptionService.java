package com.chathurya.service;

import com.chathurya.modal.PlanType;
import com.chathurya.modal.Subscription;
import com.chathurya.modal.User;
import jdk.jshell.JShell;

public interface SubscriptionService {
    Subscription createSubscription (User user);
    Subscription getUsersSubscription(Long userId)throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType planType);
    boolean isvalid(Subscription subscription);
}
