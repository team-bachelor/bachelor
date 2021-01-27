package cn.org.bachelor.demo.web.service;

import cn.hutool.core.date.DateUtil;
import cn.org.bachelor.demo.web.dao.LogsMapper;
import cn.org.bachelor.demo.web.domain.Logs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author whongyu
 * @create by 2020-12-07
 */
@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Resource
    private LogsMapper logMapper;

    public int save(Logs log) {
        //另起线程处理数据存储与解析操作
        if (log != null) {
            log.setAddTime(DateUtil.now());
        }
        new Thread(new OperateRecordDeal(log)).start();
        return 1;
    }

    class OperateRecordDeal implements Runnable {
        private final Logs log;

        public OperateRecordDeal(Logs log) {
            this.log = log;
        }

        @Override
        public void run() {
            int num = logMapper.insert(log);
            logger.info("添加操作日志记录数量num={},log={}", num, log);
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.now());
    }
}
