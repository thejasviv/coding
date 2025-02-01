package com.tester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapReverser<K, V> {

    public static void main(String[] args) {
        Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3, "d", 2);
        MapReverser<String, Integer> reverser = new MapReverser<>();
        Map<Integer, List<String>> reverseMap = reverser.reverseMap(map);
        for (Integer i : reverseMap.keySet()) {
            System.out.println(i + "=" + reverseMap.get(i));
        }
    }
    
    public Map<V, List<K>> reverseMap(Map<K, V> map) {
        Map<V, List<K>> reverseMap = new HashMap<>();
        for (K k : map.keySet()) {
            V v = map.get(k);
            if (reverseMap.get(v) == null) {
                List<K> list = new ArrayList<>();
                reverseMap.put(v, list);
            }
            reverseMap.get(v).add(k);
        }
        reverseMap = map.keySet().stream().collect(Collectors.groupingBy(k -> map.get(k)));
        return reverseMap;
    }

}
