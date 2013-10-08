package edu.uci.isr.apigen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ApiGen {

	public static final String PRODUCT_NAME = "Apigen";
	public static final String PRODUCT_VERSION = "build " + VersionInfo.getVersion("[unofficial-build]");

	protected static final String packageRootDirName = "edu" + File.separatorChar + "uci" + File.separatorChar + "isr" + File.separatorChar + "xarch";

	protected SchemaLocationMap locMap;
	protected SchemaMap schemaMap;
	protected File dir;

	public static void main(String[] args) {
		if (args.length == 0) {
			ApiGen.argError(args);
			return;
		}

		try {
			String fileName = null;
			String outDir = null;
			List mapAdditions = new ArrayList();
			List remapLocations = new ArrayList();
			List schemaAdditions = new ArrayList();
			boolean canonicalLocations = false;
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-auto")) {
					i++;
					fileName = args[i];
				}
				else if (args[i].equals("-out")) {
					i++;
					outDir = args[i];
				}
				else if (args[i].equals("-map")) {
					i++;
					mapAdditions.add(args[i]);
					i++;
					mapAdditions.add(args[i]);
				}
				else if (args[i].equals("-remap")) {
					i++;
					remapLocations.add(args[i]);
					i++;
					remapLocations.add(args[i]);
				}
				else if (args[i].equals("-process")) {
					i++;
					schemaAdditions.add(args[i]);
				}
				else if (args[i].equals("-official")) {
					canonicalLocations = true;
				}
				else {
					ApiGen.argError(args);
					return;
				}
			}
			if (fileName != null || outDir != null && mapAdditions.size() > 0 && schemaAdditions.size() > 0) {
				ApiGen.autoGenerate(fileName, outDir, mapAdditions, remapLocations, schemaAdditions, canonicalLocations);
				return;
			}
			ApiGen.argError(args);
		}
		catch (ArrayIndexOutOfBoundsException ae) {
			ApiGen.argError(args);
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static void argError(String[] args) {
		System.err.println("---");
		System.err.println(ApiGen.PRODUCT_NAME + " " + ApiGen.PRODUCT_VERSION);
		System.err.println("---");
		if (args.length > 0) {
			System.err.println("Invalid arguments " + Arrays.asList(args));
			System.err.println("");
		}
		System.err.println("Options: ");
		System.err.println("");
		System.err.println("  Start with GUI:");
		System.err.println("    java edu.uci.isr.apigen.gui.ApiGenGui");
		System.err.println("  Auto-generate based on existing settings:");
		System.err.println("    java edu.uci.isr.apigen.ApiGen [-auto settingsFile] [-map schemaURI schemaLocation] [-process schemaURI]");
		System.err.println("                                   [-out outputDirectory] [-official]");
		System.err.println("");
	}

	public static void autoGenerate(String fileName, String outDir, List mapAdditions, List<String> remapLocations, List schemaAdditions,
	        boolean canonicalLocations) {
		try {
			SchemaLocationMap slm = new SchemaLocationMap();
			if (canonicalLocations) {
				slm.setLocationsAreCanonical(true);
			}
			List schemaURIs = new ArrayList();
			File dir = null;
			if (fileName != null) {
				File f = new File(fileName);
				if (!f.exists()) {
					System.err.println("Specified settings file does not exist. (" + fileName + ")");
					return;
				}
				if (!f.canRead()) {
					System.err.println("Can't read specified settings file. (" + fileName + ")");
					return;
				}
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				slm = (SchemaLocationMap) ois.readObject();
				schemaURIs = new ArrayList(Arrays.asList((String[]) ois.readObject()));
				dir = (File) ois.readObject();
				fis.close();
				ois.close();
			}
			if (outDir != null) {
				dir = new File(outDir);
			}
			if (!dir.exists()) {
				System.err.println("Output directory does not exist. (" + dir.getPath() + ")");
				return;
			}
			for (Iterator i = mapAdditions.iterator(); i.hasNext();) {
				String schemaURI = (String) i.next();
				String schemaLocation = (String) i.next();
				System.out.println("Determining location type: " + schemaLocation);
				try {
					File file = new File(schemaLocation);
					if (!file.isFile() || !file.exists()) {
						throw new Exception("Not a file");
					}
					slm.addLocation(schemaURI, file);
				}
				catch (Exception e1) {
					try {
						URL url = new URL(schemaLocation);
						slm.addLocation(schemaURI, url);
						schemaURIs.add(schemaURI);
					}
					catch (Exception e2) {
						System.err.println("Can't determine location type. (" + schemaLocation + ")");
						return;
					}
				}
			}
			schemaURIs.addAll(schemaAdditions);
			for (int i = 0; i < remapLocations.size();) {
				String from = remapLocations.get(i++);
				String to = remapLocations.get(i++);
				slm.addRemap(from, to);
			}
			ApiGen apigen = new ApiGen(slm, dir);
			for (Iterator i = schemaURIs.iterator(); i.hasNext();) {
				String schemaURI = (String) i.next();
				System.out.println();
				System.out.println("Processing schema: " + schemaURI);
				apigen.processSchema(schemaURI);
			}
			apigen.generateNoPrefix();
			System.err.println("");
			System.err.println("Done processing.");
		}
		catch (Exception e) {
			System.err.println("Error processing schemas: ");
			e.printStackTrace();
			return;
		}
	}

	public void generateNoPrefix() {
		try {
			File baseUtilsDir = new File(dir, ApiGen.packageRootDirName);
			File fNoPrefix = new File(baseUtilsDir, "noprefix.txt");
			FileWriter fw = new FileWriter(fNoPrefix, false);
			String noPrefix = locMap.getNoPrefix();
			String[] ns = noPrefix.split(" +");
			for (String element : ns) {
				fw.write(element);
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		}
		catch (IOException ioe) {
			throw new SchemaProcessingException(ioe.toString());
		}
	}

	private static void loadSettings(File f) throws Exception {
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		SchemaLocationMap slm = (SchemaLocationMap) ois.readObject();
		String[] schemaURIs = (String[]) ois.readObject();
		File dir = (File) ois.readObject();
		fis.close();
		ois.close();
	}

	/*
	 * public static void main(String[] args){ SchemaLocationMap map = new
	 * SchemaLocationMap(); try{
	 * map.addLocation("http://www.ics.uci.edu/pub/arch/xArch/instance.xsd", new
	 * URL("http://www.ics.uci.edu/~edashofy/xArch/instance.xsd"));
	 * map.addLocation("http://www.ics.uci.edu/pub/arch/xArch/types.xsd", new
	 * URL("http://www.ics.uci.edu/~edashofy/xArch/types.xsd"));
	 * map.addLocation(
	 * "http://www.ics.uci.edu/pub/arch/xArch/implementation.xsd", new
	 * URL("http://www.ics.uci.edu/~edashofy/xArch/implementation.xsd"));
	 * map.addLocation("http://www.ics.uci.edu/pub/arch/xArch/options.xsd", new
	 * URL("http://www.ics.uci.edu/~edashofy/xArch/options.xsd"));
	 * map.addLocation("http://www.ics.uci.edu/pub/arch/xArch/variants.xsd", new
	 * URL("http://www.ics.uci.edu/~edashofy/xArch/variants.xsd"));
	 * map.addLocation("http://www.ics.uci.edu/pub/arch/xArch/versions.xsd", new
	 * URL("http://www.ics.uci.edu/~edashofy/xArch/versions.xsd")); }
	 * catch(MalformedURLException e){ e.printStackTrace(); return; } ApiGen
	 * apigen = new ApiGen(map, new File("D:\\xArchTemp"));
	 * apigen.processSchema(
	 * "http://www.ics.uci.edu/pub/arch/xArch/instance.xsd");
	 * apigen.processSchema("http://www.ics.uci.edu/pub/arch/xArch/types.xsd");
	 * apigen
	 * .processSchema("http://www.ics.uci.edu/pub/arch/xArch/implementation.xsd"
	 * );
	 * apigen.processSchema("http://www.ics.uci.edu/pub/arch/xArch/options.xsd"
	 * );
	 * apigen.processSchema("http://www.ics.uci.edu/pub/arch/xArch/variants.xsd"
	 * );
	 * apigen.processSchema("http://www.ics.uci.edu/pub/arch/xArch/versions.xsd"
	 * ); }
	 */

	public ApiGen(SchemaLocationMap locMap, File dir) {
		this.locMap = locMap;
		this.dir = dir;

		if (!dir.exists()) {
			throw new IllegalArgumentException("Destination directory does not exist.");
		}
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Destination directory must be a directory.");
		}
		this.schemaMap = new SchemaMap();
	}

	protected String getPackageTitle(String schemaURI) {
		String preSetName = locMap.getPackageName(schemaURI);
		if (preSetName != null && !preSetName.trim().equals("")) {
			return preSetName;
		}

		String packageTitle = schemaURI;
		int lastSlashIndex = schemaURI.lastIndexOf("/");
		int otherLastSlashIndex = schemaURI.lastIndexOf(File.separator);
		if (otherLastSlashIndex > lastSlashIndex) {
			lastSlashIndex = otherLastSlashIndex;
		}
		if (lastSlashIndex > -1) {
			packageTitle = schemaURI.substring(lastSlashIndex + 1);
		}
		if (packageTitle.toLowerCase().endsWith(".xsd")) {
			packageTitle = packageTitle.substring(0, packageTitle.length() - 4);
		}
		return packageTitle;
	}

	protected void mapSchema(String uri) {
		try {
			Reader r = locMap.openLocation(uri);
			Document doc = ApiGenUtils.parseToDocument(r);
			schemaMap.addSchema(uri, new XsdSchema(uri, doc));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SchemaProcessingException(e.getMessage());
		}
	}

	protected void copyFile(File baseUtilsDir, String outFile, String inFile, boolean incCopyright) {
		File file = new File(baseUtilsDir, outFile);
		if (incCopyright) {
			writeFile(file, ApiGenUtils.openResource("data/Copyright.dat"), false);
			writeFile(file, ApiGenUtils.openResource("data/" + inFile), true);
		}
		else {
			writeFile(file, ApiGenUtils.openResource("data/" + inFile), false);
		}
	}

	protected void copyFile(File baseUtilsDir, String outFile, boolean incCopyright) {
		int dot = outFile.lastIndexOf('.');
		copyFile(baseUtilsDir, outFile, outFile.substring(0, dot) + ".dat", incCopyright);
	}

	protected void readInto(StringBuffer sb, TokenMap tokMap, String dataFile) {
		Reader reader = ApiGenUtils.openResource("data/" + dataFile);
		TokenReplacerReader ttr = new TokenReplacerReader(reader, tokMap);
		readIntoBuffer(sb, ttr);
	}

	public void processSchema(String uri) {
		//Map the schema and parse it into a document.
		mapSchema(uri);

		//Figure out what the package name for this schema will be
		String packageTitle = getPackageTitle(uri);
		String packageName = "edu.uci.isr.xarch." + packageTitle;
		File packageDir = new File(dir, ApiGen.packageRootDirName + File.separatorChar + packageTitle);
		if (!packageDir.exists()) {
			if (!packageDir.mkdirs()) {
				throw new IllegalArgumentException("Couldn't create destination directories.");
			}
		}

		File baseUtilsDir = new File(dir, ApiGen.packageRootDirName);

		//Copy the utility files into the new directory.
		copyFile(baseUtilsDir, "DOMBased.java", true);
		copyFile(baseUtilsDir, "DOMUtils.java", true);
		copyFile(baseUtilsDir, "IXArch.java", true);
		copyFile(baseUtilsDir, "QName.java", true);
		copyFile(baseUtilsDir, "SequenceOrder.java", true);
		copyFile(baseUtilsDir, "SimpleNodeList.java", true);
		copyFile(baseUtilsDir, "XArchConstants.java", true);
		copyFile(baseUtilsDir, "XArchEvent.java", true);
		copyFile(baseUtilsDir, "FixedValueException.java", true);
		copyFile(baseUtilsDir, "XArchImpl.java", true);
		copyFile(baseUtilsDir, "XArchUtils.java", true);
		copyFile(baseUtilsDir, "IXArchContext.java", true);
		copyFile(baseUtilsDir, "IXArchElement.java", true);
		copyFile(baseUtilsDir, "XArchListener.java", true);
		copyFile(baseUtilsDir, "StringReplacerReader.java", true);
		copyFile(baseUtilsDir, "XArchContextCache.java", true);
		copyFile(baseUtilsDir, "DOMBasedXArchImplementation.java", true);
		copyFile(baseUtilsDir, "IXArchImplementation.java", true);
		copyFile(baseUtilsDir, "XArchParseException.java", true);
		copyFile(baseUtilsDir, "XArchSerializeException.java", true);
		copyFile(baseUtilsDir, "XArchTypeMetadata.java", true);
		copyFile(baseUtilsDir, "XArchActionMetadata.java", true);
		copyFile(baseUtilsDir, "XArchPropertyMetadata.java", true);
		copyFile(baseUtilsDir, "XArchInstanceMetadata.java", true);
		copyFile(baseUtilsDir, "XArchMetadataUtils.java", true);
		copyFile(baseUtilsDir, "FilterIterator.java", true);
		copyFile(baseUtilsDir, "package.html", "XArchPackageDoc.dat", false);

		//Generate the package constants file for this schema.
		TokenMap tokMap = new TokenMap();
		tokMap.addToken("GENERATOR_NAME", ApiGen.PRODUCT_NAME);
		tokMap.addToken("GENERATOR_VERSION", ApiGen.PRODUCT_VERSION);
		tokMap.addToken("PACKAGE_NAME", packageName);
		tokMap.addToken("PACKAGE_TITLE", packageTitle);
		tokMap.addToken("PACKAGE_TITLE_CAP", ApiGen.capFirstLetter(packageTitle));
		tokMap.addToken("PACKAGE_CONSTANTS_CLASS_NAME", ApiGen.capFirstLetter(packageTitle) + "Constants");
		tokMap.addToken("PACKAGE_CONTEXT_CLASS_NAME", ApiGen.capFirstLetter(packageTitle) + "Context");
		tokMap.addToken("PACKAGE_CONTEXT_INTERFACE_NAME", "I" + ApiGen.capFirstLetter(packageTitle) + "Context");
		tokMap.addToken("SCHEMA_URI", uri);

		String actualLocation;
		if (!locMap.locationsAreCanonical()) {
			actualLocation = uri;
		}
		else {
			Object o = locMap.getValue(uri);
			if (o instanceof URL) {
				actualLocation = o.toString();
			}
			else {
				actualLocation = uri;
			}
		}

		tokMap.addToken("SCHEMA_ACTUAL_LOCATION", actualLocation);

		File constantsFile = new File(packageDir, ApiGen.capFirstLetter(packageTitle) + "Constants.java");
		Reader constantsReader = ApiGenUtils.openResource("data/PackageConstants.dat");
		TokenReplacerReader constantsTrr = new TokenReplacerReader(constantsReader, tokMap);
		writeFile(constantsFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(constantsFile, constantsTrr, true);

		File docFile = new File(packageDir, "package.html");
		Reader docReader = ApiGenUtils.openResource("data/PackageDoc.dat");
		TokenReplacerReader docTrr = new TokenReplacerReader(docReader, tokMap);
		// writeFile(docFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(docFile, docTrr, false);

		XsdSchema schema = schemaMap.getSchema(uri);

		//Process simple types
		Collection simpleTypeColl = schema.getSimpleTypes();
		for (Iterator en = simpleTypeColl.iterator(); en.hasNext();) {
			XsdSimpleType simpleType = (XsdSimpleType) en.next();
			processSimpleType(simpleType, schema, tokMap, packageDir);
		}

		//Process complex types
		Collection complexTypeColl = schema.getComplexTypes();
		for (Iterator en = complexTypeColl.iterator(); en.hasNext();) {
			XsdComplexType complexType = (XsdComplexType) en.next();
			processComplexType(complexType, schema, tokMap, packageDir);
		}

		//Now we have to generate the Package context and context interface files.
		StringBuffer contextBuf = new StringBuffer();
		Reader contextHeaderReader = ApiGenUtils.openResource("data/ContextHeader.dat");
		TokenReplacerReader contextHeaderTrr = new TokenReplacerReader(contextHeaderReader, tokMap);
		readIntoBuffer(contextBuf, contextHeaderTrr);
		// ... and its interface 
		StringBuffer contextInterfaceBuf = new StringBuffer();
		Reader contextInterfaceHeaderReader = ApiGenUtils.openResource("data/ContextInterfaceHeader.dat");
		TokenReplacerReader contextInterfaceHeaderTrr = new TokenReplacerReader(contextInterfaceHeaderReader, tokMap);
		readIntoBuffer(contextInterfaceBuf, contextInterfaceHeaderTrr);
		// collect metadat
		StringBuffer contextMetadata = new StringBuffer();

		//Start out by finding out about all the types that are used
		//in this schema.  Some of these are defined in this
		//schema, some of them extend types defined in this
		//and other schemas, and some of them are imported
		//directly from other schemas.
		Collection usedXsdTypes = getAllXsdTypesUsed(schema);
		for (Iterator en = usedXsdTypes.iterator(); en.hasNext();) {
			UsedXsdType usedXsdType = (UsedXsdType) en.next();
			System.out.println("Processing: " + usedXsdType);

			TokenMap typeTokMap = new TokenMap(tokMap);

			String typeInterfaceName = null;
			String typeImplName = null;
			String baseTypeInterfaceName = null;
			String baseTypeImplName = null;

			if (usedXsdType.namespaceURI.equals(schema.getURI())) {
				typeInterfaceName = "I" + ApiGen.capFirstLetter(usedXsdType.typeName);
				typeImplName = ApiGen.capFirstLetter(usedXsdType.typeName) + "Impl";
			}
			else if (usedXsdType.namespaceURI.equals(XArchConstants.XSD_NS_URI)) {
				//For xsd:strings and xsd:decimals, etc.
				typeInterfaceName = "IXSD" + usedXsdType.typeName;
				typeImplName = "XSD" + usedXsdType.typeName + "Impl";
				usedXsdType.typeName = "XSD" + usedXsdType.typeName;
			}
			else {
				typeInterfaceName = "edu.uci.isr.xarch." + getPackageTitle(usedXsdType.namespaceURI) + ".I" + ApiGen.capFirstLetter(usedXsdType.typeName);
				typeImplName = "edu.uci.isr.xarch." + getPackageTitle(usedXsdType.namespaceURI) + "." + ApiGen.capFirstLetter(usedXsdType.typeName) + "Impl";
			}

			if (usedXsdType.baseTypeName != null) {
				if (usedXsdType.baseTypeNamespaceURI.equals(schema.getURI())) {
					baseTypeInterfaceName = "I" + ApiGen.capFirstLetter(usedXsdType.baseTypeName);
					baseTypeImplName = ApiGen.capFirstLetter(usedXsdType.baseTypeName) + "Impl";
				}
				else {
					baseTypeInterfaceName = "edu.uci.isr.xarch." + getPackageTitle(usedXsdType.baseTypeNamespaceURI) + ".I"
					        + ApiGen.capFirstLetter(usedXsdType.baseTypeName);
					baseTypeImplName = "edu.uci.isr.xarch." + getPackageTitle(usedXsdType.baseTypeNamespaceURI) + "."
					        + ApiGen.capFirstLetter(usedXsdType.baseTypeName + "Impl");
				}
			}

			typeTokMap.addToken("TYPE_NAME", usedXsdType.typeName);
			typeTokMap.addToken("TYPE_NAME_CAPPED", ApiGen.capFirstLetter(usedXsdType.typeName));
			typeTokMap.addToken("TYPE_INTERFACE_NAME", typeInterfaceName);
			typeTokMap.addToken("TYPE_IMPL_NAME", typeImplName);

			if (baseTypeInterfaceName != null) {
				typeTokMap.addToken("BASE_TYPE_NAME", usedXsdType.baseTypeName);
				typeTokMap.addToken("BASE_TYPE_NAME_CAPPED", ApiGen.capFirstLetter(usedXsdType.baseTypeName));
				typeTokMap.addToken("BASE_TYPE_INTERFACE_NAME", baseTypeInterfaceName);
				typeTokMap.addToken("BASE_TYPE_IMPL_NAME", baseTypeImplName);
				String baseTypeContext = "edu.uci.isr.xarch." + getPackageTitle(usedXsdType.baseTypeNamespaceURI) + "."
				        + ApiGen.capFirstLetter(getPackageTitle(usedXsdType.baseTypeNamespaceURI)) + "Context";
				typeTokMap.addToken("BASE_TYPE_CONTEXT_CLASS_NAME", baseTypeContext);
			}

			//For every type, we have to make
			//a "create" function for it.
			Reader contextCreateReader = ApiGenUtils.openResource("data/ContextCreate.dat");
			TokenReplacerReader contextCreateTrr = new TokenReplacerReader(contextCreateReader, typeTokMap);
			readIntoBuffer(contextBuf, contextCreateTrr);
			// ... and its interface 
			Reader contextInterfaceCreateReader = ApiGenUtils.openResource("data/InterfaceCreate.dat");
			TokenReplacerReader contextInterfaceCreateTrr = new TokenReplacerReader(contextInterfaceCreateReader, typeTokMap);
			readIntoBuffer(contextInterfaceBuf, contextInterfaceCreateTrr);
			// metadata
			typeTokMap.addToken("ACTION_TYPE", "XArchActionMetadata.CREATE");
			typeTokMap.remove("ACTION_SOURCE_TYPE");
			typeTokMap.addToken("ACTION_DEST_TYPE", typeInterfaceName + ".TYPE_METADATA");
			readInto(contextMetadata, typeTokMap, "ContextInterfaceMetadataAction.dat");
			contextMetadata.append(",");

			//We need to create a recontextualize function for each type, too.
			//In older versions of apigen, the recontextualize function was only
			//provided for types declared in another namespace, but it was found
			//that sometimes you want to recontextualize an element BACK into its
			//original context, so we need to have it in every context where it's used.

			//if(!usedXsdType.namespaceURI.equals(schema.getURI())){
			Reader contextRecontextReader = ApiGenUtils.openResource("data/ContextRecontextualize.dat");
			TokenReplacerReader contextRecontextTrr = new TokenReplacerReader(contextRecontextReader, typeTokMap);
			readIntoBuffer(contextBuf, contextRecontextTrr);
			// ... and its interface 
			Reader contextInterfaceRecontextReader = ApiGenUtils.openResource("data/InterfaceRecontextualize.dat");
			TokenReplacerReader contextInterfaceRecontextTrr = new TokenReplacerReader(contextInterfaceRecontextReader, typeTokMap);
			readIntoBuffer(contextInterfaceBuf, contextInterfaceRecontextTrr);
			//}
			// metadata
			typeTokMap.addToken("ACTION_TYPE", "XArchActionMetadata.RECONTEXTUALIZE");
			typeTokMap.addToken("ACTION_SOURCE_TYPE", typeTokMap.getValue("TYPE_INTERFACE_NAME") + ".TYPE_METADATA");
			typeTokMap.addToken("ACTION_DEST_TYPE", typeTokMap.getValue("TYPE_INTERFACE_NAME") + ".TYPE_METADATA");
			readInto(contextMetadata, typeTokMap, "ContextInterfaceMetadataAction.dat");
			contextMetadata.append(",");

			//If the type extends another type, we have to create
			//a promotion function for it.
			if (usedXsdType.baseTypeNamespaceURI != null) {
				Reader contextPromoteReader = ApiGenUtils.openResource("data/ContextPromote.dat");
				TokenReplacerReader contextPromoteTrr = new TokenReplacerReader(contextPromoteReader, typeTokMap);
				readIntoBuffer(contextBuf, contextPromoteTrr);
				// ... and its interface 
				Reader contextInterfacePromoteReader = ApiGenUtils.openResource("data/InterfacePromote.dat");
				TokenReplacerReader contextInterfacePromoteTrr = new TokenReplacerReader(contextInterfacePromoteReader, typeTokMap);
				readIntoBuffer(contextInterfaceBuf, contextInterfacePromoteTrr);
				// metadata
				typeTokMap.addToken("ACTION_TYPE", "XArchActionMetadata.PROMOTE");
				typeTokMap.addToken("ACTION_SOURCE_TYPE", typeTokMap.getValue("BASE_TYPE_INTERFACE_NAME") + ".TYPE_METADATA");
				typeTokMap.addToken("ACTION_DEST_TYPE", typeTokMap.getValue("TYPE_INTERFACE_NAME") + ".TYPE_METADATA");
				readInto(contextMetadata, typeTokMap, "ContextInterfaceMetadataAction.dat");
				contextMetadata.append(",");
			}
		}

		//Now we have to process the top level elements...
		Collection topLevelElements = schema.getElements();
		for (Iterator en = topLevelElements.iterator(); en.hasNext();) {
			XsdElement elt = (XsdElement) en.next();
			String eltName = elt.getName();
			String eltType = elt.getType();

			if (eltName.equals("xArch")) {
				continue;
			}

			TokenMap eltTokenMap = new TokenMap(tokMap);
			eltTokenMap.addToken("ELEMENT_NAME", eltName);
			eltTokenMap.addToken("ELEMENT_TYPE", eltType);
			eltTokenMap.addToken("ELEMENT_TYPE_CAPPED", ApiGen.capFirstLetter(eltType));
			eltTokenMap.addToken("ELEMENT_TYPE_INTERFACE_NAME", "I" + ApiGen.capFirstLetter(eltType));
			eltTokenMap.addToken("ELEMENT_TYPE_IMPL_NAME", ApiGen.capFirstLetter(eltType) + "Impl");

			Reader contextTleReader = ApiGenUtils.openResource("data/ContextTopLevelElement.dat");
			TokenReplacerReader contextTleTrr = new TokenReplacerReader(contextTleReader, eltTokenMap);
			readIntoBuffer(contextBuf, contextTleTrr);
			// ... and its interface 
			Reader contextInterfaceTleReader = ApiGenUtils.openResource("data/InterfaceTopLevelElement.dat");
			TokenReplacerReader contextInterfaceTleTrr = new TokenReplacerReader(contextInterfaceTleReader, eltTokenMap);
			readIntoBuffer(contextInterfaceBuf, contextInterfaceTleTrr);
			// metadata
			eltTokenMap.addToken("ACTION_TYPE", "XArchActionMetadata.CREATE_ELEMENT");
			eltTokenMap.remove("ACTION_SOURCE_TYPE");
			eltTokenMap.addToken("ACTION_DEST_TYPE", eltTokenMap.getValue("ELEMENT_TYPE_INTERFACE_NAME") + ".TYPE_METADATA");
			readInto(contextMetadata, eltTokenMap, "ContextInterfaceMetadataAction.dat");
			contextMetadata.append(",");
		}

		if (contextMetadata.length() > 0) {
			contextMetadata.delete(contextMetadata.length() - 1, contextMetadata.length());
		}
		tokMap.addToken("CONTEXT_METADATA_ITEMS", contextMetadata.toString());
		readInto(contextInterfaceBuf, tokMap, "ContextInterfaceMetadata.dat");

		Reader contextFooterReader = ApiGenUtils.openResource("data/ContextFooter.dat");
		TokenReplacerReader contextFooterTrr = new TokenReplacerReader(contextFooterReader, tokMap);
		readIntoBuffer(contextBuf, contextFooterTrr);
		// ... and its interface 
		Reader contextInterfaceFooterReader = ApiGenUtils.openResource("data/ContextInterfaceFooter.dat");
		TokenReplacerReader contextInterfaceFooterTrr = new TokenReplacerReader(contextInterfaceFooterReader, tokMap);
		readIntoBuffer(contextInterfaceBuf, contextInterfaceFooterTrr);

		File contextFile = new File(packageDir, ApiGen.capFirstLetter(packageTitle) + "Context.java");
		writeFile(contextFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(contextFile, ApiGen.nullRemainingVars(contextBuf.toString()), true);
		// ... and its interface 
		File contextInterfaceFile = new File(packageDir, "I" + ApiGen.capFirstLetter(packageTitle) + "Context.java");
		writeFile(contextInterfaceFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(contextInterfaceFile, ApiGen.nullRemainingVars(contextInterfaceBuf.toString()), true);

		//Prepare the package list file
		File fPackageList = new File(baseUtilsDir, "packagelist.txt");
		try {
			addPackageToPackageList(fPackageList, packageName);
		}
		catch (IOException ioe) {
			throw new SchemaProcessingException(ioe.toString());
		}
	}

	protected void addPackageToPackageList(File packageListFile, String packageName) throws IOException {
		if (packageListFile.exists()) {
			FileReader fr = new FileReader(packageListFile);
			BufferedReader br = new BufferedReader(fr);
			while (true) {
				String line = br.readLine();
				if (line == null) {
					fr.close();
					br.close();
					break;
				}
				else if (line.trim().equals(packageName)) {
					//It's already in there.
					fr.close();
					br.close();
					return;
				}
			}
		}
		//Okay, we've broken out of the loop, which means the package wasn't in the file.
		FileWriter fw = new FileWriter(packageListFile.getCanonicalPath(), true);
		fw.write(packageName);
		fw.write(System.getProperty("line.separator"));
		fw.close();
	}

	protected void processSimpleType(XsdSimpleType simpleType, XsdSchema schema, TokenMap baseTokMap, File packageDir) {
		String typeName = simpleType.getName();

		if (typeName == null) {
			throw new SchemaProcessingException("Type missing name.");
		}

		TokenMap tokMap = new TokenMap(baseTokMap);
		tokMap.addToken("TYPE_NAME", typeName);
		tokMap.addToken("TYPE_CONTEXT_NAME", getPackageTitle(schema.getURI()));
		tokMap.addToken("TYPE_INTERFACE_NAME", "I" + ApiGen.capFirstLetter(typeName));
		tokMap.addToken("TYPE_IMPL_NAME", ApiGen.capFirstLetter(typeName) + "Impl");

		StringBuffer ifaceBuf = new StringBuffer();
		StringBuffer implBuf = new StringBuffer();

		Reader ifaceHeaderReader = ApiGenUtils.openResource("data/InterfaceHeader.dat");
		TokenReplacerReader ifaceHeaderTrr = new TokenReplacerReader(ifaceHeaderReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceHeaderTrr);
		String attributeTypeFixed = null;

		XsdRestriction restr = simpleType.getRestriction();
		if (restr != null) {
			attributeTypeFixed = restr.getBase();
			Collection enumVals = restr.getEnumerationValues();
			StringBuffer enumArray = new StringBuffer();
			for (Iterator en = enumVals.iterator(); en.hasNext();) {
				XsdEnumeration enumVal = (XsdEnumeration) en.next();
				String value = enumVal.getValue();
				TokenMap enumTokMap = new TokenMap(tokMap);
				enumTokMap.addToken("ENUM_VAL", value);
				enumTokMap.addToken("ENUM_VAL_VARNAME", ApiGen.toConstantCase(value));

				Reader ifaceAttrReader = ApiGenUtils.openResource("data/InterfaceEnumVal.dat");
				TokenReplacerReader ifaceAttrTrr = new TokenReplacerReader(ifaceAttrReader, enumTokMap);
				readIntoBuffer(ifaceBuf, ifaceAttrTrr);

				enumArray.append("ENUM_").append(ApiGen.toConstantCase(value));
				if (en.hasNext()) {
					enumArray.append(", ");
				}
			}
			if (enumArray.length() > 0) {
				tokMap.addToken("ATTR_ENUM_ARRAY", "new String[]{" + enumArray.toString() + "}");
			}
		}
		else {
			attributeTypeFixed = "xsd:string";
		}

		//		if(attributeTypeFixed.lastIndexOf(":") == -1){
		//			attributeTypeFixed = getPackageTitle(schema.getTypeQName(attributeTypeFixed).getNamespaceURI())+":"+attributeTypeFixed;
		//		}
		if (schema.getTypeQName(attributeTypeFixed).getNamespaceURI().startsWith("http://www.ics.uci.edu/pub/arch/xArch/")) {
			attributeTypeFixed = getPackageTitle(schema.getTypeQName(attributeTypeFixed).getNamespaceURI()) + ":"
			        + attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(':') + 1);
		}
		else {
			attributeTypeFixed = schema.getTypeQName(attributeTypeFixed).getNamespaceURI() + ":"
			        + attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(':') + 1);
		}

		StringBuffer propMetadataBuf = new StringBuffer();
		tokMap.addToken("ATTR_NAME", "value");
		tokMap.addToken("ATTR_NAME_CAPPED", ApiGen.capFirstLetter(tokMap.getValue("ATTR_NAME")));
		tokMap.addToken("ATTR_TYPE_PACKAGE", attributeTypeFixed.substring(0, attributeTypeFixed.lastIndexOf(":")));
		tokMap.addToken("ATTR_TYPE_CONTEXT_NAME", attributeTypeFixed.substring(0, attributeTypeFixed.lastIndexOf(":")));
		tokMap.addToken("ATTR_TYPE_NAME", attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(":") + 1));
		readInto(propMetadataBuf, tokMap, "InterfaceMetadataAttr.dat");
		tokMap.addToken("PROPERTY_METADATA_ITEMS", propMetadataBuf.toString());

		readInto(ifaceBuf, tokMap, "InterfaceMetadata.dat");

		Reader ifaceAttrReader = ApiGenUtils.openResource("data/InterfaceSimpleType.dat");
		TokenReplacerReader ifaceAttrTrr = new TokenReplacerReader(ifaceAttrReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceAttrTrr);

		Reader implSimpleTypeReader = ApiGenUtils.openResource("data/ImplSimpleType.dat");
		TokenReplacerReader implSimpleTypeTrr = new TokenReplacerReader(implSimpleTypeReader, tokMap);
		readIntoBuffer(implBuf, implSimpleTypeTrr);

		Reader ifaceFooterReader = ApiGenUtils.openResource("data/InterfaceFooter.dat");
		TokenReplacerReader ifaceFooterTrr = new TokenReplacerReader(ifaceFooterReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceFooterTrr);

		File interfaceFile = new File(packageDir, "I" + ApiGen.capFirstLetter(typeName) + ".java");
		writeFile(interfaceFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(interfaceFile, ApiGen.nullRemainingVars(ifaceBuf.toString()), true);

		File implFile = new File(packageDir, ApiGen.capFirstLetter(typeName) + "Impl.java");
		writeFile(implFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(implFile, ApiGen.nullRemainingVars(implBuf.toString()), true);
	}

	//Creates files for xsd:... types like xsd:string or xsd:decimal
	//xsdTypeName = string or decimal or whatever
	protected void processXSDSimpleType(String xsdTypeName, XsdSchema schema, TokenMap baseTokMap, File packageDir) {
		String typeName = "XSD" + xsdTypeName;
		if (typeName == null) {
			throw new SchemaProcessingException("Type missing name.");
		}

		TokenMap tokMap = new TokenMap(baseTokMap);
		//This overrides the package constants class name so it gets the namespace URI
		//for the XSD namespace, so the type name becomes xsd:string instead of insteance:string or whatever.
		tokMap.addToken("PACKAGE_CONSTANTS_CLASS_NAME", "edu.uci.isr.xarch.XArchConstants");
		tokMap.addToken("TYPE_NAME", xsdTypeName);
		tokMap.addToken("TYPE_INTERFACE_NAME", "I" + ApiGen.capFirstLetter(typeName));
		tokMap.addToken("TYPE_IMPL_NAME", ApiGen.capFirstLetter(typeName) + "Impl");

		tokMap.addToken("PROPERTY_METADATA_ITEMS", "");

		StringBuffer ifaceBuf = new StringBuffer();
		StringBuffer implBuf = new StringBuffer();

		Reader ifaceHeaderReader = ApiGenUtils.openResource("data/InterfaceHeader.dat");
		TokenReplacerReader ifaceHeaderTrr = new TokenReplacerReader(ifaceHeaderReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceHeaderTrr);

		Reader ifaceAttrReader = ApiGenUtils.openResource("data/InterfaceSimpleType.dat");
		TokenReplacerReader ifaceAttrTrr = new TokenReplacerReader(ifaceAttrReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceAttrTrr);

		Reader implSimpleTypeReader = ApiGenUtils.openResource("data/ImplSimpleType.dat");
		TokenReplacerReader implSimpleTypeTrr = new TokenReplacerReader(implSimpleTypeReader, tokMap);
		readIntoBuffer(implBuf, implSimpleTypeTrr);

		Reader ifaceFooterReader = ApiGenUtils.openResource("data/InterfaceFooter.dat");
		TokenReplacerReader ifaceFooterTrr = new TokenReplacerReader(ifaceFooterReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceFooterTrr);

		File interfaceFile = new File(packageDir, "I" + ApiGen.capFirstLetter(typeName) + ".java");
		writeFile(interfaceFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(interfaceFile, ApiGen.nullRemainingVars(ifaceBuf.toString()), true);

		File implFile = new File(packageDir, ApiGen.capFirstLetter(typeName) + "Impl.java");
		writeFile(implFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(implFile, ApiGen.nullRemainingVars(implBuf.toString()), true);
	}

	//Processes complex types that extend simpleTypes
	protected void processComplexSimpleType(XsdComplexType complexType, XsdSchema schema, TokenMap baseTokMap, File packageDir) {
		String typeName = complexType.getName();

		if (typeName == null) {
			throw new SchemaProcessingException("Type missing name.");
		}

		TokenMap tokMap = new TokenMap(baseTokMap);
		tokMap.addToken("TYPE_NAME", typeName);
		tokMap.addToken("TYPE_CONTEXT_NAME", getPackageTitle(schema.getURI()));
		tokMap.addToken("TYPE_INTERFACE_NAME", "I" + ApiGen.capFirstLetter(typeName));
		tokMap.addToken("TYPE_IMPL_NAME", ApiGen.capFirstLetter(typeName) + "Impl");

		StringBuffer ifaceBuf = new StringBuffer();
		StringBuffer implBuf = new StringBuffer();

		Reader ifaceHeaderReader = ApiGenUtils.openResource("data/InterfaceHeader.dat");
		TokenReplacerReader ifaceHeaderTrr = new TokenReplacerReader(ifaceHeaderReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceHeaderTrr);

		String attributeTypeFixed = null;

		XsdSimpleContent simpleContent = complexType.getSimpleContent();
		XsdExtension ext = simpleContent.getExtension();
		XsdRestriction restr = simpleContent.getRestriction();
		if (restr != null) {
			attributeTypeFixed = restr.getBase();
			Collection enumVals = restr.getEnumerationValues();
			StringBuffer enumArray = new StringBuffer();
			for (Iterator en = enumVals.iterator(); en.hasNext();) {
				XsdEnumeration enumVal = (XsdEnumeration) en.next();
				String value = enumVal.getValue();
				TokenMap enumTokMap = new TokenMap(tokMap);
				enumTokMap.addToken("ENUM_VAL", value);
				enumTokMap.addToken("ENUM_VAL_VARNAME", ApiGen.toConstantCase(value));

				Reader ifaceAttrReader = ApiGenUtils.openResource("data/InterfaceEnumVal.dat");
				TokenReplacerReader ifaceAttrTrr = new TokenReplacerReader(ifaceAttrReader, enumTokMap);
				readIntoBuffer(ifaceBuf, ifaceAttrTrr);

				enumArray.append("ENUM_").append(ApiGen.toConstantCase(value));
				if (en.hasNext()) {
					enumArray.append(", ");
				}
			}
			if (enumArray.length() > 0) {
				tokMap.addToken("ATTR_ENUM_ARRAY", "new String[]{" + enumArray.toString() + "}");
			}
		}
		else {
			attributeTypeFixed = ext.getBase();
		}

		//		if(attributeTypeFixed.lastIndexOf(":") == -1){
		//			attributeTypeFixed = getPackageTitle(schema.getTypeQName(attributeTypeFixed).getNamespaceURI())+":"+attributeTypeFixed;
		//		}
		if (schema.getTypeQName(attributeTypeFixed).getNamespaceURI().startsWith("http://www.ics.uci.edu/pub/arch/xArch/")) {
			attributeTypeFixed = getPackageTitle(schema.getTypeQName(attributeTypeFixed).getNamespaceURI()) + ":"
			        + attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(':') + 1);
		}
		else {
			attributeTypeFixed = schema.getTypeQName(attributeTypeFixed).getNamespaceURI() + ":"
			        + attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(':') + 1);
		}

		StringBuffer propMetadataBuf = new StringBuffer();
		tokMap.addToken("ATTR_NAME", "value");
		tokMap.addToken("ATTR_NAME_CAPPED", ApiGen.capFirstLetter(tokMap.getValue("ATTR_NAME")));
		tokMap.addToken("ATTR_TYPE_PACKAGE", attributeTypeFixed.substring(0, attributeTypeFixed.lastIndexOf(":")));
		tokMap.addToken("ATTR_TYPE_CONTEXT_NAME", attributeTypeFixed.substring(0, attributeTypeFixed.lastIndexOf(":")));
		tokMap.addToken("ATTR_TYPE_NAME", attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(":") + 1));
		readInto(propMetadataBuf, tokMap, "InterfaceMetadataAttr.dat");
		tokMap.addToken("PROPERTY_METADATA_ITEMS", propMetadataBuf.toString());

		Reader ifaceMetadataReader = ApiGenUtils.openResource("data/InterfaceMetadata.dat");
		TokenReplacerReader ifaceMetadataTrr = new TokenReplacerReader(ifaceMetadataReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceMetadataTrr);

		Reader ifaceAttrReader = ApiGenUtils.openResource("data/InterfaceSimpleType.dat");
		TokenReplacerReader ifaceAttrTrr = new TokenReplacerReader(ifaceAttrReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceAttrTrr);

		Reader implSimpleTypeReader = ApiGenUtils.openResource("data/ImplSimpleType.dat");
		TokenReplacerReader implSimpleTypeTrr = new TokenReplacerReader(implSimpleTypeReader, tokMap);
		readIntoBuffer(implBuf, implSimpleTypeTrr);

		Reader ifaceFooterReader = ApiGenUtils.openResource("data/InterfaceFooter.dat");
		TokenReplacerReader ifaceFooterTrr = new TokenReplacerReader(ifaceFooterReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceFooterTrr);

		File interfaceFile = new File(packageDir, "I" + ApiGen.capFirstLetter(typeName) + ".java");
		writeFile(interfaceFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(interfaceFile, ApiGen.nullRemainingVars(ifaceBuf.toString()), true);

		File implFile = new File(packageDir, ApiGen.capFirstLetter(typeName) + "Impl.java");
		writeFile(implFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(implFile, ApiGen.nullRemainingVars(implBuf.toString()), true);
	}

	protected void processComplexType(XsdComplexType complexType, XsdSchema schema, TokenMap baseTokMap, File packageDir) {
		//System.out.println("Step1");
		if (complexType.getSimpleContent() != null) {
			processComplexSimpleType(complexType, schema, baseTokMap, packageDir);
			return;
		}

		//System.out.println("Step2");
		String typeName = complexType.getName();
		if (typeName == null) {
			throw new SchemaProcessingException("Type missing name.");
		}

		//System.out.println("Step3");
		TokenMap tokMap = new TokenMap(baseTokMap);
		tokMap.addToken("TYPE_CONTEXT_NAME", getPackageTitle(schema.getURI()));
		tokMap.addToken("TYPE_NAME", typeName);
		tokMap.addToken("TYPE_INTERFACE_NAME", "I" + ApiGen.capFirstLetter(typeName));
		tokMap.addToken("TYPE_IMPL_NAME", ApiGen.capFirstLetter(typeName) + "Impl");

		StringBuffer ifaceBuf = new StringBuffer();
		StringBuffer implBuf = new StringBuffer();
		String baseTypeName = null;
		String baseTypeInterfaceName = null;
		String baseTypeImplName = null;
		Collection xsdAttributes = null;
		Collection xsdElements = null;

		XsdComplexContent complexContent = complexType.getComplexContent();
		if (complexContent != null) {
			XsdExtension ext = complexContent.getExtension();
			if (ext == null) {
				throw new IllegalArgumentException("Can't process a complexContent that is not a type extension.");
			}
			if (ext != null) {
				String rawBaseTypeName = ext.getBase();
				if (rawBaseTypeName == null) {
					throw new IllegalArgumentException("Can't process a complexContent that is not a type extension.");
				}
				if (rawBaseTypeName != null) {
					baseTypeName = rawBaseTypeName.substring(rawBaseTypeName.lastIndexOf(":") + 1);

					if (rawBaseTypeName.lastIndexOf(":") != -1) {
						QName typeQName = schema.getTypeQName(rawBaseTypeName);
						String typeNamespaceURI = typeQName.getNamespaceURI();
						if (typeNamespaceURI.equals(schema.getURI())) {
							baseTypeInterfaceName = "I" + ApiGen.capFirstLetter(baseTypeName);
							baseTypeImplName = ApiGen.capFirstLetter(baseTypeName) + "Impl";
						}
						else {
							baseTypeInterfaceName = "edu.uci.isr.xarch." + getPackageTitle(typeNamespaceURI) + ".I" + ApiGen.capFirstLetter(baseTypeName);
							baseTypeImplName = "edu.uci.isr.xarch." + getPackageTitle(typeNamespaceURI) + "." + ApiGen.capFirstLetter(baseTypeName) + "Impl";
						}
					}
					else {
						baseTypeInterfaceName = "I" + ApiGen.capFirstLetter(baseTypeName);
						baseTypeImplName = ApiGen.capFirstLetter(baseTypeName) + "Impl";
					}
				}
			}
		}

		if (baseTypeName == null) {
			Reader ifaceHeaderReader = ApiGenUtils.openResource("data/InterfaceHeader.dat");
			TokenReplacerReader ifaceHeaderTrr = new TokenReplacerReader(ifaceHeaderReader, tokMap);
			readIntoBuffer(ifaceBuf, ifaceHeaderTrr);

			Reader implHeaderReader = ApiGenUtils.openResource("data/ImplHeader.dat");
			TokenReplacerReader implHeaderTrr = new TokenReplacerReader(implHeaderReader, tokMap);
			readIntoBuffer(implBuf, implHeaderTrr);

			xsdAttributes = complexType.getAttributes();
			xsdElements = ApiGen.getAllXsdElements(complexType);
			getRefElements(xsdElements, schema);
		}
		else {
			tokMap.addToken("BASE_TYPE_NAME", baseTypeName);
			tokMap.addToken("BASE_TYPE_INTERFACE_NAME", baseTypeInterfaceName);
			tokMap.addToken("BASE_TYPE_IMPL_NAME", baseTypeImplName);

			Reader ifaceHeaderReader = ApiGenUtils.openResource("data/InterfaceExtHeader.dat");
			TokenReplacerReader ifaceHeaderTrr = new TokenReplacerReader(ifaceHeaderReader, tokMap);
			readIntoBuffer(ifaceBuf, ifaceHeaderTrr);

			Reader implHeaderReader = ApiGenUtils.openResource("data/ImplExtHeader.dat");
			TokenReplacerReader implHeaderTrr = new TokenReplacerReader(implHeaderReader, tokMap);
			readIntoBuffer(implBuf, implHeaderTrr);

			xsdAttributes = complexContent.getExtension().getAttributes();
			xsdElements = ApiGen.getAllXsdElements(complexContent.getExtension());
		}

		//At this point, we have both the Attributes and the Elements in
		//their respective collections.

		//Generate the rest of the header knowledge for the Implementation
		StringBuffer declarationsBuf = new StringBuffer();
		StringBuffer propMetadataBuf = new StringBuffer();
		for (Iterator en = xsdAttributes.iterator(); en.hasNext();) {
			XsdAttribute xsdAttr = (XsdAttribute) en.next();
			TokenMap attrTokMap = createAttrTokMap(schema, xsdAttr, tokMap);

			Reader reader = ApiGenUtils.openResource("data/AttrNameDecl.dat");
			TokenReplacerReader trr = new TokenReplacerReader(reader, attrTokMap);
			readIntoBuffer(declarationsBuf, trr);

			reader = ApiGenUtils.openResource("data/InterfaceMetadataAttr.dat");
			trr = new TokenReplacerReader(reader, attrTokMap);
			readIntoBuffer(propMetadataBuf, trr);
			propMetadataBuf.append(",");
		}

		StringBuffer sequenceOrderBuffer = new StringBuffer();
		for (Iterator en = xsdElements.iterator(); en.hasNext();) {
			XsdElement xsdElt = (XsdElement) en.next();
			TokenMap eltTokMap = createElementTokMap(schema, xsdElt, tokMap);

			Reader reader = ApiGenUtils.openResource("data/EltNameDecl.dat");
			TokenReplacerReader trr = new TokenReplacerReader(reader, eltTokMap);
			readIntoBuffer(declarationsBuf, trr);

			reader = ApiGenUtils.openResource("data/SequenceOrderItem.dat");
			trr = new TokenReplacerReader(reader, eltTokMap);
			readIntoBuffer(sequenceOrderBuffer, trr);
			if (en.hasNext()) {
				sequenceOrderBuffer.append(",");
			}

			reader = ApiGenUtils.openResource("data/InterfaceMetadataElt.dat");
			trr = new TokenReplacerReader(reader, eltTokMap);
			readIntoBuffer(propMetadataBuf, trr);
			propMetadataBuf.append(",");
		}

		if (propMetadataBuf.length() > 0) {
			propMetadataBuf.delete(propMetadataBuf.length() - 1, propMetadataBuf.length());
		}

		tokMap.addToken("ATTRIBUTE_AND_ELEMENT_DECLARATIONS", declarationsBuf.toString());
		tokMap.addToken("PROPERTY_METADATA_ITEMS", propMetadataBuf.toString());
		tokMap.addToken("SEQUENCE_ORDER_ITEMS", sequenceOrderBuffer.toString());

		if (baseTypeName == null) {
			readInto(ifaceBuf, tokMap, "InterfaceMetadata.dat");
		}
		else {
			readInto(ifaceBuf, tokMap, "InterfaceExtMetadata.dat");
		}

		ifaceBuf = new StringBuffer(ApiGenUtils.replaceTokens(ifaceBuf.toString(), tokMap));
		implBuf = new StringBuffer(ApiGenUtils.replaceTokens(implBuf.toString(), tokMap));

		//Used to rack up the code snippets for the isEquivalent function.
		StringBuffer equivalentBuf = new StringBuffer();

		//Now we go about generating the interface and implementations for the
		//functions for each attribute.
		for (Iterator en = xsdAttributes.iterator(); en.hasNext();) {
			XsdAttribute xsdAttr = (XsdAttribute) en.next();
			TokenMap attrTokMap = createAttrTokMap(schema, xsdAttr, tokMap);

			//Generate interface snippet
			Reader ifaceAttrReader = ApiGenUtils.openResource("data/InterfaceAttr.dat");
			TokenReplacerReader ifaceAttrTrr = new TokenReplacerReader(ifaceAttrReader, attrTokMap);
			readIntoBuffer(ifaceBuf, ifaceAttrTrr);

			//Generate implementation snippet
			if (attrTokMap.getValue("ATTR_FIXED_VALUE") == null) {
				Reader implAttrReader = ApiGenUtils.openResource("data/ImplAttr.dat");
				TokenReplacerReader implAttrTrr = new TokenReplacerReader(implAttrReader, attrTokMap);
				readIntoBuffer(implBuf, implAttrTrr);
			}
			else {
				Reader implAttrReader = ApiGenUtils.openResource("data/ImplAttrFixed.dat");
				TokenReplacerReader implAttrTrr = new TokenReplacerReader(implAttrReader, attrTokMap);
				readIntoBuffer(implBuf, implAttrTrr);
			}

			if (!"id".equals(attrTokMap.getValue("ATTR_NAME"))) {
				Reader implAttrEquivReader = ApiGenUtils.openResource("data/ImplAttrEquivalent.dat");
				TokenReplacerReader implAttrEquivTrr = new TokenReplacerReader(implAttrEquivReader, attrTokMap);
				readIntoBuffer(equivalentBuf, implAttrEquivTrr);
			}
		}

		//Now we go about generating the interface and implementations for the
		//functions for each element.
		for (Iterator en = xsdElements.iterator(); en.hasNext();) {
			XsdElement xsdElt = (XsdElement) en.next();
			TokenMap eltTokMap = createElementTokMap(schema, xsdElt, tokMap);
			int minOccurs = Integer.parseInt(eltTokMap.getValue("ELT_MIN_OCCURS"));
			int maxOccurs = Integer.parseInt(eltTokMap.getValue("ELT_MAX_OCCURS"));

			// check for xsd:string or something types
			String rawEltTypeName = xsdElt.getType();
			String eltTypeName = rawEltTypeName.substring(rawEltTypeName.lastIndexOf(":") + 1);
			if (xsdElt.getType().lastIndexOf(":") != -1) {
				QName typeQName = schema.getTypeQName(xsdElt.getType());
				String typeNamespaceURI = typeQName.getNamespaceURI();
				if (typeNamespaceURI.equals(schema.getURI()) && typeNamespaceURI.equals(XArchConstants.XSD_NS_URI)) {
					//It's like xsd:string or something!
					// Generate a type wrapper for it locally (one may already exist, but it'll
					// just get overwritten.
					processXSDSimpleType(eltTypeName, schema, baseTokMap, packageDir);
				}
			}

			if (minOccurs == 0 && maxOccurs == 1) {
				//Optional element.
				Reader ifaceEltReader = ApiGenUtils.openResource("data/InterfaceOneElt.dat");
				TokenReplacerReader ifaceEltTrr = new TokenReplacerReader(ifaceEltReader, eltTokMap);
				readIntoBuffer(ifaceBuf, ifaceEltTrr);

				Reader implEltReader = ApiGenUtils.openResource("data/ImplOneElt.dat");
				TokenReplacerReader implEltTrr = new TokenReplacerReader(implEltReader, eltTokMap);
				readIntoBuffer(implBuf, implEltTrr);

				Reader implEltEquivReader = ApiGenUtils.openResource("data/ImplOneEltEquiv.dat");
				TokenReplacerReader implEltEquivTrr = new TokenReplacerReader(implEltEquivReader, eltTokMap);
				readIntoBuffer(equivalentBuf, implEltEquivTrr);
			}
			else if (minOccurs == 1 && maxOccurs == 1) {
				//Exactly-one-element
				Reader ifaceEltReader = ApiGenUtils.openResource("data/InterfaceOneElt.dat");
				TokenReplacerReader ifaceEltTrr = new TokenReplacerReader(ifaceEltReader, eltTokMap);
				readIntoBuffer(ifaceBuf, ifaceEltTrr);

				Reader implEltReader = ApiGenUtils.openResource("data/ImplOneElt.dat");
				TokenReplacerReader implEltTrr = new TokenReplacerReader(implEltReader, eltTokMap);
				readIntoBuffer(implBuf, implEltTrr);

				Reader implEltEquivReader = ApiGenUtils.openResource("data/ImplOneEltEquiv.dat");
				TokenReplacerReader implEltEquivTrr = new TokenReplacerReader(implEltEquivReader, eltTokMap);
				readIntoBuffer(equivalentBuf, implEltEquivTrr);
			}
			else if (minOccurs == 0 && maxOccurs > 1) {
				//Kleene-star element.
				boolean typeHasId = false;

				//XsdType eltType = getXsdType(schema, xsdElt.getType());
				SchemaAndType schemaAndType = getXsdType(schema, xsdElt.getType());
				XsdType eltType = schemaAndType.type;
				if (eltType instanceof XsdComplexType) {
					Collection attrColl = ((XsdComplexType) eltType).getAttributes();
					for (Iterator en3 = attrColl.iterator(); en3.hasNext();) {
						XsdAttribute attr = (XsdAttribute) en3.next();
						//Resolve attribute refs.
						String ref = attr.getRef();
						if (ref != null) {
							//attr = getRefAttribute(schema, ref);
							XsdSchema schemaOfType = schemaAndType.schema;
							attr = getRefAttribute(schemaOfType, ref);
						}
						if (attr.getName() != null && attr.getName().equals("id")) {
							typeHasId = true;
							break;
						}
					}
				}

				// This is to relieve the assumption that all elements live in the same xADL package.
				// Here if an element type is referenced in the schema, then its interface definition supplies which
				// namespace prefix should be used when an element instance is created. 
				//
				// Now this is just to support Secure xADL's use of XACML. Using the interface file,
				// which is manually modified based on a generated file, gives us the easiest control.
				// So all special changes can be localized to the external Int/Impl files. 
				//
				// In general this can be done for other elements where PACKAGE_CONSTANTS_CLASS_NAME.NS_URI
				// is used. But at this moment we only touch ImplManyElt.dat, which is needed by Secure xADL
				if (xsdElt.getReferencing() == null) {
					eltTokMap.addToken("ELEMENT_NS_SOURCE", tokMap.getValue("PACKAGE_CONSTANTS_CLASS_NAME"));
				}
				else {
					eltTokMap.addToken("ELEMENT_NS_SOURCE", eltTokMap.getValue("ELT_TYPE_INTERFACE_NAME"));
				}
				Reader ifaceEltReader = ApiGenUtils.openResource("data/InterfaceManyElt.dat");
				TokenReplacerReader ifaceEltTrr = new TokenReplacerReader(ifaceEltReader, eltTokMap);
				readIntoBuffer(ifaceBuf, ifaceEltTrr);

				Reader implEltReader = ApiGenUtils.openResource("data/ImplManyElt.dat");
				TokenReplacerReader implEltTrr = new TokenReplacerReader(implEltReader, eltTokMap);
				readIntoBuffer(implBuf, implEltTrr);

				if (typeHasId) {
					ifaceEltReader = ApiGenUtils.openResource("data/InterfaceManyIdElt.dat");
					ifaceEltTrr = new TokenReplacerReader(ifaceEltReader, eltTokMap);
					readIntoBuffer(ifaceBuf, ifaceEltTrr);

					implEltReader = ApiGenUtils.openResource("data/ImplManyIdElt.dat");
					implEltTrr = new TokenReplacerReader(implEltReader, eltTokMap);
					readIntoBuffer(implBuf, implEltTrr);
				}

				Reader implEltEquivReader = ApiGenUtils.openResource("data/ImplManyEltEquiv.dat");
				TokenReplacerReader implEltEquivTrr = new TokenReplacerReader(implEltEquivReader, eltTokMap);
				readIntoBuffer(equivalentBuf, implEltEquivTrr);
			}
			else if (minOccurs > 0 && maxOccurs > 1) {
				//More-than-one element
				boolean typeHasId = false;

				//XsdType eltType = getXsdType(schema, xsdElt.getType());
				SchemaAndType schemaAndType = getXsdType(schema, xsdElt.getType());
				XsdType eltType = schemaAndType.type;

				if (eltType instanceof XsdComplexType) {
					Collection attrColl = ((XsdComplexType) eltType).getAttributes();
					for (Iterator en3 = attrColl.iterator(); en3.hasNext();) {
						XsdAttribute attr = (XsdAttribute) en3.next();
						//System.out.println("Processing attributes on element: " + xsdElt.getName() + ":" + xsdElt.getType());
						//System.out.println("Processing attributes on name: " + ((XsdComplexType)eltType).getName());
						//System.out.println("Attr.getName=: " + attr.getName());
						if (attr.getName() != null && attr.getName().equals("id")) {
							typeHasId = true;
							break;
						}
					}
				}

				if (xsdElt.getReferencing() == null) {
					eltTokMap.addToken("ELEMENT_NS_SOURCE", tokMap.getValue("PACKAGE_CONSTANTS_CLASS_NAME"));
				}
				else {
					eltTokMap.addToken("ELEMENT_NS_SOURCE", eltTokMap.getValue("ELT_TYPE_INTERFACE_NAME"));
				}
				Reader ifaceEltReader = ApiGenUtils.openResource("data/InterfaceManyElt.dat");
				TokenReplacerReader ifaceEltTrr = new TokenReplacerReader(ifaceEltReader, eltTokMap);
				readIntoBuffer(ifaceBuf, ifaceEltTrr);

				Reader implEltReader = ApiGenUtils.openResource("data/ImplManyElt.dat");
				TokenReplacerReader implEltTrr = new TokenReplacerReader(implEltReader, eltTokMap);
				readIntoBuffer(implBuf, implEltTrr);

				if (typeHasId) {
					ifaceEltReader = ApiGenUtils.openResource("data/InterfaceManyIdElt.dat");
					ifaceEltTrr = new TokenReplacerReader(ifaceEltReader, eltTokMap);
					readIntoBuffer(ifaceBuf, ifaceEltTrr);

					implEltReader = ApiGenUtils.openResource("data/ImplManyIdElt.dat");
					implEltTrr = new TokenReplacerReader(implEltReader, eltTokMap);
					readIntoBuffer(implBuf, implEltTrr);
				}

				Reader implEltEquivReader = ApiGenUtils.openResource("data/ImplManyEltEquiv.dat");
				TokenReplacerReader implEltEquivTrr = new TokenReplacerReader(implEltEquivReader, eltTokMap);
				readIntoBuffer(equivalentBuf, implEltEquivTrr);
			}
		}

		//Generate the interface and implementation for the isEqual()
		//method (only done if this type has an 'id' attribute.
		boolean typeHasId = false;
		Collection attrColl = complexType.getAttributes();
		for (Iterator en = attrColl.iterator(); en.hasNext();) {
			XsdAttribute attr = (XsdAttribute) en.next();
			String ref = attr.getRef();
			if (ref != null) {
				attr = getRefAttribute(schema, ref);
			}
			if (attr.getName() != null && attr.getName().equals("id")) {
				typeHasId = true;
				break;
			}
		}
		if (typeHasId) {
			Reader ifaceEqualReader = ApiGenUtils.openResource("data/InterfaceIsEqual.dat");
			TokenReplacerReader ifaceEqualTrr = new TokenReplacerReader(ifaceEqualReader, tokMap);
			readIntoBuffer(ifaceBuf, ifaceEqualTrr);

			Reader implEqualReader = ApiGenUtils.openResource("data/ImplIsEqual.dat");
			TokenReplacerReader implEqualTrr = new TokenReplacerReader(implEqualReader, tokMap);
			readIntoBuffer(implBuf, implEqualTrr);
		}

		//Generate the isEquivalent interface function and the implementation for it.
		Reader ifaceEquivReader = ApiGenUtils.openResource("data/InterfaceIsEquivalent.dat");
		TokenReplacerReader ifaceEquivTrr = new TokenReplacerReader(ifaceEquivReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceEquivTrr);

		//First, fix up the isEquivalent items string.
		String isEquivalentItems = equivalentBuf.toString();
		if (isEquivalentItems.equals("")) {
			isEquivalentItems = "\t\t\ttrue;";
		}
		else {
			isEquivalentItems = isEquivalentItems.substring(0, isEquivalentItems.lastIndexOf("&&")) + ";";
		}
		tokMap.addToken("IS_EQUIVALENT_ITEMS", isEquivalentItems);

		if (baseTypeName != null) {
			Reader implEquivReader = ApiGenUtils.openResource("data/ImplExtIsEquivalent.dat");
			TokenReplacerReader implEquivTrr = new TokenReplacerReader(implEquivReader, tokMap);
			readIntoBuffer(implBuf, implEquivTrr);
		}
		else {
			Reader implEquivReader = ApiGenUtils.openResource("data/ImplIsEquivalent.dat");
			TokenReplacerReader implEquivTrr = new TokenReplacerReader(implEquivReader, tokMap);
			readIntoBuffer(implBuf, implEquivTrr);
		}

		//Generate footers
		Reader ifaceFooterReader = ApiGenUtils.openResource("data/InterfaceFooter.dat");
		TokenReplacerReader ifaceFooterTrr = new TokenReplacerReader(ifaceFooterReader, tokMap);
		readIntoBuffer(ifaceBuf, ifaceFooterTrr);

		Reader implFooterReader = ApiGenUtils.openResource("data/ImplFooter.dat");
		TokenReplacerReader implFooterTrr = new TokenReplacerReader(implFooterReader, tokMap);
		readIntoBuffer(implBuf, implFooterTrr);

		File interfaceFile = new File(packageDir, "I" + ApiGen.capFirstLetter(typeName) + ".java");
		writeFile(interfaceFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(interfaceFile, ApiGen.nullRemainingVars(ifaceBuf.toString()), true);

		File implFile = new File(packageDir, ApiGen.capFirstLetter(typeName) + "Impl.java");
		writeFile(implFile, ApiGenUtils.openResource("data/Copyright.dat"), false);
		writeFile(implFile, ApiGen.nullRemainingVars(implBuf.toString()), true);
	}

	private TokenMap createAttrTokMap(XsdSchema schema, XsdAttribute xsdAttr, TokenMap tokMap) {

		String attributeNamespace = schema.getURI();
		String ref = xsdAttr.getRef();
		if (ref != null) {
			xsdAttr = getRefAttribute(schema, ref);
			QName attributeQName = schema.getTypeQName(ref);
			attributeNamespace = attributeQName.getNamespaceURI();
		}

		if (!attributeNamespace.equals(schema.getURI())) {
			attributeNamespace = "\"" + attributeNamespace + "\"";
		}
		else {
			String packageConstants = tokMap.getValue("PACKAGE_CONSTANTS_CLASS_NAME");
			attributeNamespace = packageConstants + ".NS_URI";
		}

		String attributeTypeUnfixed = xsdAttr.getType();
		if (attributeTypeUnfixed == null) {
			throw new IllegalArgumentException("xsd:attributes must have a 'type' or a 'ref' attribute");
		}

		QName attributeType = schema.getTypeQName(attributeTypeUnfixed);
		String attributeTypeNamespace;
		String typeContext = attributeType.getNamespaceURI();
		String typeName = attributeTypeUnfixed.substring(attributeTypeUnfixed.lastIndexOf(':') + 1);
		if (!attributeType.getNamespaceURI().equals(schema.getURI())) {
			attributeTypeNamespace = "\"" + attributeType.getNamespaceURI() + "\"";
			if (ref != null) {
				xsdAttr = getRefAttribute(schema, ref);
				QName attributeQName = schema.getTypeQName(ref);
				typeContext = attributeQName.getNamespaceURI();
				typeName = xsdAttr.getName();
			}
		}
		else {
			String packageConstants = tokMap.getValue("PACKAGE_CONSTANTS_CLASS_NAME");
			attributeTypeNamespace = packageConstants + ".NS_URI";
		}
		String attributeName = xsdAttr.getName();

		String attributeTypeFixed = attributeTypeUnfixed;
		//		if(attributeTypeFixed.lastIndexOf(":") == -1){
		//			attributeTypeFixed = getPackageTitle(attributeType.getNamespaceURI())+":"+attributeTypeFixed;
		//		}
		if (typeContext.startsWith("http://www.ics.uci.edu/pub/arch/xArch")) {
			attributeTypeFixed = getPackageTitle(typeContext) + ":" + attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(':') + 1);
		}
		else {
			attributeTypeFixed = typeContext + ":" + typeName;
		}

		//Get the fixed value, if there is one.
		String attributeFixedValue = xsdAttr.getFixed();

		TokenMap attrTokMap = new TokenMap(tokMap);
		if (attributeName != null) {
			attrTokMap.addToken("ATTR_NAME", attributeName);
			attrTokMap.addToken("ATTR_NAME_VARNAME", ApiGen.toConstantCase(attributeName) + "_ATTR_NAME");
			attrTokMap.addToken("ATTR_NAME_CAPPED", ApiGen.capFirstLetter(attributeName));
		}
		if (attributeFixedValue != null) {
			attrTokMap.addToken("ATTR_FIXED_VALUE", attributeFixedValue);
		}
		attrTokMap.addToken("ATTR_NAMESPACE", attributeNamespace);
		attrTokMap.addToken("ATTR_TYPE_NAMESPACE", attributeTypeNamespace);
		attrTokMap.addToken("ATTR_TYPE_PACKAGE", attributeTypeFixed.substring(0, attributeTypeFixed.lastIndexOf(":")));
		attrTokMap.addToken("ATTR_TYPE_CONTEXT_NAME", attributeTypeFixed.substring(0, attributeTypeFixed.lastIndexOf(":")));
		attrTokMap.addToken("ATTR_TYPE_NAME", attributeTypeFixed.substring(attributeTypeFixed.lastIndexOf(":") + 1));

		return attrTokMap;
	}

	private TokenMap createElementTokMap(XsdSchema schema, XsdElement xsdElt, TokenMap tokMap) {

		String elementName = xsdElt.getName();

		int minOccurs = xsdElt.getMinOccurs();
		int maxOccurs = xsdElt.getMaxOccurs();
		if (maxOccurs == -1) {
			maxOccurs = Integer.MAX_VALUE;
		}

		//Figure out some basic stuff about the element type
		String rawEltTypeName = xsdElt.getType();
		if (rawEltTypeName == null) {
			throw new SchemaProcessingException("Cannot process untyped elements: " + xsdElt.getName());
		}
		String eltTypeName = rawEltTypeName.substring(rawEltTypeName.lastIndexOf(":") + 1);
		String eltTypeInterfaceName;
		String eltTypeImplName;
		String eltTypeContextName;

		if (rawEltTypeName.lastIndexOf(":") != -1) {
			QName typeQName = schema.getTypeQName(rawEltTypeName);
			String typeNamespaceURI = typeQName.getNamespaceURI();
			eltTypeContextName = getPackageTitle(typeNamespaceURI);
			if (typeNamespaceURI.equals(schema.getURI())) {
				eltTypeInterfaceName = "I" + eltTypeName;
				eltTypeImplName = eltTypeName + "Impl";
			}
			else if (typeNamespaceURI.equals(XArchConstants.XSD_NS_URI)) {
				//It's like xsd:string or something!
				//Generate a type wrapper for it locally (one may already exist, but it'll
				//just get overwritten.

				// this was moved elsewhere
				// processXSDSimpleType(eltTypeName, schema, baseTokMap, packageDir);

				eltTypeInterfaceName = "IXSD" + eltTypeName;
				eltTypeImplName = "XSD" + eltTypeName + "Impl";
			}
			else {
				eltTypeInterfaceName = "edu.uci.isr.xarch." + getPackageTitle(typeNamespaceURI) + ".I" + ApiGen.capFirstLetter(eltTypeName);
				eltTypeImplName = "edu.uci.isr.xarch." + getPackageTitle(typeNamespaceURI) + "." + ApiGen.capFirstLetter(eltTypeName) + "Impl";
			}
		}
		else {
			eltTypeContextName = getPackageTitle(schema.getTypeQName(rawEltTypeName).getNamespaceURI());
			eltTypeInterfaceName = "I" + ApiGen.capFirstLetter(eltTypeName);
			eltTypeImplName = ApiGen.capFirstLetter(eltTypeName) + "Impl";
		}

		TokenMap eltTokMap = new TokenMap(tokMap);
		eltTokMap.addToken("ELT_NAME", elementName);
		eltTokMap.addToken("ELT_NAME_VARNAME", ApiGen.toConstantCase(elementName) + "_ELT_NAME");
		eltTokMap.addToken("ELT_NAME_CAPPED", ApiGen.capFirstLetter(elementName));
		eltTokMap.addToken("ELT_TYPE_NAME", eltTypeName);
		eltTokMap.addToken("ELT_TYPE_CONTEXT_NAME", eltTypeContextName);
		eltTokMap.addToken("ELT_TYPE_INTERFACE_NAME", eltTypeInterfaceName);
		eltTokMap.addToken("ELT_TYPE_IMPL_NAME", eltTypeImplName);
		eltTokMap.addToken("ELT_MIN_OCCURS", "" + minOccurs);
		eltTokMap.addToken("ELT_MIN_OCCURS_CONST", minOccurs == Integer.MAX_VALUE ? "XArchPropertyMetadata.UNBOUNDED" : "" + minOccurs);
		eltTokMap.addToken("ELT_MAX_OCCURS", "" + maxOccurs);
		eltTokMap.addToken("ELT_MAX_OCCURS_CONST", maxOccurs == Integer.MAX_VALUE ? "XArchPropertyMetadata.UNBOUNDED" : "" + maxOccurs);
		return eltTokMap;
	}

	protected static Collection getAllXsdElements(XsdExtension extension) {
		Vector v = new Vector();
		Collection items = extension.getItems();
		for (Iterator en = items.iterator(); en.hasNext();) {
			Object item = en.next();
			if (item instanceof XsdElement) {
				v.addElement((XsdElement) item);
			}
			else if (item instanceof XsdSequence) {
				ApiGen.addXsdElements(v, ((XsdSequence) item));
			}
			else if (item instanceof XsdChoice) {
				ApiGen.addXsdElements(v, ((XsdChoice) item));
			}
		}
		return v;
	}

	protected static Collection getAllXsdElements(XsdComplexType complexType) {
		Vector v = new Vector();
		Collection items = complexType.getItems();
		for (Iterator en = items.iterator(); en.hasNext();) {
			Object item = en.next();
			if (item instanceof XsdElement) {
				v.addElement((XsdElement) item);
			}
			else if (item instanceof XsdSequence) {
				ApiGen.addXsdElements(v, ((XsdSequence) item));
			}
			else if (item instanceof XsdChoice) {
				ApiGen.addXsdElements(v, ((XsdChoice) item));
			}
		}
		return v;
	}

	private static void addXsdElements(Vector v, XsdSequence s) {
		Collection items = s.getItems();
		for (Iterator en = items.iterator(); en.hasNext();) {
			Object item = en.next();
			if (item instanceof XsdElement) {
				v.addElement((XsdElement) item);
			}
			else if (item instanceof XsdSequence) {
				ApiGen.addXsdElements(v, ((XsdSequence) item));
			}
			else if (item instanceof XsdChoice) {
				ApiGen.addXsdElements(v, ((XsdChoice) item));
			}
		}
	}

	private static void addXsdElements(Vector v, XsdChoice c) {
		Collection items = c.getItems();
		for (Iterator en = items.iterator(); en.hasNext();) {
			Object item = en.next();
			if (item instanceof XsdElement) {
				v.addElement((XsdElement) item);
			}
			else if (item instanceof XsdSequence) {
				ApiGen.addXsdElements(v, ((XsdSequence) item));
			}
			else if (item instanceof XsdChoice) {
				ApiGen.addXsdElements(v, ((XsdChoice) item));
			}
		}
	}

	//Get a top-level XsdAttribute because it got ref=""ed somewhere.
	protected XsdAttribute getRefAttribute(XsdSchema schema, String unfixedRef) {
		//Yeah, I know it says get TYPE qname, but it works for refs too...
		QName attrQName = schema.getTypeQName(unfixedRef);
		String nsURI = attrQName.getNamespaceURI();
		String attrName = attrQName.getName();

		if (nsURI.equals(schema.getURI())) {
			Collection attributeCollection = schema.getAttributes();
			for (Iterator en = attributeCollection.iterator(); en.hasNext();) {
				XsdAttribute attr = (XsdAttribute) en.next();
				String nameToCheck = attr.getName();
				if (attrName.equals(nameToCheck)) {
					return attr;
				}
			}
			throw new SchemaProcessingException("Can't resolve local attribute: " + attrName);
		}
		else { //It's from an imported schema, which we have to go get.
			//See if we have a mapping for it yet...
			XsdSchema importedSchema = schemaMap.getSchema(nsURI);
			if (importedSchema != null) {
				//We have to remove the prefix from the type name so we can
				//find it in the local namespace of the imported schema.
				return getRefAttribute(importedSchema, unfixedRef.substring(unfixedRef.lastIndexOf(":") + 1));
			}
			else {
				//Hmph.  We haven't mapped this schema.  Let's try to map it.
				mapSchema(nsURI);
				importedSchema = schemaMap.getSchema(nsURI);
				if (importedSchema == null) {
					throw new SchemaProcessingException("Can't map external schema: " + nsURI);
				}
				return getRefAttribute(importedSchema, unfixedRef.substring(unfixedRef.lastIndexOf(":") + 1));
			}
		}
	}

	//Get a top-level XsdAttribute because it got ref=""ed somewhere.
	protected XsdElement getRefElement(XsdSchema schema, String unfixedRef) {
		//Yeah, I know it says get TYPE qname, but it works for refs too...
		QName eltQName = schema.getTypeQName(unfixedRef);
		String nsURI = eltQName.getNamespaceURI();
		String eltName = eltQName.getName();

		if (nsURI.equals(schema.getURI())) {
			Collection elementCollection = schema.getElements();
			for (Iterator en = elementCollection.iterator(); en.hasNext();) {
				XsdElement elt = (XsdElement) en.next();
				String nameToCheck = elt.getName();
				if (eltName.equals(nameToCheck)) {
					return elt;
				}
			}
			throw new SchemaProcessingException("Can't resolve local element: " + eltName);
		}
		else { //It's from an imported schema, which we have to go get.
			//See if we have a mapping for it yet...
			XsdSchema importedSchema = schemaMap.getSchema(nsURI);
			if (importedSchema != null) {
				//We have to remove the prefix from the type name so we can
				//find it in the local namespace of the imported schema.
				return getRefElement(importedSchema, unfixedRef.substring(unfixedRef.lastIndexOf(":") + 1));
			}
			else {
				//Hmph.  We haven't mapped this schema.  Let's try to map it.
				mapSchema(nsURI);
				importedSchema = schemaMap.getSchema(nsURI);
				if (importedSchema == null) {
					throw new SchemaProcessingException("Can't map external schema: " + nsURI);
				}
				return getRefElement(importedSchema, unfixedRef.substring(unfixedRef.lastIndexOf(":") + 1));
			}
		}
	}

	protected void getRefElements(Collection elts, XsdSchema schema) {
		Vector toRemove = new Vector();
		Vector toAdd = new Vector();
		for (Iterator i = elts.iterator(); i.hasNext();) {
			XsdElement xsdElt = (XsdElement) i.next();
			String ref = xsdElt.getRef();
			if (ref != null) {
				XsdElement refElt = getRefElement(schema, ref);
				if (refElt != null) {
					refElt.setReferencing(xsdElt);
					toRemove.add(xsdElt);
					toAdd.add(refElt);
				}
			}
		}
		elts.removeAll(toRemove);
		elts.addAll(toAdd);
	}

	protected SchemaAndType getXsdType(XsdSchema schema, String unfixedTypeName) {
		QName typeQName = schema.getTypeQName(unfixedTypeName);
		String nsURI = typeQName.getNamespaceURI();
		String typeName = typeQName.getName();
		if (nsURI.equals(XArchConstants.XSD_NS_URI)) {
			return null;
		}
		else if (nsURI.equals(XArchConstants.XLINK_NS_URI)) {
			return null;
		}
		else if (nsURI.equals(XArchConstants.OLD_XSI_NS_URI)) {
			return null;
		}
		else if (nsURI.equals(XArchConstants.XSI_NS_URI)) {
			return null;
		}
		else if (nsURI.equals(schema.getURI())) {
			Collection typeCollection = schema.getTypes();
			for (Iterator en = typeCollection.iterator(); en.hasNext();) {
				XsdType type = (XsdType) en.next();
				if (type instanceof XsdSimpleType) {
					String nameToCheck = ((XsdSimpleType) type).getName();
					if (typeName.equals(nameToCheck)) {
						return new SchemaAndType(schema, type);
					}
				}
				else if (type instanceof XsdComplexType) {
					String nameToCheck = ((XsdComplexType) type).getName();
					if (typeName.equals(nameToCheck)) {
						return new SchemaAndType(schema, type);
					}
				}
			}
			throw new SchemaProcessingException("Can't resolve local type: " + typeName);
		}
		else { //It's from an imported schema, which we have to go get.
			//See if we have a mapping for it yet...
			XsdSchema importedSchema = schemaMap.getSchema(nsURI);
			if (importedSchema != null) {
				//We have to remove the prefix from the type name so we can
				//find it in the local namespace of the imported schema.
				return getXsdType(importedSchema, unfixedTypeName.substring(unfixedTypeName.lastIndexOf(":") + 1));
			}
			else {
				//Hmph.  We haven't mapped this schema.  Let's try to map it.
				mapSchema(nsURI);
				importedSchema = schemaMap.getSchema(nsURI);
				if (importedSchema == null) {
					throw new SchemaProcessingException("Can't map external schema: " + nsURI);
				}
				return getXsdType(importedSchema, unfixedTypeName.substring(unfixedTypeName.lastIndexOf(":") + 1));
			}
		}
	}

	protected String getNodeTypeString(Node n) {
		String nsURI = n.getNamespaceURI();
		String localName = n.getLocalName();
		if (nsURI == null) {
			nsURI = "";
		}
		return nsURI + ":" + localName;
	}

	protected void writeFile(File f, String s, boolean append) {
		StringReader sr = new StringReader(s);
		writeFile(f, sr, append);
	}

	protected void writeFile(File f, Reader r, boolean append) {
		try {
			FileWriter fw = new FileWriter(f, append);
			char[] buf = new char[2048];
			while (true) {
				int len = r.read(buf, 0, buf.length);
				if (len > 0) {
					fw.write(buf, 0, len);
				}
				if (len < buf.length) {
					r.close();
					fw.close();
					return;
				}
			}
		}
		catch (Exception e) {
			throw new SchemaProcessingException(e);
		}
	}

	protected StringBuffer readIntoBuffer(StringBuffer sb, Reader r) {
		try {
			char[] buf = new char[2048];
			while (true) {
				int len = r.read(buf, 0, buf.length);
				sb.append(buf, 0, len);
				if (len < buf.length) {
					r.close();
					return sb;
				}
			}
		}
		catch (Exception e) {
			throw new SchemaProcessingException(e.toString());
		}
	}

	private static boolean containsUsedType(Vector v, String namespaceURI, String typeName) {
		for (Iterator en = v.iterator(); en.hasNext();) {
			UsedXsdType usedXsdType = (UsedXsdType) en.next();
			if (usedXsdType.namespaceURI.equals(namespaceURI) && usedXsdType.typeName.equals(typeName)) {
				return true;
			}
		}
		return false;
	}

	protected Collection getAllXsdTypesUsed(XsdSchema schema) {
		//Returns a collection of UsedTypes.
		Vector v = new Vector();

		//Go through the simple types.
		Collection simpleTypes = schema.getSimpleTypes();
		for (Iterator en = simpleTypes.iterator(); en.hasNext();) {
			XsdSimpleType simpleType = (XsdSimpleType) en.next();

			UsedXsdType newType = new UsedXsdType();
			newType.namespaceURI = schema.getURI();
			newType.typeName = simpleType.getName();
			newType.baseTypeNamespaceURI = null;
			newType.baseTypeName = null;
			v.addElement(newType);
		}

		//Go through the complex types.
		Collection complexTypes = schema.getComplexTypes();
		for (Iterator en = complexTypes.iterator(); en.hasNext();) {
			XsdComplexType complexType = (XsdComplexType) en.next();
			String typeName = complexType.getName();

			UsedXsdType newType = new UsedXsdType();
			newType.namespaceURI = schema.getURI();
			newType.typeName = complexType.getName();
			newType.baseTypeNamespaceURI = null;
			newType.baseTypeName = null;

			Collection xsdElements = null;

			//We have to see if it's extending another type.
			XsdComplexContent complexContent = complexType.getComplexContent();
			if (complexContent != null) {
				XsdExtension extension = complexContent.getExtension();
				if (extension != null) {
					String unfixedTypeName = extension.getBase();
					QName fixedTypeName = schema.getTypeQName(unfixedTypeName);
					newType.baseTypeNamespaceURI = fixedTypeName.getNamespaceURI();
					newType.baseTypeName = fixedTypeName.getName();
					xsdElements = ApiGen.getAllXsdElements(extension);
				}
			}
			v.addElement(newType);

			//fix for Roshanak 5/2003 --EMD
			if (xsdElements == null) {
				xsdElements = ApiGen.getAllXsdElements(complexType);
				getRefElements(xsdElements, schema);
			}

			//Now iterate through all the element types, find
			//any imported types, and add them to our list
			for (Iterator en2 = xsdElements.iterator(); en2.hasNext();) {
				XsdElement elt = (XsdElement) en2.next();
				String unfixedEltTypeName = elt.getType();
				if (unfixedEltTypeName.lastIndexOf(":") != -1) {
					//It's an imported type.
					QName importedTypeQName = schema.getTypeQName(unfixedEltTypeName);
					if (!ApiGen.containsUsedType(v, importedTypeQName.getNamespaceURI(), importedTypeQName.getName())) {
						UsedXsdType newImportedType = new UsedXsdType();
						newImportedType.namespaceURI = importedTypeQName.getNamespaceURI();
						newImportedType.typeName = importedTypeQName.getName();
						newImportedType.baseTypeNamespaceURI = null;
						newImportedType.baseTypeName = null;
						v.addElement(newImportedType);
					}
				}
			}
		}
		return v;
	}

	protected static String capFirstLetter(String s) {
		if (s.length() == 0) {
			return s;
		}
		String firstLet = s.substring(0, 1);
		return firstLet.toUpperCase() + s.substring(1);
	}

	protected static String toConstantCase(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (Character.isUpperCase(ch)) {
				sb.append("_" + ch);
			}
			else {
				sb.append(Character.toUpperCase(ch));
			}
		}
		return sb.toString();
	}

	protected static String nullRemainingVars(String s) {
		s = s.replaceAll("\\\"\\$\\$\\(.*?\\)\\\"", "null");
		s = s.replaceAll("\\$\\$\\(.*?\\)", "null");
		return s;
	}
}

class SchemaAndType {
	public XsdSchema schema;
	public XsdType type;

	public SchemaAndType(XsdSchema schema, XsdType type) {
		this.schema = schema;
		this.type = type;
	}
}

class UsedXsdType {
	public String namespaceURI;
	public String typeName;
	public String baseTypeNamespaceURI;
	public String baseTypeName;

	public String toString() {
		return "UsedXsdType:{" + namespaceURI + ":" + typeName + "; " + baseTypeNamespaceURI + ":" + baseTypeName;
	}
}
