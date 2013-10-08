/**
 * 
 */
package edu.uci.isr.archstudio4.comp.tracelink.hypermedia;

/**
 * @author David A. Purpura
 *
 */
public interface ToolAdapter {
	
	/**
	 * Opens a file in a specific location & highlight an item
	 * @param fileURI uri of the file
	 */
	public void render (String fileURI);
	
	
	/**
	 * Searches for a given item in the file; returns true if the item is found
	 * @param searchItem  the item to search for
	 * @return true if the item is found; otherwise false;
	 */
	public boolean isFound (String searchItem);
	
	
	/**
	 * Runs a script different from the rendering tool
	 * @param executableURI uri of the file to execute
	 */
	public void execute(String executableURI);
}
