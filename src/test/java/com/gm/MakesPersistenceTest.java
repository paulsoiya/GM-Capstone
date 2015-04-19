// package com.gm.test;
package com.gm.car;

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
public class MakesPersistenceTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(Makes.class.getPackage())
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
        //     .addPackage(Makes.class.getPackage())
        //     .addAsManifestResource("test-persistence.xml", "persistence.xml")
        //     .addAsManifestResource("jbossas-ds.xml")
        //     .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        // // choose your packaging here
        // return jar;
    }
 
    private static final String[] MAKES = {
        "Make 1",
        "Make 2",
        "Make 3",
        "Make 1",
        "Make 4",
        "Make 5",
        "Make 6",
        "Make 7",
        "Make 8",
        "Make 9",
        "Make 10",
        "Make 11",
        "Make 12",
        "Make 13",
        "Make 14",
        "Make 15",
        "Make 16",
        "Make 17",
        "Make 18",
        "Make 19",
        "Make 20"
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
        em.createQuery("delete from Makes").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (String title : MAKES) {
            Makes setting = new Makes(title);
            em.persist(setting);
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
        String fetchingAllWordsInJpql = "select s from Makes s order by s.makeId";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Makes> words = em.createQuery(fetchingAllWordsInJpql, Makes.class).getResultList();

        // then
        System.out.println("Found " + words.size() + " words (using JPQL):");
        assertContainsAllWords(words);
    }

    private static void assertContainsAllWords(Collection<Makes> retrievedWords) {
        Assert.assertEquals(MAKES.length, retrievedWords.size());
        final Set<String> retrievedWordsSet = new HashSet<String>();
        for (Makes make : retrievedWords) {
            System.out.println("* " + make.getMakeName());
            retrievedWordsSet.add(make.getMakeName());
        }
        Assert.assertTrue(retrievedWordsSet.containsAll(Arrays.asList(MAKES)));
    }
}