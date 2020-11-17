package cn.org.bachelor.up.oauth2.common.utils;


import cn.org.bachelor.up.oauth2.common.exception.OAuthProblemException;
import cn.org.bachelor.up.oauth2.common.exception.OAuthSystemException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by team bachelor on 15/5/20.
 */
public final class OAuthUtils {
    private static final String ENCODING = "UTF-8";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";
    public static final String AUTH_SCHEME = "Bearer";
    private static final Pattern OAUTH_HEADER = Pattern.compile("\\s*(\\w*)\\s+(.*)");
    private static final Pattern NVP = Pattern.compile("(\\S*)\\s*\\=\\s*\"([^\"]*)\"");
    public static final String MULTIPART = "multipart/";
    private static final String DEFAULT_CONTENT_CHARSET = "UTF-8";

    public OAuthUtils() {
    }

    public static String format(Collection<? extends Map.Entry<String, Object>> parameters, String encoding) {
        StringBuilder result = new StringBuilder();
        Iterator i$ = parameters.iterator();

        while(i$.hasNext()) {
            Map.Entry parameter = (Map.Entry)i$.next();
            String value = parameter.getValue() == null?null:String.valueOf(parameter.getValue());
            if(!isEmpty((String)parameter.getKey()) && !isEmpty(value)) {
                String encodedName = encode((String)parameter.getKey(), encoding);
                String encodedValue = value != null?encode(value, encoding):"";
                if(result.length() > 0) {
                    result.append("&");
                }

                result.append(encodedName);
                result.append("=");
                result.append(encodedValue);
            }
        }

        return result.toString();
    }

    private static String encode(String content, String encoding) {
        try {
            return URLEncoder.encode(content, encoding != null ? encoding : "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            throw new IllegalArgumentException(var3);
        }
    }

    public static String saveStreamAsString(InputStream is) throws IOException {
        return toString(is, "UTF-8");
    }

    public static String toString(InputStream is, String defaultCharset) throws IOException {
        if(is == null) {
            throw new IllegalArgumentException("InputStream may not be null");
        } else {
            String charset = defaultCharset;
            if(defaultCharset == null) {
                charset = "UTF-8";
            }

            InputStreamReader reader = new InputStreamReader(is, charset);
            StringBuilder sb = new StringBuilder();

            try {
                char[] tmp = new char[4096];

                int l;
                while((l = reader.read(tmp)) != -1) {
                    sb.append(tmp, 0, l);
                }
            } finally {
                reader.close();
            }

            return sb.toString();
        }
    }

    public static OAuthProblemException handleOAuthProblemException(String message) {
        return OAuthProblemException.error("invalid_request").description(message);
    }

    public static OAuthProblemException handleMissingParameters(Set<String> missingParams) {
        StringBuffer sb = new StringBuffer("Missing parameters: ");
        if(!isEmpty(missingParams)) {
            Iterator i$ = missingParams.iterator();

            while(i$.hasNext()) {
                String missingParam = (String)i$.next();
                sb.append(missingParam).append(" ");
            }
        }

        return handleOAuthProblemException(sb.toString().trim());
    }

    public static OAuthProblemException handleBadContentTypeException(String expectedContentType) {
        StringBuilder errorMsg = (new StringBuilder("Bad request content type. Expecting: ")).append(expectedContentType);
        return handleOAuthProblemException(errorMsg.toString());
    }

    public static OAuthProblemException handleNotAllowedParametersOAuthException(List<String> notAllowedParams) {
        StringBuffer sb = new StringBuffer("Not allowed parameters: ");
        if(notAllowedParams != null) {
            Iterator i$ = notAllowedParams.iterator();

            while(i$.hasNext()) {
                String notAllowed = (String)i$.next();
                sb.append(notAllowed).append(" ");
            }
        }

        return handleOAuthProblemException(sb.toString().trim());
    }

    public static Map<String, Object> decodeForm(String form) {
        HashMap params = new HashMap();
        if(!isEmpty(form)) {
            String[] arr$ = form.split("\\&");
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String nvp = arr$[i$];
                int equals = nvp.indexOf(61);
                String name;
                String value;
                if(equals < 0) {
                    name = decodePercent(nvp);
                    value = null;
                } else {
                    name = decodePercent(nvp.substring(0, equals));
                    value = decodePercent(nvp.substring(equals + 1));
                }

                params.put(name, value);
            }
        }

        return params;
    }

    public static boolean isFormEncoded(String contentType) {
        if(contentType == null) {
            return false;
        } else {
            int semi = contentType.indexOf(";");
            if(semi >= 0) {
                contentType = contentType.substring(0, semi);
            }

            return "application/x-www-form-urlencoded".equalsIgnoreCase(contentType.trim());
        }
    }

    public static String decodePercent(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static String percentEncode(Iterable values) {
        StringBuilder p = new StringBuilder();
        Iterator i$ = values.iterator();

        while(i$.hasNext()) {
            Object v = i$.next();
            String stringValue = toString(v);
            if(!isEmpty(stringValue)) {
                if(p.length() > 0) {
                    p.append("&");
                }

                p.append(percentEncode(toString(v)));
            }
        }

        return p.toString();
    }

    public static String percentEncode(String s) {
        if(s == null) {
            return "";
        } else {
            try {
                return URLEncoder.encode(s, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
            } catch (UnsupportedEncodingException var2) {
                throw new RuntimeException(var2.getMessage(), var2);
            }
        }
    }

    private static final String toString(Object from) {
        return from == null?null:from.toString();
    }

    private static boolean isEmpty(Set<String> missingParams) {
        return missingParams == null || missingParams.size() == 0;
    }

    public static <T> T instantiateClass(Class<T> clazz) throws OAuthSystemException {
        return instantiateClassWithParameters(clazz, (Class[])null, (Object[])null);
    }

    public static <T> T instantiateClassWithParameters(Class<T> clazz, Class<?>[] paramsTypes, Object[] paramValues) throws OAuthSystemException {
        try {
            if(paramsTypes != null && paramValues != null) {
                if(paramsTypes.length != paramValues.length) {
                    throw new IllegalArgumentException("Number of types and values must be equal");
                } else if(paramsTypes.length == 0 && paramValues.length == 0) {
                    return clazz.newInstance();
                } else {
                    Constructor e = clazz.getConstructor(paramsTypes);
                    return (T) e.newInstance(paramValues);
                }
            } else {
                return clazz.newInstance();
            }
        } catch (NoSuchMethodException var4) {
            throw new OAuthSystemException(var4);
        } catch (InstantiationException var5) {
            throw new OAuthSystemException(var5);
        } catch (IllegalAccessException var6) {
            throw new OAuthSystemException(var6);
        } catch (InvocationTargetException var7) {
            throw new OAuthSystemException(var7);
        }
    }

    public static String getAuthHeaderField(String authHeader) {
        if(authHeader != null) {
            Matcher m = OAUTH_HEADER.matcher(authHeader);
            if(m.matches() && "Bearer".equalsIgnoreCase(m.group(1))) {
                return m.group(2);
            }
        }

        return null;
    }

    public static Map<String, String> decodeOAuthHeader(String header) {
        HashMap headerValues = new HashMap();
        if(header != null) {
            Matcher m = OAUTH_HEADER.matcher(header);
            if(m.matches() && "Bearer".equalsIgnoreCase(m.group(1))) {
                String[] arr$ = m.group(2).split("\\s*,\\s*");
                int len$ = arr$.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String nvp = arr$[i$];
                    m = NVP.matcher(nvp);
                    if(m.matches()) {
                        String name = decodePercent(m.group(1));
                        String value = decodePercent(m.group(2));
                        headerValues.put(name, value);
                    }
                }
            }
        }

        return headerValues;
    }

    public static String[] decodeClientAuthenticationHeader(String authenticationHeader) {
        if(authenticationHeader != null && !"".equals(authenticationHeader)) {
            String[] tokens = authenticationHeader.split(" ");
            if(tokens == null) {
                return null;
            } else {
                String encodedCreds;
                if(tokens[0] != null && !"".equals(tokens[0])) {
                    encodedCreds = tokens[0];
                    if(!encodedCreds.equalsIgnoreCase("basic")) {
                        return null;
                    }
                }

                if(tokens[1] != null && !"".equals(tokens[1])) {
                    encodedCreds = tokens[1];
                    String decodedCreds = new String(Base64.decodeBase64(encodedCreds));
                    if(decodedCreds.contains(":") && decodedCreds.split(":").length == 2) {
                        String[] creds = decodedCreds.split(":");
                        if(!isEmpty(creds[0]) && !isEmpty(creds[1])) {
                            return decodedCreds.split(":");
                        }
                    }
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public static String encodeOAuthHeader(Map<String, Object> entries) {
        StringBuffer sb = new StringBuffer();
        sb.append("Bearer").append(" ");
        Iterator i$ = entries.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            String value = entry.getValue() == null?null:String.valueOf(entry.getValue());
            if(!isEmpty((String)entry.getKey()) && !isEmpty(value)) {
                sb.append((String)entry.getKey());
                sb.append("=\"");
                sb.append(value);
                sb.append("\",");
            }
        }

        return sb.substring(0, sb.length() - 1);
    }

    public static String encodeAuthorizationBearerHeader(Map<String, Object> entries) {
        StringBuffer sb = new StringBuffer();
        sb.append("Bearer").append(" ");
        Iterator i$ = entries.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            String value = entry.getValue() == null?null:String.valueOf(entry.getValue());
            if(!isEmpty((String)entry.getKey()) && !isEmpty(value)) {
                sb.append(value);
            }
        }

        return sb.toString();
    }

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    public static boolean hasEmptyValues(String[] array) {
        if(array != null && array.length != 0) {
            String[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String s = arr$[i$];
                if(isEmpty(s)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public static String getAuthzMethod(String header) {
        if(header != null) {
            Matcher m = OAUTH_HEADER.matcher(header);
            if(m.matches()) {
                return m.group(1);
            }
        }

        return null;
    }

    public static Set<String> decodeScopes(String s) {
        HashSet scopes = new HashSet();
        if(!isEmpty(s)) {
            StringTokenizer tokenizer = new StringTokenizer(s, " ");

            while(tokenizer.hasMoreElements()) {
                scopes.add(tokenizer.nextToken());
            }
        }

        return scopes;
    }

    public static String encodeScopes(Set<String> s) {
        StringBuffer scopes = new StringBuffer();
        Iterator i$ = s.iterator();

        while(i$.hasNext()) {
            String scope = (String)i$.next();
            scopes.append(scope).append(" ");
        }

        return scopes.toString().trim();
    }

    public static boolean isMultipart(HttpServletRequest request) {
        if(!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        } else {
            String contentType = request.getContentType();
            return contentType == null?false:contentType.toLowerCase().startsWith("multipart/");
        }
    }

    public static boolean hasContentType(String requestContentType, String requiredContentType) {
        if(!isEmpty(requiredContentType) && !isEmpty(requestContentType)) {
            StringTokenizer tokenizer = new StringTokenizer(requestContentType, ";");

            do {
                if(!tokenizer.hasMoreTokens()) {
                    return false;
                }
            } while(!requiredContentType.equals(tokenizer.nextToken()));

            return true;
        } else {
            return false;
        }
    }
}
