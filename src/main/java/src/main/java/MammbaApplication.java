/**
 * MammbaApplication.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MammbaApplication extends SpringBootServletInitializer  {

    /**
     * Apply spring configurations.
     *
     * @return                              SpringApplicationBuilder reference.
     */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MammbaApplication.class);
    }

	/**
	 * The MAMMBA Main class.
	 *
	 * @param args                         parameters given.
	 * @throws Exception                   Any system errors.
	 */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MammbaApplication.class, args);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

}
