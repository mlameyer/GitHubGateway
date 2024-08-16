package github.gateway.gateway.models;

import java.util.Date;
import java.util.List;

public class UserWithReposDto {
    private final String user_name;
    private final String display_name;
    private final String avatar;
    private final String geo_location;
    private final String email;
    private final String url;
    private final Date created_at;
    private final List<Repo> repos;

    public UserWithReposDto(String user_name,
                            String display_name,
                            String avatar,
                            String geo_location,
                            String email,
                            String url,
                            Date created_at,
                            List<Repo> repos
    ) {
        this.user_name = user_name;
        this.display_name = display_name;
        this.avatar = avatar;
        this.geo_location = geo_location;
        this.email = email;
        this.url = url;
        this.created_at = created_at;
        this.repos = repos;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getGeo_location() {
        return geo_location;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public List<Repo> getRepos() {
        return repos;
    }

    public static class Repo {
        private String name;
        private String url;

        public Repo(String name, String url) {
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
}
