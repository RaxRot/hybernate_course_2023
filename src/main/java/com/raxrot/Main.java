package com.raxrot;

import com.raxrot.entities.Passport;
import com.raxrot.entities.Person;
import com.raxrot.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        //For persistance
        //EntityManagerFactory emf= Persistence.createEntityManagerFactory("my-persistence-unit");

        Map<String,String> props=new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            em.getTransaction().begin();


            Person person=new Person();
            person.setName("Vlad");

            Passport passport=new Passport();
            passport.setNumber("ABC123");

            person.setPassport(passport);
            passport.setPerson(person);

            em.persist(person);


           em.getTransaction().commit();//insert
       }catch (Exception e) {
           if (em.getTransaction().isActive()) {
               em.getTransaction().rollback();
           }
           e.printStackTrace();
       }finally {
           em.close();
       }
    }
}