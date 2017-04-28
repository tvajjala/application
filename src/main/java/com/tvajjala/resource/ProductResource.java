package com.tvajjala.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tvajjala.persistence.model.Product;
import com.tvajjala.service.ProductService;

@RestController
@RequestMapping( path = "/products" )
public class ProductResource {

    private static Logger LOG = LoggerFactory.getLogger( ProductResource.class );

    @Autowired
    ProductService productService;

    @RequestMapping( method = RequestMethod.GET )
    public List<Product> listProducts() {
        LOG.info( "Returning all the products" );

        return productService.getAllProducts();
    }

}
