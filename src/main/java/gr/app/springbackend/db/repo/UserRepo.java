package gr.app.springbackend.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.app.springbackend.db.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>, UserRepoCustom {

}