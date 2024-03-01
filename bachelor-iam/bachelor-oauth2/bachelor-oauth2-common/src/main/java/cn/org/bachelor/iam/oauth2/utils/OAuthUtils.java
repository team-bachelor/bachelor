package cn.org.bachelor.iam.oauth2.utils;


import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String buildQueryString(Collection<? extends Map.Entry<String, Object>> parameters, String encoding) {
        StringBuilder result = new StringBuilder();
        Iterator i$ = parameters.iterator();

        while (i$.hasNext()) {
            Map.Entry parameter = (Map.Entry) i$.next();
            String value = parameter.getValue() == null ? null : String.valueOf(parameter.getValue());
            if (!isEmpty((String) parameter.getKey()) && !isEmpty(value)) {
                String encodedName = urlEncode((String) parameter.getKey(), encoding);
                String encodedValue = value != null ? urlEncode(value, encoding) : "";
                if (result.length() > 0) {
                    result.append("&");
                }

                result.append(encodedName);
                result.append("=");
                result.append(encodedValue);
            }
        }

        return result.toString();
    }

    private static String urlEncode(String content, String encoding) {
        try {
            return URLEncoder.encode(content, encoding != null ? encoding : "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            throw new IllegalArgumentException(var3);
        }
    }

    public static String streamToString(InputStream is) throws IOException {
        return streamToString(is, "UTF-8");
    }

    public static String streamToString(InputStream is, String defaultCharset) throws IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream may not be null");
        } else {
            String charset = defaultCharset;
            if (defaultCharset == null) {
                charset = "UTF-8";
            }

            InputStreamReader reader = new InputStreamReader(is, charset);
            StringBuilder sb = new StringBuilder();

            try {
                char[] tmp = new char[4096];

                int l;
                while ((l = reader.read(tmp)) != -1) {
                    sb.append(tmp, 0, l);
                }
            } finally {
                reader.close();
            }

            return sb.toString();
        }
    }


    public static Map<String, Object> decodeForm(String form) {
        HashMap params = new HashMap();
        if (!isEmpty(form)) {
            String[] arr$ = form.split("\\&");
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String nvp = arr$[i$];
                int equals = nvp.indexOf(61);
                String name;
                String value;
                if (equals < 0) {
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
        if (contentType == null) {
            return false;
        } else {
            int semi = contentType.indexOf(";");
            if (semi >= 0) {
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

        while (i$.hasNext()) {
            Object v = i$.next();
            String stringValue = streamToString(v);
            if (!isEmpty(stringValue)) {
                if (p.length() > 0) {
                    p.append("&");
                }

                p.append(percentEncode(streamToString(v)));
            }
        }

        return p.toString();
    }

    public static String percentEncode(String s) {
        if (s == null) {
            return "";
        } else {
            try {
                return URLEncoder.encode(s, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
            } catch (UnsupportedEncodingException var2) {
                throw new RuntimeException(var2.getMessage(), var2);
            }
        }
    }

    private static final String streamToString(Object from) {
        return from == null ? null : from.toString();
    }

    public static String getAuthHeaderField(String authHeader) {
        if (authHeader != null) {
            Matcher m = OAUTH_HEADER.matcher(authHeader);
            if (m.matches() && "Bearer".equalsIgnoreCase(m.group(1))) {
                return m.group(2);
            }
        }

        return null;
    }

    public static Map<String, String> decodeOAuthHeader(String header) {
        HashMap headerValues = new HashMap();
        if (header != null) {
            Matcher m = OAUTH_HEADER.matcher(header);
            if (m.matches() && "Bearer".equalsIgnoreCase(m.group(1))) {
                String[] arr$ = m.group(2).split("\\s*,\\s*");
                int len$ = arr$.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String nvp = arr$[i$];
                    m = NVP.matcher(nvp);
                    if (m.matches()) {
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
        if (authenticationHeader != null && !"".equals(authenticationHeader)) {
            String[] tokens = authenticationHeader.split(" ");
            if (tokens == null) {
                return null;
            } else {
                String encodedCreds;
                if (tokens[0] != null && !"".equals(tokens[0])) {
                    encodedCreds = tokens[0];
                    if (!encodedCreds.equalsIgnoreCase("basic")) {
                        return null;
                    }
                }

                if (tokens[1] != null && !"".equals(tokens[1])) {
                    encodedCreds = tokens[1];
                    String decodedCreds = new String(Base64.decodeBase64(encodedCreds));
                    if (decodedCreds.contains(":") && decodedCreds.split(":").length == 2) {
                        String[] creds = decodedCreds.split(":");
                        if (!isEmpty(creds[0]) && !isEmpty(creds[1])) {
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

        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            String value = entry.getValue() == null ? null : String.valueOf(entry.getValue());
            if (!isEmpty((String) entry.getKey()) && !isEmpty(value)) {
                sb.append((String) entry.getKey());
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

        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            String value = entry.getValue() == null ? null : String.valueOf(entry.getValue());
            if (!isEmpty((String) entry.getKey()) && !isEmpty(value)) {
                sb.append(value);
            }
        }

        return sb.toString();
    }

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    public static boolean hasEmptyValues(String[] array) {
        if (array != null && array.length != 0) {
            String[] arr$ = array;
            int len$ = array.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String s = arr$[i$];
                if (isEmpty(s)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

//    public static String getAuthzMethod(String header) {
//        if(header != null) {
//            Matcher m = OAUTH_HEADER.matcher(header);
//            if(m.matches()) {
//                return m.group(1);
//            }
//        }
//
//        return null;
//    }
//
//    public static Set<String> decodeScopes(String s) {
//        HashSet scopes = new HashSet();
//        if(!isEmpty(s)) {
//            StringTokenizer tokenizer = new StringTokenizer(s, " ");
//
//            while(tokenizer.hasMoreElements()) {
//                scopes.add(tokenizer.nextToken());
//            }
//        }
//
//        return scopes;
//    }
//
//    public static String encodeScopes(Set<String> s) {
//        StringBuffer scopes = new StringBuffer();
//        Iterator i$ = s.iterator();
//
//        while(i$.hasNext()) {
//            String scope = (String)i$.next();
//            scopes.append(scope).append(" ");
//        }
//
//        return scopes.toString().trim();
//    }
//
//    public static boolean isMultipart(HttpServletRequest request) {
//        if(!"post".equals(request.getMethod().toLowerCase())) {
//            return false;
//        } else {
//            String contentType = request.getContentType();
//            return contentType == null?false:contentType.toLowerCase().startsWith("multipart/");
//        }
//    }
//
//    public static boolean hasContentType(String requestContentType, String requiredContentType) {
//        if(!isEmpty(requiredContentType) && !isEmpty(requestContentType)) {
//            StringTokenizer tokenizer = new StringTokenizer(requestContentType, ";");
//
//            do {
//                if(!tokenizer.hasMoreTokens()) {
//                    return false;
//                }
//            } while(!requiredContentType.equals(tokenizer.nextToken()));
//
//            return true;
//        } else {
//            return false;
//        }
//    }
}
