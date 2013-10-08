/**
 * 
 */
package edu.uci.isr.archstudio4.comp.tracelink.hypermedia;

//import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.part.FileEditorInput;


/**
 * @author dpurpura, Hazel
 */
@Deprecated //use HypermediaAdapter instead
public class MyEditor {
	
	//H: 5/23/08 
	public void run(String filename) throws PartInitException {
		System.err.println("running myeditor");
			
		//Need to check if the filename passed in is within the Eclipse workspace
		//case 1: component/connector
		
		if (filename.substring(0, 1).compareTo("#") == 0) {
			//TODO: call Archipelago's find component/connector function
		}
		
		//case 2: filename = URL
		else if (filename.substring(0, 7).compareTo("http://") == 0) {
			URL url;
			try {
				url = new URL(filename);
				IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
				IWebBrowser browser = browserSupport.createBrowser("myBrowser");
				browser.openURL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		}
		
		//case 3: filename = textfile outside the Eclipse workspace
		else if (filename.substring(0, 3).compareTo("C:\\") == 0) {
			try {
				   IWorkspace ws = ResourcesPlugin.getWorkspace();
				   //TODO: remove the hardcoded project name
				   IProject project = ws.getRoot().getProject("ProjectArtifacts");
				   if (!project.exists())
				      project.create(null);
				   if (!project.isOpen())
				      project.open(null);
				   IPath location = new Path(filename);
				   IFile ifile = project.getFile(location.lastSegment());
				   if (! ifile.exists() )
					   ifile.createLink(location, IResource.NONE, null);
				   IWorkbench workbench = PlatformUI.getWorkbench();
				   IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
				   IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(ifile.getName());
				   FileEditorInput fileEditorInput = new FileEditorInput(ifile);
				   if (page != null)
					   page.openEditor(fileEditorInput, desc.getId());

			}
			catch (CoreException e1) {
				e1.printStackTrace();
			}



			



		}
		
		//case 4: filename is within the Eclipse workspace
		//the filename should be a path with root = project name
		//this cannot be a hardcoded absolute path outside the Eclipse workspace since we will 
		//pass this into the IFile
		//final String filename = "/ProjectArtifacts/FunctionalSpecs.doc";
		else {
			// Open new file in editor
			IPath path = new Path(filename);
			
			//H: 5/23/08 IFile is strictly a path within the workspace.  Thus, not all URI have an equivalent IFile
			IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			//System.out.println(ifile.getFullPath());
			//System.out.println(ifile.getName());
			System.out.println(" " + ifile + " ifile exists: " + ifile.exists());  //ifile does not exist
			
			//H: TODO: Delete this if block?
			if (!ifile.exists()) {
				try {
					ifile.refreshLocal(IResource.DEPTH_INFINITE, null);
					
					//ifile.createLink(file.toURI(), IFile.ALLOW_MISSING_LOCAL, null);
					System.out.println(" " + ifile + " ifile exists: " + ifile.exists());
				}
				catch (CoreException e) {
					//e.printStackTrace();
					
//					try {
//						ifile.setContents(new FileInputStream(filename),
//								IFile.ALLOW_MISSING_LOCAL, null);
//					}
//					catch (FileNotFoundException e1) {
//						e1.printStackTrace();
//					}
//					catch (CoreException e1) {
//						e1.printStackTrace();
//					}
				}
			}
			
			try {
				
				FileEditorInput fileEditorInput = new FileEditorInput(ifile);
				
				// now that the FileEditorInput is created opening the Editor is trivial
				IWorkbench workbench = PlatformUI.getWorkbench();
				
				/*
				if (ifile.getFileExtension().compareToIgnoreCase("pdf") == 0 || 
					ifile.getFileExtension().compareToIgnoreCase("ppt") == 0 ||
					ifile.getFileExtension().compareToIgnoreCase("doc") == 0) {
					
					//The following works with opening pdf, ppt, doc
					IEditorDescriptor desc = workbench.getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
					IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();	
					System.out.println("editor: " + desc.getLabel());
					page.openEditor(fileEditorInput, desc.getId());
				}
				else {
					//The following works with html, txt, code
					//check if desc is null to handle pdf, ppt, etc.
					IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(ifile.getName());
					IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();	
					System.out.println("editor: " + desc.getLabel());
					page.openEditor(fileEditorInput, desc.getId());
				}
				*/
				//The following works with html, txt, code
				//check if desc is null to handle pdf, ppt, etc.
				IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(ifile.getName());
				if (desc == null) {
					desc = workbench.getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
				}
				IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();	
				System.out.println("editor: " + desc.getLabel());
				page.openEditor(fileEditorInput, desc.getId());
				
				
				

				
			}
			catch (CoreException e1) {
				e1.printStackTrace();
			}

		}
			
		
		
		
		
		
		
		/*
		 * WorkbenchAdvisor workbenchAdvisor = new MyWorkbenchAdvisor(); Display
		 * display = PlatformUI.createDisplay(); if
		 * (!PlatformUI.isWorkbenchRunning())
		 * PlatformUI.createAndRunWorkbench(display, workbenchAdvisor);
		 * IEditorDescriptor[] editors =
		 * PlatformUI.getWorkbench().getEditorRegistry().getEditors("word97.doc");
		 * for (IEditorDescriptor editor : editors) {
		 * System.out.println(editor.getLabel()); } editors =
		 * PlatformUI.getWorkbench().getEditorRegistry().getEditors("word07.docx");
		 * for (IEditorDescriptor editor : editors) {
		 * System.out.println(editor.getLabel()); } display.dispose();
		 */
	}
	
	public static void main(String[] args) {

		
	}
	
}
