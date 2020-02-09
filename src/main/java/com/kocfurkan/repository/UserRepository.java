package com.kocfurkan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kocfurkan.entity.User;

@Repository
@Transactional		// arastirilacak
public interface UserRepository extends CrudRepository<User, Long>{
	
	void removeByEmail(String email);
	
}
