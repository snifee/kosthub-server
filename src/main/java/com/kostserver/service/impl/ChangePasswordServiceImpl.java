package com.kostserver.service.impl;

import com.kostserver.dto.request.ChangePasswordDto;
import com.kostserver.model.entity.Account;
import com.kostserver.repository.AccountRepository;
import com.kostserver.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String changePassword(@Valid @RequestBody ChangePasswordDto request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepository.findByEmail(email);

        if (!passwordEncoder.matches(request.getPassword(), account.get().getPassword())){
            throw new IllegalStateException("password wrong");
        }

        String newPasswordEncoded = passwordEncoder.encode(request.getNew_password());

        account.get().setPassword(newPasswordEncoded);

        accountRepository.save(account.get());

        return "Password changed";
    }
}
