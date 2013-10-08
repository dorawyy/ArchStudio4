/**
 * 
 */
package edu.uci.isr.archstudio4.comp.tracelink.models;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.uci.isr.archstudio4.comp.tracelink.analysis.IRulePart;
import edu.uci.isr.archstudio4.comp.tracelink.analysis.ITracelinkRule;
import edu.uci.isr.archstudio4.comp.tracelink.analysis.SimpleRuleObject;
import edu.uci.isr.archstudio4.comp.tracelink.analysis.SimpleRulePart;
import edu.uci.isr.archstudio4.comp.tracelink.models.XMLSerializer;

/**
 * @author dpurpura
 *
 */
public class XMLSerializerTest {

	IRulePart partA1;
	IRulePart partA2;
	IRulePart partA3;
	
	IRulePart partC1;
	IRulePart partC2;
	
	ITracelinkRule rule1;
	ITracelinkRule rule2;
	
	ArrayList<ITracelinkRule> rules;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		IRulePart[] refs = new IRulePart[0];
		
		partA1 = new SimpleRulePart("TraceEndpoint", "getLocationHref", new String[] { ".pdf" }, refs);
		partA2 = new SimpleRulePart("TraceEndpoint", "getLocationHref", new String[] { ".doc" }, refs);
		partA3 = new SimpleRulePart("TraceEndpoint", "getLocationHref", new String[] { ".xml" }, refs);
		
		partC1 = new SimpleRulePart("Tracelink", "setDescription", new String[] { "Requirements_Link" }, new IRulePart[] { partA1, partA2 });
	
		
		rule1 = new SimpleRuleObject(false, new IRulePart[] {partA1, partA2, partA3}, partC1);
	
		rules = new ArrayList<ITracelinkRule>();
		rules.add(rule1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.models.XMLSerializer#deserialize(java.lang.String)}.
	 */
	@Test
	public void testDeserialize() {
		try {
			Collection<ITracelinkRule> rules = XMLSerializer.deserialize("test.xml");
			//throw new FileNotFoundException();
			
			for (ITracelinkRule rule : rules)
				System.out.println(rule);
			
		}
		catch (FileNotFoundException e) {
			System.err.println("text.xml for XMLSerializer deserialize test does not exist");
		}
	}

	/**
	 * Test method for {@link edu.uci.isr.archstudio4.comp.tracelink.models.XMLSerializer#serialize(java.lang.String, java.util.Collection)}.
	 */
	@Test
	public void testSerialize() {
		try {
			XMLSerializer.serialize("test.xml", rules);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
