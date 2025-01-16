package com.katri.common;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder // Map decorators to path patterns.
		// layout 추가
		.addDecoratorPath("/html/*"				, "/WEB-INF/views/decorator/emptyLayout.jsp")
		.addDecoratorPath("/sample/*"			, "/WEB-INF/views/decorator/emptyLayout.jsp")
		.addDecoratorPath("/*"					, "/WEB-INF/views/decorator/defaultLayout.jsp")

		// layout 제거
		.addExcludedPath("/smarteditor/*")
		.addExcludedPath(".json")
		.addExcludedPath("/svc/certInfo/*")
		.addExcludedPath("/svc/certInfoDetail/*")
		.addExcludedPath("/error")
		.addExcludedPath("/comm/niceSuccess/*")   // 수정(2022-12-28)
		.addExcludedPath("/comm/niceSuccess")     // 수정(2022-12-28)
		.setMimeTypes("text/html");
	}


}
