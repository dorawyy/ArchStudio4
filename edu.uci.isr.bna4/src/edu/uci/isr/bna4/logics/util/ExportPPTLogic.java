package edu.uci.isr.bna4.logics.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchActionConstants;

import edu.uci.isr.bna4.AbstractThingLogic;
import edu.uci.isr.bna4.BNAComposite;
import edu.uci.isr.bna4.BNARenderingSettings;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.DefaultBNAView;
import edu.uci.isr.bna4.DefaultCoordinateMapper;
import edu.uci.isr.bna4.IBNAMenuListener;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.IThing;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.logics.tracking.ModelBoundsTrackingLogic;

public class ExportPPTLogic extends AbstractThingLogic implements IBNAMenuListener {

	protected static final int IMAGE_PADDING = 5;

	protected ModelBoundsTrackingLogic mbtl = null;

	public ExportPPTLogic(ModelBoundsTrackingLogic mbtl) {
		this.mbtl = mbtl;
	}

	public void fillMenu(IBNAView view, IMenuManager m, int localX, int localY, IThing t, int worldX, int worldY) {
		if (t == null) {
			final IBNAView fview = view;
			IAction saveAsPTTAction = new Action("Save as PPT...") {

				@Override
				public void run() {
					saveAsPPT(fview, "ppt", "Microsoft PowerPoint 97-2003 Format (*.ppt)", "Title", SWT.IMAGE_JPEG);
				}
			};
			m.add(saveAsPTTAction);

			m.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}
	}

	protected void saveAsPPT(IBNAView view, String extension, String bitmapName, String slideTitle, int swtImageType) {
		Shell s = BNAUtils.getParentComposite(view).getShell();
		FileDialog fd = new FileDialog(s, SWT.SAVE);
		fd.setText("Save");
		String[] filterExt = { "*." + extension };
		String[] filterNames = { bitmapName };
		fd.setFilterExtensions(filterExt);
		fd.setFilterNames(filterNames);
		String selected = fd.open();

		if (selected == null) {
			return;
		}
		if (!selected.toLowerCase().trim().endsWith("." + extension)) {
			selected += "." + extension;
		}

		File f = new File(selected);
		if (f.exists()) {
			boolean confirm = MessageDialog.openConfirm(s, "Confirm Overwrite", "Overwrite existing file?");
			if (!confirm) {
				saveAsPPT(view, extension, bitmapName, slideTitle, swtImageType);
				return;
			}
		}

		FileOutputStream pptOut = null;
		try {
			// store jpeg of the architecture for the PPT thumbnail
			File fjpeg = File.createTempFile("ppt-export", "jpeg");
			ImageData imageData = createImage(view);
			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[] { imageData };
			loader.save(fjpeg.getAbsolutePath(), swtImageType);

			SlideShow ppt = new SlideShow();
			Slide slide = ppt.createSlide();
			TextBox title = slide.addTitle();
			title.setText(slideTitle);

			int idx = ppt.addPicture(fjpeg, Picture.JPEG);
			Picture pict = new Picture(idx);
			slide.addShape(pict);
			pict.setAnchor(new java.awt.Rectangle(100, 100, 600, 400));

			pptOut = new java.io.FileOutputStream(selected);
			ppt.write(pptOut);
			pptOut.close();

		}
		catch (FileNotFoundException efnf) {
			efnf.printStackTrace();
		}
		catch (IOException eio) {
			eio.printStackTrace();
		}
		finally {
			if (pptOut != null) {
				try {
					pptOut.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				pptOut = null;
			}
		}
	}

	protected ImageData createImage(IBNAView view) {
		ImageData imageData = null;
		Rectangle bounds = mbtl.getModelBounds();
		BNAComposite bnaComposite = (BNAComposite) BNAUtils.getParentComposite(view);
		Display d = bnaComposite.getDisplay();
		ResourceUtils res = ResourceUtils.resourceUtilsFor(d);

		DefaultCoordinateMapper cm = new DefaultCoordinateMapper();
		cm.repositionAbsolute(bounds.x - IMAGE_PADDING, bounds.y - IMAGE_PADDING);
		cm.rescaleAbsolute(1.0);

		DefaultBNAView newView = new DefaultBNAView(view, view.getWorld(), cm);

		Image image = null;
		GC gc = null;
		Graphics g = null;

		try {
			image = new Image(d, bounds.width + 2 * IMAGE_PADDING, bounds.height + 2 * IMAGE_PADDING);
			gc = new GC(image);
			g = new SWTGraphics(gc);
			Rectangle clip = BNAUtils.toRectangle(image.getBounds());

			gc.setAntialias(BNARenderingSettings.getAntialiasGraphics(bnaComposite) ? SWT.ON : SWT.OFF);
			gc.setTextAntialias(BNARenderingSettings.getAntialiasText(bnaComposite) ? SWT.ON : SWT.OFF);

			BNAUtils.renderWorld(newView, g, clip, res);

			imageData = image.getImageData();
		}
		finally {
			if (g != null) {
				g.dispose();
				g = null;
			}
			if (gc != null) {
				gc.dispose();
				gc = null;
			}
			if (image != null) {
				image.dispose();
				image = null;
			}
		}
		return imageData;
	}
}
