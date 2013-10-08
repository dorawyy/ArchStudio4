package edu.uci.isr.myx.fw.eclipse.bootstrap;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.osgi.service.environment.EnvironmentInfo;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import edu.uci.isr.archstudio4.comp.aim.IAIM;
import edu.uci.isr.archstudio4.comp.aimeclipse.AIMEclipseImpl;
import edu.uci.isr.myx.fw.IMyxRuntime;
import edu.uci.isr.myx.fw.MyxUtils;
import edu.uci.isr.xadlutils.XadlUtils;
import edu.uci.isr.xarchflat.ObjRef;
import edu.uci.isr.xarchflat.XArchFlatImpl;

public class Activator implements BundleActivator {

	private BundleContext context = null;

	public void start(BundleContext context) throws Exception {
		this.context = context;
		//		ServiceTracker environmentTracker = new ServiceTracker(context, EnvironmentInfo.class.getName(), null);
		//		environmentTracker.open();
		//		EnvironmentInfo environmentInfo = (EnvironmentInfo) environmentTracker.getService();
		//		environmentTracker.close();

		// retrieve the arguments
		String[] args;
		{
			ServiceReference environmentInfoServiceReference = context.getServiceReference(EnvironmentInfo.class.getName());
			EnvironmentInfo environmentInfo = (EnvironmentInfo) context.getService(environmentInfoServiceReference);
			args = environmentInfo.getNonFrameworkArgs();
			context.ungetService(environmentInfoServiceReference);

			if (args != null) {
				// skip any arguments before '--', if it exists
				List<String> argsList = Arrays.asList(args);
				int nonFrameworkArgsHintIndex = Arrays.asList(args).indexOf("--");
				if (nonFrameworkArgsHintIndex > -1) {
					argsList = argsList.subList(nonFrameworkArgsHintIndex + 1, argsList.size());
					args = argsList.toArray(new String[argsList.size()]);
				}
			}
		}

		printHeader();

		boolean argError = false;
		String xadlURLFile = null;
		String structureName = null;

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-s")) {
					if (++i == args.length) {
						argError = true;
						break;
					}
					if (structureName != null) {
						argError = true;
						break;
					}
					structureName = args[i];
				}
				else {
					if (xadlURLFile != null) {
						argError = true;
						break;
					}
					xadlURLFile = args[i];
				}
			}
		}

		if (xadlURLFile == null) {
			argError = true;
		}

		if (!argError) {
			try {
				doBootstrap(xadlURLFile, structureName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			printHelp(args);
		}
	}

	public void stop(BundleContext context) throws Exception {
	}

	private void printHeader() {
		System.out.println();
		System.out.println("------------------------------------------------------------------------");
		System.out.println("ArchStudio 4 Equinox Bootstrapper");
		System.out.println("Copyright (C) 2006 The Regents of the University of California.");
		System.out.println("All rights reserved worldwide.");
		System.out.println();
	}

	private void printHelp(String[] args) {
		System.err.println();
		System.err.println("Usage is: file|URL [-s structureName]");
		System.err.println();
		System.err.println("  where:");
		System.err.println("    file: the name of the xADL file to bootstrap");
		System.err.println("    URL: the URL of the xADL file to bootstrap");
		System.err.println("    structureName: the name of the structure to bootstrap");
		System.err.println();
		if (args != null) {
			System.err.println("arguments were: " + Arrays.asList(args));
		}
		//System.exit(-2);
	}

	private void doBootstrap(String xadlURLFile, String structureName) {
		try {
			IMyxRuntime myx = MyxUtils.getDefaultImplementation().createRuntime();
			for (final Bundle bundle : context.getBundles()) {
				myx.addClassLoader(new ClassLoader() {
					@Override
					protected URL findResource(String name) {
						return bundle.getResource(name);
					}

					@Override
					protected Class<?> findClass(String name) throws ClassNotFoundException {
						return bundle.loadClass(name);
					}

					@SuppressWarnings("unchecked")
					@Override
					protected Enumeration<URL> findResources(String name) throws IOException {
						return bundle.getResources(name);
					}
				});
			}

			XArchFlatImpl xarch = new XArchFlatImpl();
			IAIM aim = new AIMEclipseImpl(xarch);

			try {
				ObjRef xArchRef;
				try {
					xArchRef = xarch.parseFromURL(xadlURLFile);
				}
				catch (Exception e) {
					xArchRef = xarch.parseFromFile(xadlURLFile);
				}

				ObjRef typesContextRef = xarch.createContext(xArchRef, "types");
				ObjRef[] structureRefs = xarch.getAllElements(typesContextRef, "archStructure", xArchRef);

				if (structureRefs.length == 0) {
					throw new RuntimeException("Architecture has no structures to instantiate");
				}

				ObjRef structureRef = null;
				for (ObjRef structureRef2 : structureRefs) {
					String description = XadlUtils.getDescription(xarch, structureRef2);
					if (description != null && description.equals(structureName)) {
						structureRef = structureRef2;
						break;
					}
				}
				if (structureRef == null) {
					structureRef = structureRefs[0];
				}

				aim.instantiate(myx, "main", xArchRef, structureRef);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-3);
		}
	}
}
