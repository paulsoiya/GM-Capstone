// package com.gm.test;
package com.gm.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserPersistenceTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(User.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        // //     // You can use war packaging...
        // // WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
        // //     .addPackage(Game.class.getPackage())
        // //     .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
        // //     .addAsWebInfResource("jbossas-ds.xml")
        // //     .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        // // or jar packaging...
        // JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
        //     .addPackage(User.class.getPackage())
        //     .addAsManifestResource("test-persistence.xml", "persistence.xml")
        //     .addAsManifestResource("jbossas-ds.xml")
        //     .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        // // choose your packaging here
        // return jar;
    }
 
    private static final String[] USERS = {
        "User 1",
        "User 2",
        "User 3",
        "User 1",
        "User 4",
        "User 5",
        "User 6",
        "User 7",
        "User 8",
        "User 9",
        "User 10",
        "User 11",
        "User 12",
        "User 13",
        "User 14",
        "User 15",
        "User 16",
        "User 17",
        "User 18",
        "User 19",
        "User 20"
    };
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
 
    // tests go here

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from User").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (int i = 0; i < USERS.length; i++) {
            User puser = new User("Email " + i, "Password " + i, false, USERS[i], "Last Name " + i);
            em.persist(puser);
        }
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }

    @Test
    public void shouldFindAllWordsUsingJpqlQuery() throws Exception {
        // given
        String fetchingAllWordsInJpql = "select s from User s order by s.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<User> words = em.createQuery(fetchingAllWordsInJpql, User.class).getResultList();

        // then
        System.out.println("Found " + words.size() + " words (using JPQL):");
        assertContainsAllWords(words);
    }

    private static void assertContainsAllWords(Collection<User> retrievedWords) {
        Assert.assertEquals(USERS.length, retrievedWords.size());
        final Set<String> retrievedWordsSet = new HashSet<String>();
        for (User user : retrievedWords) {
            System.out.println("* " + user.getFirstName());
            retrievedWordsSet.add(user.getFirstName());
        }
        Assert.assertTrue(retrievedWordsSet.containsAll(Arrays.asList(USERS)));
    }
}