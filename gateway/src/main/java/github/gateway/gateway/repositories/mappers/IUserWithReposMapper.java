package github.gateway.gateway.repositories.mappers;

import github.gateway.gateway.models.UserWithReposDto;
import github.gateway.gateway.repositories.models.User;
import github.gateway.gateway.repositories.models.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IUserWithReposMapper {
    UserWithReposDto MapGithubUserAndReposToUserWithReposDto(User user, List<UserRepository> repos);
}
