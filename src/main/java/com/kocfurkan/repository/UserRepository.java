package com.kocfurkan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.kocfurkan.entity.User;

@Repository
@Component
public interface UserRepository extends CrudRepository<User, Long>{
	
}
