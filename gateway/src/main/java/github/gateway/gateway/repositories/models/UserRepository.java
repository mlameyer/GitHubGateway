package github.gateway.gateway.repositories.models;

public class UserRepository {
    private final String name;
    private final String url;

    public UserRepository(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
