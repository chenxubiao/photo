package cn.chenxubiao.neo4j.domain;

import cn.chenxubiao.user.domain.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-3-31.
 */
@NodeEntity
public class Person {

    @GraphId
    private Long id;
    private int userId;
    private String userName;
    @Relationship(type = "FOLLOW")
    @JsonIgnore
    private Set<Person> friends = new HashSet<>();
    @Transient
    private List<Person> follows;

    public Person() {
    }

    public Person(UserInfo userInfo) {
        this.userId = userInfo.getId();
        this.userName = userInfo.getUserName();
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

    public List<Person> getFollows() {
        return new ArrayList<>(friends);
    }
}