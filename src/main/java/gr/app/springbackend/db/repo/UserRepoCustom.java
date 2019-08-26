package gr.app.springbackend.db.repo;

import gr.app.springbackend.db.entity.UserEntity;

public interface UserRepoCustom {

    UserEntity getUser(String username, String password);
    UserEntity getUserByUsername(String username);
}