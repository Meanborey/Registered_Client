package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.service.AuthGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class CreateClientRestController {

    @Autowired
    private AuthGeneratorService authenticateAndGenerateClient;

    @PostMapping("/login")
    public Client login(@RequestParam String username, @RequestParam String password) throws Exception {
        return authenticateAndGenerateClient.authenticateAndGenerateClient(username, password);
    }
}
