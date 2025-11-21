package cn.org.bachelor.web;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 替换可以反复读取的request
 * 该类继承自 HttpServletRequestWrapper，用于将普通的 HttpServletRequest 包装成可重复读取请求体的 RequestWrapper。
 * 通常在某些场景下，原生的 HttpServletRequest 的请求体只能读取一次，使用此类可以解决这个问题。
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储body数据的容器
     * 用于存储请求体的字节数据，以便后续可以重复读取。
     */
    private final byte[] body;

    /**
     * 构造函数，初始化 RequestWrapper 实例。
     * 这里原代码声明的 `IOException` 异常在方法体中并未被抛出，建议移除。
     *
     * @param request 原始的 HttpServletRequest 对象
     */
    public RequestWrapper(HttpServletRequest request) {

        super(request);

        // 将body数据存储起来
        body = getBodyString(request).getBytes(Charset.defaultCharset());
    }

    public static boolean isSupport(ServletRequest request) {
        return request.getContentType() != null && !request.getContentType().toLowerCase().startsWith("multipart/");
    }
    /**
     * 获取请求Body
     * 从 ServletRequest 对象中获取请求体的字符串表示。
     *
     * @param request request 对象
     * @return 请求体的字符串表示
     */
    public String getBodyString(final ServletRequest request) {
        try {
            // 调用 inputStream2String 方法将输入流转换为字符串
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            // 若发生 IO 异常，记录错误日志并抛出运行时异常
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求Body
     * 从存储的字节数组中获取请求体的字符串表示。
     * 此方法目前未被使用，可考虑移除。
     *
     * @return 请求体的字符串表示
     */
    public String getBodyString() {
        // 创建一个基于存储的字节数组的输入流
        final InputStream inputStream = new ByteArrayInputStream(body);

        // 调用 inputStream2String 方法将输入流转换为字符串
        return inputStream2String(inputStream);
    }

    /**
     * 将inputStream里的数据读取出来并转换成字符串
     * 该方法用于将输入流中的数据逐行读取并拼接成字符串。
     *
     * @param inputStream 输入流对象
     * @return 输入流中的数据转换后的字符串
     */
    private String inputStream2String(InputStream inputStream) {
        // 用于拼接读取的字符串
        StringBuilder sb = new StringBuilder();
        // 用于读取输入流的字符缓冲读取器
        BufferedReader reader = null;

        try {
            // 创建一个基于输入流的字符缓冲读取器
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            // 用于存储每行读取的内容
            String line;
            // 逐行读取输入流，直到结束
            while ((line = reader.readLine()) != null) {
                // 将每行内容添加到 StringBuilder 中
                sb.append(line);
            }
        } catch (IOException e) {
            // 若发生 IO 异常，记录错误日志并抛出运行时异常
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            // 确保读取器在使用后被关闭
            if (reader != null) {
                try {
                    // 关闭读取器
                    reader.close();
                } catch (IOException e) {
                    // 若关闭读取器时发生异常，记录错误日志
                    log.error("", e);
                }
            }
        }

        // 返回拼接好的字符串
        return sb.toString();
    }

    /**
     * 重写 getReader 方法，返回一个基于自定义输入流的字符缓冲读取器。
     * 这里原代码声明的 `IOException` 异常在方法体中并未被抛出，建议移除。
     *
     * @return 字符缓冲读取器对象
     */
    @Override
    public BufferedReader getReader() {
        try {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        } catch (Exception e) {
            // 若发生异常，记录错误日志并抛出运行时异常
            log.error("获取读取器失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 重写 getInputStream 方法，返回一个自定义的 ServletInputStream 对象。
     * 这里原代码声明的 `IOException` 异常在方法体中并未被抛出，建议移除。
     *
     * @return 自定义的 ServletInputStream 对象
     */
    @Override
    public ServletInputStream getInputStream() {

        // 创建一个基于存储的字节数组的输入流
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        // 返回一个自定义的 ServletInputStream 实现
        return new ServletInputStream() {
            /**
             * 从输入流中读取一个字节。
             * 这里原代码声明的 `IOException` 异常在方法体中并未被抛出，建议移除。
             *
             * @return 读取的字节数据，若到达流的末尾则返回 -1
             */
            @Override
            public int read() {
                return inputStream.read();
            }

            /**
             * 判断输入流是否已经读取完毕。
             *
             * @return 这里始终返回 false，表示流未结束
             */
            @Override
            public boolean isFinished() {
                return false;
            }

            /**
             * 判断输入流是否准备好被读取。
             *
             * @return 这里始终返回 false，表示流未准备好
             */
            @Override
            public boolean isReady() {
                return false;
            }

            /**
             * 设置读取监听器，用于异步读取操作。
             *
             * @param readListener 读取监听器对象
             */
            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

}