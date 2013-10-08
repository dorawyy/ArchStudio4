package edu.uci.isr.bna4.things.labels;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextLayout;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.widgets.swt.constants.FontStyle;
import edu.uci.isr.widgets.swt.constants.HorizontalAlignment;
import edu.uci.isr.widgets.swt.constants.VerticalAlignment;

public class UserNotificationThingPeer extends AbstractThingPeer<UserNotificationThing> {

	class C {
		int textWidth;
		int textHeight;
	}

	public UserNotificationThingPeer(UserNotificationThing t) {
		super(t, UserNotificationThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		String text = t.getText();
		if (text == null || text.trim().length() == 0) {
			return;
		}

		Point lap = BNAUtils.worldToLocal(view.getCoordinateMapper(), t.getAnchorPoint());

		String fontName = t.getFontName();
		int fontSize = t.getFontSize();
		FontStyle fontStyle = t.getFontStyle();

		VerticalAlignment verticalAlignment = t.getVerticalAlignment();
		HorizontalAlignment horizontalAlignment = t.getHorizontalAlignment();

		TextLayout tl = null;
		try {
			tl = new TextLayout(res.getDevice());
			tl.setText(text);
			tl.setFont(res.getFont(fontName, fontSize, fontStyle.toSWT()));

			Rectangle textExtent = BNAUtils.toRectangle(tl.getBounds());
			if (!clip.intersects(textExtent)) {
				return;
			}

			int textWidth = textExtent.width;
			int textHeight = textExtent.height;

			switch (horizontalAlignment) {
			case CENTER:
				lap.x = lap.x - textWidth / 2;
				break;
			case RIGHT:
				lap.x = lap.x - textWidth;
				break;
			}

			switch (verticalAlignment) {
			case MIDDLE:
				lap.y = lap.y - textHeight / 2;
				break;
			case BOTTOM:
				lap.y = lap.y - textHeight;
				break;
			}

			//Life drops from 16 to 0
			int life = t.getLife();

			//Step goes from 0 to 8 back to 0
			int step = 8 - Math.abs(life - 8);
			int alpha = step * 64;
			if (alpha > 255) {
				alpha = 255;
			}
			g.setAlpha(alpha);

			//Draw a little shadow
			g.setForegroundColor(res.getColor(null, SWT.COLOR_DARK_GRAY));
			g.drawLine(lap.x - 1, lap.y - 1 + textHeight + 4, lap.x - 1 + textWidth + 4, lap.y - 1 + textHeight + 4);
			g.drawLine(lap.x - 1 + textWidth + 4, lap.y - 1, lap.x - 1 + textWidth + 4, lap.y - 1 + textHeight + 4);

			Color bg = res.getColor(t.getSecondaryColor(), SWT.COLOR_GRAY);
			g.setBackgroundColor(bg);
			g.fillRectangle(lap.x - 2, lap.y - 2, textWidth + 4, textHeight + 4);

			Color fg = res.getColor(t.getColor(), SWT.COLOR_BLACK);
			g.setForegroundColor(fg);
			g.drawText(text, lap.x, lap.y);

		}
		finally {
			if (tl != null) {
				tl.dispose();
				tl = null;
			}
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
