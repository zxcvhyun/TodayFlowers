package com.example.todayflowers.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//CRUD에 관련된 함수 사용 가능
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUseremail(String useremail);
    @Query(value= "SELECT max(id) FROM User")
    Integer getMaxId();

    boolean existsByUseremail(String useremail);
}
