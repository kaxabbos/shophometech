package com.shophometech.repo;

import com.shophometech.model.Ordering;
import com.shophometech.model.User;
import com.shophometech.model.enums.OrderingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepo extends JpaRepository<Ordering, Long> {
    public List<Ordering> findAllByUserAndOrderingStatus(User user, OrderingStatus orderingStatus);
}
