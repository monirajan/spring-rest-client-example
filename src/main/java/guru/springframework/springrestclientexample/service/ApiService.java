package guru.springframework.springrestclientexample.service;

import guru.springframework.api.domain.User;

import java.util.List;

public interface ApiService {
    List<User> getUsers(Integer limit);
}
