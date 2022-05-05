package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.Subscription;

import java.util.List;

public interface SubscriptionDAO {


    List<Subscription> showAllSubscriptions();

    List<Subscription> showAllSubscriptionsForAccount(int id);

    Subscription addNewSubscription(Subscription subscription);

    Subscription getSubscriptionById(int id);

    Subscription changeSubscriptionToFalse(Subscription subscription);

    Subscription deleteSubscription(Subscription subscription);

    Subscription changeSubscriptionToTrue(Subscription subscription);
}
