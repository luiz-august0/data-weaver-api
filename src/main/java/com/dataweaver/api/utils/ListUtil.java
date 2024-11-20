package com.dataweaver.api.utils;

import jakarta.persistence.Tuple;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ListUtil {

    public static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotNullOrNotEmpty(Collection<?> list) {
        return !isNullOrEmpty(list);
    }

    public static <E> E first(Collection<E> list) {
        if (isNotNullOrNotEmpty(list)) {
            return list.iterator().next();
        }

        return null;
    }

    public static boolean isGreaterThan(Collection<?> list, Integer value) {
        return list.size() > value;
    }

    public static boolean isLessThan(Collection<?> list, Integer value) {
        return list.size() < value;
    }

    public static <E> E last(Collection<E> list) {
        E e = null;

        if (isNotNullOrNotEmpty(list)) {
            Iterator<E> iterator = list.iterator();
            while (iterator.hasNext()) {
                e = iterator.next();
            }
        }

        return e;
    }

    public static <T> List<T> toList(T... values) {
        return Arrays.asList(values);
    }

    public static <T> boolean addIfNotNull(Collection<T> collection, T value) {
        if (value != null) {
            return collection.add(value);
        }
        return false;
    }

    public static <T> void addAllIfNotNull(Collection<T> collection, Collection<T> values) {
        if (isNotNullOrNotEmpty(values)) {
            for (T value : values) {
                addIfNotNull(collection, value);
            }
        }
    }

    public static <E> List<E> setListIfNotNull(Collection<E> collection, E value) {
        if (isNotNullOrNotEmpty(collection)) {
            addIfNotNull(collection, value);
            return collection.stream().collect(Collectors.toList());
        }
        return new ArrayList(Arrays.asList(value));
    }

    public static Integer size(Collection<?> items) {
        return items == null ? 0 : items.size();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static <T> Stream<T> stream(Collection<T> list) {
        return isNullOrEmpty(list)
                ? Stream.empty()
                : list.stream();
    }

    public static <T, U> void removeDuplicates(List<T> list, Function<T, U> mapper) {
        HashSet<U> existing = new HashSet<>(list.size());
        list.removeIf(item -> !existing.add(mapper.apply(item)));
    }

    public static <T, U> boolean hasDuplicates(List<T> list, Function<T, U> mapper) {
        HashSet<U> existing = new HashSet<>(list.size());
        return stream(list)
                .anyMatch(item -> !existing.add(mapper.apply(item)));
    }

    public static <T, U> boolean hasValue(List<T> list, Function<T, U> mapper, Object value) {
        if (value == null) return false;
        return stream(list)
                .anyMatch(item -> mapper.apply(item).equals(value));
    }

    public static <T, U> T getDuplicate(List<T> list, Function<T, U> mapper) {
        HashSet<U> existing = new HashSet<>(list.size());
        return first(stream(list)
                .filter(item -> !existing.add(mapper.apply(item)))
                .collect(Collectors.toList()));
    }

    public static <T> T getDistinct(List<T> list1, List<T> list2) {
        return stream(list1)
                .filter(list2::contains)
                .findFirst()
                .orElse(null);
    }

    /**
     * @return Null se list for nula ou vazia
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> list) {
        if (isNotNullOrNotEmpty(list)) {
            return list.toArray((T[]) Array.newInstance(list.iterator().next().getClass(), 0));
        }
        return null;
    }

    public static boolean isEqualList(Collection list1, Collection list2) {
        if (list1 == list2) {
            return true;
        } else if (list1 != null && list2 != null && list1.size() == list2.size()) {
            Iterator it1 = list1.iterator();
            Iterator it2 = list2.iterator();
            Object obj1 = null;
            Object obj2 = null;

            while (true) {
                if (it1.hasNext() && it2.hasNext()) {
                    obj1 = it1.next();
                    obj2 = it2.next();
                    if (obj1 == null) {
                        if (obj2 == null) {
                            continue;
                        }
                    } else if (obj1.equals(obj2)) {
                        continue;
                    }

                    return false;
                }

                return !it1.hasNext() && !it2.hasNext();
            }
        } else {
            return false;
        }
    }

    public static List<String> splitToList(String string, String separator) {
        List<String> split = new ArrayList<>();

        if (string != null) {
            string = string.trim();

            if (string.startsWith(",")) {
                string = string.substring(1);
            }
            if (string.endsWith(",")) {
                string = string.substring(0, string.length() - 1);
            }

            if (separator == null) {
                separator = ",";
            }

            split = new ArrayList<>(Arrays.asList(StringUtils.split(string, separator)));
        }
        return split;
    }

    public static String[] splitToArray(String string, String separator) {
        return splitToList(string, separator).toArray(new String[]{});
    }

    public static Map<Object, Object> mountMapWithDefaultValue(Object align, Object[] headers) {
        return Arrays.stream(headers).collect(Collectors.toMap(header -> header, header -> align));
    }

    public static Map<String, Object> tupleToMap(Tuple tuple) {
        Map<String, Object> map = new LinkedHashMap<>();

        if (Utils.isNotEmpty(tuple)) {
            tuple.getElements().forEach(tupleElement -> {
                String alias = tupleElement.getAlias();
                Object value = tuple.get(tupleElement);
                map.putIfAbsent(alias, value);
            });
        }

        return map;
    }

}
