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

public class BoxedLabelThingPeer extends AbstractThingPeer<BoxedLabelThing> {

	protected TextLayoutCache textLayoutCache = null;

	public BoxedLabelThingPeer(BoxedLabelThing t) {
		super(t, BoxedLabelThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		String text = t.getText();
		if (text == null || text.trim().length() == 0) {
			return;
		}

		Color fg = res.getColor(t.getColor(), SWT.COLOR_BLACK);
		String fontName = t.getFontName();
		int fontSize = t.getFontSize();
		FontStyle fontStyle = t.getFontStyle();
		boolean dontIncreaseFontSize = t.getDontIncreaseFontSize();

		VerticalAlignment verticalAlignment = t.getVerticalAlignment();
		HorizontalAlignment horizontalAlignment = t.getHorizontalAlignment();

		if (textLayoutCache == null) {
			textLayoutCache = new TextLayoutCache(getDisplay());
		}
		textLayoutCache.setIncreaseFontSize(!dontIncreaseFontSize);
		textLayoutCache.setText(text);
		textLayoutCache.setFont(res.getFont(fontName, fontSize, fontStyle.toSWT()));
		{
			/*
			 * The size of the local bounding box may differ slightly depending
			 * on the world origin. This causes excessive recalculation of the
			 * text layout, which is not really necessary. To overcome this, the
			 * width and height of the layout are only changed if they differ
			 * significantly from what they were before.
			 */

			Point oldSize = textLayoutCache.getSize();
			Point size = new Point(lbb.width, lbb.height);
			int dx = size.x - oldSize.x;
			int dy = size.y - oldSize.y;
			if (0 <= dx && dx <= 1 && 0 <= dy && dy <= 1) {
				size = oldSize;
			}
			textLayoutCache.setSize(size);
		}
		textLayoutCache.setAlignment(horizontalAlignment.toSWT());

		TextLayout tl = textLayoutCache.getTextLayout();
		if (tl != null) {
			Rectangle tlBounds = BNAUtils.toRectangle(tl.getBounds());

			int x = lbb.x;
			switch (horizontalAlignment) {
			case LEFT:
				break;
			case CENTER:
				x += (lbb.width - tlBounds.width) / 2;
				break;
			case RIGHT:
				x += lbb.width - tlBounds.width;
				break;
			}

			int y = lbb.y;
			switch (verticalAlignment) {
			case TOP:
				break;
			case MIDDLE:
				y += (lbb.height - tlBounds.height) / 2;
				break;
			case BOTTOM:
				y += lbb.height - tlBounds.height;
				break;
			}

			g.drawTextLayout(tl, x, y);
		}
		else {
			drawFakeLines(g, lbb);
		}
	}

	private void drawFakeLines(Graphics g, Rectangle localBoundingBox) {
		g.setLineStyle(SWT.LINE_DASHDOT);
		if (localBoundingBox.width >= 3) {
			if (localBoundingBox.height >= 1) {
				int y = localBoundingBox.y + localBoundingBox.height / 2;
				g.drawLine(localBoundingBox.x + 1, y, localBoundingBox.x + localBoundingBox.width - 2, y);
			}
			if (localBoundingBox.height > 5) {
				int y = localBoundingBox.y + localBoundingBox.height / 2 - 2;
				g.drawLine(localBoundingBox.x + 1, y, localBoundingBox.x + localBoundingBox.width - 2, y);
				y += 4;
				g.drawLine(localBoundingBox.x + 1, y, localBoundingBox.x + localBoundingBox.width - 2, y);
			}
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return BNAUtils.isWithin(t.getBoundingBox(), worldX, worldY);
	}

}
