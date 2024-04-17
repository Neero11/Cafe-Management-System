package com.inn.cafe.category.serviceImpl;
import com.google.common.base.Strings;
import com.inn.cafe.category.Repository.CategoryRepository;
import com.inn.cafe.category.model.Category;
import com.inn.cafe.category.service.CategoryService;
import com.inn.cafe.constents.CafeConstant;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService
{
     @Autowired
     CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (validationCategoryMap(requestMap, false)) ;
            {
                categoryRepository.save(getCategoryFromMap(requestMap, false));
                return CafeUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validationCategoryMap(Map<String,String> requestMap, boolean validateId)
    {
        if(requestMap.containsKey("name"))
        {
            if(requestMap.containsKey("id") && validateId)
            {
                return true;
            }
            else if(!validateId)
            {
                return true;
            }
        }
        return false;
    }
    private Category getCategoryFromMap(Map<String,String> requestMap,Boolean isAdd)
    {
        Category category = new Category();
        if(isAdd)
        {
            category.setId(Long.valueOf(String.valueOf(Long.parseLong(requestMap.get("id")))));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try
        {
            if(Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true"))
            {
                return new ResponseEntity<>(categoryRepository.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryRepository.findAll(),HttpStatus.OK);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap, String id) {
        try
        {

            // validate request map . true to validate id
            if(validationCategoryMap(requestMap, true)) {
                Optional optional = categoryRepository.findById(String.valueOf(Long.valueOf(requestMap.get("id"))));


                if (!optional.isEmpty()) {
                    categoryRepository.save(getCategoryFromMap(requestMap, true));
                    return CafeUtils.getResponseEntity("Category Update Successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
                }

            }
            return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
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




