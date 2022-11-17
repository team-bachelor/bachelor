package cn.org.bachelor.log.operate.service;

import cn.org.bachelor.context.ILogonUserContext;
import cn.org.bachelor.log.operate.domain.OperateLog;
import cn.org.bachelor.log.operate.OperateLogObject;
import cn.org.bachelor.log.operate.OperateLogSubject;
import cn.org.bachelor.log.operate.util.UUIDUtil;
import cn.org.bachelor.log.operate.dao.OperateLogMapper;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class OperateLogService {

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
        if (StringUtils.isNotEmpty(logParams.getOpOrg())) {
            criteria.andLike("operatorOrgan", "%" + logParams.getOpOrg() + "%");
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

    public int writeLog(OperateLogSubject subject, Operate operate, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, null, operate.getOpName(), null, data));
    }

    public int writeLog(OperateLogSubject subject, String attribute, Operate operate, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, attribute, operate.getOpName(), null, data));
    }

    public int writeLog(OperateLogSubject subject, String attribute, Operate operate, String result, OperateLogObject data) {
        return writeLog(createWhiteLog(subject, attribute, operate.getOpName(), result, data));
    }

    private OperateLog createWhiteLog(OperateLogSubject subject, String attribute, String operate, String result, OperateLogObject data) {
        if (data == null) {
            return null;
        }
        OperateLog ol = new OperateLog();

        ol.setId(UUIDUtil.getUUID());
        ol.setDataBase(subject.getDataBase());
        ol.setSubject(subject.getSubject());
        ol.setPredicate(operate);
        String detail = JSON.toJSONStringWithDateFormat(data, LOG_TIME_FORMAT);
        ol.setDetail(detail);
        ol.setIdentify(data.getIdentify());
        ol.setAttribute(attribute);
        ol.setResult(result);
        ol.setOpAccount(logonUserContext.getLogonUser().getCode());
        ol.setOpOrg(logonUserContext.getLogonUser().getOrgId());
        ol.setOpTime(new Date());
        ol.setSeriesNumber(data.getSerialNumber());
        return ol;
    }

    private static final String LOG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public enum Operate {
        ADD("添加"), MOD("修改"), DEL("删除"), AUDIT("审批"), CANCEL("取消"), DEL_REMOTE("删除关联系统");
        private String opName;

        Operate(String op) {
            this.opName = op;
        }

        String getOpName() {
            return opName;
        }
    }
}
