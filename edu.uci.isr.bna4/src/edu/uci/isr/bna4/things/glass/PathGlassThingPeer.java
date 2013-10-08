package edu.uci.isr.bna4.things.glass;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.PathData;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.PathDataUtils;
import edu.uci.isr.bna4.ResourceUtils;

public class PathGlassThingPeer extends AbstractThingPeer<PathGlassThing> {

	public PathGlassThingPeer(PathGlassThing t) {
		super(t, PathGlassThing.class);
	}

	@Override
	public void draw(IBNAView view, final Graphics g, Rectangle clip, ResourceUtils res) {
		if (!t.isSelected()) {
			return;
		}

		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		Path path = null;
		try {
			path = new Path(res.getDevice());
			PathData pathData = t.getPathData();
			Point o = t.getAnchorPoint();
			PathDataUtils.addPathDataToPath(path, pathData, o, view.getCoordinateMapper());
			int offset = t.getRotatingOffset();

			final Path fPath = path;
			BNAUtils.drawMarquee(g, res, offset, true, new Runnable() {

				public void run() {
					g.drawPath(fPath);
				};
			});
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
		Rectangle bb = t.getBoundingBox();
		if (!bb.contains(worldX, worldY)) {
			return false;
		}

		GC g = null;
		Path path = null;
		try {
			g = new GC(getDisplay());
			path = new Path(g.getDevice());
			PathData pathData = t.getPathData();
			Point o = t.getAnchorPoint();
			PathDataUtils.addPathDataToPath(path, pathData, o, view.getCoordinateMapper());
			return path.contains(worldX, worldY, g, true);
		}
		finally {
			if (path != null) {
				path.dispose();
				path = null;
			}
			if (g != null) {
				g.dispose();
				g = null;
			}
		}
	}
}
