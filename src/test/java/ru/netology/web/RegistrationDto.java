package ru.netology.web;

public class RegistrationDto {
        private String login;
        private String password;
        private String status;

        public RegistrationDto(String login, String password, String status) {
                this.login = login;
                this.password = password;
                this.status = status;
        }
}
