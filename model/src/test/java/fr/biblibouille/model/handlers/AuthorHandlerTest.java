package fr.biblibouille.model.handlers;

import fr.biblibouille.model.Author;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class AuthorHandlerTest {

    private AuthorHandler authorHandler = new AuthorHandler();

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void should_save_auteur() {
        Author author1 = new Author("nom", "prenom");
        Author author2 = new Author("nom2", "prenom2");

        authorHandler.save(author1);
        authorHandler.save(author2);

        List<Author> authors = authorHandler.findAll();

        assertThat(authors).hasSize(2);

        for ( Author author : authors) {
            assertThat(author.getId()).isNotNull();
        }
    }


//    @Test
//    public void should_save_auteur() {
//        // create a couple of events...
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.save( new Author("nom", "prenom", null, null, null) );
//        session.save( new Author("nom2", "prenom2", null, null, null) );
//        session.getTransaction().commit();
//        session.close();
//
//        // now lets pull events from the database and list them
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List result = session.createQuery( "from Author" ).list();
//
//        for ( Author auteur : (List<Author>) result ) {
//            System.out.println( "Author (" + auteur.getNom() + ") : " + auteur.getPrenom() );
//        }
//
//        session.getTransaction().commit();
//        session.close();
//    }

//    @Test
//    public void should_create_auteur() {
//        Author auteur1 = new Author("nom", "prenom");
//        Author auteur2 = new Author("nom2", "prenom2");
//
//
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist( auteur1 );
//        entityManager.persist(auteur2);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//
//        // now lets pull events from the database and list them
//        entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        List<Author> result = entityManager.createQuery( "from Author" ).getResultList();
//
//        for ( Author auteur : (List<Author>) result ) {
//            System.out.println( "Author (" + auteur.getNom() + ") : " + auteur.getPrenom() );
//        }
//
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }


}
