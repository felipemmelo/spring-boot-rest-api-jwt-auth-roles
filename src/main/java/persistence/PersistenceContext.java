package persistence;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import constants.Parameters;

/**
 * This class configures the JPA infrastructure, providing beans for {@link DataSource}, {@link LocalContainerEntityManagerFactoryBean}
 * and {@link JpaTransactionManager}. 
 *
 */
@Configuration
@EnableTransactionManagement
public class PersistenceContext {
	
	@Bean
	public DataSource dataSource(Environment env){
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(Parameters.KEY_DATASOURCE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(Parameters.KEY_DATASOURCE_URL));
		dataSource.setUsername(env.getRequiredProperty(Parameters.KEY_DATASOURCE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(Parameters.KEY_DATASOURCE_PASSWORD));

		return dataSource;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(Parameters.KEY_MODEL_PACKAGE);

		Properties jpaProperties = new Properties();

		jpaProperties.put("hibernate.dialect", env.getRequiredProperty(Parameters.KEY_DATASOURCE_DIALECT));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty(Parameters.KEY_DATASOURCE_AUTO_DDL));
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty(Parameters.KEY_DATASOURCE_SHOW_SQL));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);		
		
		return entityManagerFactoryBean;
	}
	
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    	
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        
        return transactionManager;
    }
}

