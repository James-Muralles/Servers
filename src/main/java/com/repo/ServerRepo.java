package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jm.server.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Object findAll = null;

    Server findByIpAddress(String ipAddress);

}
