package xyz.cafeconleche.web.chica.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import xyz.cafeconleche.web.chica.base.view.AbstractITextPdfView;
import xyz.cafeconleche.web.chica.dto.Face;

public class PdfView extends AbstractITextPdfView {

	@Override
	public void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("model: " + model);
		
		Face face = (Face) model.get("face");
		System.out.println("face: " + face);
		
		Rectangle pageSize = document.getPageSize();
		System.out.println("pageSize: " + pageSize);
		System.out.println("w: " + pageSize.getWidth() + ", h: " + pageSize.getHeight());
		
		Image leftEye = getImage("eyes/" + face.getEyes());
		float leftAbsY = (pageSize.getHeight() - leftEye.getHeight());
		leftEye.setAbsolutePosition(0, leftAbsY);
		
		System.out.println("absoulte looking Y: " + leftAbsY);
		
		Image rightEye = getImage("eyes/" + face.getEyes());
		float rightAbsX = leftEye.getWidth()/2;
		System.out.println("looking for: " + rightAbsX);
		rightEye.setAbsolutePosition(rightAbsX, leftAbsY);
		
		Image nose = getImage("noses/" + face.getNose());
		float noseAbsX = leftEye.getWidth()/4;
		float noseAbsY = leftAbsY-(nose.getHeight()/4);
		nose.setAbsolutePosition(noseAbsX, noseAbsY);
		
		Image mouth = getImage("mouths/" + face.getMouth());
		float mouthAbsX = leftEye.getWidth()/4;
		float mouthAbsY = leftAbsY-(mouth.getHeight()/1.7f);
		mouth.setAbsolutePosition(mouthAbsX, mouthAbsY);
		
		System.out.println("w: " + leftEye.getWidth());
		System.out.println("plain w: " + leftEye.getPlainWidth());
		System.out.println("absolute: " + leftEye.getAbsoluteX() + "," + leftEye.getAbsoluteY());
		
		System.out.println("leftEye: " + leftEye + " - " + System.identityHashCode(leftEye));
		System.out.println("rightEye: " + rightEye + " - " + System.identityHashCode(rightEye));
		System.out.println("mouth: " + mouth);
		
		document.add(leftEye);
		document.add(rightEye);
		document.add(nose);
		document.add(mouth);
		
	}
	
	private Image getImage(String imgSrc) throws BadElementException, MalformedURLException, IOException {
		String imgBaseDir = "http://localhost:8080/chica/resources/core/images/face/";
		Image image = Image.getInstance(imgBaseDir + imgSrc + ".png");
		return image;
	}

}
