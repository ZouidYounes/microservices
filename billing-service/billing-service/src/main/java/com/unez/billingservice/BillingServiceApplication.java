package com.unez.billingservice;

import com.unez.billingservice.entities.Bill;
import com.unez.billingservice.entities.ProductItem;
import com.unez.billingservice.feign.CustomerRestClient;
import com.unez.billingservice.feign.ProductItemRestClient;
import com.unez.billingservice.model.Customer;
import com.unez.billingservice.model.Product;
import com.unez.billingservice.repository.BillRepository;
import com.unez.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            ProductItemRestClient productItemRestClient){
        return args -> {
            Customer customer = customerRestClient.getCustomerById(1L);
            Bill bill1 = billRepository.save(new Bill(null, new Date(),null, customer.getId(), null));
            PagedModel<Product> productPagedModel = productItemRestClient.pageProducts(0,3);
            productPagedModel.forEach(p -> {
                ProductItem productItem = new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+new Random().nextInt(100));
                productItem.setBill(bill1);
                productItem.setProductId(p.getId());
                productItemRepository.save(productItem);
            });
//            System.out.println("---------------------------------");
//            System.out.println(customer.getId());
//            System.out.println(customer.getName());
//            System.out.println(customer.getEmail());
        };
    }

}
