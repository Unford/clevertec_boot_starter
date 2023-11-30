package ru.clevertec.course.session.api.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class DefaultSessionDetails implements SessionDetails {
    private String id;
    private String login;
    private LocalDateTime createDateTime;

    public DefaultSessionDetails() {
    }

    public DefaultSessionDetails(String id, String login) {
        this.id = id;
        this.login = login;
        this.createDateTime = LocalDateTime.now();
    }

    public DefaultSessionDetails(String id, String login, LocalDateTime createDateTime) {
        this.id = id;
        this.login = login;
        this.createDateTime = createDateTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public DefaultSessionDetails setId(String id) {
        this.id = id;
        return this;
    }

    public DefaultSessionDetails setLogin(String login) {
        this.login = login;
        return this;
    }

    public DefaultSessionDetails setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultSessionDetails that)) return false;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(login, that.login)) return false;
        return Objects.equals(createDateTime, that.createDateTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (createDateTime != null ? createDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultSessionDetails{");
        sb.append("id='").append(id).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", createDateTime=").append(createDateTime);
        sb.append('}');
        return sb.toString();
    }
}
