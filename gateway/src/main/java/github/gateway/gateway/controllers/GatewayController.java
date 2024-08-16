package github.gateway.gateway.controllers;

import github.gateway.gateway.models.UserWithReposDto;
import github.gateway.gateway.repositories.IGitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GatewayController {
    private IGitHubRepository repo;

    @Autowired
    public GatewayController(IGitHubRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/users/{user}")
    public ResponseEntity<UserWithReposDto> getUser(@PathVariable String user) throws IOException, InterruptedException {
        UserWithReposDto response;
        try {
            response = repo.GetUserWithRepos(user);
        } catch (Exception e) {
            String message = e.getMessage();
            if(message != null && message.contains("Not Found")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
