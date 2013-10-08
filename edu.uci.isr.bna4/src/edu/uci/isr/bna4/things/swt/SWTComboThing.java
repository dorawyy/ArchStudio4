package edu.uci.isr.bna4.things.swt;

public class SWTComboThing extends SWTListThing {

	public SWTComboThing() {
		this(null);
	}

	public SWTComboThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setAllowsArbitraryInput(true);
	}
}
