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
public class ModelYearsPersistenceTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(ModelYears.class.getPackage())
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
        //     .addPackage(ModelYears.class.getPackage())
        //     .addAsManifestResource("test-persistence.xml", "persistence.xml")
        //     .addAsManifestResource("jbossas-ds.xml")
        //     .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        // // choose your packaging here
        // return jar;
    }
 
    private static final String[] MODEL_YEARS = {
        "Model Year 1",
        "Model Year 2",
        "Model Year 3",
        "Model Year 1",
        "Model Year 4",
        "Model Year 5",
        "Model Year 6",
        "Model Year 7",
        "Model Year 8",
        "Model Year 9",
        "Model Year 10",
        "Model Year 11",
        "Model Year 12",
        "Model Year 13",
        "Model Year 14",
        "Model Year 15",
        "Model Year 16",
        "Model Year 17",
        "Model Year 18",
        "Model Year 19",
        "Model Year 20"
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
        em.createQuery("delete from ModelYears").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (String title : MODEL_YEARS) {
            ModelYears year = new ModelYears(0, title);
            em.persist(year);
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
        String fetchingAllWordsInJpql = "select s from ModelYears s";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<ModelYears> words = em.createQuery(fetchingAllWordsInJpql, ModelYears.class).getResultList();

        // then
        System.out.println("Found " + words.size() + " words (using JPQL):");
        assertContainsAllWords(words);
    }

    private static void assertContainsAllWords(Collection<ModelYears> retrievedWords) {
        Assert.assertEquals(MODEL_YEARS.length, retrievedWords.size());
        final Set<String> retrievedWordsSet = new HashSet<String>();
        for (ModelYears year : retrievedWords) {
            System.out.println("* " + year);
            retrievedWordsSet.add(year.getYearName());
        }
        Assert.assertTrue(retrievedWordsSet.containsAll(Arrays.asList(MODEL_YEARS)));
    }
}