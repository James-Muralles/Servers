package com.jm.server.service;

import java.io.IOException;
import java.util.Collection;

import com.jm.server.model.Server;

public interface ServerService {

    Server create(Server server);

    Server ping(String ipAddress) throws IOException;

    Collection<Server> list(int limit);

    Server getServer(Long id);

    Server updateServer(Server server);

    Boolean deleteServer(Long id);

}
