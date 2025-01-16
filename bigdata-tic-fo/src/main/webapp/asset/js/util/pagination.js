/**
 * 2019.11.28
 * KMCHO
 * CorePagination UI 페이징 처리
 */
corePaginationInfo = {

	/**
	 * Required Fields
	 * - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 *
	 * currentPageNo : 현재 페이지 번호
	 * recordCountPerPage : 한 페이지당 게시되는 게시물 건 수
	 * pageSize : 페이지 리스트에 게시되는 페이지 건수,
	 * totalRecordCount : 전체 게시물 건 수.
	 */
	currentPageNo : 1,
	recordCountPerPage : 10,
	pageSize : 10,
	totalRecordCount : 0,

	getRecordCountPerPage : function() {
		return this.recordCountPerPage;
	},

	setRecordCountPerPage : function(recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	},

	getPageSize : function() {
		return this.pageSize;
	},

	setPageSize : function(pageSize) {
		this.pageSize = pageSize;
	},

	getCurrentPageNo : function() {
		return this.currentPageNo;
	},

	setCurrentPageNo : function(currentPageNo) {
		this.currentPageNo = currentPageNo;
	},

	setTotalRecordCount : function(totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	},

	getTotalRecordCount : function() {
		return this.totalRecordCount;
	},

	/**
	 * Not Required Fields
	 * - 이 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는 필드 값이다.
	 *
	 * totalPageCount: 페이지 개수
	 * firstPageNoOnPageList : 페이지 리스트의 첫 페이지 번호
	 * lastPageNoOnPageList : 페이지 리스트의 마지막 페이지 번호
	 * firstRecordIndex : 페이징 SQL의 조건절에 사용되는 시작 rownum.
	 * lastRecordIndex : 페이징 SQL의 조건절에 사용되는 마지막 rownum.
	 */

	totalPageCount : 0,
	firstPageNoOnPageList : 0,
	lastPageNoOnPageList : 0,
	firstRecordIndex : 0,
	lastRecordIndex : 0,

	getTotalPageCount : function() {
		this.totalPageCount = parseInt((this.getTotalRecordCount() - 1) / this.getRecordCountPerPage()) + 1;
		return this.totalPageCount;
	},

	getFirstPageNo : function(){
		return 1;
	},

	getLastPageNo : function(){
		return this.getTotalPageCount();
	},

	getFirstPageNoOnPageList : function() {
		this.firstPageNoOnPageList = parseInt((this.getCurrentPageNo() - 1) / this.getPageSize()) * this.getPageSize() + 1;
		return this.firstPageNoOnPageList;
	},

	getLastPageNoOnPageList : function() {
		this.lastPageNoOnPageList = 0;
		if(this.getTotalPageCount() > 0){
			if (this.getTotalPageCount() < this.getPageSize()){
				this.lastPageNoOnPageList = this.getTotalPageCount();
			} else {
				if(this.firstPageNoOnPageList + this.getPageSize() - 1 > this.getTotalPageCount()) {
					this.lastPageNoOnPageList = this.getTotalPageCount();
				} else {
					this.lastPageNoOnPageList = this.firstPageNoOnPageList + this.getPageSize() - 1;
				}
			}
		}
		return this.lastPageNoOnPageList;
	},

	getFirstRecordIndex : function() {
		this.firstRecordIndex = (this.getCurrentPageNo() - 1) * this.getRecordCountPerPage() + 1;
		return this.firstRecordIndex;
	},

	getLastRecordIndex : function() {
		this.lastRecordIndex = this.getCurrentPageNo() * this.getRecordCountPerPage();
		return this.lastRecordIndex;
	},

	/**
	 * Not Required Fields
	 * - 이 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는 필드 값이다.
	 *
	 * 2018.09.10 추가
	 * pageStartRow : 페이징 화면 목록에서 사용되는 시작 rownum.
	 * pageEndRow: 페이징 화면 목록에서 사용되는 마지막 rownum.
	 */

	pageStartRow : 0,
	pageEndRow: 0,

	getPageEndRow : function() {
		this.pageEndRow = this.getTotalRecordCount() - ((this.getCurrentPageNo() - 1) * this.getRecordCountPerPage());
		return this.pageEndRow;
	},

	getPageStartRow : function() {
		if(this.getTotalRecordCount() == 0) {
			this.pageStartRow = 0;
		} else if(this.getCurrentPageNo() == this.getLastPageNoOnPageList()) {
			this.pageStartRow = 1;
		} else {
			this.pageStartRow = this.getPageEndRow() - this.getRecordCountPerPage() + 1;
		}
		return this.pageStartRow;
	}
}

corePaginationRenderer = {

	firstPageLabel : "<a href=\"#\" class=\"direction first\" onclick=\"{0}({1}); return false; \">이전</a>&#160;",
	previousPageLabel : "<a href=\"#\" class=\"direction prev\" onclick=\"{0}({1}); return false;\">이전</a>&#160;",
	currentPageLabel : "<a href=\"javascript:\" class=\"on\">{0}</a>&#160;",
	otherPageLabel : "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>&#160;",
	nextPageLabel : "<a href=\"#\" class=\"direction next\" onclick=\"{0}({1}); return false;\">다음</a>&#160;",
	lastPageLabel : "<a href=\"#\" class=\"direction last\" onclick=\"{0}({1}); return false;\">다음</a>&#160;",

	/**
	 * 이전 다음 호출시 사용하는 빈 함수
	 * @param currentPageNo
	 * @returns
	 */
	emtpyFunction : function(currentPageNo) {
		console.log(currentPageNo);
	},

	renderPagination : function(paginationInfo, jsFunction) {
		var strBuff = "";

		//console.log('renderPagination', paginationInfo);

		var firstPageNo = paginationInfo.getFirstPageNo();
		var firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
		var totalPageCount = paginationInfo.getTotalPageCount();
		var pageSize = paginationInfo.getPageSize();
		var lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		var currentPageNo = paginationInfo.getCurrentPageNo();
		var lastPageNo = paginationInfo.getLastPageNo();
		/*
		console.log('firstPageNo', firstPageNo);
		console.log('firstPageNoOnPageList', firstPageNoOnPageList);
		console.log('totalPageCount', totalPageCount);
		console.log('pageSize', pageSize);
		console.log('lastPageNoOnPageList', lastPageNoOnPageList);
		console.log('currentPageNo', currentPageNo);
		console.log('lastPageNo', lastPageNo);
		*/
		if (totalPageCount > pageSize) {
			var callFunction = (currentPageNo == firstPageNo ? 'corePaginationRenderer.emtpyFunction' : jsFunction);

			if (firstPageNoOnPageList > pageSize) {
				strBuff += this.firstPageLabel.replace("{0}", callFunction).replace("{1}", firstPageNo);
				strBuff += this.previousPageLabel.replace("{0}", (currentPageNo == (firstPageNoOnPageList - 1) ? 'corePaginationRenderer.emtpyFunction' : jsFunction)).replace("{1}", (firstPageNoOnPageList - 1));
			} else {
				strBuff += this.firstPageLabel.replace("{0}", callFunction).replace("{1}", firstPageNo);
				strBuff += this.previousPageLabel.replace("{0}", callFunction).replace("{1}", firstPageNo);
			}
		}

		for (var i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
			if (i == currentPageNo) {
				strBuff += this.currentPageLabel.replace("{0}", i);
			} else {
				strBuff += this.otherPageLabel.replace("{0}", jsFunction).replace("{1}", i).replace("{2}", i);
			}
		}

		if (totalPageCount > pageSize) {
			var callFunction = (currentPageNo == lastPageNo ? 'corePaginationRenderer.emtpyFunction' : jsFunction);

			if (lastPageNoOnPageList < totalPageCount) {
				strBuff += this.nextPageLabel.replace("{0}", (currentPageNo == (firstPageNoOnPageList + pageSize) ? 'corePaginationRenderer.emtpyFunction' : jsFunction)).replace("{1}", (firstPageNoOnPageList + pageSize));
				strBuff += this.lastPageLabel.replace("{0}", callFunction).replace("{1}", lastPageNo);
			} else {
				strBuff += this.nextPageLabel.replace("{0}", callFunction).replace("{1}", lastPageNo);
				strBuff += this.lastPageLabel.replace("{0}", callFunction).replace("{1}", lastPageNo);
			}
		}
		return strBuff;
	}
}
/**
 * 함수명 : $.fn.PaginationRender
 * 함수설명 : JSP 페이징을 동적으로 설정 ex) <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fnLinkPage" />
 * 파라미터	: options
 * {
 * 		currentPageNo : 현재 페이지 번호
 * 		recordCountPerPage : 한 페이지당 게시되는 게시물 건 수
 * 		pageSize : 페이지 리스트에 게시되는 페이지 건수,
 * 		totalRecordCount : 전체 게시물 건 수.
 *  	jsFunction : 페이지 클릭 호출 함수명.
 * }
 * callBackFunction : html 결과 리턴 함수
 */
$.fn.PaginationRender = function(options, callBackFunction) {
	if(options != undefined) {

		var paginationInfo = corePaginationInfo;
		// 현재 페이지 번호
		if(options.currentPageNo != undefined && options.currentPageNo != '') {
			paginationInfo.setCurrentPageNo(options.currentPageNo);
		}
		// 한 페이지당 게시되는 게시물 건 수
		if(options.recordCountPerPage != undefined && options.recordCountPerPage != '') {
			paginationInfo.setRecordCountPerPage(options.recordCountPerPage);
		}
		// 페이지 리스트에 게시되는 페이지 건수
		if(options.pageSize != undefined && options.pageSize != '') {
			paginationInfo.setPageSize(options.pageSize);
		}
		// 전체 게시물 건 수
		if(options.totalRecordCount != undefined && options.totalRecordCount != '') {
			paginationInfo.setTotalRecordCount(options.totalRecordCount);
		}
		// 페이지 클릭 호출 함수명
		var clickedFunction = '';
		if(options.jsFunction != undefined && options.jsFunction != '') {
			clickedFunction = options.jsFunction;
		}

		var pageHtml = corePaginationRenderer.renderPagination(paginationInfo, clickedFunction);

		//console.log('PaginationRender', pageHtml);

		callBackFunction( pageHtml );
	}
}
