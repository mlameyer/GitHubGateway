package github.gateway.gateway.repositories;

import github.gateway.gateway.models.UserWithReposDto;
import github.gateway.gateway.repositories.mappers.UserWithReposMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GitHubRepositoryTest {
    @Mock
    UserWithReposMapper mockUserWithReposMapper;


    @InjectMocks
    GitHubRepository underTest;

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

        when(mockUserWithReposMapper.MapGithubUserAndReposToUserWithReposDto(any(), any())).thenReturn(expected.get());

        underTest.GetUserWithRepos("test");

        String userName = "test";
        UserWithReposDto actual = underTest.GetUserWithRepos(userName);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.get(), actual),
                () -> assertEquals(userWithReposDto.getUser_name(), actual.getUser_name()),
                () -> assertNotNull(actual.getRepos())
        );
    }

    @Test
    void get_shouldThrowExceptionNotFound() throws Exception {
        try{
            underTest.GetUserWithRepos("rrewsdfsdf");
        } catch (Exception e) {
            assertAll(
                    () -> assertNotNull(e),
                    () -> assertTrue(e.getMessage().contains("Not Found"))
            );
        }
    }
}