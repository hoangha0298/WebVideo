package com.example.demo.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {

	public static <T> Set asSet(T... object) {
		Set<T> result = new HashSet();
		Collections.addAll(result, object);
		return result;
	}

}
