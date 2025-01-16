// initialize jqxGrid
$(function() {
	// 설명 : 기본 그리드 제공 함수
	$.fn.commGrid = function(source, colummn, editMode) {
		var editable = false;
		if(typeof editMode != 'undefined' && editMode != ''){
			editable = editMode;
		}

		var dataAdapter = new $.jqx.dataAdapter(source);

		var commGrid = this.selector;
		//디폴터
		$(commGrid).jqxGrid({
			width: '100%',
			height: 570,
			source: dataAdapter, 					// 출력할 데이터 소스 정의
			pageable: false, 						// 페이징
			columnsresize: true, 					// 컬럼 가로 길이 조절 가능 여부
			columnsreorder: true,					// 컬럼 정렬
			sortable: true, 						// 정렬 기능 여부
			enablemousewheel: true,					// 가로 스크롤 마우스 휠 사용여부
			enablebrowserselection: true,
			showsortmenuitems: true,
			editable: editable,
			enabletooltips: true,
			autorowheight: false, 					// 셀 길이보다 데이터 길이가 길 경우 "..."으로 표시 없이 개행되게끔
			autoheight: false, 						// autorowheight를 true로 할 경우 autoheight나 pageable값을 필수로 true해야 함
			selectionmode: 'multiplerowsextended',
			ready: function () {},
			columns: colummn
		});
	}
	// 설명 : 상세 그리드 제공 함수
	$.fn.commGridDetail = function(source, colummn, rowdetailstemplate, initrowdetails) {
		var dataAdapter = new $.jqx.dataAdapter(source);

		var commGrid = this.selector;
		//디폴터
		$(commGrid).jqxGrid({
			width: '100%',
			height: 570,
			source: dataAdapter, 					// 출력할 데이터 소스 정의
			pageable: false, 						// 페이징
			columnsresize: true, 					// 컬럼 가로 길이 조절 가능 여부
			columnsreorder: true,					// 컬럼 정렬
			sortable: true, 						// 정렬 기능 여부
			enablemousewheel: true,					// 가로 스크롤 마우스 휠 사용여부
			showsortmenuitems: true,
			editable: false,
			enabletooltips: true,
			autorowheight: false, 					// 셀 길이보다 데이터 길이가 길 경우 "..."으로 표시 없이 개행되게끔
			autoheight: false, 						// autorowheight를 true로 할 경우 autoheight나 pageable값을 필수로 true해야 함
			selectionmode: 'multiplerowsextended',
			ready: function () {},
			columns: colummn,
			rowdetails: true,
			rowdetailstemplate: rowdetailstemplate,
			initrowdetails: initrowdetails
		});
	}
});


/**
 * 체크박스 전체 체크
 * @param obj 체크박스 object
 * @param event 체크박스 이벤트
 * @param gridId Grid ID
 * @param chkId 체크박스 ID
 */
function fn_gridAllChk(obj, event, gridId, chkId) {
	event.stopPropagation();
	var checked = $(obj).is(":checked");
	var gridObj = $("#"+gridId);
	gridObj.jqxGrid("beginupdate");
	var rows = gridObj.jqxGrid("getboundrows");
	for(var i=0; i<rows.length; i++){
		gridObj.jqxGrid("setcellvalue", i, chkId, checked);
	}
	gridObj.jqxGrid("endupdate");
}