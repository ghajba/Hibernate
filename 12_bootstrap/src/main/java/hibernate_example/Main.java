package hibernate_example;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author GHajba
 */
public class Main {

    public static void main(String[] args) {
//        final SessionFactory factory = HibernateUtil.createSessionFactory("hibernate.properties");
        final SessionFactory factory = HibernateUtil2.createSessionFactory();
        final Session session = factory.openSession();
        final Book book = new Book("9781617291999", "Java 8 in Action", "Raoul-Gabriel Urma, Mario Fusco, and Alan Mycroft");
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        CriteriaQuery<Book> query = session.getCriteriaBuilder().createQuery(Book.class);
        query.from(Book.class);
        final List<Book> books = session.createQuery(query).getResultList();
        System.out.println("\n----\n");
        System.out.println(MessageFormat.format("Storing {0} books in the database", books.size()));
        for (final Book b : books) {
            System.out.println(b);
        }
        System.out.println("\n----\n");
        session.close();
        factory.close();
    }
}
