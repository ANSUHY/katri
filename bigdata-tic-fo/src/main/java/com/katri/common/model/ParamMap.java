package com.katri.common.model;

import java.util.HashMap;

public class ParamMap<K, V> extends HashMap<K, V> {

	/** serialVersionUID */
	private static final long serialVersionUID = -4182901057678825181L;

	/**
	 * 파라미터에 있는 HTML 태그 치환
	 */
	public V put(K key, V value) {
		return super.put(key, value);
	}

}
