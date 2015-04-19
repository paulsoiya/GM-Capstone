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
public class PendingUserPersistenceTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(PendingUser.class.getPackage())
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
        //     .addPackage(PendingUser.class.getPackage())
        //     .addAsManifestResource("test-persistence.xml", "persistence.xml")
        //     .addAsManifestResource("jbossas-ds.xml")
        //     .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        // // choose your packaging here
        // return jar;
    }
 
    private static final String[] PENDING_USER = {
        "Pending User 1",
        "Pending User 2",
        "Pending User 3",
        "Pending User 1",
        "Pending User 4",
        "Pending User 5",
        "Pending User 6",
        "Pending User 7",
        "Pending User 8",
        "Pending User 9",
        "Pending User 10",
        "Pending User 11",
        "Pending User 12",
        "Pending User 13",
        "Pending User 14",
        "Pending User 15",
        "Pending User 16",
        "Pending User 17",
        "Pending User 18",
        "Pending User 19",
        "Pending User 20"
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
        em.createQuery("delete from PendingUser").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (int i = 0; i < PENDING_USER.length; i++) {
            PendingUser puser = new PendingUser(PENDING_USER[i], "Last Name " + i, "Email " + i,
                "Password " + i, "Position, " + i, "Reason " + i);
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
        String fetchingAllWordsInJpql = "select s from PendingUser s order by s.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<PendingUser> words = em.createQuery(fetchingAllWordsInJpql, PendingUser.class).getResultList();

        // then
        System.out.println("Found " + words.size() + " words (using JPQL):");
        assertContainsAllWords(words);
    }

    private static void assertContainsAllWords(Collection<PendingUser> retrievedWords) {
        Assert.assertEquals(PENDING_USER.length, retrievedWords.size());
        final Set<String> retrievedWordsSet = new HashSet<String>();
        for (PendingUser puser : retrievedWords) {
            System.out.println("* " + puser);
            retrievedWordsSet.add(puser.getFirstName());
        }
        Assert.assertTrue(retrievedWordsSet.containsAll(Arrays.asList(PENDING_USER)));
    }
}