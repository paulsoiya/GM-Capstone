// package com.gm.test;
package com.gm.setting;

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
public class AnalyticSettingTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(AnalyticSetting.class.getPackage())
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
        //     .addPackage(AnalyticSetting.class.getPackage())
        //     .addAsManifestResource("test-persistence.xml", "persistence.xml")
        //     .addAsManifestResource("jbossas-ds.xml")
        //     .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        // // choose your packaging here
        // return jar;
    }
 
    private static final String[] BAD_WORDS = {
        "Bad Word 1",
        "Bad Word 2",
        "Bad Word 3",
        "Bad Word 1",
        "Bad Word 4",
        "Bad Word 5",
        "Bad Word 6",
        "Bad Word 7",
        "Bad Word 8",
        "Bad Word 9",
        "Bad Word 10",
        "Bad Word 11",
        "Bad Word 12",
        "Bad Word 13",
        "Bad Word 14",
        "Bad Word 15",
        "Bad Word 16",
        "Bad Word 17",
        "Bad Word 18",
        "Bad Word 19",
        "Bad Word 20"
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
        em.createQuery("delete from AnalyticSetting").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        for (String word : BAD_WORDS) {
            AnalyticSetting setting = new AnalyticSetting(word);
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
        String fetchingAllWordsInJpql = "select s from AnalyticSetting s order by s.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<AnalyticSetting> words = em.createQuery(fetchingAllWordsInJpql, AnalyticSetting.class).getResultList();

        // then
        System.out.println("Found " + words.size() + " words (using JPQL):");
        assertContainsAllWords(words);
    }

    private static void assertContainsAllWords(Collection<AnalyticSetting> retrievedWords) {
        Assert.assertEquals(BAD_WORDS.length, retrievedWords.size());
        final Set<String> retrievedWordsSet = new HashSet<String>();
        for (AnalyticSetting setting : retrievedWords) {
            System.out.println("* " + setting);
            retrievedWordsSet.add(setting.getExplicitWords());
        }
        Assert.assertTrue(retrievedWordsSet.containsAll(Arrays.asList(BAD_WORDS)));
    }
}