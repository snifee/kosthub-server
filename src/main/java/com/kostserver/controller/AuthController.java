package com.kostserver.controller;


import com.kostserver.dto.LoginRequestDto;
import com.kostserver.dto.RegisterRequestDto;
import com.kostserver.model.EnumRole;
import com.kostserver.service.LoginService;
import com.kostserver.service.OtpService;
import com.kostserver.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/penyedia/register")
    ResponseEntity<Map> registerPenyedia(@RequestBody RegisterRequestDto request){
        Map response = registerService.register(request, EnumRole.ROLE_USER_PENYEDIA);
        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }

    @PostMapping("/penyewa/register")
    ResponseEntity<Map> registerPenyewa(@RequestBody RegisterRequestDto request){
        Map response = registerService.register(request, EnumRole.ROLE_USER_PENYEWA);
        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }

    @PostMapping("/login")
    ResponseEntity<Map> login(@RequestBody LoginRequestDto request){
        Map response = loginService.login(request);
        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }


    @PostMapping("/confirm")
    ResponseEntity<Map> confirmEmail(@RequestParam String otp){
        Map<String, Object > response = new HashMap<>();

        try{
            otpService.confirmOtp(otp);
            response.put("status",HttpStatus.OK);
            response.put("message","Account activated");
        }catch (Exception e){
            response.put("status",HttpStatus.BAD_REQUEST);
            response.put("message",e.getMessage());
        }

        return new ResponseEntity<Map>(response, (HttpStatus) response.get("status"));
    }
}