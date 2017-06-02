package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.enums.MessageStatusEnum;
import cn.chenxubiao.message.enums.MessageTypeEnum;
import cn.chenxubiao.message.service.MessageService;
import cn.chenxubiao.neo4j.domain.Person;
import cn.chenxubiao.neo4j.service.PersonService;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.repository.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Autowired
    private UserFollowRepository userFollowRepository;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PersonService personService;

    @Override
    public boolean disposeFollowUser(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0 || startUserId == endUserId) {
            return false;
        }
        UserInfo startUserInfo = userInfoService.findById(startUserId);
        UserInfo endUserInfo = userInfoService.findById(endUserId);
        if (startUserInfo == null
                || endUserInfo == null) {
            return false;
        }
        if (startUserInfo.getStatus() != BBSConsts.UserStatus.USER_IS_NORMAL
                || endUserInfo.getStatus() != BBSConsts.UserStatus.USER_IS_NORMAL) {
            return false;
        }
        UserFollow userFollowDB = findByStartUserIdAndEndUserId(startUserId, endUserId);

        Person person = personService.findByUserId(endUserId);

        if (userFollowDB != null) {
            Message message = new Message();
            message.setType(MessageTypeEnum.USER_UNFOLLOW.getCode());
            message.setSender(endUserInfo.getId());
            message.setReceiver(startUserId);
            message.setStatus(MessageStatusEnum.SEND.getCode());
            message.setMessage("");
            message.setCreateTime(new Date());
            message.setModifyTime(message.getCreateTime());
            messageService.save(message);
            userFollowRepository.delete(userFollowDB);


            if (person != null) {
                Set<Person> personSet = person.getFriends();
                if (CollectionUtil.isNotEmpty(personSet)) {
                    Iterator<Person> it = personSet.iterator();
                    while (it.hasNext()) {
                       Person friend = it.next();
                        if (friend != null && friend.getUserId() == startUserId) {
                            it.remove();
                        }
                    }
                    personService.save(person);
                }
            }
            return true;
        }

        Message message = new Message();
        message.setType(MessageTypeEnum.USER_FOLLOW.getCode());
        message.setSender(endUserInfo.getId());
        message.setReceiver(startUserId);
        message.setStatus(MessageStatusEnum.SEND.getCode());
        message.setMessage("");
        message.setCreateTime(new Date());
        message.setModifyTime(message.getCreateTime());
        messageService.save(message);

        UserFollow userFollow = new UserFollow();
        userFollow.setStartUserId(startUserId);
        userFollow.setEndUserId(endUserId);
        userFollow.setCreateTime(new Date());
        userFollow.setModifyTime(userFollow.getCreateTime());
        userFollowRepository.save(userFollow);


        if (person != null) {
            Person friend = personService.findByUserId(startUserId);
            if (friend != null) {
                person.getFriends().add(friend);
                personService.save(person);
            }
        }
        return true;
    }

    @Override
    public int countFollows(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return userFollowRepository.countByStartUserId(userId);
    }

    @Override
    public boolean isFollowed(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0 || startUserId == endUserId) {
            return false;
        }
        return userFollowRepository.countByStartUserIdAndEndUserId(startUserId, endUserId) > 0;
    }

    @Override
    public int isFollow(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0 || startUserId == endUserId) {
            return 0;
        }
        return userFollowRepository.countByStartUserIdAndEndUserId(startUserId, endUserId);
    }

    @Override
    public UserFollow findByStartUserIdAndEndUserId(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0) {
            return null;
        }
        return userFollowRepository.findByStartUserIdAndEndUserId(startUserId, endUserId);
    }

    @Override
    public int countFollowing(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return userFollowRepository.countByEndUserId(userId);
    }

    /**
     * 求粉丝
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public List<UserFollow> findFollows(int userId, Pageable pageable) {

        if (userId <= 0) {
            return null;
        }
        Page<UserFollow> userFollowPage = userFollowRepository.findByStartUserId(userId, pageable);
        return userFollowPage.getContent();
    }

    @Override
    public List<UserFollow> findFollows(int userId) {
        if (userId <= 0) {
            return null;
        }
        return userFollowRepository.findAllByStartUserId(userId);
    }

    /**
     * 求他关注的用户
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public List<UserFollow> findFollowing(int userId, Pageable pageable) {
        if (userId <= 0) {
            return null;
        }
        Page<UserFollow> userFollowPage = userFollowRepository.findByEndUserId(userId, pageable);
        return userFollowPage.getContent();
    }

    @Override
    public List<UserFollow> findFollowing(int userId) {
        if (userId <= 0) {
            return null;
        }
        return userFollowRepository.findAllByEndUserId(userId);
    }

}
