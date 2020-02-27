package by.touchsoft.office.daysoffsystem.web.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * This class contains beans and configurations to work with database.
 */
@EnableTransactionManagement
@PropertySource(value = "classpath:db/db.properties")
@EnableJpaRepositories("by.touchsoft.office.daysoffsystem.db.repository")
public class HibernateConfig {

    private Environment environment;

    public HibernateConfig(final Environment environment) {
        this.environment = environment;
    }

    private Properties hibernateProperties() {
        Properties databaseProperties = new Properties();
        databaseProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        databaseProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        databaseProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return databaseProperties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public HibernatePersistenceProvider hibernatePersistenceProvider() {
        return new HibernatePersistenceProvider();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("by.touchsoft.office.daysoffsystem.db.repository.entity");
        entityManagerFactory.setJpaProperties(hibernateProperties());
        entityManagerFactory.setPersistenceProvider(hibernatePersistenceProvider());
        return entityManagerFactory;
    }

}
