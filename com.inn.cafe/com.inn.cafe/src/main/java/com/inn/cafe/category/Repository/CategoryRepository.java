package com.inn.cafe.category.Repository;

import com.inn.cafe.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String > {
    List<Category> getAllCategory();
}
