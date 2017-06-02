package cn.chenxubiao.neo4j.repository;

import cn.chenxubiao.neo4j.domain.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by chenxb on 17-3-31.
 */
@Repository
@Transactional
public interface PersonRepository extends GraphRepository<Person> {

    Person findByUserName(String name);

    Person findByUserId(int userId);

    List<Person> findAllByUserIdNotNull();

    @Query(value = "MATCH (js:Person)-[:FOLLOW]-()-[:FOLLOW]-(surfer)\n" +
            "WHERE js.userId = {userId}\n" +
            "RETURN DISTINCT surfer")
    List<Person> findLike(@Param("userId") int userId);

}
