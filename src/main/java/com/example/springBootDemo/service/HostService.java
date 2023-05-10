package com.example.springBootDemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Slf4j
public class HostService {

    public String getHostData() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();
        String hostAddress = inetAddress.getHostAddress();
        log.debug("Hostname={} host address={}", hostName, hostAddress);
        return "Hostname=" + hostName + " host address=" + hostAddress;
    }
}
