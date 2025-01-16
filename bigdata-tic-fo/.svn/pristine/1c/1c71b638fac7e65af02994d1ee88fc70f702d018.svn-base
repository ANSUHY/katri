package com.katri.web.search.model;

import java.util.List;

import com.katri.web.ctnt.archive.model.ArchiveSelectRes;
import com.katri.web.ctnt.faq.model.FaqSelectRes;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.dataUsageGuide.model.CertDataSelectRes;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Search Response")
public class SearchSelectRes {

	/** 공지사항 목록 */
	private List<NoticeSelectRes> noticeList;

	/** FAQ 목록 */
	private List<FaqSelectRes> faqList;

	/** 자료실 목록 */
	private List<ArchiveSelectRes> archiveList;

	/** 인증데이터 목록 */
	private List<CertDataSelectRes> certDataList;

}
