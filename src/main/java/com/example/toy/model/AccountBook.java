package com.example.toy.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transaction;

import lombok.Data;

@Data
// AccountBook.java
@Entity
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
            // String deposit;
            // String withdraw;
            String recentmoney;
            String balance; // 잔액
            String title; //사유
            // String choicePositionTypeSelect; //예금,입금
            String date;
    private String transactionType; // 문자열로 거래 유형 처리

    private LocalDateTime transactionTime;

}