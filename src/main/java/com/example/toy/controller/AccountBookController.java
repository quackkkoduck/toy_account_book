package com.example.toy.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    // @GetMapping
    // public String index(Model model) {
    // model.addAttribute("transactions", accountBookRepository.findAll());

    // return "accountbook"; // HTML 파일의 이름을 "accountbook"으로 변경
    // }
    @GetMapping
    public String index(Model model) {
        List<AccountBook> transactions = accountBookRepository.findAll();
        model.addAttribute("transactions", transactions);
        return "accountbook";
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

        if ("출금".equals(accountBook.getTransactionType())) {
            recentMoney = Integer.parseInt(accountBook.getBalance());
        } else {
            recentMoney = Integer.parseInt(accountBook.getBalance());
        }

        int nowBalanceInt;
        if ("출금".equals(accountBook.getTransactionType())) {
            nowBalanceInt = Integer.parseInt(nowBalance.get(0).getBalance())
                    - Integer.parseInt(accountBook.getBalance());
            if (nowBalanceInt < 0) {
                model.addAttribute("error", "현재 잔액을 초과했습니다.");
                return "redirect:/errorPage";
            }
        } else {
            nowBalanceInt = Integer.parseInt(nowBalance.get(0).getBalance())
                    + Integer.parseInt(accountBook.getBalance());
        }

        // 남은 돈이 없으면 에러 페이지로 리다이렉트
        // if ("출금".equals(accountBook.getTransactionType()) && nowBalanceInt < 0) {
        // model.addAttribute("error", "남은 돈이 없습니다.");
        // return "redirect:/errorPage";
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

    @GetMapping("/filtered")
public String filteredTransactions(
        @RequestParam(name = "yearField") String year,
        @RequestParam(name = "monthField") String month,
        Model model) {
    // year와 month를 이용하여 해당 연도와 월에 해당하는 데이터를 조회
    //List<AccountBook> filteredTransactions = accountBookRepository.findByYearAndMonth(year, month);
String day = year+"-"+month;
   System.out.println(day);
List<AccountBook> adf = accountBookRepository.findByDateSubstring(day);
        System.out.println(adf);
    // Model에 데이터 추가
   model.addAttribute("transactions", adf);

    // 원하는 뷰로 리턴
    return "accountbook";
}

@GetMapping("/all")
public String showAllTransactions(Model model) {
    List<AccountBook> allTransactions = accountBookRepository.findAll();
    model.addAttribute("transactions", allTransactions);
    return "accountbook";
}

@GetMapping("/accountbook/edit/{id}")
public String showEditTransactionForm(@PathVariable Long id, Model model) {
    System.out.println("showEditTransactionForm method called with id: " + id);
    AccountBook transaction = accountBookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + id));

    model.addAttribute("transaction", transaction);
    return "edit";
}

@PostMapping("/accountbook/edit/{id}")
public String editTransaction(@PathVariable Long id, @ModelAttribute AccountBook updatedTransaction) {
    System.out.println("editTransaction method called with id: " + id);
    AccountBook transaction = accountBookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + id));

    transaction.setDate(updatedTransaction.getDate());
    transaction.setTitle(updatedTransaction.getTitle());
    transaction.setBalance(updatedTransaction.getBalance());
    transaction.setTransactionType(updatedTransaction.getTransactionType());

    accountBookRepository.save(transaction);

    return "redirect:/accountbook";
}

    @GetMapping("/accountbook/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        accountBookRepository.deleteById(id);
        return "redirect:/accountbook";
    }

    
//         System.out.println(
//             "@@@@@@@@@"
//         );

//     return null;
// }


}
