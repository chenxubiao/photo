package cn.chenxubiao.picture.service;

import cn.chenxubiao.picture.domain.DownloadLog;
import cn.chenxubiao.picture.repository.DownloadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-5-14.
 */
@Service
public class DownloadServiceImpl implements DownloadLogService {
    @Autowired
    private DownloadLogRepository downloadLogRepository;


    @Override
    public void save(DownloadLog downloadLog) {
        if (downloadLog == null) {
            return;
        }
        downloadLogRepository.save(downloadLog);
    }
}
