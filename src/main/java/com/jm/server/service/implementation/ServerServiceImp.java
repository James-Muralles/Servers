package com.jm.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import java.util.Random;
import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    private final ServerRepo serverRepo;

    private static final Logger log = LoggerFactory.getLogger(ServerServiceImp.class);

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);

    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers: {}");

        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server getServer(Long id) {
        log.info("Fetching server by ID {}", id);

        return serverRepo.findById(id).get();
    }

    @Override
    public Server updateServer(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);

    }

    @Override
    public Boolean deleteServer(Long id) {
        log.info("Deleting server by ID: {}", id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;
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
        String[] imageNames = { "server1.Png", "server2.Png", "server3.png", "server4.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }

}
