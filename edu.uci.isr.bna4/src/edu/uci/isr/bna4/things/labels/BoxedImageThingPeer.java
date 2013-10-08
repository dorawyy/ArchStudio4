package edu.uci.isr.bna4.things.labels;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.widgets.swt.constants.HorizontalAlignment;
import edu.uci.isr.widgets.swt.constants.VerticalAlignment;

public class BoxedImageThingPeer extends AbstractThingPeer<BoxedImageThing> {

	public BoxedImageThingPeer(BoxedImageThing thing) {
		super(thing, BoxedImageThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb) || lbb.width <= 1 || lbb.height <= 1) {
			return;
		}

		ImageData imageData = t.getImageData();
		if (imageData == null) {
			return;
		}

		Image image = res.getImage(imageData);
		if (image == null) {
			return;
		}

		org.eclipse.swt.graphics.Rectangle ib = image.getBounds();
		if (t.isScaled()) {
			g.drawImage(image, ib.x, ib.y, ib.width, ib.height, lbb.x, lbb.y, lbb.width, lbb.height);
		}
		else {
			VerticalAlignment verticalAlignment = t.getVerticalAlignment();
			HorizontalAlignment horizontalAlignment = t.getHorizontalAlignment();
			int x = lbb.x, y = lbb.y;
			switch (horizontalAlignment) {
			case CENTER:
				x = x + lbb.width / 2 - ib.width / 2;
				break;
			case LEFT:
				// do nothing
				break;
			case RIGHT:
				x = x + lbb.width - ib.width;
				break;
			}

			switch (verticalAlignment) {
			case MIDDLE:
				y = y + lbb.height / 2 - ib.height / 2;
				break;
			case TOP:
				// do nothing
				break;
			case BOTTOM:
				y = y + lbb.height - ib.height;
				break;
			}
			g.drawImage(image, ib.x, ib.y, ib.width, ib.height, lbb.x, lbb.y, ib.width, ib.height);
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		Rectangle bb = t.getBoundingBox();
		return bb.contains(worldX, worldY);
	}
}
