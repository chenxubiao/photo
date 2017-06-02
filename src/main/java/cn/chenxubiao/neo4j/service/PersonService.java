package cn.chenxubiao.neo4j.service;

import cn.chenxubiao.neo4j.domain.Person;

import java.util.List;

/**
 * Created by chenxb on 17-6-2.
 */
public interface PersonService {

    void save(Person person);

    Person findByUserId(int userId);

    List<Person> findAll();

    List<Person> recommondUser(int userId);
}
