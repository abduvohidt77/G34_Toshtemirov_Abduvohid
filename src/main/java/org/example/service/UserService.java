package org.example.service;

import org.example.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface UserService {
    List<User> USERS = new ArrayList<>();

    User register(User user);
    User login(String username, String password);
    String confirm(String toEmail);
    String changePassword(String username, String password);
    void getInformationAboutDate(int month, int day) throws IOException, InterruptedException;
}
