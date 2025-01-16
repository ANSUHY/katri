<%@page import="com.katri.common.Const"%>
<%@page import="com.katri.web.login.model.LoginAuthrtMenuRes"%>
<%@page import="java.util.List"%>
<%@page import="com.katri.common.util.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>

	$(function(){

	});

	/*
	 * 함수명 : fn_pageFileDownload
	 * 설   명 : DB에 없는 파일 다운로드(파일은 실제로 넣어줘야함)
	 */
	function fn_pageFileDownload(newFileNm, srcFileNm){
		$("#frm_fileDown").remove();

		//동적폼생성
		let form = $('<form id="frm_fileDown" name="frm_fileDown"></form>');
		form.attr('action','/file/pageFileDownload');
		form.attr('method','post');
		form.appendTo('body');

		let inputNewFileNm 	= $("<input type='hidden' name='newFileNm' 	value='" + newFileNm + "' />");
		let inputSrcFileNm 	= $("<input type='hidden' name='srcFileNm'	value='" + srcFileNm + "' />");
		form.append(inputNewFileNm);
		form.append(inputSrcFileNm);

		form.submit();

	}

</script>

<!-- BO 메뉴 권한에 대한 처리는 세션에 담아서 처리한다. -->
<div id="header">
	<h1 class="h1Tit" style="font-size:16px; line-height:15px; padding-top:2px;">
		<a>시험인증 빅데이터<br />포탈 관리자</a>
	</h1>
	<div id="gnb-wrap">
		<ul id="gnb">
<%
//Login 한 사람의 권한에 따른 메뉴
List<LoginAuthrtMenuRes> menuList = SessionUtil.getLoginAuthrtMenuList();
int gnbSeq = 0;
int nowDept = 0;

Boolean haveUrl 		= true;		//MENU URL을 가지고있는지
String strMenuHref 		= "";		//MENU URL
String strTarget 		= "";		//_blank || _self
LoginAuthrtMenuRes menu = null; 	//현재메뉴
LoginAuthrtMenuRes nextMenu = null; //다음메뉴

if(menuList != null && menuList.size() > 0) {

	for(int nLoop =0; nLoop<menuList.size(); nLoop++){

		haveUrl		= true;
		strMenuHref = "";
		strTarget 	= "";
		menu 		= null;
		nextMenu 	= null;

		menu = menuList.get(nLoop);

		haveUrl 	= (menu.getMenuUrlAddr() == null || ("").equals(menu.getMenuUrlAddr())? false : true);
		strMenuHref = (haveUrl	?  menu.getMenuUrlAddr() : "javascript:void(0);");
		strTarget 	= (haveUrl && (Const.Code.LinkTypeCd.NEW ).equals( menu.getLinkTyCd() ) ? "_blank" : "_self");

		if( (nLoop +1 ) < menuList.size()){
			nextMenu = menuList.get(nLoop+1);
		}

		if(menu.getMenuLvlVal() == 1){
%>
				<li class="gnb<%=gnbSeq++%>"><a href="<%=strMenuHref%>" target="<%=strTarget%>" class="d1"><%=menu.getMenuNm()%></a>
<%
			nowDept = 1;

		}else if(menu.getMenuLvlVal() == 2){
			if(nowDept == 1){
%>
					<div class="deth2">
						<ul>
<%
			}
%>
<%-- 							<li><a href="<%=strMenuHref%>" target="<%=strTarget%>" ><%=menu.getMenuNm()%></a></li> --%>
							<li><a href="<%="/".equals(strMenuHref) ? "javascript:alert('준비중입니다.');" : strMenuHref%>" target="<%=strTarget%>" ><%=menu.getMenuNm()%></a></li>
<%
			if(nextMenu == null || nextMenu.getMenuLvlVal() == 1){
%>
						</ul>
					</div>
<%
			}
			nowDept = 2;
		}


		if(nextMenu == null || nextMenu.getMenuLvlVal() == 1){
%>
			</li>
<%
		}
	}
}

%>
		</ul>
	</div>
	<div class="utill">
		<a href="<c:url value='/logout'/>" class="btn btn-white"><i class="fa fa-sign-out"></i> 로그아웃</a>
	</div>
</div><!-- /header -->

