package github.gateway.gateway.repositories.mappers;

import github.gateway.gateway.models.UserWithReposDto;
import github.gateway.gateway.repositories.models.User;
import github.gateway.gateway.repositories.models.UserRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserWithReposMapper implements IUserWithReposMapper {
    @Override
    public UserWithReposDto MapGithubUserAndReposToUserWithReposDto(User user, List<UserRepository> userRepos) {

        List<UserWithReposDto.Repo> repos = new ArrayList<>();
        for(UserRepository userRepo : userRepos) {
            repos.add(new UserWithReposDto.Repo(
                    userRepo.getName(),
                    userRepo.getUrl()
            ));
        }

        UserWithReposDto userWithReposDto = new UserWithReposDto(
                user.getLogin(),
                user.getName(),
                user.getAvatar_url(),
                user.getLocation(),
                user.getEmail(),
                user.getUrl(),
                user.getCreated_at(),
                repos
        );

        return userWithReposDto;
    }
}
