package github.gateway.gateway.repositories.models;

import java.util.Date;

public class User {
    private final String login;
    private final String name;
    private final String avatar_url;
    private final String location;
    private final String email;
    private final String url;
    private final Date created_at;

    public User(String user_name,
                String display_name,
                String avatar,
                String geo_location,
                String email,
                String url,
                Date created_at
    ) {
        this.login = user_name;
        this.name = display_name;
        this.avatar_url = avatar;
        this.location = geo_location;
        this.email = email;
        this.url = url;
        this.created_at = created_at;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getLocation() {
        return location;
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

}
