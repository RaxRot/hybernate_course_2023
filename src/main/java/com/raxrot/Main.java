package com.raxrot;

import com.raxrot.entities.Product;
import com.raxrot.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        //EntityManagerFactory emf= Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em;
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>())) {
            em = emf.createEntityManager();
        }

        try {
           em.getTransaction().begin();

           Product p = new Product();
           p.setId(2L);
           p.setName("Chocolate");
           em.persist(p);//register object

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