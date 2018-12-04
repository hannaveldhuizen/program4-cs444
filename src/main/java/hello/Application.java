/*=============================================================================
 |   Assignment:  Program #4: Database-driven Web Application
 |       Author:  Yebin Brandt (yebinbrandt@email.arizona.edu)
 |       Grader:  Terrence Lim and Bailey Nottingham
 |
 |       Course:  CSC 460
 |   Instructor:  L. McCann
 |     Due Date:  December 4, 2018 at the beginning of class
 |
 |  Description:  Uses Spring MVC to connect to Oracle database and do various ops.
 |
 |     Language:  Java (JDK 1.8)
 | Ex. Packages:  org.springframework, javax.sql, org.slf4j.Logger, etc.
 |                
 | Deficiencies:  I know of no unsatisfied requirements or logic errors.
 *===========================================================================*/
package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application {
    @Bean
    public DataSource dataSource() {
      DriverManagerDataSource ds = new DriverManagerDataSource();
      ds.setDriverClassName(oracle.jdbc.driver.OracleDriver.class.getName());
      ds.setUrl("jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle");
      ds.setUsername("lshoemake");
      ds.setPassword("a6826");
      return ds;
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
