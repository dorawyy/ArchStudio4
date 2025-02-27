package edu.uci.isr.bna4.assemblies;

import edu.uci.isr.bna4.IBNAModel;
import edu.uci.isr.bna4.IThing;
import edu.uci.isr.bna4.facets.IHasEndpoints;
import edu.uci.isr.bna4.facets.IHasMidpoints;
import edu.uci.isr.bna4.logics.coordinating.MirrorEndpointLogic;
import edu.uci.isr.bna4.logics.coordinating.MirrorValueLogic;
import edu.uci.isr.bna4.things.glass.SplineGlassThing;
import edu.uci.isr.bna4.things.labels.ArrowheadThing;
import edu.uci.isr.bna4.things.shapes.SplineThing;

public class SplineAssembly extends AbstractAssembly {

	public static final String SPLINE = "spline";
	public static final String ARROWHEAD1 = "arrowhead1";
	public static final String ARROWHEAD2 = "arrowhead2";
	public static final String GLASS = "glass";

	public SplineAssembly(IBNAModel model, IThing parentThing, Object assemblyKind) {
		super(model, parentThing, assemblyKind);

		// Create sub-things
		SplineThing splineThing = new SplineThing();
		model.addThing(splineThing, rootThing);

		ArrowheadThing arrowheadThing1 = new ArrowheadThing();
		model.addThing(arrowheadThing1, splineThing);

		ArrowheadThing arrowheadThing2 = new ArrowheadThing();
		model.addThing(arrowheadThing2, splineThing);

		SplineGlassThing splineGlassThing = new SplineGlassThing();
		model.addThing(splineGlassThing, rootThing);

		// Set up connections
		MirrorValueLogic.mirrorValue(splineGlassThing, IHasEndpoints.ENDPOINT_1_PROPERTY_NAME, splineThing);
		MirrorValueLogic.mirrorValue(splineGlassThing, IHasEndpoints.ENDPOINT_2_PROPERTY_NAME, splineThing);
		MirrorValueLogic.mirrorValue(splineGlassThing, IHasMidpoints.MIDPOINTS_PROPERTY_NAME, splineThing);
		MirrorEndpointLogic.mirrorEndpoint(splineThing, 1, arrowheadThing1);
		MirrorEndpointLogic.mirrorEndpoint(splineThing, 2, arrowheadThing2);

		markPart(SPLINE, splineThing);
		markPart(ARROWHEAD1, arrowheadThing1);
		markPart(ARROWHEAD2, arrowheadThing2);
		markPart(GLASS, splineGlassThing);
	}

	public SplineThing getSplineThing() {
		return getPart(SPLINE);
	}

	public ArrowheadThing getEndpoint1ArrowheadThing() {
		return getPart(ARROWHEAD1);
	}

	public ArrowheadThing getEndpoint2ArrowheadThing() {
		return getPart(ARROWHEAD2);
	}

	public SplineGlassThing getSplineGlassThing() {
		return getPart(GLASS);
	}
}
