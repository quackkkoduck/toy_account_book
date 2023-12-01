package com.example.toy.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;

import com.example.toy.model.AccountBook;
import com.example.toy.repository.AccountBookRepository;

@Controller
@RequestMapping("/accountbook")
public class AccountBookController {

    @Autowired
    private AccountBookRepository accountBookRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("transactions", accountBookRepository.findAll());
        return "accountbook"; // HTML 파일의 이름을 "accountbook"으로 변경
    }

    @PostMapping("/detail")
    public String transaction(@ModelAttribute AccountBook accountBook, Model model) {
    System.out.println(accountBook);

    // 출금 또는 입금 여부 확인
    if ("출금".equals(accountBook.getTransactionType())) {
        System.out.println("출금");
    } else if ("입금".equals(accountBook.getTransactionType())) {
        System.out.println("입금");
    }

    // 입력한 금액 확인
    System.out.println("입력한 금액: " + accountBook.getBalance());

    List<AccountBook> nowBalance = accountBookRepository.findAllByOrderByTransactionTimeDesc();
    // System.out.println("@@@@@@" + nowBalance.get(0).getBalance());

    AccountBook transaction = new AccountBook();

    int recentMoney;
    
    if("출금".equals(accountBook.getTransactionType())){
        recentMoney = Integer.parseInt(accountBook.getBalance());
    }else{
        recentMoney = Integer.parseInt(accountBook.getBalance());
    }

    int nowBalanceInt;
    if ("출금".equals(accountBook.getTransactionType())) {
        nowBalanceInt = Integer.parseInt(nowBalance.get(0).getBalance()) - Integer.parseInt(accountBook.getBalance());
        if (nowBalanceInt < 0) {
            model.addAttribute("error", "현재 잔액을 초과했습니다.");
            return "redirect:/errorPage";
        }
    } else {
        nowBalanceInt = Integer.parseInt(nowBalance.get(0).getBalance()) + Integer.parseInt(accountBook.getBalance());
    }

    // 남은 돈이 없으면 에러 페이지로 리다이렉트
    // if ("출금".equals(accountBook.getTransactionType()) && nowBalanceInt < 0) {
    //     model.addAttribute("error", "남은 돈이 없습니다.");
    //     return "redirect:/errorPage";
    // }

    // transaction 객체에 정보 설정
    transaction.setRecentmoney(String.valueOf(recentMoney));
    transaction.setDate(accountBook.getDate());
    transaction.setBalance(String.valueOf(nowBalanceInt));
    transaction.setTitle(accountBook.getTitle());
    transaction.setTransactionType(accountBook.getTransactionType());

    // 포맷터를 사용하여 LocalDateTime을 원하는 형식의 문자열로 변환
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedTime = LocalDateTime.now().format(formatter);

    // 문자열을 LocalDateTime으로 변환하여 저장
    transaction.setTransactionTime(LocalDateTime.parse(formattedTime, formatter));

    // 추가: 입력 타입과 금액 출력
    System.out.println("입력 타입: " + accountBook.getTransactionType());
    System.out.println("입력한 금액: " + accountBook.getBalance());

    // 저장
    accountBookRepository.save(transaction);

    // 데이터베이스에 저장된 내역을 다시 가져와서 Model에 추가
    model.addAttribute("transactions", accountBookRepository.findAll());
    return "redirect:/accountbook";
}

}
