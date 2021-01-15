package com.apoem.mmxx.eventtracking.serialnumber;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: NamedThreadLocal </p>
 * <p>Description: 指定的名称的ThreadLocal </p>
 * <p>Date: 2020/7/21 8:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

	private final String name;

	/**
	 * 使用给定的名称创建一个实例
	 * @param name 描述性名称
	 */
	public NamedThreadLocal(String name) {
		Strings.hasText(name, "Name must not be empty");
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}