package com.kocfurkan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kocfurkan.entity.User;

@Repository
@Transactional // arastirilacak
public interface UserRepository extends CrudRepository<User, Long> {

	Long removeByEmail(String email);

	@Query(value = "select * from users  where role_id = :id", nativeQuery = true)
	List<User> getUserListByRole(@Param("id") Long id);

	User findByEmail(String email);

}
