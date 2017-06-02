package cn.chenxubiao.neo4j.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.neo4j.domain.Person;
import cn.chenxubiao.neo4j.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenxb on 17-6-2.
 */
@RestController
public class PersonController extends GuestBaseController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/neo4j/person/list")
    public ResponseEntity findAll() {

        List<Person> personList = personService.findAll();
        return ResponseEntity.success().set(BBSConsts.DATA, personList);
    }

    @RequestMapping(value = "/neo4j/recommond/user")
    public ResponseEntity getUserLike(HttpServletRequest request) {

        UserSession userSession = super.getUserSession(request);
        int userId = userSession == null ? 0 : userSession.getUserId();
        List<Person> personList = personService.recommondUser(userId);
        return ResponseEntity.success().set(BBSConsts.DATA, personList);
    }
}
