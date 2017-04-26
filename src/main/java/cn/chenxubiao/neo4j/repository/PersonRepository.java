package cn.chenxubiao.neo4j.repository;

import cn.chenxubiao.neo4j.domain.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by chenxb on 17-3-31.
 */
public interface PersonRepository extends GraphRepository<Person> {

    Person findByName(String name);
}
