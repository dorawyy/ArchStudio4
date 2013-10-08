package edu.uci.isr.apigen.gui;

import java.io.*;
import edu.uci.isr.apigen.*;

class GeneratorThread extends Thread{

	private SchemaLocationMap map;
	private File dir;
	private ApiGen apigen;
	
	private Object lock = new Object();
	private Object doneLock = new Object();
	private String processMe;
	private boolean lastDone;
	private Exception lastResult;
	
	public GeneratorThread(SchemaLocationMap map, File dir){
		this.map = map;
		this.dir = dir;
		this.processMe = null;
		this.lastResult = null;
		this.lastDone = false;
		apigen = new ApiGen(map, dir);
	}
	
	public void process(String uri){
		lastResult = null;
		lastDone = false;
		processMe = uri;
		synchronized(lock){
			lock.notify();
		}
	}
	
	public void run(){
		while(true){
			while(processMe == null){
				synchronized(lock){
					try{
						lock.wait();
					}
					catch(InterruptedException e){}
				}
			}
			try{
				apigen.processSchema(processMe);
			}
			catch(Exception e){
				lastResult = e;
			}
			processMe = null;
			lastDone = true;
			synchronized(doneLock){
				doneLock.notifyAll();
			}
		}
	}
	
	public void waitUntilDone(){
		while(!lastDone){
			synchronized(doneLock){
				try{
					doneLock.wait();
				}
				catch(InterruptedException e){}
			}
		}
		apigen.generateNoPrefix();
	}

	public Exception getLastResult(){
		return lastResult;
	}
}

