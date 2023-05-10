package com.example.springBootDemo.controller;

import com.example.springBootDemo.service.HostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@Tag(name = "Host Controller")
@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @GetMapping("/")
    public String getHost() throws UnknownHostException {
        return hostService.getHostData();
    }

    @ExceptionHandler(UnknownHostException.class)
    ResponseEntity<Object> handleUnknownHostException(UnknownHostException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }
}
