package com.katri.common;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder // Map decorators to path patterns.
		// layout 추가
		.addDecoratorPath("/"				, "/WEB-INF/views/decorator/emptyLayout.jsp")
		.addDecoratorPath("/login/*"		, "/WEB-INF/views/decorator/emptyLayout.jsp")
		.addDecoratorPath("/logout"			, "/WEB-INF/views/decorator/emptyLayout.jsp")
		.addDecoratorPath("*/*PopUp"		, "/WEB-INF/views/decorator/popUpLayout.jsp")
		.addDecoratorPath("/*"				, "/WEB-INF/views/decorator/defaultLayout.jsp")

		// layout 제거
		.addExcludedPath("/html/*")
		.addExcludedPath("/smarteditor/*")
		.addExcludedPath(".json")
		.addExcludedPath("/error")
		.setMimeTypes("text/html");
	}


}
