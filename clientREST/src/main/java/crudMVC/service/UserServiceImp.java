package crudMVC.service;

import crudMVC.model.User;
import crudMVC.model.UserTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserServiceImp(RestTemplate restTemplate, @Value("${application.server.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @Override
    public void add(UserTo userTo) {
        HttpEntity<UserTo> requestEntity = new HttpEntity<>(userTo);
        restTemplate.exchange(serverUrl + "/admin/users", HttpMethod.POST, requestEntity, UserTo.class);
    }

    @Override
    public User getUserById(long id) {
        return restTemplate.getForObject(serverUrl + "/admin/users/" + id, User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return restTemplate.exchange(serverUrl + "/admin/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    @Override
    public void update(UserTo userTo) {
        HttpEntity<UserTo> requestEntity = new HttpEntity<>(userTo);
        ResponseEntity<UserTo> responseEntity =restTemplate.exchange(serverUrl + "/admin/users", HttpMethod.PUT, requestEntity, UserTo.class);
    }

    @Override
    public void delete(long id) {
//        userDao.deleteById(id);

    }
}
