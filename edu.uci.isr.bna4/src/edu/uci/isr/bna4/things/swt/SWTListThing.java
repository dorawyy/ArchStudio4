package edu.uci.isr.bna4.things.swt;

public class SWTListThing extends SWTTextThing {

	public static final String OPTIONS_PROPERTY_NAME = "options";
	public static final String ALLOWS_ARBITRARY_INPUT_PROPERTY_NAME = "allowsArbitraryInput";

	public SWTListThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setOptions(new String[] { "[No Options]" });
		setAllowsArbitraryInput(false);
	}

	public void setOptions(String[] options) {
		setProperty(OPTIONS_PROPERTY_NAME, options);
	}

	public String[] getOptions() {
		return getProperty(OPTIONS_PROPERTY_NAME);
	}

	public void setAllowsArbitraryInput(boolean allowsArbitraryInput) {
		setProperty(ALLOWS_ARBITRARY_INPUT_PROPERTY_NAME, allowsArbitraryInput);
	}

	public boolean getAllowsArbitraryInput() {
		return getProperty(ALLOWS_ARBITRARY_INPUT_PROPERTY_NAME);
	}
}
