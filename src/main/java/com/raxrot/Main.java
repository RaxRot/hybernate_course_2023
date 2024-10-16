package com.raxrot;

import com.raxrot.entities.Employee;
import com.raxrot.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        //EntityManagerFactory emf= Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

           Employee e1= em.find(Employee.class, 1);
            em.remove(e1);
            Employee e2=new Employee();
            e2.setId(1);
            e2.setName("Li");
            e2.setAddress("Chona");

           em.persist(e2);//register object

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