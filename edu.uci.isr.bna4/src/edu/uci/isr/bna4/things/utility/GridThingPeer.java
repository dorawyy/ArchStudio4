package edu.uci.isr.bna4.things.utility;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ICoordinateMapper;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.constants.GridDisplayType;

public class GridThingPeer extends AbstractThingPeer<GridThing> {

	public GridThingPeer(GridThing t) {
		super(t, GridThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		int gridSpacing = t.getGridSpacing();
		if (gridSpacing == 0) {
			return;
		}

		GridDisplayType gdt = t.getGridDisplayType();
		if (gdt == null || gdt == GridDisplayType.NONE) {
			return;
		}

		g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_WIDGET_LIGHT_SHADOW));

		ICoordinateMapper cm = view.getCoordinateMapper();
		int wx = cm.localXtoWorldX(clip.x);
		int wy = cm.localYtoWorldY(clip.y);
		int wx2 = cm.localXtoWorldX(clip.x + clip.width);
		int wy2 = cm.localYtoWorldY(clip.y + clip.height);

		int jump = 1;
		int localGridSpacing = BNAUtils.round(gridSpacing * cm.getScale());
		if (localGridSpacing < 1) {
			localGridSpacing = 1;
		}
		while (localGridSpacing * jump < 10) {
			jump++;
		}

		int dx = wx % (gridSpacing * jump);
		int dy = wy % (gridSpacing * jump);

		if (gdt == GridDisplayType.SOLID_LINES || gdt == GridDisplayType.DOTTED_LINES) {
			if (gdt == GridDisplayType.DOTTED_LINES) {
				g.setLineWidth(1);
				g.setLineStyle(SWT.LINE_DOT);
			}
			else {
				g.setLineWidth(1);
				g.setLineStyle(SWT.LINE_SOLID);
			}
			for (int i = wx - dx; i <= wx2; i += gridSpacing * jump) {
				int gx = cm.worldXtoLocalX(i);
				g.drawLine(gx, clip.y, gx, clip.y + clip.height);
			}
			for (int i = wy - dy; i <= wy2; i += gridSpacing * jump) {
				int gy = cm.worldYtoLocalY(i);
				g.drawLine(clip.x, gy, clip.x + clip.width, gy);
			}
		}
		else if (gdt == GridDisplayType.DOTS_AT_CORNERS) {
			g.setLineWidth(1);
			for (int i = wx - dx; i <= wx2; i += gridSpacing * jump) {
				int gx = cm.worldXtoLocalX(i);
				for (int j = wy - dy; j <= wy2; j += gridSpacing * jump) {
					int gy = cm.worldYtoLocalY(j);
					g.drawPoint(gx, gy);
				}
			}
		}
		else if (gdt == GridDisplayType.CROSSES_AT_CORNERS) {
			g.setLineWidth(1);
			for (int i = wx - dx; i <= wx2; i += gridSpacing * jump) {
				int gx = cm.worldXtoLocalX(i);
				for (int j = wy - dy; j <= wy2; j += gridSpacing * jump) {
					int gy = cm.worldYtoLocalY(j);
					g.drawLine(gx - 3, gy, gx + 3, gy);
					g.drawLine(gx, gy - 3, gx, gy + 3);
				}
			}
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
