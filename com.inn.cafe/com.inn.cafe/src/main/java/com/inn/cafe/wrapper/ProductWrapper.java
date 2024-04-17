package com.inn.cafe.wrapper;

import lombok.Data;

@Data
public class ProductWrapper {
        private Long id;
        private String name;
        private String description;
        private Long price;
        private String status;
       private Long categoryId;
       private String categoryName;

        // Other constructors (if needed)
    public ProductWrapper(){

    }

        public ProductWrapper(Long id, String name, String description, Long price, String status, Long categoryId, String categoryName) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.status = status;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
        }
    }

