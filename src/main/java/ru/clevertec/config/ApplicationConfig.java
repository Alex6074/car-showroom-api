package ru.clevertec.config;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("ru.clevertec")
@PropertySource(value = "classpath:application.yml", factory = ApplicationProperties.class)
@EnableJpaRepositories("ru.clevertec.repository")
@EnableTransactionManagement
public class ApplicationConfig {

    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.driver}")
    private String driver;
    @Value("${datasource.user}")
    private String user;
    @Value("${datasource.password}")
    private String password;

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.format_sql}")
    private String formatSql;
    @Value("${hibernate.ddl_auto}")
    private String ddlAuto;

    @Value("${hibernate.search.backend.type}")
    private String backendType;
    @Value("${hibernate.search.backend.directory.type}")
    private String backendDirectoryType;
    @Value("${hibernate.search.backend.directory.root}")
    private String backendDirectoryRoot;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String useSecondLevelCache;
    @Value("${hibernate.cache.use_query_cache}")
    private String useQueryCache;
    @Value("${hibernate.cache.region.factory_class}")
    private String regionFactoryClass;
    @Value("${hibernate.cache.javax.cache.uri}")
    private String cacheUri;
    @Value("${hibernate.cache.javax.cache.provider}")
    private String cacheProvider;


    @Value("${liquibase.changelog}")
    private String changelog;


    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelog);
        liquibase.setDataSource(dataSource());
        return liquibase;
    }

    @Bean
    public TransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driver);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(user);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPackagesToScan("ru.clevertec");
        Properties jpaProperties = new Properties();
        jpaProperties.putAll(Map.of(
                "hibernate.dialect", dialect,
                "hibernate.show_sql", showSql,
                "hibernate.format_sql", formatSql,
                "hibernate.hbm2ddl.auto", ddlAuto,
                "hibernate.search.backend.type", backendType,
                "hibernate.search.backend.directory.type", backendDirectoryType,
                "hibernate.search.backend.directory.root", backendDirectoryRoot,
                "hibernate.cache.use_second_level_cache", useSecondLevelCache,
                "hibernate.cache.use_query_cache", useQueryCache,
                "hibernate.cache.region.factory_class", regionFactoryClass
        ));
        jpaProperties.putAll(Map.of(
                "hibernate.javax.cache.uri", cacheUri,
                "hibernate.javax.cache.provider", cacheProvider
        ));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManagerFactoryBean;
    }
}
