package com.sg.config;

import com.sg.dao.GameDAO;
import com.sg.dao.GameDAOImpl;
import com.sg.dao.GuessDAO;
import com.sg.dao.GuessDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/BullsAndCows");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public GameDAO getGameDAO() {
        return new GameDAOImpl(getDataSource());
    }

    @Bean
    GuessDAO getGuessDAO() {
        return new GuessDAOImpl(getDataSource());
    }
}
