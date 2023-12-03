package ru.clevertec.course.session.api.model;

import java.util.Objects;

public class DefaultLoginProvider implements LoginProvider {
    private String login;

    public DefaultLoginProvider(String login) {
        this.login = login;
    }


    public DefaultLoginProvider setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultLoginProvider that)) return false;

        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
