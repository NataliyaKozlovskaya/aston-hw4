package util;

import models.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(Cinema.class)
                    .addAnnotatedClass(CarCinema.class)
                    .addAnnotatedClass(ClassicCinema.class)
                    .addAnnotatedClass(Film.class)
                    .addAnnotatedClass(Actor.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
            return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}


//
//        private static SessionFactory sessionFactory = buildSessionFactory();
//
//        protected static SessionFactory buildSessionFactory() {
//            // A SessionFactory is set up once for an application!
//            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//
//                    .configure()
//                    // configures settings from hibernate.cfg.xml
//                    .build();
//            try {
//                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//            }
//            catch (Exception e) {
//                // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//                // so destroy it manually.
//                StandardServiceRegistryBuilder.destroy( registry );
//
//                throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
//            }
//            return sessionFactory;
//        }
//
//        public static SessionFactory getSessionFactory() {
//            return sessionFactory;
//        }
//
//        public static void shutdown() {
//            // Close caches and connection pools
//            getSessionFactory().close();
//        }

//    private static SessionFactory sessionFactory;

//    private HibernateUtil() {}
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration().configure();
//
//                configuration.addAnnotatedClass(AbstractCinema.class);
//                configuration.addAnnotatedClass(CarCinema.class);
//                configuration.addAnnotatedClass(ClassicCinema.class);
//                configuration.addAnnotatedClass(Film.class);
//                configuration.addAnnotatedClass(Actor.class);
//                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//                sessionFactory = configuration.buildSessionFactory(builder.build());
//            } catch (Exception e) {
//                System.out.println("Исключение!" + e);
//            }
//        }
//        return sessionFactory;
//    }
