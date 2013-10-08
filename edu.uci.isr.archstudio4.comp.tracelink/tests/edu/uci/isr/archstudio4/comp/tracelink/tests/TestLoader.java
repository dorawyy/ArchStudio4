package edu.uci.isr.archstudio4.comp.tracelink.tests;

import java.util.Vector;

import edu.uci.isr.archstudio4.comp.tracelink.models.ITracelinkElement;
import edu.uci.isr.archstudio4.comp.tracelink.models.SimpleTracelinkElement;




public class TestLoader {
	
	public static Vector<ITracelinkElement> loadLinks1(Vector<ITracelinkElement> linksModel) {
		ITracelinkElement link1 = new SimpleTracelinkElement();
		link1.setAttribute("name", "link1");
		link1.setAttribute("author", "david");
		link1.setAttribute("quality", "1");
		
		ITracelinkElement link2 = new SimpleTracelinkElement();
		link2.setAttribute("name", "link2");
		link2.setAttribute("author", "hazel");
		link2.setAttribute("quality", "0");
		
		ITracelinkElement link3 = new SimpleTracelinkElement();
		link3.setAttribute("name", "link3");
		link3.setAttribute("author", "scott");
		link3.setAttribute("quality", "1");
		
		linksModel.add(link1);
		linksModel.add(link2);
		linksModel.add(link3);
		
		System.out.println(linksModel);
		return linksModel;
	}
	
	public static Vector<ITracelinkElement> loadLinks2(Vector<ITracelinkElement> linksModel) {
		ITracelinkElement link1 = new SimpleTracelinkElement();
		link1.setAttribute("major", "CSE");
		link1.setAttribute("name", "joe");
		link1.setAttribute("year", "2009");
		
		ITracelinkElement link2 = new SimpleTracelinkElement();
		link2.setAttribute("major", "informatics");
		link2.setAttribute("name", "mary");
		link2.setAttribute("year", "2008");
		
		ITracelinkElement link3 = new SimpleTracelinkElement();
		link3.setAttribute("major", "ics");
		link3.setAttribute("name", "john");
		link3.setAttribute("year", "2007");
		link3.setAttribute("quality", "1");
		
		linksModel.add(link1);
		linksModel.add(link2);
		linksModel.add(link3);
		
		return linksModel;
	}
	
}
