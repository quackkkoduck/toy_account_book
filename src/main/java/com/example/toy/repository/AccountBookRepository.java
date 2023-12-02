package com.example.toy.repository;

// import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.toy.model.AccountBook;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findAllByOrderByTransactionTimeDesc();

    List<AccountBook> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<AccountBook> findAllByDate(LocalDateTime filterDateTime);

    List<AccountBook>findByDateOrderByDate(LocalDateTime date);

    // List<AccountBook> findAllByOrderByTransactionTimeDesc();

    // 다음 메서드는 JPQL 쿼리를 직접 작성하여 사용하고 있습니다.
    @Query("SELECT ab FROM AccountBook ab WHERE YEAR(ab.transactionTime) = :year AND MONTH(ab.transactionTime) = :month ORDER BY ab.transactionTime DESC")
    List<AccountBook> findByYearAndMonth(
        @Param("year") String year,
        @Param("month") String month
    );




    @Query("SELECT ab FROM AccountBook ab WHERE SUBSTRING(ab.date, 1, 7) = :dateSubstring ORDER BY ab.date DESC")
    List<AccountBook> findByDateSubstring(@Param("dateSubstring") String dateSubstring);

}

