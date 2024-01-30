package org.example.service.impl;

import org.example.domain.User;
import org.example.service.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

public class UserServiceImpl implements UserService {
    static {
        USERS.add(new User("Abduvohid", "blahblahblah@mail.com", 16, "abu", "A001"));
    }
    static UserServiceImpl userService;
    public static UserServiceImpl getInstance() {
        if(Objects.isNull(userService)) {
            userService = new UserServiceImpl();
            return userService;
        }
        return userService;
    }
    @Override
    public User register(User user) {
        if (isExistUser(user)) {
            System.out.println("This user is already exist!! " + '\n' +
                    "Login or try again to register");
        }
        USERS.add(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        for (User u : USERS) {
            if(u.getUserName().equals(username)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public String confirm(String toEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        String fromEmail = "toshtemirovabduvohid77@gmail.com";
        String password = "vgxrppybwluywvnk";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        String code = "";

        try {
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("This is confirmation Code !!!");
            Random random = new Random();
            code = "" + random.nextInt(100000, 999999);
            message.setText(code);

            Transport.send(message);
        }catch (Exception e) {
            System.out.println("Something went wrong !!");
        }
        return code;
    }

    @Override
    public String changePassword(String username, String password) {
        for (User user : USERS) {
            if (user.getUserName().equals(username)) {
                user.setPassword(password);
                return "Success !";
            }
        }
        return "Something is wrong !";
    }

    @Override
    public void getInformationAboutDate(int month, int day) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://numbersapi.p.rapidapi.com/%s/%s/date?fragment=true&json=true"
                        .formatted(month, day)))
                .header("X-RapidAPI-Key", "4a6f220ce6msh8cb3764d70c661bp1a8cafjsn845356306585")
                .header("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
    private static boolean isExistUser(User user) {
        for (User u : USERS) {
            if(user.getUserName().equals(u.getUserName())
                    || user.getEMail().equals(u.getEMail())) {
                return true;
            }
        }
        return false;
    }
}
