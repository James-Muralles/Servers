package com.jm.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.server.enumeration.Status;
import com.jm.server.model.Server;
import com.jm.server.service.ServerService;
import com.repo.ServerRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImp implements ServerService {

    @Autowired
    ServerRepo serverRepo;
    private static final Logger log = LoggerFactory.getLogger(ServerServiceImp.class);

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);

    }

    @Override
    public Collection<Server> list(int limit) {
        return null;
    }

    @Override
    public Server getServer(Long id) {
        return null;
    }

    @Override
    public Server updateServer(Server server) {
        return null;
    }

    @Override
    public Boolean deleteServer(Long id) {
        return null;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    private String setServerImageUrl() {
        return null;
    }

}
