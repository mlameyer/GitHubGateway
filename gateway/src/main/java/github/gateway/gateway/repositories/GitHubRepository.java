package github.gateway.gateway.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import github.gateway.gateway.models.UserWithReposDto;
import github.gateway.gateway.repositories.mappers.IUserWithReposMapper;
import github.gateway.gateway.repositories.models.User;
import github.gateway.gateway.repositories.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Repository
public class GitHubRepository implements IGitHubRepository {
    private IUserWithReposMapper mapper;

    @Autowired
    public GitHubRepository(IUserWithReposMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserWithReposDto GetUserWithRepos(String name) throws Exception {
        User user = getUser(name);

        List<UserRepository> repos = getUserRepositories(name);

        return mapper.MapGithubUserAndReposToUserWithReposDto(user, repos);
    }

    private static List<UserRepository> getUserRepositories(String name) throws Exception {
        Gson gson = new Gson();
        HttpResponse<String> responseRepo;
        HttpClient clientRepo = HttpClient.newBuilder().build();
        HttpRequest requestRepo = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + name + "/repos"))
                .build();

        responseRepo = clientRepo.send(requestRepo, HttpResponse.BodyHandlers.ofString());
        if(responseRepo.statusCode() != 200){
            throw new Exception(responseRepo.body());
        }

        TypeToken<List<UserRepository>> collectionType = new TypeToken<>() {};
        List<UserRepository> repos = gson.fromJson(responseRepo.body(), collectionType);
        return repos;
    }

    private static User getUser(String name) throws Exception {
        Gson gson = new Gson();
        HttpResponse<String> responseUser;
        HttpRequest requestUser = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + name))
                .build();

        HttpClient clientUser = HttpClient.newBuilder().build();

        responseUser = clientUser.send(requestUser, HttpResponse.BodyHandlers.ofString());
        if(responseUser.statusCode() != 200){
            throw new Exception(responseUser.body());
        }

        User user = gson.fromJson(responseUser.body(), User.class);
        return user;
    }
}
