package xyz.cafeconleche.web.chica.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class PdfViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		PdfView pdfView = new PdfView();
		return pdfView;
	}

}
