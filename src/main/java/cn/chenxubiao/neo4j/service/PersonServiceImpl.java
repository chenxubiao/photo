package cn.chenxubiao.neo4j.service;

import cn.chenxubiao.neo4j.domain.Person;
import cn.chenxubiao.neo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenxb on 17-6-2.
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void save(Person person) {
        if (person == null) {
            return;
        }
        personRepository.save(person);
    }

    @Override
    public Person findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return personRepository.findByUserId(userId);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAllByUserIdNotNull();
    }

    @Override
    public List<Person> recommondUser(int userId) {
        if (userId <= 0) {
            return null;
        }
        return personRepository.findLike(userId);
    }

}
