package com.raxrot;

import com.raxrot.entities.Student;
import com.raxrot.entities.keys.StudentKey;
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

            StudentKey studentKey = new StudentKey();
            studentKey.setCode("ABC");
            studentKey.setNumber(10);
            Student student = new Student();
            student.setId(studentKey);
            student.setName("Vlad");

            em.persist(student);

            Student st=em.find(Student.class, studentKey);
            System.out.println(st);

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