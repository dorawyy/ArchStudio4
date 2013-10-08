package org.eclipse.jet.compiled;

import org.eclipse.jet.JET2Context;
import org.eclipse.jet.JET2Template;
import org.eclipse.jet.JET2Writer;
import org.eclipse.jet.taglib.RuntimeTagElement;
import org.eclipse.jet.taglib.TagInfo;

public class _jet_main implements JET2Template {
    private static final String _jetns_c = "org.eclipse.jet.controlTags"; //$NON-NLS-1$
    private static final String _jetns_java = "org.eclipse.jet.javaTags"; //$NON-NLS-1$
    private static final String _jetns_ws = "org.eclipse.jet.workspaceTags"; //$NON-NLS-1$

    public _jet_main() {
        super();
    }

    private static final String NL = System.getProperty("line.separator"); //$NON-NLS-1$
    
    private static final TagInfo _td_ws_project_9_1 = new TagInfo("ws:project", //$NON-NLS-1$
            9, 1,
            new String[] {
                "name", //$NON-NLS-1$
            },
            new String[] {
                "{$org.eclipse.jet.resource.project.name}", //$NON-NLS-1$
            } );
    private static final TagInfo _td_ws_folder_10_3 = new TagInfo("ws:folder", //$NON-NLS-1$
            10, 3,
            new String[] {
                "path", //$NON-NLS-1$
            },
            new String[] {
                "{/Input/Env/@srcFolder}", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_setVariable_11_3 = new TagInfo("c:setVariable", //$NON-NLS-1$
            11, 3,
            new String[] {
                "select", //$NON-NLS-1$
                "var", //$NON-NLS-1$
            },
            new String[] {
                "/Input/Brick", //$NON-NLS-1$
                "BrickInfo", //$NON-NLS-1$
            } );
    private static final TagInfo _td_java_class_12_7 = new TagInfo("java:class", //$NON-NLS-1$
            12, 7,
            new String[] {
                "package", //$NON-NLS-1$
                "name", //$NON-NLS-1$
                "template", //$NON-NLS-1$
            },
            new String[] {
                "{$BrickInfo/@package}", //$NON-NLS-1$
                "{$BrickInfo/@class}", //$NON-NLS-1$
                "templates/{$BrickInfo/@template}", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_if_32_1 = new TagInfo("c:if", //$NON-NLS-1$
            32, 1,
            new String[] {
                "test", //$NON-NLS-1$
            },
            new String[] {
                "isVariableDefined('org.eclipse.jet.resource.project.name')", //$NON-NLS-1$
            } );
    private static final TagInfo _td_ws_file_33_5 = new TagInfo("ws:file", //$NON-NLS-1$
            33, 5,
            new String[] {
                "template", //$NON-NLS-1$
                "path", //$NON-NLS-1$
            },
            new String[] {
                "templates/dump.jet", //$NON-NLS-1$
                "{$org.eclipse.jet.resource.project.name}/dump.xml", //$NON-NLS-1$
            } );

    public void generate(final JET2Context context, final JET2Writer __out) {
        JET2Writer out = __out;
        // Main entry point for edu.uci.isr.myxcodegen 
        // 
        //TODO: traverse input model, performing calculations and storing 
        //the results as model annotations via c:set tag 
        out.write(NL);         
        RuntimeTagElement _jettag_ws_project_9_1 = context.getTagFactory().createRuntimeTag(_jetns_ws, "project", "ws:project", _td_ws_project_9_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_ws_project_9_1.setRuntimeParent(null);
        _jettag_ws_project_9_1.setTagInfo(_td_ws_project_9_1);
        _jettag_ws_project_9_1.doStart(context, out);
        while (_jettag_ws_project_9_1.okToProcessBody()) {
            RuntimeTagElement _jettag_ws_folder_10_3 = context.getTagFactory().createRuntimeTag(_jetns_ws, "folder", "ws:folder", _td_ws_folder_10_3); //$NON-NLS-1$ //$NON-NLS-2$
            _jettag_ws_folder_10_3.setRuntimeParent(_jettag_ws_project_9_1);
            _jettag_ws_folder_10_3.setTagInfo(_td_ws_folder_10_3);
            _jettag_ws_folder_10_3.doStart(context, out);
            while (_jettag_ws_folder_10_3.okToProcessBody()) {
                RuntimeTagElement _jettag_c_setVariable_11_3 = context.getTagFactory().createRuntimeTag(_jetns_c, "setVariable", "c:setVariable", _td_c_setVariable_11_3); //$NON-NLS-1$ //$NON-NLS-2$
                _jettag_c_setVariable_11_3.setRuntimeParent(_jettag_ws_folder_10_3);
                _jettag_c_setVariable_11_3.setTagInfo(_td_c_setVariable_11_3);
                _jettag_c_setVariable_11_3.doStart(context, out);
                _jettag_c_setVariable_11_3.doEnd();
                out.write("      ");  //$NON-NLS-1$        
                RuntimeTagElement _jettag_java_class_12_7 = context.getTagFactory().createRuntimeTag(_jetns_java, "class", "java:class", _td_java_class_12_7); //$NON-NLS-1$ //$NON-NLS-2$
                _jettag_java_class_12_7.setRuntimeParent(_jettag_ws_folder_10_3);
                _jettag_java_class_12_7.setTagInfo(_td_java_class_12_7);
                _jettag_java_class_12_7.doStart(context, out);
                _jettag_java_class_12_7.doEnd();
                out.write(NL);         
                _jettag_ws_folder_10_3.handleBodyContent(out);
            }
            _jettag_ws_folder_10_3.doEnd();
            _jettag_ws_project_9_1.handleBodyContent(out);
        }
        _jettag_ws_project_9_1.doEnd();
        out.write(NL);         
        //
        //TODO: traverse annotated model, performing text generation actions 
        //such as ws:file, ws:folder and ws:project 
        out.write("   ");  //$NON-NLS-1$        
        out.write(NL);         
        out.write(NL);         
        // For debug purposes, dump the annotated input model in 
        //   the root of the project containing the original input model.
        //   
        //   Note that model formatting may not be identical, and that in
        //   the case of non-XML input models, the dump may look quite different.
        out.write(NL);         
        RuntimeTagElement _jettag_c_if_32_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "if", "c:if", _td_c_if_32_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_if_32_1.setRuntimeParent(null);
        _jettag_c_if_32_1.setTagInfo(_td_c_if_32_1);
        _jettag_c_if_32_1.doStart(context, out);
        while (_jettag_c_if_32_1.okToProcessBody()) {
            out.write("    ");  //$NON-NLS-1$        
            RuntimeTagElement _jettag_ws_file_33_5 = context.getTagFactory().createRuntimeTag(_jetns_ws, "file", "ws:file", _td_ws_file_33_5); //$NON-NLS-1$ //$NON-NLS-2$
            _jettag_ws_file_33_5.setRuntimeParent(_jettag_c_if_32_1);
            _jettag_ws_file_33_5.setTagInfo(_td_ws_file_33_5);
            _jettag_ws_file_33_5.doStart(context, out);
            _jettag_ws_file_33_5.doEnd();
            out.write(NL);         
            _jettag_c_if_32_1.handleBodyContent(out);
        }
        _jettag_c_if_32_1.doEnd();
    }
}
