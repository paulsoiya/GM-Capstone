/**
 * Interfaces with the Entity Manager for the 
 * User entity.
 * 
 * @author Paul Soiya II (psoiya@asu.edu)
 * @version 1/18/2015
 */
package com.gm.user.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Date;

import com.gm.user.User;

@Stateless
public class UserDao {
    
    @PersistenceContext(unitName = "mysql_ds")
    private EntityManager em;
    
    /**
     * Creates a new user in the database by persisting the new User
     * 
     * @param email
     * @param password
     * @param admin
     * @param firstName
     * @param lastName
     * @param createDate
     * @return 
     */
    public User createUser(String email, String password, boolean admin, 
            String firstName, String lastName){
        
        String salt = ""; // this will be replaced by hashing function
        User user = new User(email, password, salt, admin,
                firstName, lastName);
        
        //persist the new user to the database
        em.persist(user);
        
        return user;

    }
    
    /**
     * Retrieves all users from the user table
     * @return 
     */
    public List<User> retrieveAllUsers(){
        
        Query query = em.createQuery("SELECT u from User u");
        
        return query.getResultList();
    }
    
    /**
     * Find a user by his/her id
     * @param id
     * @return 
     */
    public User findUser(long id){
        return em.find(User.class, id);
    }
    
    /**
     * Delete a user by his/her id
     * @param id 
     */
    public void deleteUser(long id){
        em.remove( em.find(User.class, id) );
    }
    
}