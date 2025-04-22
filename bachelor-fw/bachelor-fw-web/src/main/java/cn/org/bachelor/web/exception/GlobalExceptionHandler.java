package cn.org.bachelor.web.exception;

import cn.org.bachelor.exception.BusinessException;
import cn.org.bachelor.exception.RemoteException;
import cn.org.bachelor.exception.SystemException;
import cn.org.bachelor.web.json.JsonResponse;
import cn.org.bachelor.web.json.ResponseStatus;
import cn.org.bachelor.web.util.MessageUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static cn.org.bachelor.web.json.ResponseStatus.BIZ_ERR;
import static cn.org.bachelor.web.json.ResponseStatus.SYS_ERR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author liuzhuo
 * @since 2018/11/9
 * 全局异常处理
 * 该类用于统一处理项目中出现的各种异常，将异常信息转换为统一的响应格式返回给客户端。
 */
//@RestControllerAdvice(annotations = {ExceptionHandle.class})
@Slf4j
@Order(20)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理远程异常
     * 当捕获到 RemoteException 时，此方法会根据异常的具体情况进行处理。
     * 如果异常的原因是 BusinessException，则调用 handleBusinessException 方法处理；
     * 否则，将其包装为 SystemException 并调用 handleSystemException 方法处理。
     *
     * @param request 发生异常的 HttpServletRequest 对象
     * @param e 捕获到的异常对象
     * @return 包含异常响应信息的 ResponseEntity 对象
     * @throws Exception 可能抛出的异常
     */
    @ExceptionHandler(value = RemoteException.class)
    public ResponseEntity handleRemoteException(HttpServletRequest request, Exception e) throws Exception {
        // 通用预处理，如清除分页信息
        commonPreHandler(request, e);
        // 记录警告日志
        logWarn(e);
        // 将异常转换为 RemoteException 类型
        RemoteException re = (RemoteException) e;
        // 远程异常
        Throwable cause = e.getCause();
        // 如果异常的原因是 BusinessException，则处理业务异常
        if (cause != null && cause instanceof BusinessException) {
            return handleBusinessException(request, (BusinessException) cause);
        } else {
            // 否则处理系统异常
            return handleSystemException(request, new SystemException(re));
        }
    }

    /**
     * 通用预处理方法
     * 此方法在处理异常前执行通用的操作，如清除分页信息。
     *
     * @param request 发生异常的 HttpServletRequest 对象
     * @param e 捕获到的异常对象
     */
    private void commonPreHandler(HttpServletRequest request, Throwable e) {
        // 清除分页信息
        PageHelper.clearPage();
    }

    /**
     * 处理业务异常
     * 当捕获到 BusinessException 时，此方法会将异常信息转换为统一的响应格式返回给客户端。
     *
     * @param request 发生异常的 HttpServletRequest 对象
     * @param e 捕获到的异常对象
     * @return 包含异常响应信息的 ResponseEntity 对象
     * @throws Exception 可能抛出的异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity handleBusinessException(HttpServletRequest request, Exception e) throws Exception {
        // 通用预处理，如清除分页信息
        commonPreHandler(request, e);
        // 记录调试日志
        logDebug(e);
        // 将异常转换为 BusinessException 类型
        BusinessException be = (BusinessException) e;
        // 创建异常响应实体
        return createExceptionResponseEntity(e.getMessage(), be.getArgs(), BIZ_ERR, BAD_REQUEST);
    }

    /**
     * 处理系统异常
     * 当捕获到 SystemException 时，此方法会将异常信息转换为统一的响应格式返回给客户端。
     *
     * @param request 发生异常的 HttpServletRequest 对象
     * @param e 捕获到的异常对象
     * @return 包含异常响应信息的 ResponseEntity 对象
     * @throws Exception 可能抛出的异常
     */
    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity handleSystemException(HttpServletRequest request, Exception e) throws Exception {
        // 通用预处理，如清除分页信息
        commonPreHandler(request, e);
        // 记录错误日志
        logError(e);
        // 创建异常响应实体
        return createExceptionResponseEntity(e.getMessage(), null, SYS_ERR, INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理通用异常
     * 当捕获到其他类型的 Throwable 异常时，此方法会将异常信息转换为统一的响应格式返回给客户端。
     *
     * @param request 发生异常的 HttpServletRequest 对象
     * @param e 捕获到的异常对象
     * @return 包含异常响应信息的 ResponseEntity 对象
     * @throws Exception 可能抛出的异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity handleException(HttpServletRequest request, Throwable e) throws Exception {
        // 通用预处理，如清除分页信息
        commonPreHandler(request, e);
        // 记录错误日志
        logError(e);
        // 创建异常响应实体
        return createExceptionResponseEntity("UNEXPECT_SYSTEM_EXCEPTION", null, SYS_ERR, INTERNAL_SERVER_ERROR);
    }

    /**
     * 创建异常响应实体
     * 此方法根据传入的异常代码、参数、响应状态和 HTTP 状态码创建一个包含异常信息的响应实体。
     *
     * @param code 异常代码
     * @param args 异常参数数组
     * @param rs 响应状态
     * @param hs HTTP 状态码
     * @return 包含异常响应信息的 ResponseEntity 对象
     */
    public static ResponseEntity createExceptionResponseEntity(String code, String[] args, ResponseStatus rs, HttpStatus hs) {
        // 初始化消息为异常代码
        String msg = code;
        try {
            // 如果异常代码不为空且不为空字符串
            if (code != null && !"".equals(code)) {
                // 根据异常代码和参数获取消息
                msg = MessageUtil.getMessage(code, args == null ? "null" : Arrays.asList(args).toArray());
            }
        } catch (NoSuchMessageException e) {
            // 记录调试日志
            log.debug(e.getMessage());
        }
        // 创建 JsonResponse 对象
        JsonResponse jr = new JsonResponse(null, code, msg, rs);
        // 创建 ResponseEntity 对象并返回
        return new ResponseEntity(jr, hs);
    }

    /**
     * 记录警告日志
     * 当日志级别为警告时，记录异常信息；当日志级别为调试时，打印异常堆栈信息。
     *
     * @param e 捕获到的异常对象
     */
    private void logWarn(Throwable e) {
        // 如果日志级别为警告
        if (logger.isWarnEnabled()) {
            // 记录警告日志
            logger.warn("", e);
        }
        // 如果日志级别为调试
        if (logger.isDebugEnabled()) {
            // 打印异常堆栈信息
            e.printStackTrace();
        }
    }

    /**
     * 记录错误日志
     * 当日志级别为错误时，记录异常信息；当日志级别为调试时，打印异常堆栈信息。
     *
     * @param e 捕获到的异常对象
     */
    private void logError(Throwable e) {
        // 如果日志级别为错误
        if (logger.isErrorEnabled()) {
            // 记录错误日志
            logger.error("", e);
        }
        // 如果日志级别为调试
        if (logger.isDebugEnabled()) {
            // 打印异常堆栈信息
            e.printStackTrace();
        }
    }

    /**
     * 记录调试日志
     * 当日志级别为调试时，记录异常信息并打印异常堆栈信息。
     *
     * @param e 捕获到的异常对象
     */
    private void logDebug(Throwable e) {
        // 如果日志级别为调试
        if (logger.isDebugEnabled()) {
            // 记录调试日志
            logger.debug("", e);
            // 打印异常堆栈信息
            e.printStackTrace();
        }
    }
}