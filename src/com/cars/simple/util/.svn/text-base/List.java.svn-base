package com.cars.simple.util;

import java.util.Vector;

/*******************************************************************
 * 文件名称	: List.java
 * 作	 者	: fushangbin
 * 创建时间	: 
 * 文件描述	: 自定义的链表
 * @param <E>
 ******************************************************************/
public class List<E>
{
	/**
	 * 列表头节点
	 */
	private ListNode firstNode;
	
	/**
	 * 列表尾节点
	 */
	private ListNode lastNode;
	
	/**
	 * 列表尾节点
	 */
	private ListNode currNode;
	
	/**
	 * 链表中的元素个数
	 */
	private int elementCount = 0;
  
	/*******************************************************************
	 * 函数名称	: List
	 * 函数描述	: 构造函数
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: N/A
	 * 备注		: N/A
	 ******************************************************************/
	public List(){
		firstNode = lastNode = currNode = null; 
	}
  
	/*******************************************************************
	 * 函数名称	: insertAtFront
	 * 函数描述	: 在列表的前端插入数据
	 * 输入参数	: @param insertItem 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void insertAtFront(Object insertItem){
		if(firstNode == null){
			firstNode = lastNode = new ListNode( insertItem);
		}else{
			firstNode = new ListNode( insertItem, firstNode); 
		}
		elementCount++;
	}
  
	/*******************************************************************
	 * 函数名称	: insertAtBack
	 * 函数描述	: 在列表的尾端插入数据
	 * 输入参数	: @param insertItem 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void addElement(Object insertItem){
		if( firstNode == null){
			currNode = firstNode = lastNode = new ListNode(insertItem);
		}else{
			currNode = lastNode = lastNode.next = new ListNode(insertItem);
		}
		elementCount++;
	}
  
	/*******************************************************************
	 * 函数名称	: delFromFront
	 * 函数描述	: 删除头部的节点数据
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: Object
	 * 备注		: N/A
	 ******************************************************************/
	public Object delFromFront(){
		Object removeItem = null;
		if (firstNode == null){
			return removeItem;
		}
		removeItem = firstNode.data;
		if( firstNode.equals (lastNode)){
			firstNode = lastNode = null;
		}else{
			firstNode = firstNode.next;
		}
		elementCount--;
		currNode = firstNode;
		return removeItem;   
	}
  
	/*******************************************************************
	 * 函数名称	: delFromBack
	 * 函数描述	: 删除尾部的节点数据
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: Object
	 * 备注		: N/A
	 ******************************************************************/
	public Object delFromBack(){
		Object removeItem = null;
		if (firstNode == null){
			return removeItem;
		}
		removeItem = lastNode.data;
		if(firstNode.equals (lastNode)){
			currNode = firstNode = lastNode = null;
		}else{      
			ListNode movePoObject = firstNode;
			while( movePoObject.next != lastNode){
				movePoObject = movePoObject.next;
			}
			lastNode = movePoObject;
			movePoObject.next = null;
			currNode = lastNode;
		}
		elementCount--;
		return removeItem;
	}
  
	/*******************************************************************
	 * 函数名称	: isEmpty
	 * 函数描述	: 判断链表是否为空
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: boolean
	 * 备注		: N/A
	 ******************************************************************/
	public boolean isEmpty(){
		return (firstNode == null); 
	}
	
	/*******************************************************************
	 * 函数名称	: removeAllElements
	 * 函数描述	: 移除所有元素
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void removeAllElements(){
		while (firstNode != null){
			delFromFront();
		}
		elementCount = 0;
	}
	
	/*******************************************************************
	 * 函数名称	: lastElement
	 * 函数描述	: 最后一个元素
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: Component
	 * 备注		: N/A
	 ******************************************************************/
	public Object lastElement(){
		if (lastNode == null){
			return null;
		}
		currNode = lastNode;
		return currNode.data;
	}
	
	/*******************************************************************
	 * 函数名称	: firstElement
	 * 函数描述	: 第一个元素
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: Component
	 * 备注		: N/A
	 ******************************************************************/
	public Object firstElement(){
		if (firstNode == null){
			return null;
		}
		currNode = firstNode;
		return currNode.data;
	}
	
	/*******************************************************************
	 * 函数名称	: getNext
	 * 函数描述	: 获取队列中的下一个元素
	 * 输入参数	: @return
	 * 输入参数	: @throws InterruptedException 
	 * 输出参数	: void
	 * 返回值  	: Object
	 * 备注		: N/A
	 ******************************************************************/
	public Object getNext(){
		if (firstNode == null){
			return null;
		}
		Object obj = null;
		if (currNode.next != null){
			currNode = currNode.next;
			obj = currNode.data;
		}
		return obj;
	}
	
	/*******************************************************************
	 * 函数名称	: removeElement
	 * 函数描述	: 删除元素
	 * 输入参数	: @param obj
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: boolean
	 * 备注		: N/A
	 ******************************************************************/
	public boolean removeElement(Object obj){
		boolean ret = false;
		if (firstNode == null){
			return ret;
		}
		ListNode tmpListNode = currNode;
		ListNode pNode = firstNode;
		// 删除第一个节点
		if (obj.equals(pNode.data)){
			if( firstNode.equals (lastNode)){
				firstNode = lastNode = null;
			}else{
				firstNode = firstNode.next;
			}
			currNode = firstNode;
			ret = true;
		}else{
			while (pNode != null && pNode.next != null){
				if (obj.equals(pNode.next.data)){
					pNode.next = pNode.next.next;
					ret = true;
					break;
				}
				pNode = pNode.next;
			}
			if (ret){
				currNode = pNode;
			}else {
				currNode = tmpListNode;
			}
		}
		if (ret){
			elementCount--;
		}
		return ret;
	}
	
	/*******************************************************************
	 * 函数名称	: indexOf
	 * 函数描述	: 计算元素在链表中的索引位置
	 * 输入参数	: @param data
	 * 输出参数	: void
	 * 返回值  	: int
	 * 备注		: N/A
	 ******************************************************************/
	public int indexOf(Object data){
		int index = -1;
		if (firstNode == null){
			return index;
		}
		currNode = firstNode;
		while (currNode != null){
			index++;
			if (currNode.data.equals(data)){
				break;
			}
			currNode = currNode.next;
		}
		if (currNode == null){
			return -1;
		}
		return index;
	}
	
	/*******************************************************************
	 * 函数名称	: insert
	 * 函数描述	: 在指定的位置插入元素
	 * 输入参数	: @param data
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void insert(Object data, int index){
		// 循环找到元素要插入位置的前一位置的元素
		ListNode tmp = firstNode;
		int k = 0;
		while (tmp != null && k < index - 1){
			tmp = tmp.next;
			k++;
		}
		// index大于链表的长度 ,插入失败
		if (tmp == null && firstNode != null){
			return;
		}
		// 构建要插入的新节点
		ListNode newNode = new ListNode(data);
		// 链表为空或插入位置为链表第一个元素之前
		if (firstNode == null || index == 0){
			// 新节点成为链表的第一个节点
			newNode.next = firstNode;
			// 如果原来的链表为空, 则链表的尾节点也为新节点
			if (firstNode == null){
				lastNode = newNode;
			}
			firstNode = newNode;
		}else {// 插入在链表的中间或尾部 
			newNode.next = tmp.next;
			if (tmp.next == null){
				lastNode = newNode;
			}
			tmp.next = newNode;
		}
		elementCount++;
	}
 
	/****************************************************************************************
	 * 函数名称：toVector
	 * 函数描述：将队列转换成vector 对象
	 * 输入参数：@return
	 * 输出参数：
	 * 返回    值：Vector<Object>
	 * 备         注：
	 ****************************************************************************************/
	public Vector<Object> toVector(){
		Vector<Object> items = new Vector<Object>(5,5);
		ListNode temp = firstNode;
		while(null != temp){
			items.add(temp.data);
			temp = temp.next;
		}
		return items;
		
	}
	/**
	 * 列表节点类
	 */
	class ListNode{
		/**
		 * 节点数据
		 */
		Object data;
		
		/**
		 * 指向下一节点的数据
		 */
		ListNode next;
		
		/*******************************************************************
		 * 函数名称	: ListNode
		 * 函数描述	: 构造函数
		 * 输入参数	: @param o 
		 * 输出参数	: void
		 * 返回值  	: N/A
		 * 备注		: N/A
		 ******************************************************************/
		ListNode( Object o){
			data = o;
			next = null; 
		} 
  
		/*******************************************************************
		 * 函数名称	: ListNode
		 * 函数描述	: 构造函数
		 * 输入参数	: @param o
		 * 输入参数	: @param nextNode 
		 * 输出参数	: void
		 * 返回值  	: N/A
		 * 备注		: N/A
		 ******************************************************************/
		ListNode( Object o, ListNode nextNode){
			data = o;
			next = nextNode; 
		}
	}

}

