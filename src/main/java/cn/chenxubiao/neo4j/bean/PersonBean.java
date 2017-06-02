package cn.chenxubiao.neo4j.bean;

import cn.chenxubiao.neo4j.domain.Person;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chenxb on 17-6-3.
 */
public class PersonBean {

    private Long id;
    private int userId;
    private String userName;
    private Set<Person> friends = new HashSet<>();

    public PersonBean() {

    }

    public PersonBean(Person person) {
        this.id = person.getId();
        this.userId = person.getUserId();
        this.userName = person.getUserName();
        this.friends = person.getFriends();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public void setFriends(Set<Person> friends) {
        this.friends = friends;
    }
}
