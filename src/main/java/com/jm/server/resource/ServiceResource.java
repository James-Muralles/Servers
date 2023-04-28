package com.jm.server.resource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jm.server.enumeration.Status;
import com.jm.server.model.Response;
import com.jm.server.model.Server;
import com.jm.server.service.implementation.ServerServiceImp;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServiceResource {

        private final ServerServiceImp serverService;

        @GetMapping("/list")
        public ResponseEntity<Response> getServers() {
                return ResponseEntity.ok(
                                Response.builder()
                                                .timeStamp(LocalDateTime.now())
                                                .data(Map.of("servers", serverService.list(30)))
                                                .message("Servers retrieved successfully")
                                                .status(HttpStatus.OK)
                                                .statusCode(HttpStatus.OK.value())
                                                .build());

        }

        @GetMapping("/ping/{ipAddress}")
        public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
                Server server = serverService.ping(ipAddress);
                return ResponseEntity.ok(
                                Response.builder()
                                                .timeStamp(LocalDateTime.now())
                                                .data(Map.of("server", server))
                                                .message(server.getStatus() == Status.SERVER_UP ? "Ping successful"
                                                                : "Ping failed")
                                                .status(HttpStatus.OK)
                                                .statusCode(HttpStatus.OK.value())
                                                .build());

        }

}
