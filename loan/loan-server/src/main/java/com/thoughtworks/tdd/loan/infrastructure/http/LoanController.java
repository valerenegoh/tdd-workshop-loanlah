package com.thoughtworks.tdd.loan.infrastructure.http;

import com.thoughtworks.tdd.loan.domain.Loan;
import com.thoughtworks.tdd.loan.domain.LoanRepository;
import com.thoughtworks.tdd.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/loans")
public class LoanController {

  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private LoanService loanService;

  @PostMapping
  public ResponseEntity<?> createNew(@PathVariable("accountId") String accountId,
                                     @Valid @RequestBody NewLoanCommand newLoanCommand) {
    try {
      var loan = loanService.createLoan(accountId, LocalDate.now(), newLoanCommand);
      var status = new LoanStatus("ok", format("/api/v1/accounts/%s/loans/%s", accountId, loan.getId()));
      return accepted().body(status);
    } catch (IllegalArgumentException e){
      return badRequest().body(Map.of("status", "not ok", "msg", e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<Loan>> getAll(@PathVariable("accountId") String accountId) {
    var loans = loanRepository.findAllByAccount(accountId);
    return ok(loans);
  }

  @GetMapping
  @RequestMapping("/{loanId}")
  public ResponseEntity<Loan> getLoan(@PathVariable("accountId") String accountId,
                                      @PathVariable("loanId") Long loanId) {
    var optionalLoan = loanRepository.findByIdAndAccount(loanId, accountId);
    return optionalLoan.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}
