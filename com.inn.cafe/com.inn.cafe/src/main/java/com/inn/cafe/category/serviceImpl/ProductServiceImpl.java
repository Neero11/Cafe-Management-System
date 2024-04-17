package com.inn.cafe.category.serviceImpl;

import com.inn.cafe.category.Repository.ProductRepository;
import com.inn.cafe.category.model.Category;
import com.inn.cafe.category.model.Product;
import com.inn.cafe.category.service.ProductService;
import com.inn.cafe.constents.CafeConstant;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(validateProductMap(requestMap, false)){
                productRepository.save(getProductFromMap(requestMap, false));
                return CafeUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
    return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Long.valueOf(String.valueOf(Long.parseLong(requestMap.get("categoryId")))));

        Product product = new Product();
        if (isAdd){
            product.setId(Long.valueOf(String.valueOf(Long.parseLong(requestMap.get("id")))));
        }else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Long.valueOf(String.valueOf(Long.parseLong(requestMap.get("price")))));
        return product;
    }
    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return  new ResponseEntity<>(productRepository.getAllProduct(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
