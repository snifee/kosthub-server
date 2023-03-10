package com.kostserver.controller;

import com.kostserver.dto.request.BookingDto;
import com.kostserver.dto.request.TransactionPayDto;
import com.kostserver.dto.request.UpdateTransactionStatusDto;
import com.kostserver.model.response.Response;
import com.kostserver.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/booking")
    ResponseEntity<Response> booking(@Valid @RequestBody BookingDto request){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Response response = new Response();
            response.setStatus(HttpStatus.CREATED.value());
            response.setData(transactionService.booking(request,email));
            response.setMessage("booking success");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("booking failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    ResponseEntity<Response> accountTransaction(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(transactionService.getUserTransactions(email));
            response.setMessage("success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/owner")
    ResponseEntity<Response> ownerTransaction(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(transactionService.getOwnerTransactions(email));
            response.setMessage("success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/owner")
    ResponseEntity<Response> updateTransactionStatus(@Valid @RequestBody UpdateTransactionStatusDto request){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(transactionService.updateOwnerTransactionsStatus(email,request));
            response.setMessage("success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history/{id}")
    ResponseEntity<Response> getTransactionById(@PathVariable("id") Long id){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(transactionService.getTransactionById(email,id));
            response.setMessage("success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/payment")
    ResponseEntity<Response> transactionPay(@Valid @ModelAttribute TransactionPayDto request){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Response response = new Response();
            response.setStatus(HttpStatus.OK.value());
            response.setData(transactionService.transactionPay(email,request));
            response.setMessage("success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Response response = new Response();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("failed");
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
