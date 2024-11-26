package com.nexign.service;

import com.nexign.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateService {
    private static final Configuration CONFIGURATION = new Configuration()
            .addAnnotatedClass(UserEntity.class);

    private static final Session currentSession = CONFIGURATION
            .buildSessionFactory()
            .getCurrentSession();

    public UserEntity getUser(Long id) {
        currentSession.beginTransaction();

        UserEntity userEntity = currentSession.get(UserEntity.class, id);

        currentSession.getTransaction().commit();
        return userEntity;
    }


    public void save() {
        currentSession.beginTransaction();
        UserEntity user1 = new UserEntity("Misha");
        UserEntity user2 = new UserEntity("Serezha");
        currentSession.persist(user1);
        currentSession.persist(user2);
        currentSession.getTransaction().commit();
    }


    public void getSpecialUser() {
        currentSession.beginTransaction();
        Query<UserEntity> loginPrefix = currentSession.createQuery("FROM UserEntity WHERE login LIKE :loginPrefix", UserEntity.class)
                .setParameter("loginPrefix", "A%");
        System.out.println(loginPrefix.stream().findFirst());
        currentSession.getTransaction().commit();
    }
}
