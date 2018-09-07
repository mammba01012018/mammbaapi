/**
 * MammbaApplication.java - MAMMBA Application
 * 2018 All rights reserved.
 *
 */
package src.main.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
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

}
