package org.jmb.domain;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private String nif;
    private String name;
    private String surname;
    private String email;
    private String userName;
    private LocalDate birthDate;

    private User() { }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override public String toString() {
        return "User{" +
            "nif='" + nif + '\'' +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", email='" + email + '\'' +
            ", userName='" + userName + '\'' +
            ", birthDate=" + birthDate +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(nif, user.nif) &&
            Objects.equals(name, user.name) &&
            Objects.equals(surname, user.surname) &&
            Objects.equals(email, user.email) &&
            Objects.equals(userName, user.userName) &&
            Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, name, surname, email, userName, birthDate);
    }

    public static class Builder {

        private String nif;
        private String name;
        private String surname;
        private String email;
        private String userName;
        private LocalDate birthDate;

        public Builder nif(final String nif) {
            this.nif = nif;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder surname(final String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder username(final String userName) {
            this.userName = userName;
            return this;
        }

        public Builder birthDate(final LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public User build() {
            final User user = new User();
            user.birthDate = this.birthDate;
            user.email = this.email;
            user.name = this.name;
            user.nif = this.nif;
            user.surname = this.surname;
            user.userName = this.userName;
            return user;
        }
    }
}
