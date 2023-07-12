package com.blue.bluearchive.board.repository.formRepository.food;

import com.blue.bluearchive.board.entity.formEntity.food.FoodInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FoodInformationRepository extends JpaRepository<FoodInformation, Integer> {
    @Query("SELECT c FROM FoodInformation c WHERE c.foodInformationName LIKE %:searchQuery%")
    Page<FoodInformation> getSearchFood(@Param("searchQuery") String searchQuery, Pageable pageable);
}
