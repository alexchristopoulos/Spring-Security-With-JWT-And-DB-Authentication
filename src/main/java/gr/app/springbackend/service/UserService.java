package gr.app.springbackend.service;

import gr.app.springbackend.bean.AuthenticationBean;

public interface UserService {
    
    public AuthenticationBean authenticateUser(String username, String password) throws Exception;
}