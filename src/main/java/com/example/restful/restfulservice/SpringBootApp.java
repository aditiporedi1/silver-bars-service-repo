package com.example.restful.restfulservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Ignore
@SpringBootApplication
@ComponentScan("com.baeldung.h2db.demo.server")
public class SpringBootApp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @PostConstruct
    private void initDb() {
        System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Orders"));

        String sqlStatements[] = {
               // "drop table orders if exists",
               // "create table orders(user_id varchar(9),price_per_kg number,quantity number, order_type text)",
                "insert into orders(user_id, price_per_kg, quantity, order_type) values('user1',210,2.5,'SELL')",
                "insert into orders(user_id, price_per_kg, quantity, order_type) values('user2',209,1.5,'BUY')"
        };

        Arrays.asList(sqlStatements).stream().forEach(sql -> {
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        });

        System.out.println(String.format("****** Fetching from table: %s ******", "Orders"));
        jdbcTemplate.query("select user_id, price_per_kg, quantity, order_type from orders",
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        System.out.println(String.format("user_id:%s,price_per_kg:%s,quantity:%s,order_type:%s",
                                rs.getString("user_id"),
                                rs.getString("price_per_kg"),
                                rs.getString("quantity"),
                                rs.getString("order_type")));
                        return null;
                    }
                });
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9091");
    }
}

