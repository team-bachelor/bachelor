package cn.org.bachelor.iam.utils;

import java.util.*;

public abstract class StringUtils
{
  private static final String FOLDER_SEPARATOR = "/";
  private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
  private static final String TOP_PATH = "..";
  private static final String CURRENT_PATH = ".";
  private static final char EXTENSION_SEPARATOR = '.';
  public static final String EMPTY = "";

  public StringUtils() {
  }

  public static boolean isEmpty(Object str) {
    return str == null || "".equals(str);
  }

  public static boolean hasLength(CharSequence str)
  {
    return (str != null) && (str.length() > 0);
  }

  public static boolean hasLength(String str)
  {
    return hasLength(str);
  }

  public static boolean hasText(CharSequence str)
  {
    if (!hasLength(str)) {
      return false;
    }
    int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  public static boolean hasText(String str)
  {
    return hasText(str);
  }

  public static boolean containsWhitespace(CharSequence str)
  {
    if (!hasLength(str)) {
      return false;
    }
    int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  public static boolean containsWhitespace(String str)
  {
    return containsWhitespace(str);
  }

  public static String trimWhitespace(String str)
  {
    if (!hasLength(str)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str);
    while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(0)))) {
      buf.deleteCharAt(0);
    }
    while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(buf.length() - 1)))) {
      buf.deleteCharAt(buf.length() - 1);
    }
    return buf.toString();
  }

  public static String trimAllWhitespace(String str)
  {
    if (!hasLength(str)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str);
    int index = 0;
    while (buf.length() > index) {
      if (Character.isWhitespace(buf.charAt(index))) {
        buf.deleteCharAt(index); continue;
      }

      index++;
    }

    return buf.toString();
  }

  public static String trimLeadingWhitespace(String str)
  {
    if (!hasLength(str)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str);
    while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(0)))) {
      buf.deleteCharAt(0);
    }
    return buf.toString();
  }

  public static String trimTrailingWhitespace(String str)
  {
    if (!hasLength(str)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str);
    while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(buf.length() - 1)))) {
      buf.deleteCharAt(buf.length() - 1);
    }
    return buf.toString();
  }

  public static String trimLeadingCharacter(String str, char leadingCharacter)
  {
    if (!hasLength(str)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str);
    while ((buf.length() > 0) && (buf.charAt(0) == leadingCharacter)) {
      buf.deleteCharAt(0);
    }
    return buf.toString();
  }

  public static String trimTrailingCharacter(String str, char trailingCharacter)
  {
    if (!hasLength(str)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str);
    while ((buf.length() > 0) && (buf.charAt(buf.length() - 1) == trailingCharacter)) {
      buf.deleteCharAt(buf.length() - 1);
    }
    return buf.toString();
  }

  public static boolean startsWithIgnoreCase(String str, String prefix)
  {
    if ((str == null) || (prefix == null)) {
      return false;
    }
    if (str.startsWith(prefix)) {
      return true;
    }
    if (str.length() < prefix.length()) {
      return false;
    }
    String lcStr = str.substring(0, prefix.length()).toLowerCase();
    String lcPrefix = prefix.toLowerCase();
    return lcStr.equals(lcPrefix);
  }

  public static boolean endsWithIgnoreCase(String str, String suffix)
  {
    if ((str == null) || (suffix == null)) {
      return false;
    }
    if (str.endsWith(suffix)) {
      return true;
    }
    if (str.length() < suffix.length()) {
      return false;
    }

    String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
    String lcSuffix = suffix.toLowerCase();
    return lcStr.equals(lcSuffix);
  }

  public static boolean substringMatch(CharSequence str, int index, CharSequence substring)
  {
    for (int j = 0; j < substring.length(); j++) {
      int i = index + j;
      if ((i >= str.length()) || (str.charAt(i) != substring.charAt(j))) {
        return false;
      }
    }
    return true;
  }

  public static int countOccurrencesOf(String str, String sub)
  {
    if ((str == null) || (sub == null) || (str.length() == 0) || (sub.length() == 0)) {
      return 0;
    }
    int count = 0; int pos = 0; int idx = 0;
    while ((idx = str.indexOf(sub, pos)) != -1) {
      count++;
      pos = idx + sub.length();
    }
    return count;
  }

  public static String replace(String inString, String oldPattern, String newPattern)
  {
    if ((!hasLength(inString)) || (!hasLength(oldPattern)) || (newPattern == null)) {
      return inString;
    }
    StringBuffer sbuf = new StringBuffer();

    int pos = 0;
    int index = inString.indexOf(oldPattern);

    int patLen = oldPattern.length();
    while (index >= 0) {
      sbuf.append(inString.substring(pos, index));
      sbuf.append(newPattern);
      pos = index + patLen;
      index = inString.indexOf(oldPattern, pos);
    }
    sbuf.append(inString.substring(pos));

    return sbuf.toString();
  }

  public static String delete(String inString, String pattern)
  {
    return replace(inString, pattern, "");
  }

  public static String deleteAny(String inString, String charsToDelete)
  {
    if ((!hasLength(inString)) || (!hasLength(charsToDelete))) {
      return inString;
    }
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < inString.length(); i++) {
      char c = inString.charAt(i);
      if (charsToDelete.indexOf(c) == -1) {
        out.append(c);
      }
    }
    return out.toString();
  }

  public static String quote(String str)
  {
    return str != null ? "'" + str + "'" : null;
  }

  public static Object quoteIfString(Object obj)
  {
    return (obj instanceof String) ? quote((String)obj) : obj;
  }

  public static String unqualify(String qualifiedName)
  {
    return unqualify(qualifiedName, '.');
  }

  public static String unqualify(String qualifiedName, char separator)
  {
    return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
  }

  public static String capitalize(String str)
  {
    return changeFirstCharacterCase(str, true);
  }

  public static String uncapitalize(String str)
  {
    return changeFirstCharacterCase(str, false);
  }

  private static String changeFirstCharacterCase(String str, boolean capitalize) {
    if ((str == null) || (str.length() == 0)) {
      return str;
    }
    StringBuffer buf = new StringBuffer(str.length());
    if (capitalize) {
      buf.append(Character.toUpperCase(str.charAt(0)));
    }
    else {
      buf.append(Character.toLowerCase(str.charAt(0)));
    }
    buf.append(str.substring(1));
    return buf.toString();
  }

  public static String getFilename(String path)
  {
    if (path == null) {
      return null;
    }
    int separatorIndex = path.lastIndexOf("/");
    return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
  }

  public static String getFilenameExtension(String path)
  {
    if (path == null) {
      return null;
    }
    int sepIndex = path.lastIndexOf('.');
    return sepIndex != -1 ? path.substring(sepIndex + 1) : null;
  }

  public static String stripFilenameExtension(String path)
  {
    if (path == null) {
      return null;
    }
    int sepIndex = path.lastIndexOf('.');
    return sepIndex != -1 ? path.substring(0, sepIndex) : path;
  }

  public static String applyRelativePath(String path, String relativePath)
  {
    int separatorIndex = path.lastIndexOf("/");
    if (separatorIndex != -1) {
      String newPath = path.substring(0, separatorIndex);
      if (!relativePath.startsWith("/")) {
        newPath = newPath + "/";
      }
      return newPath + relativePath;
    }

    return relativePath;
  }

  public static String cleanPath(String path)
  {
    if (path == null) {
      return null;
    }
    String pathToUse = replace(path, "\\", "/");

    int prefixIndex = pathToUse.indexOf(":");
    String prefix = "";
    if (prefixIndex != -1) {
      prefix = pathToUse.substring(0, prefixIndex + 1);
      pathToUse = pathToUse.substring(prefixIndex + 1);
    }
    if (pathToUse.startsWith("/")) {
      prefix = prefix + "/";
      pathToUse = pathToUse.substring(1);
    }

    String[] pathArray = delimitedListToStringArray(pathToUse, "/");
    List pathElements = new LinkedList();
    int tops = 0;

    for (int i = pathArray.length - 1; i >= 0; i--) {
      String element = pathArray[i];
      if (".".equals(element)) {
        continue;
      }
      if ("..".equals(element))
      {
        tops++;
      }
      else if (tops > 0)
      {
        tops--;
      }
      else
      {
        pathElements.add(0, element);
      }

    }

    for (int i = 0; i < tops; i++) {
      pathElements.add(0, "..");
    }

    return prefix + collectionToDelimitedString(pathElements, "/");
  }

  public static boolean pathEquals(String path1, String path2)
  {
    return cleanPath(path1).equals(cleanPath(path2));
  }

  public static Locale parseLocaleString(String localeString)
  {
    String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
    String language = parts.length > 0 ? parts[0] : "";
    String country = parts.length > 1 ? parts[1] : "";
    String variant = "";
    if (parts.length >= 2)
    {
      int endIndexOfCountryCode = localeString.indexOf(country) + country.length();

      variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
      if (variant.startsWith("_")) {
        variant = trimLeadingCharacter(variant, '_');
      }
    }
    return language.length() > 0 ? new Locale(language, country, variant) : null;
  }

  public static String toLanguageTag(Locale locale)
  {
    return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
  }

  public static String[] addStringToArray(String[] array, String str)
  {
    if (ObjectUtils.isEmpty(array)) {
      return new String[] { str };
    }
    String[] newArr = new String[array.length + 1];
    System.arraycopy(array, 0, newArr, 0, array.length);
    newArr[array.length] = str;
    return newArr;
  }

  public static String[] concatenateStringArrays(String[] array1, String[] array2)
  {
    if (ObjectUtils.isEmpty(array1)) {
      return array2;
    }
    if (ObjectUtils.isEmpty(array2)) {
      return array1;
    }
    String[] newArr = new String[array1.length + array2.length];
    System.arraycopy(array1, 0, newArr, 0, array1.length);
    System.arraycopy(array2, 0, newArr, array1.length, array2.length);
    return newArr;
  }

  public static String[] mergeStringArrays(String[] array1, String[] array2)
  {
    if (ObjectUtils.isEmpty(array1)) {
      return array2;
    }
    if (ObjectUtils.isEmpty(array2)) {
      return array1;
    }
    List result = new ArrayList();
    result.addAll(Arrays.asList(array1));
    for (int i = 0; i < array2.length; i++) {
      String str = array2[i];
      if (!result.contains(str)) {
        result.add(str);
      }
    }
    return toStringArray(result);
  }

  public static String[] sortStringArray(String[] array)
  {
    if (ObjectUtils.isEmpty(array)) {
      return new String[0];
    }
    Arrays.sort(array);
    return array;
  }

  public static String[] toStringArray(Collection collection)
  {
    if (collection == null) {
      return null;
    }
    return (String[])(String[])collection.toArray(new String[collection.size()]);
  }

  public static String[] toStringArray(Enumeration enumeration)
  {
    if (enumeration == null) {
      return null;
    }
    List list = Collections.list(enumeration);
    return (String[])(String[])list.toArray(new String[list.size()]);
  }

  public static String[] trimArrayElements(String[] array)
  {
    if (ObjectUtils.isEmpty(array)) {
      return new String[0];
    }
    String[] result = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      String element = array[i];
      result[i] = (element != null ? element.trim() : null);
    }
    return result;
  }

  public static String[] removeDuplicateStrings(String[] array)
  {
    if (ObjectUtils.isEmpty(array)) {
      return array;
    }
    Set set = new TreeSet();
    for (int i = 0; i < array.length; i++) {
      set.add(array[i]);
    }
    return toStringArray(set);
  }

  public static String[] split(String toSplit, String delimiter)
  {
    if ((!hasLength(toSplit)) || (!hasLength(delimiter))) {
      return null;
    }
    int offset = toSplit.indexOf(delimiter);
    if (offset < 0) {
      return null;
    }
    String beforeDelimiter = toSplit.substring(0, offset);
    String afterDelimiter = toSplit.substring(offset + delimiter.length());
    return new String[] { beforeDelimiter, afterDelimiter };
  }

  public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter)
  {
    return splitArrayElementsIntoProperties(array, delimiter, null);
  }

  public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete)
  {
    if (ObjectUtils.isEmpty(array)) {
      return null;
    }
    Properties result = new Properties();
    for (int i = 0; i < array.length; i++) {
      String element = array[i];
      if (charsToDelete != null) {
        element = deleteAny(array[i], charsToDelete);
      }
      String[] splittedElement = split(element, delimiter);
      if (splittedElement == null) {
        continue;
      }
      result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
    }
    return result;
  }

  public static String[] tokenizeToStringArray(String str, String delimiters)
  {
    return tokenizeToStringArray(str, delimiters, true, true);
  }

  public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens)
  {
    if (str == null) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(str, delimiters);
    List tokens = new ArrayList();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (trimTokens) {
        token = token.trim();
      }
      if ((!ignoreEmptyTokens) || (token.length() > 0)) {
        tokens.add(token);
      }
    }
    return toStringArray(tokens);
  }

  public static String[] delimitedListToStringArray(String str, String delimiter)
  {
    return delimitedListToStringArray(str, delimiter, null);
  }

  public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete)
  {
    if (str == null) {
      return new String[0];
    }
    if (delimiter == null) {
      return new String[] { str };
    }
    List result = new ArrayList();
    if ("".equals(delimiter)) {
      for (int i = 0; i < str.length(); i++)
        result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
    }
    else
    {
      int pos = 0;
      int delPos = 0;
      while ((delPos = str.indexOf(delimiter, pos)) != -1) {
        result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
        pos = delPos + delimiter.length();
      }
      if ((str.length() > 0) && (pos <= str.length()))
      {
        result.add(deleteAny(str.substring(pos), charsToDelete));
      }
    }
    return toStringArray(result);
  }

  public static String[] commaDelimitedListToStringArray(String str)
  {
    return delimitedListToStringArray(str, ",");
  }

  public static Set commaDelimitedListToSet(String str)
  {
    Set set = new TreeSet();
    String[] tokens = commaDelimitedListToStringArray(str);
    for (int i = 0; i < tokens.length; i++) {
      set.add(tokens[i]);
    }
    return set;
  }

  public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix)
  {
    if (CollectionUtils.isEmpty(coll)) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    Iterator it = coll.iterator();
    while (it.hasNext()) {
      sb.append(prefix).append(it.next()).append(suffix);
      if (it.hasNext()) {
        sb.append(delim);
      }
    }
    return sb.toString();
  }

  public static String collectionToDelimitedString(Collection coll, String delim)
  {
    return collectionToDelimitedString(coll, delim, "", "");
  }

  public static String collectionToCommaDelimitedString(Collection coll)
  {
    return collectionToDelimitedString(coll, ",");
  }

  public static String arrayToDelimitedString(Object[] arr, String delim)
  {
    if (ObjectUtils.isEmpty(arr)) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < arr.length; i++) {
      if (i > 0) {
        sb.append(delim);
      }
      sb.append(arr[i]);
    }
    return sb.toString();
  }

  public static String arrayToCommaDelimitedString(Object[] arr)
  {
    return arrayToDelimitedString(arr, ",");
  }

  public static boolean isBlank(String s) {
    return "".equals(s);
  }
}