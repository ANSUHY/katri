//GNB
$(function(){
	var $obj = $("#gnb>li>a.d1");
	
	$obj.bind("mouseenter focus",function(){
		$(this).addClass("on");
		var onChk = $(this).next().is(".deth2:visible");
		
		if (onChk==true){
		} else {
			$obj.next().slideUp("fast", "easeOutSine");
			$obj.removeClass("on");
			$(this).addClass("on");
			$(this).next().slideDown("fast", "easeOutQuad");
		}
	});
	$obj.next().find("li:last").focusout(function(){
		$obj.removeClass("on");
	});
	$obj.parent().bind("mouseleave", function(){
		$obj.removeClass("on");
		$obj.next().slideUp("fast", "easeOutSine");
	});
	//
});


//snb
$( document ).ready(function() {

	if($("#gnb> li").length > 4){
		$("#gnb").addClass('big');
	}

	if($("#lm> ul> li > ul").length > 0){
		$('#lm> ul >li > ul').parent().addClass('has-sub');
		$('#lm> ul >li > a').on('click', function(){
			//$(this).removeAttr('href');
			var element = $(this).parent('li');
				if (element.hasClass('open')) {
					element.removeClass('open');
					element.find('li').removeClass('open');
					element.find('ul').slideUp();
				}
				else {
					element.addClass('open');
					element.children('ul').slideDown();
			}
		});
	}

	
});

function gnb($d1n, $d2n, $d3m){
	var $id = "";

	$("#gnb").children().filter("[class^=gnb]").each(function(){
		$id = $(this).attr("class").replace("gnb", "");
		
		if($d1n == $id){
			$(".gnb"+$d1n).addClass("top_2th_ov");

			if($d2n)	$(".gnb"+$d1n+">ul>li").eq(parseInt($d2n) - 1).addClass("top_3th_ov");
			if($d3m)	$(".gnb"+$d1n+">ul>li.gsnb"+$d2n+">ul>li").eq(parseInt($d3m) - 1).addClass("top_4th_ov");
		}
	});
	$("#snb>ul").children().filter("[class^=snb]").each(function(){
		$id = $(this).attr("class").replace("snb", "");
		
		if($d2n == $id){
			$(".snb"+$d2n).addClass("active");
			if($d3m)	$(".snb"+$d2n+"> ul>li").eq(parseInt($d3m) - 1).addClass("link_3th_ov");
		}
	});
	
}

function lm_check(gn,lm1,lm2){
	gn--;
	lm1--;
	lm2--;
	if(jQuery("#gnb > li").eq(gn).find(">.deth2").length > 0){
		jQuery("#gnb > li").eq(gn).find(">.deth2").animate(
			{
				height:jQuery("#gnb > li").eq(gn).find(" > .deth2")},gn_speed,
				function(){
					jQuery("#gnb > li").eq(gn).find(" > .deth2").show();					
					jQuery(".gnb-bg").show();
					//jQuery("#gnbtop > li > ul").animate({opacity:1});
				}
		);
	}
	var lm0 = lm1+parseInt('1');
	var gn0 = gn+parseInt('1');
	if(lm0 != "0"){
		jQuery("#lm > li").eq(lm1).find(">a").toggleClass("link_2th_ov");
		jQuery("#lm > li").eq(lm1).find("ul>li").eq(lm2).find(">a").toggleClass("link_3th_ov");
	}
	if(gn0 != "0"){
		jQuery("#gnb > li").eq(gn).find(">a").toggleClass("top_2th_ov");
		if(gn0 == '100'){
			jQuery("#gnb > li").eq(gn).find(".deth2 li").eq(lm2).find(">a").toggleClass("top_3th_ov");
		}else{
			jQuery("#gnb > li").eq(gn).find(".deth2 li").eq(lm1).find(">a").toggleClass("top_3th_ov");
		}
	//alert(jQuery("#lm > li").eq(lm1).attr("class"));
	}
}

/** gnb end **/

$(function() {
    $( ".datepicker" ).datepicker({
		showOn: "button",
		buttonImage: "images/icon_calendar.png",
		buttonImageOnly: false,
		buttonText: ""	
	});

});

function adjustScript() {
    var hei = $(window).height();
	var hei01 = $('#container>.hd-search').height();
	var hei02 = $('#dash-wrap').height();
	$("#grid-wrap").css({height:hei-244-hei01});
	$(".left-mn").css({minHeight:hei});
	$(".left-mn").css({height:hei02+60});
}

$(function() {
    //adjustScript($(this).width());
    $(window).resize(function() {
        adjustScript();
    });
	adjustScript();
});

function popitup(url) {
	newwindow=window.open(url,'name','height=600,width=1100');
	if (window.focus) {newwindow.focus()}
	return false;
}

$(function() {
	$("input,select,textarea").each(function(){
		if($(this).prop("disabled")){
			$(this).addClass("read");
		}
	});
	$("input,select,textarea").each(function(){
		if($(this).prop("readonly")){
			$(this).addClass("read");
		}
	});
});