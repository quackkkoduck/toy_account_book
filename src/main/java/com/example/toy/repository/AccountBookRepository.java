package com.example.toy.repository;

// import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.toy.model.AccountBook;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findAllByOrderByTransactionTimeDesc();

    List<AccountBook> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<AccountBook> findAllByDate(LocalDateTime filterDateTime);

    @Query("SELECT ab FROM AccountBook ab WHERE ab.transactionTime BETWEEN :startDate AND :endDate ORDER BY ab.transactionTime DESC")
    List<AccountBook> findByDateBetweenOrderByTransactionTimeDesc(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

}
