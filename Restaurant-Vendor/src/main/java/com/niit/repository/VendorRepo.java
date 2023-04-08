package com.niit.repository;

import com.niit.domain.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepo extends MongoRepository<Vendor, String> {
}
