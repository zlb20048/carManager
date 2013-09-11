package com.cars.simple.util;

import java.util.LinkedList;

/**
 * 堆栈
 * @author shifeng
 *
 */
public class Stack<T> {


	private LinkedList<T> stack;

	public Stack() {
		stack = new LinkedList<T>();
	}

	/*
	 * 入栈操作
	 */
	public void push(T obj) {
		stack.addFirst(obj);
	}

	/*
	 *  获取栈顶元素
	 */
	public T peek() {
		if (!isEmpty()) {
			return stack.getFirst();
		} else {
			return null;
		}
	}

	/*
	 *  出栈操作
	 */
	public T pop() {
		if (!isEmpty()) {
			return stack.removeFirst();
		} else {
			return null;
		}
	}

	/*
	 * 判断栈是否为空
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public void clear() {
		stack.clear();
	}

}
