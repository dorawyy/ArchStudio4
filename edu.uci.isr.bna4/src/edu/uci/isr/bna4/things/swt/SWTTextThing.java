package edu.uci.isr.bna4.things.swt;

import edu.uci.isr.bna4.facets.IHasMutableText;

public class SWTTextThing extends AbstractSWTThing implements IHasMutableText {

	public SWTTextThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setText("");
	}

	public String getText() {
		return (String) getProperty(TEXT_PROPERTY_NAME);
	}

	public void setText(String text) {
		setProperty(TEXT_PROPERTY_NAME, text);
	}
}
