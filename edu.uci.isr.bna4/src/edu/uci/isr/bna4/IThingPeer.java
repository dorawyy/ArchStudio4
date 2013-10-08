package edu.uci.isr.bna4;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public interface IThingPeer {

	/**
	 * Draws the peer's associated {@link IThing} on the given {@link Graphics},
	 * using the given {@link IBNAView} to map all world coordinates to local
	 * coordinates if necessary.
	 * 
	 * @param view
	 *            The {@link IBNAView} that sets the context for drawing the
	 *            {@link IThing}.
	 * @param g
	 *            The {@link Graphics} on which to draw the Thing.
	 * @param clip
	 *            The clipping rectangle for the current graphics, as obtained
	 *            form {@link Graphics#getClip(Rectangle)}.
	 * @param res
	 *            The {@link IResourceUtils} to use for creating resources.
	 */
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res);

	/**
	 * Determine if the given point (in {@link ICoordinateMapper world
	 * coordinates}) is "in" the peer's associated {@link IThing}. That is, if
	 * the user clicked the mouse on the given world-point, would they
	 * reasonably expect to have hit this {@link IThing}?
	 * 
	 * @param view
	 *            The {@link IBNAView} that sets the context for making the
	 *            determination.
	 * @param worldX
	 *            The X-coordinate of the world point to test.
	 * @param worldY
	 *            The Y-coordinate of the world point to test.
	 * @return <code>true</code> if the point is "in" the {@link IThing},
	 *         <code>false</code> otherwise.
	 */
	public boolean isInThing(IBNAView view, int worldX, int worldY);
}