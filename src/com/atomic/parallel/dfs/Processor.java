package com.atomic.parallel.dfs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Processor extends Thread {
	AtomicInteger[] load;
	AtomicBoolean[] waiting;
	AtomicBoolean done;
	private int threadNumber;
	private Graph graph;
	private int numberOfProcessor;
	public Processor(Graph graph,int numberOfProcessor,int id){
		load = new AtomicInteger[numberOfProcessor];
		waiting = new AtomicBoolean[numberOfProcessor];
		for(int i = 0;i<numberOfProcessor;i++){
			load[i] = new AtomicInteger();
			waiting[i] = new AtomicBoolean();
		}
		done = new AtomicBoolean();
		done.set(false);
		this.threadNumber = id;
		this.numberOfProcessor = numberOfProcessor; 
		this.graph = graph;
	}
	
	@Override
	public long getId() {
		return threadNumber;
	}

	@Override
	public void run() {
		while(done.get()==false){
			load[threadNumber].set(graph.dfs());
			for(int i = 1;load[i-1].get()==0 && i<numberOfProcessor;i++){
				if(i==(numberOfProcessor-1) && load[i].get()==0)
					done.set(true);
			}
			if(load[threadNumber].get()==0){
				waiting[threadNumber].set(true);
			}else{
				for(int i = 0;i<numberOfProcessor;i++){//sets waiting to false for all threads
					waiting[i].set(false);
				}
			}
			while(waiting[threadNumber].get()==true && done.get()==false){
				System.out.println("Im waiting - " + getThreadNumber());
			}
		}
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public int getNumberOfProcessor() {
		return numberOfProcessor;
	}

	public void setNumberOfProcessor(int numberOfProcessor) {
		this.numberOfProcessor = numberOfProcessor;
	}
	
}
