<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>

<%@ page import="com.katri.common.Const" %>
<c:set var="SRWRD_TY_CD_SEARCH_KEYWORD" value="<%=Const.Code.SrwrdTyCd.SEARCH_KEYWORD%>" />

<script type="text/javascript">

$(document).ready(function() {

	/* 목록 조회 */
	fn_getKeywordList();

	/* 사용중인 추천 검색어 개수 */
	fn_countUseYn();

	/* 사용중인 검색어만 보기 */
	$("#checkY").change(function(){
		if($("#checkY").is(":checked")){
			fn_searchUseY();
		} else {
			fn_getKeywordList();
		}
	})

	/* flag 값 셋팅 */
	$("#flag").val("N");

	fn_keywordInit();
});


/*
 * 함수명	: fn_keywordInit
 * 설 명 : 엔터키 처리
 */
function fn_keywordInit() {
	$(document).on("keydown", function(key){
		if(key.keyCode == 13) {
			fn_isReg();
		}
	});
}


/*
 * 함수명	: fn_getKeywordList
 * 설 명	: 추천 검색어 목록 조회
 */
function fn_getKeywordList() {

	 $.ajax({
		 url		: '/search/keyword/getKeywordList'
	   , type		: 'GET'
	   , data		: { "srwrdTyCd" : $("#srwrdTyCd").val() }
	   , dataType	: 'json'
	   , success	: function(jsonData, textStatus, jqXHR) {

		   let listHtml = '';

		   		if(jsonData.data != null && jsonData.data.length > 0) {
					$.each(jsonData.data, function(index,data) {
						listHtml += fn_makeKeywordList(data);
					});
				} else {
					listHtml += '<tr><td class="center" colspan="2">' + "<spring:message code='result-message.messages.common.message.no.data'/>" + '</td></tr>'; //데이터가 없습니다.
				}

			//list뿌리기
			$("#keywordListArea").html(listHtml);
	   }
	 });
}

/*
* 함수명	: fn_makeKeywordList
* 설 명		: 추천 검색어 리스트 생성
*/
function fn_makeKeywordList(data) {

	 	let returnHtml = '';

	 	returnHtml	 += "<tr onclick='fn_setValue(" + '"' + data.srwrdSn  + '"'+ ',' + '"'+ data.srwrdNm + '"' +',' + '"' + data.useYn + '"' +")' style='cursor:pointer;'>";
		returnHtml	 += "<td>" + data.srwrdNm + "</td>";

		if(data.use_yn == "Y"){
			returnHtml	 += "<td><b>" + data.useYn + "</b></td>";
		}else{
			returnHtml	 += "<td>" + data.useYn + "</td>";
		}
		returnHtml	 += "</tr>";

		return returnHtml;
}


 /*
  * 함수명	: fn_regKeyword
  * 설 명		: 추천 검색어 추가
  */
function fn_regKeyword() {

	let count = fn_checkEmptyValue();
	if(count > 0) {
		return;
	}

	$.ajax({
		url			: "/search/keyword/regKeyword"
	  , type		: 'POST'
	  , data		: { "srwrdNm" 	: $("#srwrdNm").val(),
		  				"useYn"   	: $("input[name=useYn]:checked").val(),
		  				"srwrdTyCd" : $("#srwrdTyCd").val()
		  		  	  }
	  , dataType	: "json"
	  , success		: function(jsonData, textStatus, jqXHR) {
				if(jsonData.resultCode == 200) {
					if(jsonData.data == 1) {
						alert("<spring:message code='result-message.messages.common.message.insert'/>"); //정상적으로 등록 하였습니다.
						location.reload();
					}
				}
	  }
	});
}

 /*
  * 함수명	: fn_isReg
  * 설 명		: 입력/수정 분기 처리
  */
 function fn_isReg(){

	  if($("input[name=useYn]:checked").val() == 'Y' && $("#useLimit").val() >= 5) {
		  //사용중인 추천 검색어가 5개 이상이면 리턴
		  alert("<spring:message code='result-message.messages.keyword.message.useyn.limit'/>");
		  return;
	  }

	//신규 입력일 때
	if($("#flag").val() == 'N') {
		fn_regKeyword();
	} else if ($("#flag").val() == 'U'){
		//수정일 때
		fn_updateKeyword();
	}
 }

 /*
  * 함수명	: fn_resetValue
  * 설 명		: 신규 등록용 리셋 함수
  */
  function fn_resetValue(){
	  $("#srwrdNm").val("");
	  $("#flag").val("N");
  }


 /*
  * 함수명	: fn_countUseYn
  * 설 명		: 사용중인 추천 검색어 카운트
  */
 function fn_countUseYn() {
	 $.ajax({
		 url		: '/search/keyword/getUseYnCount'
	   , type		: 'GET'
	   , data		: { "srwrdTyCd" : $("#srwrdTyCd").val() }
	   , dataType	: 'json'
	   , success	: function(jsonData, textStatus, jqXHR) {
			if(jsonData.resultCode == 200) {
				$("#useLimit").val(jsonData.data);
			}
	   }
	 })
 }

 /*
  * 함수명	: fn_searchUseY
  * 설 명		: 사용중인 검색어만 보기
  */
 function fn_searchUseY() {
	 $.ajax({
		 url		: '/search/keyword/getUseYList'
	   , type		: 'POST'
	   , data		: { "srwrdTyCd" : $("#srwrdTyCd").val() }
	   , dataType	: 'json'
	   , success	: function(jsonData, textStatus, jqXHR) {

		   let listHtml = '';

		   	if(jsonData.resultCode == 200 && jsonData.data != null) {
		   		$.each(jsonData.data, function(index,data) {
					listHtml += fn_makeKeywordList(data);
				});
		   	} else {
		   		listHtml += '<tr><td class="center" colspan="2">' + "<spring:message code='result-message.messages.common.message.no.data'/>" + '</td></tr>';
		   	}

		   	//html 뿌리기
		   	$("#keywordListArea").html(listHtml);
	   }
	 })
 }

 /*
  * 함수명	: fn_checkEmptyValue
  * 설 명		: 필수 입력값 체크
  */
 function fn_checkEmptyValue(){

	let count = 0;

	if(fn_emptyCheck($("#srwrdNm").val())){
		count += 1;
		alert("<spring:message code='result-message.messages.common.message.required.data' arguments='추천 검색어'/>") // '{0}는(은) 필수 입력항목입니다.'
	}

	if(fn_emptyCheck(!$('input:radio[name=useYn]').is(":checked"))){
		count += 1;
		alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용 여부'/>") // '{0}는(은) 필수 입력항목입니다.'
	}

	return count;
 }

 /*
  * 함수명	: fn_setValue
  * 설 명		: 선택된 추천 검색어 정보 셋팅
  */
 function fn_setValue(num, name, use_yn) {
	//추천 검색어 번호 셋팅
	$("#srwrdSn").val(num);

	//추천 검색어 셋팅
	$("#srwrdNm").val(name);

	//사용 여부 셋팅
	if(use_yn == 'Y') {
		$("#use" + use_yn).prop("checked", true);
	} else {
		$("#use" + use_yn).prop("checked", true);
	}

	//flag 셋팅
	$("#flag").val("U");

	// 추천검색어 포커스
	$("#srwrdNm").focus();
 }

 /*
  * 함수명	: fn_updateKeyword
  * 설 명		: 추천 검색어 수정
  */
 function fn_updateKeyword() {

	  	let count = fn_checkEmptyValue();
		if(count > 0) {
			return;
		}

		$("#use").val($('input[name="useYn"]:checked').val());
		$("#keyword").val($("#srwrdNm").val());

		$.ajax({
			url			: "/search/keyword/updateKeyword"
		  , type		: "POST"
		  , data		: {  "srwrdSn" : $("#srwrdSn").val()
			  				,"srwrdNm"	: $("#srwrdNm").val()
			  				,"useYn"	: $('input[name="useYn"]:checked').val()
			  				, "srwrdTyCd" : $("#srwrdTyCd").val()
			  			  }
		  , dataType	: "json"
		  , success		: function(jsonData, textStatus, jqXHR) {
					if(jsonData.resultCode == 200) {
						if(jsonData.data > 0 ) {
							alert("<spring:message code='result-message.messages.common.message.save'/>") //정상적으로 저장하였습니다.

							/* 수정 데이터 셋팅 */
							$("#srwrdNm").val($("#keyword").val());
							if($("#use").val() == 'Y') {
								$("#useY").prop("checked",true);
							} else {
								$("#useN").prop("checked",true);
							}

							fn_countUseYn();		//사용중인 추천 검색어 갯수 갱신
							fn_getKeywordList();	//리스트 만들기
							$("#srwrdNm").focus();
						}

					} else {
						alert(jsonData.resultMessage);
					}
		  }
		});
 }
</script>

<div id="container">

	<div class="titArea">
		<div class="location">
			<span class="ic-home"><i></i></span><span>시스템 관리</span><em>관리자 관리</em>
		</div>
	</div>

	<div class="btn-box bot r">
		<div class="left">
			<h2 class="pop-tit2" style="margin-top: 0px;">추천 검색어 정보</h2>
		</div>
		<a href="javascript:void(0)" class="btn btn-white" onclick="fn_resetValue();">신규입력</a>
		<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_isReg();">저장</a>
	</div>

	<div class="row-tbl">
		<input type="hidden" id="flag"      name="flag"      value=""/>
		<input type="hidden" id="useLimit"  name="useLimit"  value=""/>
		<input type="hidden" id="srwrdSn"   name="srwrdSn"   value=""/>
		<input type="hidden" id="keyword"   name="keyword"   value=""/>
		<input type="hidden" id="use"       name="use"       value=""/>
		<input type="hidden" id="srwrdTyCd" name="srwrdTyCd" value="${SRWRD_TY_CD_SEARCH_KEYWORD }"/>
		<table>
			<colgroup>
				<col style="width: 250px;">
				<col style="width: 100px;">
			</colgroup>
			<thead>
				<tr>
					<th>
						<span class="blt_req">*</span> 추천 검색어
					</th>
					<th>
						<span class="blt_req">*</span> 사용 여부
					</th>
				</tr>
			</thead>
				<tbody id="searchArea">
					<tr>
						<td>
							<input type="text" class="input" style="width:100%;" id="srwrdNm" name="srwrdNm">
						</td>
						<td>
							<label><input type="radio" id="useY" name="useYn" value="Y"> Y</label>
							<label><input type="radio" id="useN" name="useYn" value="N" checked="checked" > N</label>
						</td>
					</tr>
				</tbody>
		</table>
	</div>


	<div class="left" style="margin-top:30px;">
			<h2 class="pop-tit2" style="display:inline;">추천 검색어 목록</h2>
			<span style="float:right; padding-top:15px;">
				<label><input type="checkbox" name="checkY" id="checkY">사용중인 검색어만 보기</label>
			</span>
	</div>
	<div class="row-tbl mt10">
		<table>
			<colgroup>
				<col style="width: 250px;">
				<col style="width: 100px;">
			</colgroup>
			<thead>
				<tr>
					<th>추천 검색어</th>
					<th>사용여부</th>
				</tr>
			</thead>
				<tbody id="keywordListArea">
				</tbody>
		</table>
	</div>


</div><!-- /container -->
