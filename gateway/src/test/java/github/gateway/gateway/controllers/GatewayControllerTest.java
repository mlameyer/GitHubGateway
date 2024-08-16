package github.gateway.gateway.controllers;

import github.gateway.gateway.models.UserWithReposDto;
import github.gateway.gateway.repositories.GitHubRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GatewayControllerTest {
    @Mock
    GitHubRepository mockRepositoryService;

    @InjectMocks
    GatewayController underTest;

    @Test
    void get_shouldGetSuccessfully() throws Exception {
        Date currentDate = new Date();

        List<UserWithReposDto.Repo> repos = new ArrayList<>();
        repos.add(new UserWithReposDto.Repo(
                "SixDemonBag.api",
                "https://github.com/jburton/SixDemonBag"
        ));

        UserWithReposDto userWithReposDto = new UserWithReposDto(
                "jburton",
                "Jack Burton",
                "https://test.url",
                "San Francisco",
                "j.burton@test.test",
                "https://test.url",
                currentDate,
                repos
        );

        Optional<UserWithReposDto> expected = Optional.of(userWithReposDto);

        when(mockRepositoryService.GetUserWithRepos(any())).thenReturn(expected.get());

        String userName = "test";
        ResponseEntity<UserWithReposDto> response = underTest.getUser(userName);
        UserWithReposDto actual = response.getBody();

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(expected.get(), actual),
                () -> assertEquals(userWithReposDto.getUser_name(), actual.getUser_name()),
                () -> assertNotNull(actual.getRepos())
        );

    }

    @Test
    void get_shouldReturnInternalServerError() throws Exception {

        Mockito.doThrow(IOException.class).when(mockRepositoryService).GetUserWithRepos(any());
        ResponseEntity<UserWithReposDto> response = underTest.getUser("test");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void get_shouldReturnNotFoundError() throws Exception {
        Exception exception = new Exception("Not Found");
        Mockito.doThrow(exception).when(mockRepositoryService).GetUserWithRepos(any());
        ResponseEntity<UserWithReposDto> response = underTest.getUser("test");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}