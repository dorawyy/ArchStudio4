package edu.uci.isr.bna4.things.labels;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.widgets.swt.constants.Flow;
import edu.uci.isr.widgets.swt.constants.Orientation;

public class DirectionalLabelThingPeer extends AbstractThingPeer<DirectionalLabelThing> {

	public DirectionalLabelThingPeer(DirectionalLabelThing t) {
		super(t, DirectionalLabelThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		Flow f = t.getFlow();
		if (f.equals(Flow.NONE)) {
			return;
		}

		Orientation o = t.getOrientation();
		if (o.equals(Orientation.NONE)) {
			return;
		}

		g.setBackgroundColor(res.getColor(t.getColor(), SWT.COLOR_BLACK));

		if (f.equals(Flow.OUT) || f.equals(Flow.IN)) {
			//For "out" facing flows, the triangle points in the direction of the 
			//orientation.  For "in" facing flows, it points the opposite direction.
			if (f.equals(Flow.IN)) {
				o = o.opposite();
			}
			int[] trianglePoints = BNAUtils.createIsocolesTriangle(lbb, o);
			g.fillPolygon(trianglePoints);
		}
		else if (f.equals(Flow.INOUT)) {
			Rectangle sbb = lbb.getExpanded(1, 1);
			//We have to render two triangles.
			int[] trianglePoints = new int[6];
			switch (o) {
			case NORTH:
			case SOUTH:
				trianglePoints[0] = sbb.x;
				trianglePoints[1] = sbb.y + sbb.height / 2;

				trianglePoints[2] = sbb.x + sbb.width / 2;
				trianglePoints[3] = sbb.y;

				trianglePoints[4] = sbb.x + sbb.width;
				trianglePoints[5] = sbb.y + sbb.height / 2;

				g.fillPolygon(trianglePoints);

				trianglePoints[0] = sbb.x;
				trianglePoints[1] = sbb.y + sbb.height / 2 + 1;

				trianglePoints[2] = sbb.x + sbb.width / 2;
				trianglePoints[3] = sbb.y + sbb.height;

				trianglePoints[4] = sbb.x + sbb.width;
				trianglePoints[5] = sbb.y + sbb.height / 2 + 1;

				g.fillPolygon(trianglePoints);
				break;
			case EAST:
			case WEST:
				trianglePoints[0] = sbb.x + sbb.width / 2;
				trianglePoints[1] = sbb.y;

				trianglePoints[2] = sbb.x;
				trianglePoints[3] = sbb.y + sbb.height / 2;

				trianglePoints[4] = sbb.x + sbb.width / 2;
				trianglePoints[5] = sbb.y + sbb.height;

				g.fillPolygon(trianglePoints);

				trianglePoints[0] = sbb.x + sbb.width / 2 + 1;
				trianglePoints[1] = sbb.y;

				trianglePoints[2] = sbb.x + sbb.width;
				trianglePoints[3] = sbb.y + sbb.height / 2;

				trianglePoints[4] = sbb.x + sbb.width / 2;
				trianglePoints[5] = sbb.y + sbb.height;

				g.fillPolygon(trianglePoints);
				break;
			case NORTHEAST:
			case SOUTHWEST:
				trianglePoints[0] = sbb.x + 1 + 1;
				trianglePoints[1] = sbb.y + 1;

				trianglePoints[2] = sbb.x + sbb.width;
				trianglePoints[3] = sbb.y + 1;

				trianglePoints[4] = sbb.x + sbb.width;
				trianglePoints[5] = sbb.y + sbb.height - 1;

				g.fillPolygon(trianglePoints);

				trianglePoints[0] = sbb.x + 1;
				trianglePoints[1] = sbb.y + 1;

				trianglePoints[2] = sbb.x + 1;
				trianglePoints[3] = sbb.y + sbb.height;

				trianglePoints[4] = sbb.x + sbb.width;
				trianglePoints[5] = sbb.y + sbb.height;

				g.fillPolygon(trianglePoints);
				break;
			case NORTHWEST:
			case SOUTHEAST:
				trianglePoints[0] = sbb.x + 1;
				trianglePoints[1] = sbb.y + 1;

				trianglePoints[2] = sbb.x + sbb.width - 1;
				trianglePoints[3] = sbb.y + 1;

				trianglePoints[4] = sbb.x + 1;
				trianglePoints[5] = sbb.y + sbb.height - 1;

				g.fillPolygon(trianglePoints);

				trianglePoints[0] = sbb.x + sbb.width;
				trianglePoints[1] = sbb.y + sbb.height;

				trianglePoints[2] = sbb.x + sbb.width;
				trianglePoints[3] = sbb.y + 1;

				trianglePoints[4] = sbb.x + 1;
				trianglePoints[5] = sbb.y + sbb.height;

				g.fillPolygon(trianglePoints);
				break;
			}

		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return BNAUtils.isWithin(t.getBoundingBox(), worldX, worldY);
	}
}
