package hibernate_example;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 * Utility class for bootstrapping Hibernate through Java code only.
 *
 * @author GHajba
 */
public class HibernateUtil {

    protected HibernateUtil() {
    }

    public static SessionFactory createSessionFactory() {
        MetadataSources metadataSources = new MetadataSources(configureServiceRegistry());
        addClasses(metadataSources);
        return metadataSources.buildMetadata()
                .getSessionFactoryBuilder()
                .build();
    }

    private static ServiceRegistry configureServiceRegistry() {
        return new StandardServiceRegistryBuilder()
                .applySettings(getProperties())
                .build();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", "org.h2.Driver");
        properties.put("hibernate.connection.url", "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;MVCC=TRUE");
        properties.put("hibernate.connection.username", "sa");
        properties.put("hibernate.connection.password", "");
        properties.put("hibernate.connection.pool_size", 1);
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("", "");
        return properties;
    }

    private static void addClasses(MetadataSources metadataSources) {
        metadataSources.addAnnotatedClass(Book.class);
    }
}
