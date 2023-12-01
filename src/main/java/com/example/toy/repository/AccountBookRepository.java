package com.example.toy.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.toy.model.AccountBook;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findAllByOrderByTransactionTimeDesc();

}