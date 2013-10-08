package edu.uci.isr.xadlutils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.uci.isr.sysutils.UIDGenerator;
import edu.uci.isr.xarchflat.IXArchPropertyMetadata;
import edu.uci.isr.xarchflat.IXArchTypeMetadata;
import edu.uci.isr.xarchflat.ObjRef;
import edu.uci.isr.xarchflat.XArchFlatEvent;
import edu.uci.isr.xarchflat.XArchFlatInterface;
import edu.uci.isr.xarchflat.XArchFlatQueryInterface;
import edu.uci.isr.xarchflat.XArchMetadataUtils;

public class XadlUtils {

	/*
	 * public static void main(String[] args){ XArchFlatInterface xarch = new
	 * XArchFlatImpl(); ObjRef xArchRef = xarch.createXArch("urn:foo"); ObjRef
	 * typesContextRef = xarch.createContext(xArchRef, "types"); ObjRef
	 * archStructureRef1 = xarch.createElement(typesContextRef,
	 * "archStructure"); ObjRef archStructureRef2 =
	 * xarch.createElement(typesContextRef, "archStructure"); ObjRef
	 * componentRef = xarch.create(typesContextRef, "component");
	 * System.out.println("I'm now adding the id to the thing.");
	 * //xarch.dump(componentRef); xarch.set(componentRef, "id", "myid");
	 * //xarch.dump(componentRef); xarch.add(xArchRef, "Object",
	 * archStructureRef1); xarch.add(xArchRef, "Object", archStructureRef2);
	 * System.out.println("-----------------------STARTING");
	 * xarch.add(archStructureRef1, "component", componentRef);
	 * System.out.println(xarch.serialize(xArchRef));
	 * System.out.println("componentRef is:" + componentRef); ObjRef myidRef =
	 * xarch.getByID(xArchRef, "myid"); System.out.println("myidRef is:" +
	 * myidRef); xarch.remove(archStructureRef1, "component", componentRef);
	 * xarch.add(archStructureRef2, "component", componentRef);
	 * System.out.println("componentRef is:" + componentRef); ObjRef myidRef2 =
	 * xarch.getByID(xArchRef, "myid"); System.out.println("myidRef2 is:" +
	 * myidRef2); }
	 */

	public static ObjRef getArchTypes(XArchFlatInterface xarch, ObjRef xArchRef) {
		ObjRef typesContextRef = xarch.createContext(xArchRef, "types");
		ObjRef archTypesRef = xarch.getElement(typesContextRef, "archTypes", xArchRef);
		return archTypesRef;
	}

	public static String getHref(XArchFlatQueryInterface xarch, ObjRef ref) {
		try {
			String href = (String) xarch.get(ref, "href");
			return href;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static String getID(XArchFlatQueryInterface xarch, ObjRef ref) {
		try {
			if (ref != null) {
				return (String) xarch.get(ref, "id");
			}
		}
		catch (Exception e) {
		}
		return null;
	}

	public static ObjRef resolveXLink(XArchFlatQueryInterface xarch, ObjRef ref) {
		String linkTypeString = (String) xarch.get(ref, "type");
		if (linkTypeString == null || linkTypeString.equals("simple")) {
			String hrefString = (String) xarch.get(ref, "href");
			if (hrefString == null) {
				return null;
			}
			else {
				ObjRef xArchRef = xarch.getXArch(ref);
				try {
					ObjRef targetRef = xarch.resolveHref(xArchRef, hrefString);
					return targetRef;
				}
				catch (Exception e) {
					return null;
				}
			}
		}
		else {
			throw new IllegalArgumentException("Can only resolve simple type XLinks.");
		}
	}

	public static ObjRef resolveXLink(XArchFlatQueryInterface xarch, ObjRef ref, String childName) {
		try {
			Object value = xarch.get(ref, childName);
			if (value instanceof ObjRef) {
				ObjRef childRef = (ObjRef) value;
				if (childRef != null && xarch.isInstanceOf(childRef, "instance#XMLLink")) {
					return XadlUtils.resolveXLink(xarch, childRef);
				}
			}
		}
		catch (Throwable t) {
		}
		return null;
	}

	public static void setXLinkByHref(XArchFlatInterface xarch, ObjRef ref, String linkElementName, String href) {
		boolean foundLinkRef = true;
		ObjRef linkRef = (ObjRef) xarch.get(ref, linkElementName);
		if (linkRef == null) {
			foundLinkRef = false;
			ObjRef xArchRef = xarch.getXArch(ref);
			ObjRef instanceContextRef = xarch.createContext(xArchRef, "instance");
			linkRef = xarch.create(instanceContextRef, "XMLLink");
		}
		xarch.set(linkRef, "type", "simple");
		xarch.set(linkRef, "href", href);
		if (!foundLinkRef) {
			xarch.set(ref, linkElementName, linkRef);
		}
	}

	public static void setXLink(XArchFlatInterface xarch, ObjRef ref, String linkElementName, String targetID) {
		XadlUtils.setXLinkByHref(xarch, ref, linkElementName, "#" + targetID);
	}

	public static void setXLink(XArchFlatInterface xarch, ObjRef ref, String linkElementName, ObjRef targetRef) {
		ObjRef refXArchRef = xarch.getXArch(ref);
		ObjRef targetXArchRef = xarch.getXArch(targetRef);
		if (refXArchRef.equals(targetXArchRef)) {
			XadlUtils.setXLinkByHref(xarch, ref, linkElementName, "#" + XadlUtils.getID(xarch, targetRef));
		}
		else {
			XadlUtils.setXLinkByHref(xarch, ref, linkElementName, xarch.getXArchURI(targetXArchRef) + "#" + XadlUtils.getID(xarch, targetRef));
		}
	}

	public static String getDescription(XArchFlatQueryInterface xarch, ObjRef ref) {
		return XadlUtils.getDescription(xarch, ref, "description", null);
	}

	public static String getDescription(XArchFlatQueryInterface xarch, ObjRef ref, String name, String defaultValue) {
		try {
			ObjRef descRef = (ObjRef) xarch.get(ref, name);
			if (descRef != null) {
				String desc = (String) xarch.get(descRef, "Value");
				return desc;
			}
		}
		catch (Exception e) {
		}
		return defaultValue;
	}

	public static void setDescription(XArchFlatInterface xarch, ObjRef ref, String newDescription) {
		XadlUtils.setDescription(xarch, ref, "description", newDescription);
	}

	public static void setDescription(XArchFlatInterface xarch, ObjRef ref, String name, String newDescription) {
		try {
			if (newDescription != null) {
				ObjRef descRef = (ObjRef) xarch.get(ref, name);
				if (descRef != null) {
					xarch.set(descRef, "Value", newDescription);
					return;
				}
				else {
					ObjRef xarchRef = xarch.getXArch(ref);
					ObjRef instanceContextRef = xarch.createContext(xarchRef, "instance");
					descRef = xarch.create(instanceContextRef, "Description");
					xarch.set(descRef, "Value", newDescription);
					xarch.set(ref, name, descRef);
				}
			}
			else {
				xarch.clear(ref, name);
			}
		}
		catch (Exception e) {
		}
	}

	public static String getDirection(XArchFlatQueryInterface xarch, ObjRef ref) {
		try {
			ObjRef dirRef = (ObjRef) xarch.get(ref, "Direction");
			if (dirRef != null) {
				String dir = (String) xarch.get(dirRef, "Value");
				return dir;
			}
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static void setDirection(XArchFlatInterface xarch, ObjRef ref, String newDirection) {
		try {
			ObjRef dirRef = (ObjRef) xarch.get(ref, "Direction");
			if (dirRef != null) {
				xarch.set(dirRef, "Value", newDirection);
				return;
			}
			else {
				ObjRef xarchRef = xarch.getXArch(ref);
				ObjRef instanceContextRef = xarch.createContext(xarchRef, "instance");
				dirRef = xarch.create(instanceContextRef, "Direction");
				xarch.set(dirRef, "Value", newDirection);
				xarch.set(ref, "Direction", dirRef);
			}
		}
		catch (Exception e) {
		}
	}

	public static LinkInfo getLinkInfo(XArchFlatQueryInterface xarch, ObjRef linkRef, boolean resolve) {
		LinkInfo li = new LinkInfo();

		li.setLinkRef(linkRef);

		ObjRef[] pointRefs = xarch.getAll(linkRef, "Point");

		ObjRef xArchRef = null;
		if (resolve) {
			xArchRef = xarch.getXArch(linkRef);
		}

		if (pointRefs.length > 0) {
			li.setPoint1Ref(pointRefs[0]);
			ObjRef anchor1Ref = (ObjRef) xarch.get(pointRefs[0], "AnchorOnInterface");
			if (anchor1Ref != null) {
				li.setAnchor1Ref(anchor1Ref);
				String anchor1Type = (String) xarch.get(anchor1Ref, "type");
				li.setAnchor1Type(anchor1Type);
				String anchor1Href = (String) xarch.get(anchor1Ref, "href");
				li.setAnchor1Href(anchor1Href);

				if (resolve) {
					ObjRef point1Target = xarch.resolveHref(xArchRef, anchor1Href);
					li.setPoint1Target(point1Target);
				}
			}
		}
		if (pointRefs.length > 1) {
			li.setPoint2Ref(pointRefs[1]);
			ObjRef anchor2Ref = (ObjRef) xarch.get(pointRefs[1], "AnchorOnInterface");
			if (anchor2Ref != null) {
				li.setAnchor2Ref(anchor2Ref);
				String anchor2Type = (String) xarch.get(anchor2Ref, "type");
				li.setAnchor2Type(anchor2Type);
				String anchor2Href = (String) xarch.get(anchor2Ref, "href");
				li.setAnchor2Href(anchor2Href);

				if (resolve) {
					ObjRef point2Target = xarch.resolveHref(xArchRef, anchor2Href);
					li.setPoint2Target(point2Target);
				}
			}
		}
		return li;
	}

	public static LinkInfo getTransitionInfo(XArchFlatQueryInterface xarch, ObjRef transitionRef, boolean resolve) {
		LinkInfo li = new LinkInfo();

		li.setLinkRef(transitionRef);

		ObjRef xArchRef = null;
		if (resolve) {
			xArchRef = xarch.getXArch(transitionRef);
		}

		ObjRef anchor1Ref = (ObjRef) xarch.get(transitionRef, "fromState");
		if (anchor1Ref != null) {
			li.setAnchor1Ref(anchor1Ref);
			String anchor1Type = (String) xarch.get(anchor1Ref, "type");
			li.setAnchor1Type(anchor1Type);
			String anchor1Href = (String) xarch.get(anchor1Ref, "href");
			li.setAnchor1Href(anchor1Href);

			if (resolve) {
				ObjRef point1Target = xarch.resolveHref(xArchRef, anchor1Href);
				li.setPoint1Target(point1Target);
			}
		}

		ObjRef anchor2Ref = (ObjRef) xarch.get(transitionRef, "toState");
		if (anchor2Ref != null) {
			li.setAnchor2Ref(anchor2Ref);
			String anchor2Type = (String) xarch.get(anchor2Ref, "type");
			li.setAnchor2Type(anchor2Type);
			String anchor2Href = (String) xarch.get(anchor2Ref, "href");
			li.setAnchor2Href(anchor2Href);

			if (resolve) {
				ObjRef point2Target = xarch.resolveHref(xArchRef, anchor2Href);
				li.setPoint2Target(point2Target);
			}
		}
		return li;
	}

	//Gets all the signature-interface mappings in a type involving this signature
	public static ObjRef[] getSignatureInterfaceMappings(XArchFlatQueryInterface xarch, ObjRef typeRef, ObjRef signatureRef) {
		ArrayList<ObjRef> simRefList = new ArrayList<ObjRef>();
		ObjRef subArchitectureRef = (ObjRef) xarch.get(typeRef, "subArchitecture");
		if (subArchitectureRef != null) {
			ObjRef xArchRef = xarch.getXArch(subArchitectureRef);
			//System.out.println("got subArchitectureRef");
			ObjRef[] simRefs = xarch.getAll(subArchitectureRef, "signatureInterfaceMapping");
			for (ObjRef element : simRefs) {
				//System.out.println("checking SIM" + i);
				ObjRef sigLinkRef = (ObjRef) xarch.get(element, "outerSignature");
				if (sigLinkRef != null) {
					//System.out.println("got sig link ref");
					String href = XadlUtils.getHref(xarch, sigLinkRef);
					if (href != null) {
						//System.out.println("got sig link href");
						ObjRef referencedSigRef = xarch.resolveHref(xArchRef, href);
						//System.out.println("referencedSigRef = " + referencedSigRef);
						//System.out.println("referencedSigID = " + getID(xarch, referencedSigRef));
						//System.out.println("sigRef = " + signatureRef);
						//System.out.println("sigID= " + getID(xarch, signatureRef));
						if (referencedSigRef != null && referencedSigRef.equals(signatureRef)) {
							//System.out.println("got referenced sig ref");
							simRefList.add(element);
						}
					}
				}
			}
		}
		return simRefList.toArray(new ObjRef[0]);
	}

	public static ObjRef[] getInvalidSignatureInterfaceMappings(XArchFlatQueryInterface xarch, ObjRef typeRef) {
		Set<ObjRef> invalidSIMRefList = new HashSet<ObjRef>();
		ObjRef subArchitectureRef = (ObjRef) xarch.get(typeRef, "subArchitecture");
		if (subArchitectureRef != null) {
			ObjRef xArchRef = xarch.getXArch(subArchitectureRef);
			ObjRef[] simRefs = xarch.getAll(subArchitectureRef, "signatureInterfaceMapping");
			for (ObjRef element : simRefs) {
				boolean simValid = true;

				ObjRef outerSignatureRef = XadlUtils.resolveXLink(xarch, element, "outerSignature");
				if (outerSignatureRef == null) {
					invalidSIMRefList.add(element);
					continue;
				}
				if (!xarch.isInstanceOf(outerSignatureRef, "types#Signature")) {
					invalidSIMRefList.add(element);
					continue;
				}

				ObjRef outerSignatureParentRef = xarch.getParent(outerSignatureRef);
				if (outerSignatureParentRef == null) {
					invalidSIMRefList.add(element);
					continue;
				}

				if (!xarch.isEqual(typeRef, outerSignatureParentRef)) {
					invalidSIMRefList.add(element);
					continue;
				}

				//OK, the outer signature is OK.  Let's check the inner interface.
				ObjRef innerInterfaceRef = XadlUtils.resolveXLink(xarch, element, "innerInterface");
				if (innerInterfaceRef == null) {
					invalidSIMRefList.add(element);
					continue;
				}
				if (!xarch.isInstanceOf(innerInterfaceRef, "types#Interface")) {
					invalidSIMRefList.add(element);
					continue;
				}

				//should be the brick
				ObjRef innerInterfaceParentRef = xarch.getParent(innerInterfaceRef);
				if (innerInterfaceParentRef == null) {
					invalidSIMRefList.add(element);
					continue;
				}
				//Should be the structure
				ObjRef innerInterfaceGrandparentRef = xarch.getParent(innerInterfaceParentRef);
				if (innerInterfaceGrandparentRef == null) {
					invalidSIMRefList.add(element);
					continue;
				}
				if (!xarch.isInstanceOf(innerInterfaceGrandparentRef, "types#ArchStructure")) {
					invalidSIMRefList.add(element);
					continue;
				}

				//Make sure the structure of the inner brick matches the inner structure of the type
				ObjRef innerStructureRef = XadlUtils.resolveXLink(xarch, subArchitectureRef, "archStructure");
				if (innerStructureRef == null) {
					invalidSIMRefList.add(element);
					continue;
				}
				if (!xarch.isInstanceOf(innerStructureRef, "types#ArchStructure")) {
					invalidSIMRefList.add(element);
					continue;
				}
				if (!xarch.isEqual(innerStructureRef, innerInterfaceGrandparentRef)) {
					invalidSIMRefList.add(element);
					continue;
				}
			}
		}
		return invalidSIMRefList.toArray(new ObjRef[0]);
	}

	public static class LinkInfo {

		protected ObjRef linkRef;
		protected ObjRef point1Ref;
		protected ObjRef anchor1Ref;
		protected ObjRef point2Ref;
		protected ObjRef anchor2Ref;
		protected String anchor1Type;
		protected String anchor1Href;
		protected String anchor2Type;
		protected String anchor2Href;

		protected ObjRef point1Target;
		protected ObjRef point2Target;

		public String getAnchor1Href() {
			return anchor1Href;
		}

		public ObjRef getAnchor1Ref() {
			return anchor1Ref;
		}

		public String getAnchor1Type() {
			return anchor1Type;
		}

		public String getAnchor2Href() {
			return anchor2Href;
		}

		public ObjRef getAnchor2Ref() {
			return anchor2Ref;
		}

		public String getAnchor2Type() {
			return anchor2Type;
		}

		public ObjRef getLinkRef() {
			return linkRef;
		}

		public ObjRef getPoint1Ref() {
			return point1Ref;
		}

		public ObjRef getPoint1Target() {
			return point1Target;
		}

		public ObjRef getPoint2Ref() {
			return point2Ref;
		}

		public ObjRef getPoint2Target() {
			return point2Target;
		}

		public void setAnchor1Href(String string) {
			anchor1Href = string;
		}

		public void setAnchor1Ref(ObjRef ref) {
			anchor1Ref = ref;
		}

		public void setAnchor1Type(String string) {
			anchor1Type = string;
		}

		public void setAnchor2Href(String string) {
			anchor2Href = string;
		}

		public void setAnchor2Ref(ObjRef ref) {
			anchor2Ref = ref;
		}

		public void setAnchor2Type(String string) {
			anchor2Type = string;
		}

		public void setLinkRef(ObjRef ref) {
			linkRef = ref;
		}

		public void setPoint1Ref(ObjRef ref) {
			point1Ref = ref;
		}

		public void setPoint1Target(ObjRef ref) {
			point1Target = ref;
		}

		public void setPoint2Ref(ObjRef ref) {
			point2Ref = ref;
		}

		public void setPoint2Target(ObjRef ref) {
			point2Target = ref;
		}
	}

	private static String replaceSuffix(String s, String oldSuffix, String newSuffix) {
		if (s.endsWith(oldSuffix)) {
			s = s.substring(0, s.length() - oldSuffix.length());
			return s + newSuffix;
		}
		return null;
	}

	public static String guessGoodSignatureDescription(String interfaceName) {
		if (interfaceName == null) {
			return "[New Signature]";
		}
		String signatureName = interfaceName;

		String s = null;
		if ((s = XadlUtils.replaceSuffix(signatureName, "iface", "sig")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "Iface", "Sig")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "IFACE", "SIG")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "intf", "sig")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "Intf", "Sig")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "INTF", "SIG")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "interface", "signature")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "Interface", "Signature")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "INTERFACE", "SIGNATURE")) != null) {
			return s;
		}
		return signatureName;
	}

	public static String guessGoodInterfaceDescription(String signatureName) {
		if (signatureName == null) {
			return "[New Interface]";
		}

		String interfaceName = signatureName;

		String s = null;
		if ((s = XadlUtils.replaceSuffix(signatureName, "sig", "iface")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "Sig", "Iface")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "SIG", "IFACE")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "signature", "interface")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "Signature", "Interface")) != null) {
			return s;
		}
		if ((s = XadlUtils.replaceSuffix(signatureName, "SIGNATURE", "INTERFACE")) != null) {
			return s;
		}
		return interfaceName;
	}

	private static final int WAS_COMPONENT = 100;
	private static final int WAS_CONNECTOR = 200;

	public static String typeToInstanceDescription(String typeDescription) {
		try {
			if (typeDescription.toLowerCase().endsWith("_type")) {
				return typeDescription.substring(0, typeDescription.length() - 5).trim();
			}
			else if (typeDescription.toLowerCase().endsWith("type")) {
				return typeDescription.substring(0, typeDescription.length() - 4).trim();
			}
			return typeDescription;
		}
		catch (Exception e) {
			return typeDescription;
		}
	}

	public static String sigToIfaceDescription(String sigDescription) {
		try {
			if (sigDescription.toLowerCase().endsWith("_signature")) {
				return sigDescription.substring(0, sigDescription.length() - 10).trim();
			}
			else if (sigDescription.toLowerCase().endsWith("signature")) {
				return sigDescription.substring(0, sigDescription.length() - 9).trim();
			}
			else if (sigDescription.toLowerCase().endsWith("_sig")) {
				return sigDescription.substring(0, sigDescription.length() - 4).trim();
			}
			else if (sigDescription.toLowerCase().endsWith("sig")) {
				return sigDescription.substring(0, sigDescription.length() - 3).trim();
			}
			return sigDescription;
		}
		catch (Exception e) {
			return sigDescription;
		}
	}

	public static ObjRef stampOutType(XArchFlatInterface xarch, ObjRef typeRef, String typeID, ObjRef archStructureRef) {
		ObjRef xArchRef = xarch.getXArch(typeRef);
		ObjRef typesContextRef = xarch.createContext(xArchRef, "types");
		ObjRef instanceContextRef = xarch.createContext(xArchRef, "instance");

		ObjRef brickRef = null;

		int kindOfBrick = 0;
		if (xarch.isInstanceOf(typeRef, "types#ComponentType")) {
			brickRef = xarch.create(typesContextRef, "Component");
			String brickID = UIDGenerator.generateUID("component");
			xarch.set(brickRef, "id", brickID);

			String brickDescription = "(New Component)";
			String typeDescription = XadlUtils.getDescription(xarch, typeRef);
			if (typeDescription != null) {
				brickDescription = XadlUtils.typeToInstanceDescription(typeDescription);
			}

			ObjRef brickDescriptionRef = xarch.create(instanceContextRef, "Description");
			xarch.set(brickDescriptionRef, "Value", brickDescription);
			xarch.set(brickRef, "Description", brickDescriptionRef);

			kindOfBrick = XadlUtils.WAS_COMPONENT;
		}
		else if (xarch.isInstanceOf(typeRef, "types#ConnectorType")) {
			brickRef = xarch.create(typesContextRef, "Connector");
			String brickID = UIDGenerator.generateUID("connector");
			xarch.set(brickRef, "id", brickID);

			String brickDescription = "(New Connector)";
			String typeDescription = XadlUtils.getDescription(xarch, typeRef);
			if (typeDescription != null) {
				brickDescription = XadlUtils.typeToInstanceDescription(typeDescription);
			}

			ObjRef brickDescriptionRef = xarch.create(instanceContextRef, "Description");
			xarch.set(brickDescriptionRef, "Value", brickDescription);
			xarch.set(brickRef, "Description", brickDescriptionRef);

			kindOfBrick = XadlUtils.WAS_CONNECTOR;
		}
		else {
			return null;
		}

		ObjRef[] signatureRefs = xarch.getAll(typeRef, "signature");
		for (ObjRef signatureRef : signatureRefs) {
			String signatureDirection = XadlUtils.getDirection(xarch, signatureRef);

			ObjRef signatureTypeLinkRef = (ObjRef) xarch.get(signatureRef, "type");
			String signatureTypeLinkType = null;
			String signatureTypeLinkHref = null;
			if (signatureTypeLinkRef != null) {
				signatureTypeLinkType = (String) xarch.get(signatureTypeLinkRef, "type");
				signatureTypeLinkHref = (String) xarch.get(signatureTypeLinkRef, "href");
			}

			ObjRef interfaceRef = xarch.create(typesContextRef, "Interface");
			String interfaceID = UIDGenerator.generateUID("interface");
			xarch.set(interfaceRef, "id", interfaceID);

			String interfaceDescription = "(New Interface)";
			String sigDescription = XadlUtils.getDescription(xarch, signatureRef);
			if (sigDescription != null) {
				interfaceDescription = XadlUtils.sigToIfaceDescription(sigDescription);
			}

			ObjRef interfaceDescriptionRef = xarch.create(instanceContextRef, "Description");
			xarch.set(interfaceDescriptionRef, "Value", interfaceDescription);
			xarch.set(interfaceRef, "Description", interfaceDescriptionRef);

			if (signatureDirection != null) {
				ObjRef interfaceDirectionRef = xarch.create(instanceContextRef, "Direction");
				xarch.set(interfaceDirectionRef, "Value", signatureDirection);
				xarch.set(interfaceRef, "Direction", interfaceDirectionRef);
			}

			String signatureID = XadlUtils.getID(xarch, signatureRef);
			if (signatureID != null) {
				ObjRef interfaceSignatureLinkRef = xarch.create(instanceContextRef, "XMLLink");
				xarch.set(interfaceSignatureLinkRef, "type", "simple");
				xarch.set(interfaceSignatureLinkRef, "href", "#" + signatureID);
				xarch.set(interfaceRef, "signature", interfaceSignatureLinkRef);
			}

			ObjRef interfaceTypeLinkRef = xarch.create(instanceContextRef, "XMLLink");
			if (signatureTypeLinkType != null) {
				xarch.set(interfaceTypeLinkRef, "type", signatureTypeLinkType);
			}
			if (signatureTypeLinkHref != null) {
				xarch.set(interfaceTypeLinkRef, "href", signatureTypeLinkHref);
			}
			xarch.set(interfaceRef, "type", interfaceTypeLinkRef);
			xarch.add(brickRef, "interface", interfaceRef);
		}

		ObjRef brickTypeLinkRef = xarch.create(instanceContextRef, "XMLLink");
		xarch.set(brickTypeLinkRef, "type", "simple");
		xarch.set(brickTypeLinkRef, "href", "#" + typeID);

		xarch.set(brickRef, "type", brickTypeLinkRef);

		if (kindOfBrick == XadlUtils.WAS_COMPONENT) {
			xarch.add(archStructureRef, "component", brickRef);
		}
		else if (kindOfBrick == XadlUtils.WAS_CONNECTOR) {
			xarch.add(archStructureRef, "connector", brickRef);
		}

		return brickRef;
	}

	public static boolean isTargetedToDocument(XArchFlatInterface xarch, ObjRef xArchRef, XArchFlatEvent evt) {
		if (evt == null) {
			return false;
		}
		if (xArchRef == null) {
			return false;
		}
		ObjRef srcRef = evt.getSource();
		if (!xarch.isValidObjRef(srcRef))
			return false;
		if (srcRef != null) {
			//ObjRef targetXArchRef = xarch.getXArch(srcRef);
			//return xarch.equals(xArchRef, targetXArchRef);
			return xarch.equals(xArchRef, xarch.getXArch(srcRef));
		}
		if (evt.getTarget() instanceof ObjRef) {
			//ObjRef targetXArchRef = xarch.getXArch((ObjRef)evt.getTarget());
			//return xarch.equals(xArchRef, targetXArchRef);
			return xarch.equals(xArchRef, xarch.getXArch((ObjRef) evt.getTarget()));
		}
		return true;
	}

	public static void remove(XArchFlatInterface xarch, ObjRef objRef) {
		ObjRef parentRef = xarch.getParent(objRef);
		if (parentRef != null) {
			ObjRef xArchRef = xarch.getXArch(objRef);
			String elementName = xArchRef.equals(parentRef) ? "Object" : xarch.getElementName(objRef);
			IXArchTypeMetadata type = xarch.getTypeMetadata(parentRef);
			IXArchPropertyMetadata prop = type.getProperty(elementName);
			if (prop.getMaxOccurs() > 1) {
				xarch.remove(parentRef, elementName, objRef);
			}
			else {
				xarch.clear(parentRef, elementName);
			}
		}
	}

	public static ObjRef getObjRefForXMLLink(XArchFlatInterface xArch, ObjRef objRef, String xmlLinkName) {
		ObjRef xmlLinkRef = (ObjRef) xArch.get(objRef, xmlLinkName);
		if (xmlLinkRef != null) {
			String href = (String) xArch.get(xmlLinkRef, "href");
			if (href != null && !"".equals(href.trim())) {
				String xArchID = href.replaceFirst("#", "");
				return xArch.getByID(xArchID);
			}
		}
		return null;
	}

	public static List<ObjRef> getObjRefsForXMLLink(XArchFlatInterface xArch, ObjRef objRef, String xmlLinkName) {
		ObjRef[] xmlLinkRefs = xArch.getAll(objRef, xmlLinkName);
		List<ObjRef> objRefs = new ArrayList<ObjRef>();
		for (ObjRef xmlLinkRef : xmlLinkRefs) {
			if (xmlLinkRef != null) {
				String href = (String) xArch.get(xmlLinkRef, "href");
				if (href != null && !"".equals(href.trim())) {
					String xArchID = href.replaceFirst("#", "");
					ObjRef ref = xArch.getByID(xArchID);
					if (ref != null) {
						objRefs.add(ref);
					}
				}
			}
		}
		return objRefs;
	}

	public static class CloneResults {

		public final ObjRef sourceXArchRef;
		public final ObjRef sourceObjRef;
		public final ObjRef targetXArchRef;
		public final ObjRef targetObjRef;
		public final Map<String, String> sourceToTargetIDMap;
		public final Collection<ObjRef> clonedLinkRefs;

		public CloneResults(XArchFlatQueryInterface xarch, ObjRef sourceObjRef, ObjRef targetObjRef, Map<String, String> sourceToTargetIDMap,
		        Collection<ObjRef> clonedLinkRefs) {
			this.sourceObjRef = sourceObjRef;
			this.targetObjRef = targetObjRef;
			this.sourceXArchRef = xarch.getXArch(sourceObjRef);
			this.targetXArchRef = xarch.getXArch(targetObjRef);
			this.sourceToTargetIDMap = sourceToTargetIDMap;
			this.clonedLinkRefs = clonedLinkRefs;
		}
	}

	private static ObjRef clone(XArchFlatInterface xarch, ObjRef sourceRef, boolean sourceRefIsElement, ObjRef sourceXArchRef, ObjRef targetXArchRef,
	        boolean createNewIDs, Map<String, String> sourceToTargetIDMap, Collection<ObjRef> linkRefs) {
		IXArchTypeMetadata tm = xarch.getTypeMetadata(sourceRef);
		ObjRef targetContextRef = xarch.createContext(targetXArchRef, XArchMetadataUtils.getTypeContext(tm.getType()));
		ObjRef targetRef;
		if (sourceRefIsElement) {
			targetRef = xarch.createElement(targetContextRef, XArchMetadataUtils.getTypeName(tm.getType()));
		}
		else {
			targetRef = xarch.create(targetContextRef, XArchMetadataUtils.getTypeName(tm.getType()));
		}
		if (xarch.isInstanceOf(targetRef, "instance#XMLLink")) {
			linkRefs.add(targetRef);
		}
		for (IXArchPropertyMetadata p : tm.getProperties()) {
			switch (p.getMetadataType()) {

			case IXArchPropertyMetadata.ATTRIBUTE: {
				String value;
				if ("id".equals(p.getName())) {
					String oldValue = (String) xarch.get(sourceRef, p.getName());
					value = createNewIDs ? UIDGenerator.generateUID() : oldValue;
					if (oldValue != null) {
						// we need the mappings even to the same ID in order to update links later
						sourceToTargetIDMap.put(oldValue, value);
					}
				}
				else {
					value = (String) xarch.get(sourceRef, p.getName());
				}
				if (value != null) {
					xarch.set(targetRef, p.getName(), value);
				}
			}
				break;

			case IXArchPropertyMetadata.ELEMENT: {
				ObjRef value = (ObjRef) xarch.get(sourceRef, p.getName());
				if (value != null) {
					xarch.set(targetRef, p.getName(), XadlUtils.clone(xarch, value, false, sourceXArchRef, targetXArchRef, createNewIDs, sourceToTargetIDMap,
					        linkRefs));
				}
			}
				break;

			case IXArchPropertyMetadata.ELEMENT_MANY: {
				ObjRef[] values = xarch.getAll(sourceRef, p.getName());
				for (int i = 0; i < values.length; i++) {
					values[i] = XadlUtils.clone(xarch, values[i], false, sourceXArchRef, targetXArchRef, createNewIDs, sourceToTargetIDMap, linkRefs);
				}
				xarch.add(targetRef, p.getName(), values);
			}
				break;

			default:
				throw new RuntimeException();
			}
		}
		return targetRef;
	}

	public static CloneResults clone(XArchFlatInterface xarch, ObjRef sourceRef, boolean sourceRefIsElement, ObjRef targetXArchRef, boolean createNewIDs) {
		Map<String, String> sourceToTargetIDMap = new HashMap<String, String>();
		List<ObjRef> linkRefs = new ArrayList<ObjRef>();
		ObjRef targetRef = XadlUtils.clone(xarch, sourceRef, sourceRefIsElement, xarch.getXArch(sourceRef), targetXArchRef, createNewIDs, sourceToTargetIDMap,
		        linkRefs);
		return new CloneResults(xarch, sourceRef, targetRef, sourceToTargetIDMap, linkRefs);
	}

	public static void redirectClonedLinksToCloneResults(XArchFlatInterface xarch, CloneResults cloneResults) throws URISyntaxException {
		URI sourceURI = new URI(xarch.getXArchURI(cloneResults.sourceXArchRef));
		for (ObjRef linkRef : cloneResults.clonedLinkRefs) {
			String href = (String) xarch.get(linkRef, "href");
			if (href != null) {
				URI clonedURI = XadlUtils.resolve(sourceURI, href);
				String id = clonedURI.getFragment();
				if (cloneResults.sourceToTargetIDMap.containsKey(id)) {
					xarch.set(linkRef, "href", new URI(null, null, cloneResults.sourceToTargetIDMap.get(id)).toString());
					continue;
				}
				URI relativedLinkURI = XadlUtils.relativize(sourceURI, clonedURI);
				xarch.set(linkRef, "href", relativedLinkURI.toString());
			}
		}
	}

	public static void redirectExternalLinksToCloneResults(XArchFlatInterface xarch, CloneResults cloneResults) throws URISyntaxException {
		for (Map.Entry<String, String> entry : cloneResults.sourceToTargetIDMap.entrySet()) {
			URI clonedURI = new URI(xarch.getXArchURI(cloneResults.targetXArchRef));
			clonedURI = new URI(clonedURI.getScheme(), clonedURI.getSchemeSpecificPart(), entry.getValue());

			for (ObjRef linkRef : xarch.getReferences(cloneResults.sourceXArchRef, entry.getKey())) {
				URI linkURI = new URI(xarch.getXArchURI(xarch.getXArch(linkRef)));
				URI relativizedLinkURI = XadlUtils.relativize(linkURI, clonedURI);

				xarch.set(linkRef, "href", relativizedLinkURI.toString());
			}
		}
	}

	/**
	 * Similar to {@link URI#resolve(String)} except for the following:
	 * <ol>
	 * <li>if the child is a fragment (i.e., of the form "#ID"), then the ID is
	 * <i>always</i> added to the base URI. This differs from
	 * {@link URI#resolve(String)}, which will return the child if the base URI
	 * is {@link URI#isOpaque() opaque} (i.e., of the form "urn:something"
	 * without a '/' after the scheme).
	 * <li>the fragment is treated as unencoded (i.e., ' ' is not transformed to
	 * '%20') unless there is the presence of a '%' in the fragment.</li>
	 * <li>the result is {@link URI#normalize() normalized}.
	 * </ol>
	 * 
	 * @param xArch
	 *            search within this document first (for hrefs of form "#ID")
	 * @param base
	 *            the absolute base URI for the resolution
	 * @param href
	 *            the href to resolve against the base URI
	 * @return a resolved, normalized, URI.
	 * @throws URISyntaxException
	 */
	public static URI resolve(URI base, String href) throws URISyntaxException {
		if (href.startsWith("#")) {
			String id;
			if (href.indexOf('%') >= 0) {
				// assume that it's already encoded properly if there is a '%' in it
				id = new URI(href).getFragment();
			}
			else {
				// assume that it's not encoded, so don't decode it
				id = href.substring(1);
			}
			return new URI(base.getScheme(), base.getSchemeSpecificPart(), id).normalize();
		}
		return base.resolve(href).normalize();
	}

	/**
	 * Similar to {@link URI#relativize(URI)} except that it additionally
	 * searches for a common root in the URI paths.
	 * 
	 * @param base
	 *            the base URI against which to determine a relative URI
	 * @param child
	 *            the URI which should be obtained from resolving the results
	 *            against the base URI
	 * @return a relative, URI
	 * @throws URISyntaxException
	 */
	public static URI relativize(URI base, URI child) throws URISyntaxException {
		URI relURI = base.relativize(child);
		try {
			if (relURI.equals(child)) {
				if (!new URI(base.getScheme(), base.getAuthority(), "/", base.getQuery(), base.getFragment()).relativize(child).equals(child)) {
					// all other things being equal, child is relative to the root of base
					base = base.normalize();
					child = child.normalize();

					String bp = base.getPath();
					String cp = child.getPath();

					// find the first differing character
					int d = 0;
					while (d < bp.length() && d < cp.length()) {
						if (bp.charAt(d) == cp.charAt(d)) {
							d++;
						}
						else {
							break;
						}
					}

					// find the last '/' before d
					int lastSlash = bp.lastIndexOf('/', d);
					String dbp = bp.substring(lastSlash + 1);
					String dcp = cp.substring(lastSlash + 1);
					StringBuffer dcpb = new StringBuffer();

					int backSegmentCount = dbp.split("/").length;
					if (!dbp.endsWith("/")) {
						// we don't need to go back for the file itself, e.g., "a/b/c/d.xml" only needs 3 "../"'s
						backSegmentCount--;
					}
					for (int i = 0; i < backSegmentCount; i++) {
						dcpb.append("../");
					}
					dcpb.append(dcp);

					URI newRelURI = new URI(child.getScheme(), child.getAuthority(), dcpb.toString(), child.getQuery(), child.getFragment());

					// I don't trust this code THAT much :)
					if (base.resolve(newRelURI).normalize().equals(child)) {
						relURI = newRelURI;
					}
				}
			}
		}
		catch (Throwable t) {
			// oh well, we tried
		}
		return relURI;
	}
}
