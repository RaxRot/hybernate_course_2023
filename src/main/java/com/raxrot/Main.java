package com.raxrot;

import com.raxrot.entities.Comment;
import com.raxrot.entities.Post;
import com.raxrot.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            // Create a post
            Post post = new Post();
            post.setTitle("My First Post");
            post.setContent("This is the content of my first post");

            // Create comments for the post
            Comment comment1 = new Comment();
            comment1.setContent("Great post!");

            Comment comment2 = new Comment();
            comment2.setContent("Thanks for sharing!");

            // Link comments to the post
            List<Comment> comments = new ArrayList<>();
            comments.add(comment1);
            comments.add(comment2);
            post.setComments(comments);

            // Set post for each comment
            comment1.setPost(post);
            comment2.setPost(post);

            // Persist the post (comments will be persisted because of cascade)
            em.persist(post);



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