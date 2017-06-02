package cn.chenxubiao.neo4j.config;

import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by chenxb on 17-3-31.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("cn.chenxubiao.neo4j.domain")
@EnableNeo4jRepositories("cn.chenxubiao.neo4j.repository")
public class Neo4jConfig extends Neo4jDataAutoConfiguration {

}
