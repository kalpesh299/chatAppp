package com.example.chatAppp.dao;

import com.example.chatAppp.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface userRepository extends JpaRepository<Users,Integer> {

    @Query(value="Select * from tbl_user where username= :username and status_id=1",nativeQuery = true)
    public List<Users> findByUserName(String username);

    @Query(value="Select * from tbl_user where user_id= :userId and status_id=1",nativeQuery = true)
    public List <Users>getUserbyUserId(int userId);

    @Query(value = "Select * from tbl_user where status_id=1",nativeQuery = true)
    public List<Users>getAllUsers();

    @Modifying
    @Transactional
    @Query(value="update tble_user set status_id=2 where user_id=:userId",countQuery = "SELECT count(*)FROM tbl_user",nativeQuery = true)
    public void deleteUserById(int userId);
//    public void deleteUserById(@Param("userId") int userId);


}
