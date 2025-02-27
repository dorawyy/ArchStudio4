/*
 * *** IMPORTANT: Machine generated, DO NOT MODIFY ***
 * Generated by:
 * transform ID: org.eclipse.jet.transforms.tagfactory
 * on input: /org.eclipse.jet/plugin.xml
 *
 * The transformation may be found in the JET CVS repository
 */
/**
 *
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 */

package org.eclipse.jet.internal.taglib.control;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jet.taglib.CustomTag;
import org.eclipse.jet.taglib.JET2TagException;
import org.eclipse.jet.taglib.TagInstanceFactory;

/**
 * Tag Factory for controlTags
 */
public class TagFactory implements TagInstanceFactory
{

  private final Map tagOrdinalByName;
  
  /**
   * 
   */
  public TagFactory()
  {
    tagOrdinalByName = new HashMap(30);

    tagOrdinalByName.put("addElement",new Integer(1)); //$NON-NLS-1$
    tagOrdinalByName.put("addTextElement",new Integer(2)); //$NON-NLS-1$
    tagOrdinalByName.put("case",new Integer(3)); //$NON-NLS-1$
    tagOrdinalByName.put("choose",new Integer(4)); //$NON-NLS-1$
    tagOrdinalByName.put("copyElement",new Integer(5)); //$NON-NLS-1$
    tagOrdinalByName.put("deepContent",new Integer(6)); //$NON-NLS-1$
    tagOrdinalByName.put("deepIterate",new Integer(7)); //$NON-NLS-1$
    tagOrdinalByName.put("dump",new Integer(8)); //$NON-NLS-1$
    tagOrdinalByName.put("get",new Integer(9)); //$NON-NLS-1$
    tagOrdinalByName.put("if",new Integer(10)); //$NON-NLS-1$
    tagOrdinalByName.put("include",new Integer(11)); //$NON-NLS-1$
    tagOrdinalByName.put("initialCode",new Integer(12)); //$NON-NLS-1$
    tagOrdinalByName.put("invokeTransform",new Integer(13)); //$NON-NLS-1$
    tagOrdinalByName.put("iterate",new Integer(14)); //$NON-NLS-1$
    tagOrdinalByName.put("load",new Integer(15)); //$NON-NLS-1$
    tagOrdinalByName.put("loadContent",new Integer(16)); //$NON-NLS-1$
    tagOrdinalByName.put("log",new Integer(17)); //$NON-NLS-1$
    tagOrdinalByName.put("marker",new Integer(18)); //$NON-NLS-1$
    tagOrdinalByName.put("otherwise",new Integer(19)); //$NON-NLS-1$
    tagOrdinalByName.put("override",new Integer(20)); //$NON-NLS-1$
    tagOrdinalByName.put("removeElement",new Integer(21)); //$NON-NLS-1$
    tagOrdinalByName.put("replaceStrings",new Integer(22)); //$NON-NLS-1$
    tagOrdinalByName.put("set",new Integer(23)); //$NON-NLS-1$
    tagOrdinalByName.put("setVariable",new Integer(24)); //$NON-NLS-1$
    tagOrdinalByName.put("stringTokens",new Integer(25)); //$NON-NLS-1$
    tagOrdinalByName.put("userRegion",new Integer(26)); //$NON-NLS-1$
    tagOrdinalByName.put("visit",new Integer(27)); //$NON-NLS-1$
    tagOrdinalByName.put("visitor",new Integer(28)); //$NON-NLS-1$
    tagOrdinalByName.put("when",new Integer(29)); //$NON-NLS-1$
    tagOrdinalByName.put("with",new Integer(30)); //$NON-NLS-1$
  }

  public CustomTag createCustomTag(String name)
  {
    Integer ordinal = (Integer)tagOrdinalByName.get(name);
    
    switch(ordinal == null ? 0 : ordinal.intValue()) {
      case 1: // addElement
        return new org.eclipse.jet.internal.taglib.control.AddElementTag();
      case 2: // addTextElement
        return new org.eclipse.jet.internal.taglib.control.AddTextElementTag();
      case 3: // case
        return new org.eclipse.jet.internal.taglib.control.WhenTag();
      case 4: // choose
        return new org.eclipse.jet.internal.taglib.control.ChooseTag();
      case 5: // copyElement
        return new org.eclipse.jet.internal.taglib.control.CopyElementTag();
      case 6: // deepContent
        return new org.eclipse.jet.internal.taglib.control.DeepContentTag();
      case 7: // deepIterate
        return new org.eclipse.jet.internal.taglib.control.DeepIterateTag();
      case 8: // dump
        return new org.eclipse.jet.internal.taglib.control.DumpTag();
      case 9: // get
        return new org.eclipse.jet.internal.taglib.control.GetTag();
      case 10: // if
        return new org.eclipse.jet.internal.taglib.control.IfTag();
      case 11: // include
        return new org.eclipse.jet.internal.taglib.control.IncludeTag();
      case 12: // initialCode
        return new org.eclipse.jet.internal.taglib.control.InitialCodeTag();
      case 13: // invokeTransform
        return new org.eclipse.jet.internal.taglib.control.InvokeTransformTag();
      case 14: // iterate
        return new org.eclipse.jet.internal.taglib.control.IterateTag();
      case 15: // load
        return new org.eclipse.jet.internal.taglib.control.LoadTag();
      case 16: // loadContent
        return new org.eclipse.jet.internal.taglib.control.LoadContentTag();
      case 17: // log
        return new org.eclipse.jet.internal.taglib.control.LogTag();
      case 18: // marker
        return new org.eclipse.jet.internal.taglib.control.MarkerTag();
      case 19: // otherwise
        return new org.eclipse.jet.internal.taglib.control.OtherwiseTag();
      case 20: // override
        return new org.eclipse.jet.internal.taglib.control.OverrideTag();
      case 21: // removeElement
        return new org.eclipse.jet.internal.taglib.control.RemoveElementTag();
      case 22: // replaceStrings
        return new org.eclipse.jet.internal.taglib.control.ReplaceStringsTag();
      case 23: // set
        return new org.eclipse.jet.internal.taglib.control.SetTag();
      case 24: // setVariable
        return new org.eclipse.jet.internal.taglib.control.SetVariableTag();
      case 25: // stringTokens
        return new org.eclipse.jet.internal.taglib.control.StringTokensTag();
      case 26: // userRegion
        return new org.eclipse.jet.internal.taglib.control.UserRegionTag();
      case 27: // visit
        return new org.eclipse.jet.internal.taglib.control.VisitTag();
      case 28: // visitor
        return new org.eclipse.jet.internal.taglib.control.VisitorTag();
      case 29: // when
        return new org.eclipse.jet.internal.taglib.control.WhenTag();
      case 30: // with
        return new org.eclipse.jet.internal.taglib.control.WithTag();
      default:
        throw new JET2TagException("Unknown Tag: " + name); //$NON-NLS-1$
    }
  }

}
