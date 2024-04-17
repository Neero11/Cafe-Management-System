package com.inn.cafe.category.rest;

import com.inn.cafe.category.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {
    @PostMapping(path = "/add")
    @PreAuthorize("hasAnyRole('admin')")
    ResponseEntity<String > addNewCategory(@RequestBody(required = true)
                                           Map<String , String > requestMap);
    @GetMapping(path = "/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false)String filterValue);
    @PutMapping(path = "/update/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    ResponseEntity<String > updateCategory(@RequestBody(required = true)
                                           Map<String , String > requestMap, @PathVariable String  id);
    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    ResponseEntity<String > deleteCategory(@RequestBody (required = true)
                                           Map<String , String> requestMap, @PathVariable String id);


}
