package com.basic.test;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月12日 @此类的作用：
 */
public class Test {
	public static void main(String[] args) {
		int[] arr = new int[10];
		for (int i = 1; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 100);
			System.out.print(arr[i] + "\t");
		}
		System.out.println();
		for (int i = 1; i < arr.length; i++) {
			arr[i] = arr[i-1] > arr[i] ? arr[i-1] : arr[i];//前后2个数据对比，把大的放后面
		}
		System.out.println("max:" + arr[arr.length - 1]);
		for (int i = 1; i < arr.length; i++) {
			arr[i] = arr[i-1] < arr[i] ? arr[i-1] : arr[i];//前后2个数据对比，把小的放后面
		}
		System.out.println("min:" + arr[arr.length - 1]);
	}
}
