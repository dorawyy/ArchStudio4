/**
 * 
 */
package edu.uci.isr.archstudio4.comp.tracelink.reports;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author dpurpura
 *
 */
public class ReportsViewTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.reports.ReportsView#begin()}.
	 */
	@Test
	public void testBegin() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.reports.ReportsView#getServiceObject(edu.uci.isr.myx.fw.IMyxName)}.
	 */
	@Test
	public void testGetServiceObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.reports.ReportsView#runReport(java.util.Vector, java.lang.String)}.
	 */
	@Test
	public void testRunReport() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.reports.ReportsView#selectElements(java.util.Vector, java.lang.String)}.
	 */
	@Test
	public void testSelectElements() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.reports.ReportsView#printReport(java.util.Vector)}.
	 */
	@Test
	public void testPrintReport() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.reports.ReportsView#humanize(java.lang.String)}.
	 */
	@Test
	public void testHumanize() {
		//test null
		assertEquals("", ReportsView.humanize(null));
		
		//test lengths
		assertEquals("A", ReportsView.humanize("a"));
		assertEquals("Ab", ReportsView.humanize("ab"));
		assertEquals("Abc", ReportsView.humanize("abc"));
		
		//test numbers
		assertEquals("1234", ReportsView.humanize("1234"));
		
		//test camel case
		assertEquals("David Purpura", ReportsView.humanize("davidPurpura"));
		assertEquals("David Anthony Purpura", ReportsView.humanize("davidAnthonyPurpura"));
	}

}
