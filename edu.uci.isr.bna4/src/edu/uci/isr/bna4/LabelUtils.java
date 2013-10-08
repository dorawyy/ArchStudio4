package edu.uci.isr.bna4;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;

import edu.uci.isr.widgets.swt.constants.FontStyle;

public class LabelUtils {

	public static Rectangle setFontToRender(Display d, TextLayout tl, int maxHeight, String name, int size, FontStyle style) {
		ResourceUtils res = ResourceUtils.resourceUtilsFor(d);

		Font initialFont = res.getFont(name, size, style.toSWT());
		tl.setFont(initialFont);
		Rectangle bounds = BNAUtils.toRectangle(tl.getBounds());
		if (bounds.height <= maxHeight) {
			return bounds;
		}
		//It doesn't fit, heightwise, when we lay out the text.
		//We need to find the largest font size less than the given
		//size that will fit when the text is laid out.
		int max = size;
		int min = 1;
		int maxThatFits = 0;
		int fontSizeSet = size;
		int comps = 0;
		while (true) {
			if (max <= min || max - 1 == min) {
				if (maxThatFits == 0) {
					return null;
				}
				if (fontSizeSet != maxThatFits) {
					tl.setFont(res.getFont(name, maxThatFits, style.toSWT()));
				}
				return BNAUtils.toRectangle(tl.getBounds());
			}
			int s = (max - min) / 2 + min;

			Font f = res.getFont(name, s, style.toSWT());
			fontSizeSet = s;
			tl.setFont(f);
			comps++;

			bounds = BNAUtils.toRectangle(tl.getBounds());
			if (bounds.height > maxHeight) {
				max = s;
			}
			else {
				min = s;
				maxThatFits = min;
			}
		}
	}

}
