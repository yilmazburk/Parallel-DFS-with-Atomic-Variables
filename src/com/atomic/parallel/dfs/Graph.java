package com.atomic.parallel.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

public class Graph {
	private int size;//Number of Nodes
	private int[][] vertices;//Adjacency Matrix
	private Stack<Integer> globalStack;
	private List<Stack<Integer>> localStacks;
	private boolean[] visited;
	private boolean isDone;
	private int counter;
	public Graph(int size,boolean[] visited,int numberOfProcessors){
		this.size = size;
		localStacks = new ArrayList<Stack<Integer>>(numberOfProcessors);
		for(int i=0;i<numberOfProcessors;i++){
			localStacks.add(new Stack<Integer>());
		}
		vertices = new int[size][size];
		this.visited = visited;
		isDone = false;
		globalStack = new Stack<Integer>();
		globalStack.push(size-1);
		setCounter(0);
		for(int i = 0; i<this.size; i++)
			for(int j = 0; j<this.size; j++){
				Random boolNumber = new Random();
                boolean edge = boolNumber.nextBoolean();
                if(i==j)
                	vertices[i][j]=1;
                else	
                	vertices[i][j]=edge ? 1 : 0;
			}
	}
	public void print() {
		for(int i = 0;i<size;i++){
			if(!visited[i]){
				System.out.println("Failure");
				break;
			}
		}
		System.out.println("Success ");
	}
	public int getSize(){
		return size;
	}
	
	public boolean isNeighbour(int node, int neighbour){
		return vertices[node][neighbour]==1 ? true : false;
	}
	
	public int dfs(){
		System.out.println("Current core: " + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
		int node;
		if(!globalStack.isEmpty()){
			node = globalStack.pop();
			while(visited[node]){
				if(globalStack.isEmpty()){
					return 0;
				}else{
					node = globalStack.pop();
				}
			}
			visited[node] = true;
			for(int i = 0;i<size;i++){
				if(node==i)continue;
				if(isNeighbour(node, i) && !visited[i]){
					globalStack.push(i);
				}
			}
			return globalStack.size();
		}else{
			return 0;
		}

	}
	public boolean isDone() {
		return isDone;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
}
