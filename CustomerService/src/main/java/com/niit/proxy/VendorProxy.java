/*
 * Author : Mushib Khan
 * Date : 08-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.proxy;

import com.niit.domain.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-vendor-service", url = "localhost:8087")
public interface VendorProxy {
    @PostMapping("/vendor/addVendor")
    public ResponseEntity<?> saveCustomerToVendor(@RequestBody Customer customer);
}
