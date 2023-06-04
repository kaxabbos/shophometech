package com.shophometech.repo;

import com.shophometech.model.Appliances;
import com.shophometech.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliancesRepo extends JpaRepository<Appliances, Long> {
    List<Appliances> findAllByCategory(Category category);
}
