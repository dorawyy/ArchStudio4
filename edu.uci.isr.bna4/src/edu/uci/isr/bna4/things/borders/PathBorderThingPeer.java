package edu.uci.isr.bna4.things.borders;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.PathData;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.PathDataUtils;
import edu.uci.isr.bna4.ResourceUtils;

public class PathBorderThingPeer extends AbstractThingPeer<PathBorderThing> {

	public PathBorderThingPeer(PathBorderThing t) {
		super(t, PathBorderThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_BLACK));
		g.setLineStyle(t.getLineStyle());
		g.setLineWidth(t.getLineWidth());

		Path path = null;
		try {
			path = new Path(res.getDevice());
			PathData pathData = t.getPathData();
			Point o = t.getAnchorPoint();
			PathDataUtils.addPathDataToPath(path, pathData, o, view.getCoordinateMapper());
			g.drawPath(path);
		}
		finally {
			if (path != null) {
				path.dispose();
				path = null;
			}
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
