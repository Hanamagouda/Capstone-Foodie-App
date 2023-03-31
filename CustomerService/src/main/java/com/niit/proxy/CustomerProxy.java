/*
 * Author : Mushib Khan
 * Date : 31-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.proxy;

import com.niit.domain.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-authentication-service", url = "http://localhost:8083")
public interface CustomerProxy {

    @PostMapping("/userAuth/register")
    public ResponseEntity<?> saveCustomerToAuthentication(@RequestBody Customer customer);
}
