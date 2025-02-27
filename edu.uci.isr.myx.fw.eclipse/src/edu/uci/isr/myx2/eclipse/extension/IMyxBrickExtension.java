package edu.uci.isr.myx2.eclipse.extension;

import java.util.Collection;

/**
 * Brick. This class represents a brick defined in the extension point. (See
 * schema/edu.uci.isr.myx2.eclipse.myxBrick.exsd file for the schema) A brick
 * has the following attributes and elements.
 * <ul>
 * <li>id : the id attribute of the brick. This should be unique in the
 * workspace.</li>
 * <li>name : the name attribute of the brick.</li>
 * <li>baseJavaClass : the myx base class name. It is intended to hide the
 * complexity of the myx framwork, and it implements the myx framework
 * interfaces. The class will be generated by the source code generation, and it
 * is not intended to be modified by the client.</li>
 * <li>defaultImplClass : the name of skeleton code. It will be generated by the
 * source code generation. It extends the baseJavaClass, and it is intended to
 * be modified by the client.</li>
 * <li>(Collection of) interfaces : the collection of interface elements of the
 * brick.</li>
 * <li>description : the description elements of the brick.</li>
 * <li>parentBrickId : the parent brick id. If this is specified, the brick
 * extends the brick specified by the id.</li>
 * </ul>
 * Sample:
 * 
 * <pre>
 * &lt;brick
 *       id=&quot;sample.brick.Sink&quot;
 *       name=&quot;Sink&quot;
 *       baseJavaClass=&quot;sample.brick.myx.SinkBase&quot;
 *       defaultImplClass=&quot;sample.brick.myx.Sink&quot;&gt;
 *    &lt;interface
 *          name=&quot;message&quot;
 *          direction=&quot;inServiceObject&quot;
 *          javaInterface=&quot;sample.IMessage&quot;
 *          connectionTiming=&quot;beforeBegin&quot;
 *          templateType=&quot;delegate&quot;&gt;
 *    &lt;/interface&gt;
 *    &lt;interface
 *          name=&quot;otherService&quot;
 *          direction=&quot;outMultipleServiceObject&quot;
 *          javaInterface=&quot;sample.IOtherService&quot;
 *          connectionTiming=&quot;beforeBegin&quot;
 *          templateType=&quot;delegate&quot;&gt;
 *    &lt;/interface&gt;
 *    &lt;description&gt;
 *          This is a sample.
 *    &lt;/description&gt;   
 *    &lt;parentBrick
 *          parentBrickId=&quot;other.sample.brick.AbstractBrick&quot;&gt;
 *    &lt;/parentBrick&gt;
 * &lt;/brick&gt;
 * </pre>
 * 
 * @author Nobu Takeo nobu.takeo@gmail.com, nobu.takeo@uci.edu
 */
public interface IMyxBrickExtension extends IExtensionPointLocation {

	/** the "brick" element name of the extension */
	public static final String ELEMENT_NAME = "brick";

	/** the "id" attribute of the "brick" element */
	public static final String ATTR_ID = "id";

	/** the "name" attribute of the "brick" element */
	public static final String ATTR_NAME = "name";

	/** the "baseJavaClass" attribute of the "brick" element */
	public static final String ATTR_BASE_JAVA_CLASS = "baseJavaClass";

	/** the "defaultImplClass" attribute of the "brick" element */
	public static final String ATTR_DEFAULT_IMPL__CLASS = "defaultImplClass";

	/** the "parentBrickId" attribute of the "parentBrick" element */
	public static final String ATTR_PARENT_BRICK_ID = "parentBrickId";

	/** the description element of this brick */
	public static final String ELEMENT_DESCRIPTION_NAME = "description";

	/** the "parentBrick" element of this brick */
	public static final String ELEMENT_PARENT_BRICK_NAME = "parentBrick";

	/**
	 * Returns the list of Myx Interfaces
	 * 
	 * @return
	 */
	public Collection<IInterface> getInterfaces();

	/**
	 * Returns the id of this brick
	 * 
	 * @return the id, or null is no id is assigned.
	 */
	public String getId();

	/**
	 * Returns the name of this brick
	 * 
	 * @return the name of this brick. null if no name is assigned.
	 */
	public String getName();

	/**
	 * Returns the fully qualified Java Class Name of default implementation
	 * class of this brick
	 * 
	 * @return the fully qualified java class name. null if no class name is
	 *         assigned.
	 */
	public String getFQDefaultImplClassName();

	/**
	 * Returns the fully qualified Java Class Name of Myx Base class of this
	 * brick
	 * 
	 * @return the fully qualified java class name. null if no class name is
	 *         assigned.
	 */
	public String getFQBaseClassName();

	/**
	 * Returns the parent brick id
	 * 
	 * @return
	 */
	public String getParentBrickId();

	/**
	 * Returns the description of this description.
	 * 
	 * @return the description or null if no description is assigned.
	 */
	public String getDescription();

	/**
	 * Returns the parent brick
	 * 
	 * @return the parent brick
	 */
	public IMyxBrickExtension getParentBrick();

	public void setParentBrick(IMyxBrickExtension parentBrick);

	public Collection<IInterface> getAncestorsExtensionInterfaces();

}