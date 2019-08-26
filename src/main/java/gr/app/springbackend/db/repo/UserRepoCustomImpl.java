package gr.app.springbackend.db.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;

import gr.app.springbackend.db.entity.UserEntity;

@Repository
@Transactional(readOnly = true)
public class UserRepoCustomImpl implements UserRepoCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public UserEntity getUser(String username, String password) {

        Query query = entityManager.createNativeQuery("SELECT * FROM public.user WHERE username = ?1 AND password = ?2",
                UserEntity.class);

        query.setParameter(1, username);
        query.setParameter(2, password);

        return (UserEntity) query.getSingleResult();
    }

    @Override
    public UserEntity getUserByUsername(String username){
        Query query = entityManager.createNativeQuery("SELECT * FROM public.user WHERE username = ?1",
                UserEntity.class);

        query.setParameter(1, username);

        return (UserEntity) query.getSingleResult();
    }


}
