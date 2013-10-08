package org.eclipse.jet.internal.compiler.templates;

import org.eclipse.jet.JET2Context;
import org.eclipse.jet.JET2Template;
import org.eclipse.jet.JET2Writer;
import org.eclipse.jet.taglib.RuntimeTagElement;
import org.eclipse.jet.taglib.TagInfo;
import org.eclipse.jet.internal.compiler.GenXMLElement;

public class XMLEndGenerator implements JET2Template {
    private static final String _jetns_c = "org.eclipse.jet.controlTags"; //$NON-NLS-1$

    public XMLEndGenerator() {
        super();
    }

    private static final String NL = System.getProperty("line.separator"); //$NON-NLS-1$
    
    private static final TagInfo _td_c_get_7_1 = new TagInfo("c:get", //$NON-NLS-1$
            7, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_8_1 = new TagInfo("c:get", //$NON-NLS-1$
            8, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_10_1 = new TagInfo("c:get", //$NON-NLS-1$
            10, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_13_1 = new TagInfo("c:get", //$NON-NLS-1$
            13, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );

    public void generate(final JET2Context context, final JET2Writer __out) {
        JET2Writer out = __out;

	GenXMLElement element = (GenXMLElement) context.getVariable("element"); //$NON-NLS-1$

  if(element.hasBody()) { 
        RuntimeTagElement _jettag_c_get_7_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_7_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_7_1.setRuntimeParent(null);
        _jettag_c_get_7_1.setTagInfo(_td_c_get_7_1);
        _jettag_c_get_7_1.doStart(context, out);
        _jettag_c_get_7_1.doEnd();
        out.write("    ");  //$NON-NLS-1$        
        out.write( element.getTagVariable() );
        out.write(".handleBodyContent(out);");  //$NON-NLS-1$        
        out.write(NL);         
        RuntimeTagElement _jettag_c_get_8_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_8_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_8_1.setRuntimeParent(null);
        _jettag_c_get_8_1.setTagInfo(_td_c_get_8_1);
        _jettag_c_get_8_1.doStart(context, out);
        _jettag_c_get_8_1.doEnd();
        out.write("}");  //$NON-NLS-1$        
        out.write(NL);         
     if(element.requiresNewWriter()) { 
        RuntimeTagElement _jettag_c_get_10_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_10_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_10_1.setRuntimeParent(null);
        _jettag_c_get_10_1.setTagInfo(_td_c_get_10_1);
        _jettag_c_get_10_1.doStart(context, out);
        _jettag_c_get_10_1.doEnd();
        out.write("out = ");  //$NON-NLS-1$        
        out.write( element.getTagVariable() );
        out.write("_saved_out;");  //$NON-NLS-1$        
        out.write(NL);         
     } 
  } 
        RuntimeTagElement _jettag_c_get_13_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_13_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_13_1.setRuntimeParent(null);
        _jettag_c_get_13_1.setTagInfo(_td_c_get_13_1);
        _jettag_c_get_13_1.doStart(context, out);
        _jettag_c_get_13_1.doEnd();
        out.write( element.getTagVariable() );
        out.write(".doEnd();");  //$NON-NLS-1$        
        out.write(NL);         
    }
}
