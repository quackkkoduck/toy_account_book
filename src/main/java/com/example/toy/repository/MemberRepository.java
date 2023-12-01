package com.example.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.toy.model.Member;



@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
    Member findByEmail(String email);
    Member findById(String id);
    Member findByPhone(String phone);
    Member findByPw(String Pw);
    Member findByBirth(String birth);
    Member findByName(String name);

}
