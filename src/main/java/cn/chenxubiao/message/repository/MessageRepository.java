package cn.chenxubiao.message.repository;

import cn.chenxubiao.message.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    int countByReceiverAndStatus(int userId, int status);

    List<Message> findAllByReceiverAndStatus(int userId, int status);

    @Query(value = "update Message a set a.status = ?2 where a.receiver = ?1")
    void disposeUnLookMsg(int userId, int status);

}
