package com.unez.billingservice.web;

import com.unez.billingservice.entities.Bill;
import com.unez.billingservice.feign.CustomerRestClient;
import com.unez.billingservice.feign.ProductItemRestClient;
import com.unez.billingservice.model.Customer;
import com.unez.billingservice.model.Product;
import com.unez.billingservice.repository.BillRepository;
import com.unez.billingservice.repository.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi-> {
            Product product = productItemRestClient.getProductById(pi.getProductId());
            pi.setProduct(product);
        });
        return bill;
    }
}
