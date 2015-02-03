package com.atomic.parallel.dfs;

import java.util.Calendar;

public class ParallelDFS_Main {

	public static void main(String[] args) {
		long start, finish;
		final int  numberOfNodes = 1000;
//		int counter = 0;//Node counter;
		final int numberOfProcessors = 10;
		
		boolean[] visited = new boolean[numberOfNodes];
		for(int i = 0; i<numberOfNodes; i++){
			visited[i] = false;
		}
		Graph graph = new Graph(numberOfNodes,visited,numberOfProcessors);
//		ConcurrentLinkedDeque<Integer> globalStack = new ConcurrentLinkedDeque<Integer>();
//		globalStack.addFirst(0);//Start node pushed to stack
		
		start = Calendar.getInstance().getTimeInMillis();
		Thread[] processors = new Processor[numberOfProcessors];
		for(int i = 0; i<numberOfProcessors; i++){
			processors[i] = new Processor(graph,numberOfProcessors,i);
			processors[i].start();
		}
		for(int i = 0; i<numberOfProcessors; i++){
			try {
				processors[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		finish = Calendar.getInstance().getTimeInMillis();
		System.out.println("Time "+(finish-start));
		graph.print();
	}

}
