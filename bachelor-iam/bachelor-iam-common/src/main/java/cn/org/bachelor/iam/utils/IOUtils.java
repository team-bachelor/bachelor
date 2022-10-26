package cn.org.bachelor.iam.utils;

import cn.org.bachelor.iam.oauth2.OAuthConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IOUtils {
    private static Logger logger= LoggerFactory.getLogger(IOUtils.class);

    public static final int DEFAULT_BUFFER_SIZE = 4096;

    public IOUtils() {
    }

    public static String read(InputStream in) {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            throw new IllegalStateException(var3.getMessage(), var3);
        }

        return read((Reader)reader);
    }

    public static String readFromResource(String resource) throws IOException {
        InputStream in = null;

        String text;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if(in == null) {
                in = IOUtils.class.getResourceAsStream(resource);
            }

            if(in != null) {
                text = read(in);
                String var3 = text;
                return var3;
            }

            text = null;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("close error", e);
                }
            }
        }

        return text;
    }

    public static byte[] readByteArrayFromResource(String resource) throws IOException {
        InputStream in = null;

        Object var2;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if(in != null) {
                byte[] var6 = readByteArray(in);
                return var6;
            }

            var2 = null;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("close error", e);
                }
            }
        }

        return (byte[])var2;
    }

    public static byte[] readByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        boolean EOF = true;
        byte[] buffer = new byte[4096];
        long count = 0L;

        int n1;
        for(boolean n = false; -1 != (n1 = input.read(buffer)); count += (long)n1) {
            output.write(buffer, 0, n1);
        }

        return count;
    }

    public static String read(Reader reader) {
        try {
            StringWriter ex = new StringWriter();
            char[] buffer = new char[4096];
            boolean n = false;

            int n1;
            while(-1 != (n1 = reader.read(buffer))) {
                ex.write(buffer, 0, n1);
            }

            return ex.toString();
        } catch (IOException var4) {
            throw new IllegalStateException("read error", var4);
        }
    }

    public static String read(Reader reader, int length) {
        try {
            char[] ex = new char[length];
            int offset = 0;
            int rest = length;

            int len;
            while((len = reader.read(ex, offset, rest)) != -1) {
                rest -= len;
                offset += len;
                if(rest == 0) {
                    break;
                }
            }

            return new String(ex, 0, length - rest);
        } catch (IOException var6) {
            throw new IllegalStateException("read error", var6);
        }
    }

    public static String toString(Date date) {
        if(date == null) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(date);
        }
    }

    public static String getStackTrace(Throwable ex) {
        StringWriter buf = new StringWriter();
        ex.printStackTrace(new PrintWriter(buf));
        return buf.toString();
    }

    public static String toString(StackTraceElement[] stackTrace) {
        StringBuilder buf = new StringBuilder();
        StackTraceElement[] arr$ = stackTrace;
        int len$ = stackTrace.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            StackTraceElement item = arr$[i$];
            buf.append(item.toString());
            buf.append("\n");
        }

        return buf.toString();
    }

    public static String returnResourceFile(String fileName,String info) throws ServletException, IOException {
        String text = IOUtils.readFromResource("support/http/resources" + fileName);
        if(text == null) {
            return "错误信息模版为定义";
        } else {
            return StringUtils.replace(text, OAuthConstant.TEMPLATE_REPLACE_STRING,info);
        }

    }
}
