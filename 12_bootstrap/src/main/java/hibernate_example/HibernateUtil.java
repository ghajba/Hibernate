package hibernate_example;

import java.io.IOException;
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

    public static SessionFactory createSessionFactory(String propertyFile) {
        MetadataSources metadataSources = new MetadataSources(configureServiceRegistry(propertyFile));
        addClasses(metadataSources);
        return metadataSources.buildMetadata()
                .getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory createSessionFactory() {
        return createSessionFactory(null);
    }

    private static ServiceRegistry configureServiceRegistry(String propertyFile) {
        return new StandardServiceRegistryBuilder()
                .applySettings(getProperties(propertyFile))
                .build();
    }

    private static Properties getProperties(String propertyFile) {
        Properties properties = new Properties();
        if (propertyFile != null) {
            loadPropertyFile(propertyFile, properties);
        }
        else {
            properties.put("hibernate.connection.driver_class", "org.h2.Driver");
            properties.put("hibernate.connection.url", "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;MVCC=TRUE");
            properties.put("hibernate.connection.username", "sa");
            properties.put("hibernate.connection.password", "");
            properties.put("hibernate.connection.pool_size", 1);
            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.hbm2ddl.auto", "create");
        }
        return properties;
    }

    private static void loadPropertyFile(String propertyFile, Properties properties) {
        try {
            properties.load(HibernateUtil.class
                    .getResourceAsStream("/" + propertyFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addClasses(MetadataSources metadataSources) {
        metadataSources.addAnnotatedClass(Book.class);
    }
}
