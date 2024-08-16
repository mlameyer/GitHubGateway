package github.gateway.gateway.repositories;

import github.gateway.gateway.models.UserWithReposDto;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface IGitHubRepository {
    UserWithReposDto GetUserWithRepos(String name) throws Exception;
}
