/*
 * Author : Mushib Khan
 * Date : 30-03-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.repository;

import com.niit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // @Repository used to access the information in DataBase
}
