package cn.org.bachelor.up.oauth2.client.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class CollectionUtils
{
  public static boolean isEmpty(Collection collection)
  {
    return (collection == null) || (collection.isEmpty());
  }

  public static boolean isEmpty(Map map)
  {
    return (map == null) || (map.isEmpty());
  }

  public static List arrayToList(Object source)
  {
    return Arrays.asList(ObjectUtils.toObjectArray(source));
  }

  public static void mergeArrayIntoCollection(Object array, Collection collection)
  {
    if (collection == null) {
      throw new IllegalArgumentException("Collection must not be null");
    }
    Object[] arr = ObjectUtils.toObjectArray(array);
    for (int i = 0; i < arr.length; i++)
      collection.add(arr[i]);
  }

  public static void mergePropertiesIntoMap(Properties props, Map map)
  {
    if (map == null)
      throw new IllegalArgumentException("Map must not be null");
    Enumeration en;
    if (props != null)
      for (en = props.propertyNames(); en.hasMoreElements(); ) {
        String key = (String)en.nextElement();
        map.put(key, props.getProperty(key));
      }
  }

  public static boolean contains(Iterator iterator, Object element)
  {
    if (iterator != null) {
      while (iterator.hasNext()) {
        Object candidate = iterator.next();
        if (ObjectUtils.nullSafeEquals(candidate, element)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean contains(Enumeration enumeration, Object element)
  {
    if (enumeration != null) {
      while (enumeration.hasMoreElements()) {
        Object candidate = enumeration.nextElement();
        if (ObjectUtils.nullSafeEquals(candidate, element)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean containsInstance(Collection collection, Object element)
  {
    Iterator it;
    if (collection != null) {
      for (it = collection.iterator(); it.hasNext(); ) {
        Object candidate = it.next();
        if (candidate == element) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean containsAny(Collection source, Collection candidates)
  {
    if ((isEmpty(source)) || (isEmpty(candidates))) {
      return false;
    }
    for (Iterator it = candidates.iterator(); it.hasNext(); ) {
      if (source.contains(it.next())) {
        return true;
      }
    }
    return false;
  }

  public static Object findFirstMatch(Collection source, Collection candidates)
  {
    if ((isEmpty(source)) || (isEmpty(candidates))) {
      return null;
    }
    for (Iterator it = candidates.iterator(); it.hasNext(); ) {
      Object candidate = it.next();
      if (source.contains(candidate)) {
        return candidate;
      }
    }
    return null;
  }

  public static Object findValueOfType(Collection collection, Class type)
  {
    if (isEmpty(collection)) {
      return null;
    }
    Object value = null;
    for (Iterator it = collection.iterator(); it.hasNext(); ) {
      Object obj = it.next();
      if ((type == null) || (type.isInstance(obj))) {
        if (value != null)
        {
          return null;
        }
        value = obj;
      }
    }
    return value;
  }

  public static Object findValueOfType(Collection collection, Class[] types)
  {
    if ((isEmpty(collection)) || (ObjectUtils.isEmpty(types))) {
      return null;
    }
    for (int i = 0; i < types.length; i++) {
      Object value = findValueOfType(collection, types[i]);
      if (value != null) {
        return value;
      }
    }
    return null;
  }

  public static boolean hasUniqueObject(Collection collection)
  {
    if (isEmpty(collection)) {
      return false;
    }
    boolean hasCandidate = false;
    Object candidate = null;
    for (Iterator it = collection.iterator(); it.hasNext(); ) {
      Object elem = it.next();
      if (!hasCandidate) {
        hasCandidate = true;
        candidate = elem;
      }
      else if (candidate != elem) {
        return false;
      }
    }
    return true;
  }
}