// JavaScript Document

$(document).ready(function(){
	$("#demo .col").mouseenter(function(){
		$(this).addClass("active");
		$('.active').siblings().css("opacity", "0.4");
	});
	$("#demo .col").mouseleave(function(){
		$(this).removeClass("active");
		$("#demo .col").css("opacity", "1");
	});
});