package com.inn.cafe.category.serviceImpl;

import com.inn.cafe.category.Repository.CategoryRepository;
import com.inn.cafe.category.model.Category;
import com.inn.cafe.category.service.CategoryService;

import com.inn.cafe.constents.CafeConstant;

import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {

            if (validateCafeCategoryMap(requestMap, false)) {
               Category category= getCategoryFromMap(requestMap, true);
                categoryRepository.save(category);
                return CafeUtils.getResponseEntity("Category Succesfully added", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity("Category Not Succesfully added", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCafeCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {

            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }

        }
        return false;
    }

    ////        return null;
    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {

            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Inside if");

                return new ResponseEntity<List<Category>>(categoryRepository.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity updateCategory(Map<String, String> requestMap, String id) {
//        try {
//            if (validateCafeCategoryMap(requestMap, true)) {
//                Optional optional = categoryRepository.findById(String.valueOf(Long.parseLong(id)));
//                if (optional.isPresent()) {
//                    categoryRepository.save(getCategoryFromMap(requestMap, true));
//                    return CafeUtils.getResponseEntity("Category Successfully added", HttpStatus.OK);
        try {
            // Validate the category map
            if (validateCafeCategoryMap(requestMap, false)) {
                // Attempt to find the category by ID
                Optional<Category> optional = categoryRepository.findById(id);
                if (optional.isPresent()) {
                    // Update the category with the provided map data
                    Category updatedCategory = getCategoryFromMap(requestMap, false);
                    // Set the ID of the updated category to the existing category's ID
                    updatedCategory.setId(optional.get().getId());
                    categoryRepository.save(updatedCategory);
                    // Return success response
                    return CafeUtils.getResponseEntity("Category successfully updated", HttpStatus.OK);
                }
            } else {
            }
            return CafeUtils.getResponseEntity("Category id does exist", HttpStatus.BAD_REQUEST);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Map<String, String> requestMap, String id) {
        try {
            // Find the category by ID
            Optional<Category> optional = categoryRepository.findById(id);
            if (!optional.isPresent()) {
                return CafeUtils.getResponseEntity("Category with id " + id + " not found", HttpStatus.NOT_FOUND);
            }

            // Delete the category
            categoryRepository.delete(optional.get());

            // Return success response
            return CafeUtils.getResponseEntity("Category successfully deleted", HttpStatus.OK);
        } catch (Exception ex) {
            // Log the exception for debugging
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}





