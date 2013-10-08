package edu.uci.isr.bna4.things.swt;

import edu.uci.isr.bna4.constants.CompletionStatus;
import edu.uci.isr.bna4.facets.IHasMutableCompletionStatus;
import edu.uci.isr.bna4.facets.IHasMutableEditing;
import edu.uci.isr.bna4.things.essence.AnchorPointEssenceThing;

public class AbstractSWTThing extends AnchorPointEssenceThing implements IHasMutableCompletionStatus, IHasMutableEditing {

	public AbstractSWTThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setCompletionStatus(CompletionStatus.INCOMPLETE);
		setEditing(false);
	}

	public CompletionStatus getCompletionStatus() {
		return getProperty(COMPLETION_STATUS_PROPERTY_NAME);
	}

	public void setCompletionStatus(CompletionStatus completionStatus) {
		setProperty(COMPLETION_STATUS_PROPERTY_NAME, completionStatus);
	}

	public void setEditing(boolean editing) {
		setProperty(EDITING_PROPERTY_NAME, editing);
	}

	public boolean isEditing() {
		return getProperty(EDITING_PROPERTY_NAME);
	}
}
