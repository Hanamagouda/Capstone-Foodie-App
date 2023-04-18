package com.niit.proxy;

import com.niit.domain.Vendor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-authentication-service", url = "http://localhost:8083")
public interface UserAuthProxy {
    @PostMapping("/userAuth/register")
    public ResponseEntity<?> saveVendorToAuthentication(@RequestBody Vendor vendor);
}
