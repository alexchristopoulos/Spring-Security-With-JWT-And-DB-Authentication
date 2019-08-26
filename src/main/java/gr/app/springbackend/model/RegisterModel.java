package gr.app.springbackend.model;

import java.util.ArrayList;
import java.util.List;

import gr.app.springbackend.db.entity.UserEntity;

public class RegisterModel {

    private String username;
    private String name;
    private String surname;
    private String password;
    private List<String> authorities = new ArrayList<String>();

    public RegisterModel() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorities(List<String> authorities){
        this.authorities = authorities;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return this.password;
    }

    public List<String> getAuthorities(){
        return this.authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterModel(String username, String name, String surname, String password, List<String> authorities) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public UserEntity getUserEntity(RegisterModel registerModel) {

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(registerModel.getUsername());
        userEntity.setPassword(registerModel.getPassword());
        userEntity.setName(registerModel.getName());
        userEntity.setSurname(registerModel.getSurname());
        userEntity.setAuthorities(registerModel.getAuthorities());

        return userEntity;
    }

    /*
     * private void validateEntry(String value, int minLen, int maxLen){
     * 
     * }
     */

}