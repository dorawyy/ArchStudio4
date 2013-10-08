/**
 * 
 */
package edu.uci.isr.archstudio4.comp.tracelink.tests;

import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * @author David A. Purpura
 *
 */
public class TestStats {
	
	private TestSuite tests;
	
	public TestStats() {
		this.tests = new TestSuite(JUnitTest.class);
	}
	
	public void printResults() {
		TestResult result  = new TestResult();
		tests.run(result);
		
		System.out.println("Expect \n" +
				"\t# of Tests:	3\n" +
				"\t# of Failures:	2\n" +
				"\t# of Errors:	0");
		
		System.out.println("Result");
		System.out.println("\t# of Tests:\t" + result.runCount());
		System.out.println("\t# of Failures:\t" + result.failureCount());
		System.out.println("\t# of Errors:\t" + result.errorCount());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestStats test = new TestStats();
		
		test.printResults();
		
	}
	
}
