package c.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import c.Application;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Application.class)
public class JpaConfig {

	@Value("${dataSource.driverClassName}")
	private String driver;
	@Value("${dataSource.url}")
	private String url;
	@Value("${dataSource.username}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
	@Value("${hibernate.dialect}")
	private String dialect;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;
	@Value("${hibernate.show_sql}")
	private String show_sql;

	@Bean
	public FactoryBean<SessionFactory> createSessionFactory() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(driver);
		dataSource.setJdbcUrl(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(new String[] {"c.account", "c.measurement", "c.beerSources", "c.brew", "c.brewer"});
		sessionFactory.setHibernateProperties(hibernateProperties().getObject());

		return sessionFactory;
	}

	@Bean
	public FactoryBean<Properties> hibernateProperties() {
		PropertiesFactoryBean pfb = new PropertiesFactoryBean();
		Properties properties = new Properties();
		properties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
		properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
		properties.put(org.hibernate.cfg.Environment.SHOW_SQL, show_sql);
		pfb.setProperties(properties);
		return pfb;
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws Exception {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(createSessionFactory().getObject());

		return txManager;
	}
}
