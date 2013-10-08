/*******************************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 * /
 *******************************************************************************/

package org.eclipse.jet.taglib;


import org.eclipse.jet.JET2Context;
import org.eclipse.jet.JET2Writer;
import org.eclipse.jet.XPathContextExtender;
import org.eclipse.jet.core.expressions.IEmbeddedExpression;


/**
 * An abstract base class for all implementations of {@link CustomTag}.
 *
 */
public abstract class AbstractCustomTag implements CustomTag
{

  private CustomTag parent;

  private JET2Context context = null;

  private TagInfo td = null;

  private JET2Writer out = null;

  /**
   * 
   */
  public AbstractCustomTag()
  {
    super();
  }

  /**
   * @see org.eclipse.jet.taglib.CustomTag#getParent()
   */
  public final CustomTag getParent()
  {
    return parent;
  }

  /**
   * @see org.eclipse.jet.taglib.CustomTag#setParent(org.eclipse.jet.taglib.CustomTag)
   */
  public final void setParent(CustomTag parent)
  {
    this.parent = parent;
  }

  public final void setContext(JET2Context context)
  {
    if (this.context != null)
    {
      throw new IllegalStateException("Context already set."); //$NON-NLS-1$
    }
    this.context = context;
  }

  public final void setTagInfo(TagInfo td)
  {
    if (this.td != null)
    {
      throw new IllegalStateException("TagInfo already set."); //$NON-NLS-1$
    }
    this.td = td;
  }

  public final String getRawAttribute(String name)
  {
    if (this.td == null)
    {
      throw new IllegalStateException("TagInfo not set."); //$NON-NLS-1$
    }
    return td.getAttribute(name);
  }

  public final String getAttribute(String name) throws JET2TagException
  {
    String raw = getRawAttribute(name);
    if (raw != null)
    {
      final IEmbeddedExpression expr = context.getExpressionFactory().createExpression(raw);
      return expr.isText() ? XPathContextExtender.resolveDynamic(raw, context) : expr.evalAsString(context);
    }
    return null;
  }

  public final void setOut(JET2Writer out)
  {
    if (this.out != null)
    {
      throw new IllegalStateException("out already set."); //$NON-NLS-1$
    }
    this.out = out;
  }

  public final JET2Writer getOut()
  {
    return out;
  }
}
