package cn.org.bachelor.log.operate.service;

import cn.org.bachelor.context.ILogonUser;
import cn.org.bachelor.context.ILogonUserContext;
import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.log.operate.OperateLogObject;
import cn.org.bachelor.log.operate.OperateLogSubject;
import cn.org.bachelor.log.operate.dao.OperateLogMapper;
import cn.org.bachelor.log.operate.domain.OperateLog;
import cn.org.bachelor.log.operate.util.UUIDUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class OperateLogService {
    private static Logger logger = LoggerFactory.getLogger(OperateLogService.class);
    @Autowired
    private OperateLogMapper logMapper;

    @Autowired
    private ILogonUserContext logonUserContext;

    public List<OperateLog> getOperateLog(OperateLog logParams) {

        Condition condition = new Condition(OperateLog.class);
        Example.Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotEmpty(logParams.getId())) {
            criteria.andEqualTo("id", logParams.getId());
        }
        if (StringUtils.isNotEmpty(logParams.getOpAccount())) {
            criteria.andLike("operatorAccount", "%" + logParams.getOpAccount() + "%");
        }
        if (StringUtils.isNotEmpty(logParams.getOpOrgId())) {
            criteria.andLike("operatorOrgan", "%" + logParams.getOpOrgId() + "%");
        }
        if (StringUtils.isNotEmpty(logParams.getDataBase())) {
            criteria.andEqualTo("dataBase", logParams.getDataBase());
        }
        if (StringUtils.isNotEmpty(logParams.getSubject())) {
            criteria.andEqualTo("subject", logParams.getSubject());
        }
        if (StringUtils.isNotEmpty(logParams.getPredicate())) {
            criteria.andLike("predicate", "%" + logParams.getPredicate() + "%");
        }
        if (StringUtils.isNotEmpty(logParams.getDetail())) {
            criteria.andLike("detail", "%" + logParams.getDetail() + "%");
        }

        if (StringUtils.isNotEmpty(logParams.getIdentify())) {
            criteria.andLike("identify", "%" + logParams.getIdentify() + "%");
        }
        if (ObjectUtils.isNotEmpty(logParams.getOperatorTimeStart())) {
            criteria.andGreaterThanOrEqualTo("opTime", logParams.getOperatorTimeStart());
        }
        if (ObjectUtils.isNotEmpty(logParams.getOperatorTimeEnd())) {
            criteria.andLessThanOrEqualTo("opTime", logParams.getOperatorTimeEnd());
        }
        condition.orderBy("opTime").desc();
        return logMapper.selectByExample(condition);

    }

    public int writeLog(OperateLog whiteLog) {
        return logMapper.insertSelective(whiteLog);
    }

    public int writeLog(OperateLogSubject subject, String predicate, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, null, predicate, data, null));
    }

    public int writeLog(OperateLogSubject subject, String attribute, String predicate, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, attribute, predicate, data, null));
    }

    public int writeLog(OperateLogSubject subject, String attribute, String predicate, String result, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, attribute, predicate, data, result));
    }

    public int writeLog(OperateLogSubject subject, DefaultPredicate predicate, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, null, predicate.getOpName(), data, null));
    }

    public int writeLog(OperateLogSubject subject, String attribute, DefaultPredicate predicate, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, attribute, predicate.getOpName(), data, null));
    }

    public int writeLog(OperateLogSubject subject, String attribute, DefaultPredicate predicate, OperateLogObject data, String result) {
        return writeLog(createWhiteLog(subject, attribute, predicate.getOpName(), data, result));
    }

    private OperateLog createWhiteLog(OperateLogSubject subject, String attribute, String predicate, OperateLogObject data, String result) {
        if (data == null) {
            throw new BusinessException("log object cat not be null!");
        }
        OperateLog ol = new OperateLog();

        ol.setId(UUIDUtil.getUUID());
        ol.setDataBase(subject.getDataBase());
        ol.setSubject(subject.getSubject());
        ol.setPredicate(predicate);
        String detail = JSON.toJSONStringWithDateFormat(data, LOG_TIME_FORMAT);
        ol.setDetail(detail);
        ol.setIdentify(data.getIdentify());
        ol.setAttribute(StringUtils.isEmpty(attribute) ? data.getAttribute() : attribute);
        ol.setResult(result);
        ILogonUser user = logonUserContext.getLogonUser();
        if (user == null) {
            logger.warn("操作日志未获取到操作用户，可以尝试在工程中引入bachelor-iam-client模块，并通过网关访问当前操作。");
            ol.setOpAccount("未获取");
            ol.setOpOrgId("未获取");
        } else {
            ol.setOpAccount(user.getCode());
            ol.setOpOrgId(user.getOrgId());
        }
        ol.setOpTime(new Date());
        ol.setSeriesNumber(data.getSerialNumber());
        return ol;
    }

    private static final String LOG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public enum DefaultPredicate {
        ADD("添加"), MOD("修改"), DEL("删除"), AUDIT("审批"), CANCEL("取消"), DEL_REMOTE("关联删除");
        private String opName;

        DefaultPredicate(String op) {
            this.opName = op;
        }

        String getOpName() {
            return opName;
        }
    }
}
