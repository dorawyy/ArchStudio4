package org.eclipse.jet.internal.compiler.templates;

import org.eclipse.jet.JET2Context;
import org.eclipse.jet.JET2Template;
import org.eclipse.jet.JET2Writer;
import org.eclipse.jet.taglib.RuntimeTagElement;
import org.eclipse.jet.taglib.TagInfo;
import org.eclipse.jet.internal.compiler.GenXMLElement;

public class XMLStartGenerator implements JET2Template {
    private static final String _jetns_c = "org.eclipse.jet.controlTags"; //$NON-NLS-1$

    public XMLStartGenerator() {
        super();
    }

    private static final String NL = System.getProperty("line.separator"); //$NON-NLS-1$
    
    private static final TagInfo _td_c_get_6_1 = new TagInfo("c:get", //$NON-NLS-1$
            6, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
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
    private static final TagInfo _td_c_get_9_1 = new TagInfo("c:get", //$NON-NLS-1$
            9, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_12_1 = new TagInfo("c:get", //$NON-NLS-1$
            12, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_14_1 = new TagInfo("c:get", //$NON-NLS-1$
            14, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_16_1 = new TagInfo("c:get", //$NON-NLS-1$
            16, 1,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "$indent", //$NON-NLS-1$
            } );

    public void generate(final JET2Context context, final JET2Writer __out) {
        JET2Writer out = __out;

	GenXMLElement element = (GenXMLElement) context.getVariable("element"); //$NON-NLS-1$

        RuntimeTagElement _jettag_c_get_6_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_6_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_6_1.setRuntimeParent(null);
        _jettag_c_get_6_1.setTagInfo(_td_c_get_6_1);
        _jettag_c_get_6_1.doStart(context, out);
        _jettag_c_get_6_1.doEnd();
        out.write("RuntimeTagElement ");  //$NON-NLS-1$        
        out.write( element.getTagVariable() );
        out.write(" = context.getTagFactory().createRuntimeTag(_jetns_");  //$NON-NLS-1$        
        out.write( element.getNSPrefix() );
        out.write(", \"");  //$NON-NLS-1$        
        out.write( element.getTagNCName() );
        out.write("\", \"");  //$NON-NLS-1$        
        out.write( element.getName() );
        out.write("\", ");  //$NON-NLS-1$        
        out.write( element.getTagInfoVariable() );
        out.write("); //$NON-NLS-1$ //$NON-NLS-2$");  //$NON-NLS-1$        
        out.write(NL);         
        RuntimeTagElement _jettag_c_get_7_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_7_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_7_1.setRuntimeParent(null);
        _jettag_c_get_7_1.setTagInfo(_td_c_get_7_1);
        _jettag_c_get_7_1.doStart(context, out);
        _jettag_c_get_7_1.doEnd();
        out.write( element.getTagVariable() );
        out.write(".setRuntimeParent(");  //$NON-NLS-1$        
        out.write( element.getParentTagVariable() );
        out.write(");");  //$NON-NLS-1$        
        out.write(NL);         
        RuntimeTagElement _jettag_c_get_8_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_8_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_8_1.setRuntimeParent(null);
        _jettag_c_get_8_1.setTagInfo(_td_c_get_8_1);
        _jettag_c_get_8_1.doStart(context, out);
        _jettag_c_get_8_1.doEnd();
        out.write( element.getTagVariable() );
        out.write(".setTagInfo(");  //$NON-NLS-1$        
        out.write( element.getTagInfoVariable() );
        out.write(");");  //$NON-NLS-1$        
        out.write(NL);         
        RuntimeTagElement _jettag_c_get_9_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_9_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_9_1.setRuntimeParent(null);
        _jettag_c_get_9_1.setTagInfo(_td_c_get_9_1);
        _jettag_c_get_9_1.doStart(context, out);
        _jettag_c_get_9_1.doEnd();
        out.write( element.getTagVariable() );
        out.write(".doStart(context, out);");  //$NON-NLS-1$        
        out.write(NL);         
  if(element.hasBody()) { 
     if(element.requiresNewWriter()) { 
        RuntimeTagElement _jettag_c_get_12_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_12_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_12_1.setRuntimeParent(null);
        _jettag_c_get_12_1.setTagInfo(_td_c_get_12_1);
        _jettag_c_get_12_1.doStart(context, out);
        _jettag_c_get_12_1.doEnd();
        out.write("JET2Writer ");  //$NON-NLS-1$        
        out.write( element.getTagVariable() );
        out.write("_saved_out = out;");  //$NON-NLS-1$        
        out.write(NL);         
     } 
        RuntimeTagElement _jettag_c_get_14_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_14_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_14_1.setRuntimeParent(null);
        _jettag_c_get_14_1.setTagInfo(_td_c_get_14_1);
        _jettag_c_get_14_1.doStart(context, out);
        _jettag_c_get_14_1.doEnd();
        out.write("while (");  //$NON-NLS-1$        
        out.write( element.getTagVariable() );
        out.write(".okToProcessBody()) {");  //$NON-NLS-1$        
        out.write(NL);         
     if(element.requiresNewWriter()) { 
        RuntimeTagElement _jettag_c_get_16_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_16_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_get_16_1.setRuntimeParent(null);
        _jettag_c_get_16_1.setTagInfo(_td_c_get_16_1);
        _jettag_c_get_16_1.doStart(context, out);
        _jettag_c_get_16_1.doEnd();
        out.write("    out = out.newNestedContentWriter();");  //$NON-NLS-1$        
        out.write(NL);         
     } 
  } 
    }
}
