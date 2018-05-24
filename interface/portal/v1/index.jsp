<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="weaver.general.StaticObj" %>
<%@ page import="java.util.*"%>

<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@ page import="weaver.hrm.User"%>

<%@ page import="weaver.systeminfo.menuconfig.MenuMaint"%>
<%@ page import="weaver.systeminfo.SystemEnv"%>
<%@page import="weaver.general.GCONST,weaver.filter.MultiLangFilter"%>
<%@ page import="weaver.general.IsGovProj" %>
<%@page import="weaver.rdeploy.portal.PortalUtil"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<jsp:useBean id="baseBean" class="weaver.general.BaseBean" scope="page" />
<jsp:useBean id="MouldStatusCominfo" class="weaver.systeminfo.MouldStatusCominfo" scope="page" />
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>南京国土资源局</title>
		
		<script type="text/javascript">
			(function(win,doc){
			  var docE = doc.documentElement;
			  var resize = function(){
			    var deviceWidth = docE.clientWidth;
			    docE.style.fontSize = deviceWidth /19.2 + 'px';
			  }
			  window.onresize = resize;
			  resize();
			}(window,document))
		</script>
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<!--[if lt IE 9]>
		<script src="js/html5.js"></script>
		<script src="js/respond.js"></script>
		<![endif]-->
		<style>
			/*public*/
			html,div,body,p,ul,li,h3,i{
				padding: 0;
				margin: 0;
				text-decoration: none;
				list-style-type: none;
				background-position: center;
				background-size: contain;
				background-repeat: no-repeat;
			}
			iframe{
				outline: none;
				border: none;
			}
			.box{
				
			}
			
			/*header*/
			.box .header{
				height: 100px;
				background-image: url(img/header-bg.png);
				background-size: cover;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/header-bg.png',sizingMethod='scale'); ;
			}
			.box .header .header-left,
			.box .header .header-right,
			.box .header .header-center{
				height: 100px;
				line-height: 100px;
				display: inline-block;
			}
			.box .header .header-left{
				padding-left: 225px;
			}
			.box .header .header-left p{
				height: 100px;
				line-height: 100px;
				color: #FFFFFF;
				height: 100px;
			    line-height: 100px;
			    color: #FFFFFF;
			    font-size: 30px;
			    font-weight: bold;
			    position: relative;
			    padding-left: 90px;
				
			}
			.box .header .header-left p i{
				display: inline-block;
				height: 86px;
				width: 86px;
				top: 50%;
				margin-top: -43px;
				position: absolute;
				background-image: url(img/header-logo.png);
				left: 0px;
			}
			.box .header .header-center{
				
			}
			.box .header .header-right{
				
			}
			
			.box .header .header-right .header-img{
				height: 100px;
				line-height: 120px;
				text-align: center;
			}
			.box .header .header-right .header-img .header-img-inner{
				height: 34px;
				width: 44px;
				display: inline-block;
			}
			
			.box .header .header-right .header-img:first-child .header-img-inner{
				background-image: url(img/header-img1.png);
			}
			.box .header .header-right .header-img:first-child+div .header-img-inner{
				background-image: url(img/header-img2.png);
				border-left: 2px solid #FFF;
    			border-right: 2px solid #FFF;
			}
			.box .header .header-right .header-img:first-child+div+div .header-img-inner{
				background-image: url(img/header-img3.png);
			}
			
			/*content*/
			.box .content{
				
			}
			.box .content .content-head{
				height: 100px;
				line-height: 100px;
				background-color: #FFFFFF;
				
			}
			.box .content .content-head .content-menu{
				height: 100px;
				line-height: 100px;
				padding-left: 0px;
				padding-right: 0px;
			}
			.box .content .content-head .content-menu .content-menu-list{
			    height: 100px;
			    line-height: 100px;
			    display: inline-block;
			    width: 11.11%;
			    padding: 0;
			    margin: 0;
			    float: left;
		        cursor: pointer;
			}
			
			.box .content .content-head .content-menu .content-menu-list .content-menu-list-img{
				height: 56px;
				background-size: 40% 70%;
			}
			
			.box .content .content-head .content-menu .content-menu-list .content-menu-list-text{
				height: 42px;
				line-height: 42px;
				text-align: center;
			}
			
			.box .content .content-head .content-menu .content-menu-list .content-menu-list-text span{
				text-align: center;
				color: #61C3F2;
				font-size: 24px;
			}
			
			
			.box .content .content-head .content-menu .content-menu-list:first-child .content-menu-list-img{
				background-image: url(img/head-menu-1.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div .content-menu-list-img{
				background-image: url(img/head-menu-2.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div .content-menu-list-img{
				background-image: url(img/head-menu-3.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div+div .content-menu-list-img{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div+div+div .content-menu-list-img{
				background-image: url(img/head-menu-5.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div+div+div+div .content-menu-list-img{
				background-image: url(img/head-menu-6.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div+div+div+div+div .content-menu-list-img{
				background-image: url(img/head-menu-7.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div+div+div+div+div+div .content-menu-list-img{
				background-image: url(img/head-menu-8.png);
			}
			.box .content .content-head .content-menu .content-menu-list:first-child+div+div+div+div+div+div+div+div .content-menu-list-img{
				background-image: url(img/head-menu-9.png);
			}
			
			.box .content .content-body{
				height: 750px;
				background-color: #F2F2F2;
				padding: 25px 0px;
			}
			.box .content .content-body .content-inner{
				background-color: #FFFFFF;
			    height: 700px;
			    padding: 60px 78px 26px 78px;
			}
			.box .content .content-body .content-inner .inner-box{
				height: 612px;
				width: 100%;
			}
			.box .content .content-body .content-inner .content-inner-left,
			.box .content .content-body .content-inner .content-inner-right{
				height: 612px;
			}
			.box .content .content-body .content-inner .content-inner-left{
				
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-title{
				height: 80px;
				line-height: 80px;
				background-color: #3966A7;
				text-align: center;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-title h3{
				color: #FFFFFF;
				position: relative;
				height: 80px;
				line-height: 80px;
				font-size: 36px;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-title h3 i{
				width: 35px;
			    height: 35px;
			    background-image: url(img/left-title.png);
			    position: absolute;
			    top: 50%;
			    margin-top: -17.5px;
			    left: 15px;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body{
				
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list{
				height: 105px;
				line-height: 105px;
				background-color: #F3F3F3;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner .top-img{
				background-size: 30% 60%;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child .content-inner-left-body-list-inner:first-child .top-img{
				background-image: url(img/left-1.png);
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child .content-inner-left-body-list-inner:first-child+div .top-img{
				background-image: url(img/left-2.png);
			}
			
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div .content-inner-left-body-list-inner:first-child .top-img{
				background-image: url(img/left-3.png);
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div .content-inner-left-body-list-inner:first-child+div .top-img{
				background-image: url(img/left-4.png);
			}
			
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div+div .content-inner-left-body-list-inner:first-child .top-img{
				background-image: url(img/left-5.png);
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div+div .content-inner-left-body-list-inner:first-child+div .top-img{
				background-image: url(img/left-6.png);
			}
			
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div+div+div .content-inner-left-body-list-inner:first-child .top-img{
				background-image: url(img/left-7.png);
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div+div+div .content-inner-left-body-list-inner:first-child+div .top-img{
				background-image: url(img/left-8.png);
			}
			
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div+div+div+div .content-inner-left-body-list-inner:first-child .top-img{
				background-image: url(img/left-9.png);
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:first-child+div+div+div+div .content-inner-left-body-list-inner:first-child+div .top-img{
				background-image: url(img/left-10.png);
			}
			
			
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner{
				height: 105px;
				line-height: 105px;
				border: 1px solid #E5E5E5;
				cursor: pointer;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner .top-img{
				height: 60px;
				line-height: 60px;
			}
			
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner .bottom-text{
				height: 45px;
				line-height: 45px;
				text-align: center;
			}
			
			.box .content .content-body .content-inner .content-inner-right{
				
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top{
				border: 1px solid #E5E5E5;
			    margin-bottom: 10px;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom{
				border: 1px solid #E5E5E5;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p{
				height: 50px;
				line-height: 50px;
				font-size: 25px;
				color: #191919;
				position: relative;
				padding-left: 60px;
				border-bottom: 1px dashed #E5E5E5;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-img,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-img,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-btn,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-btn{
			    height: 50px;
			    line-height:  50px;
			   /* background-color: red;*/
			    display: inline-block;
			    width:  50px;
			    position: absolute;
			    top: 50%;
			    margin-top: -25px;
			    left: 0px;
		        cursor: pointer;
			    background-size: 50% 50%;
			    background-image: url(img/head-menu-5.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-btn,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-btn{
				left: 93%;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box{
				height: 196px;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model{
				height: 98px;
			    padding: 10px 10px;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box{
				height: 78px;
    			width: 100%;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(1) .border-box{
				background-color: #DAA6E3;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(2) .border-box{
				background-color: #88C896;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(3) .border-box{
				background-color: #F2A6A3;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(4) .border-box{
				background-color: #8B96CA;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(5) .border-box{
				background-color: #65D0BC;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(6) .border-box{
				background-color: #F387BC;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(7) .border-box{
				background-color: #B1D365;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(8) .border-box{
				background-color: #51A1B9;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(9) .border-box{
				background-color: #F29A75;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(10) .border-box{
				background-color: #FFF798;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .left-border{
				height: 78px;
				background-size: 50%;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(1) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(2) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(3) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(4) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(5) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(6) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(7) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(8) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(9) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(10) .border-box .left-border{
				background-image: url(img/head-menu-4.png);
			}
			
			
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .right-border{
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .right-border  p{
				height: 39px;
			    line-height: 39px;
			    color: #FFF;
			    text-align: center;
			}
			
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box{
				height: 296px;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .left-border{
				height: 78px;
				background-size: 50% 50%;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(1) .left-border{
				background-image: url(img/head-menu-2.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(2) .left-border{
				background-image: url(img/head-menu-2.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(3) .left-border{
				background-image: url(img/head-menu-2.png);
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(4) .left-border{
				background-image: url(img/head-menu-2.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(5) .left-border{
				background-image: url(img/head-menu-2.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(6) .left-border{
				background-image: url(img/head-menu-2.png);
			}
			
			
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border{
				height: 78px;
			    
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border p{
				height: 39px;
			    line-height: 39px;
			    color: #FFF;
			    text-align: center;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .right-border  p:nth-child(1),
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border p:nth-child(1){
				font-size: 25px;
				cursor: pointer;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .right-border  p:nth-child(2),
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border p:nth-child(2){
				font-size: 16px;
			
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model{
			    padding: 10px  10px;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .border-box{
				height: 78px;
				width:100%;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(1) .border-box{
				background-color: #6FC07C;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(2) .border-box{
				background-color: #ECC941;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(3) .border-box{
				background-color: #F2A6A3;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(4) .border-box{
				background-color: #F5AA6D;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(5) .border-box{
				background-color: #65D0BC;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(6) .border-box{
				background-color: #D37371;
			}
			
			
			/*footer*/
			.box .footer{
				height: 50px;
				line-height: 50px;
				background-color: #434448;
				text-align: center;
				color: #F0F0F0;
			}
			
			/*重写bootstrap的样式*/
			.col-md-12,
			.col-lg-12{
				padding-left:0px;
				padding-right: 0px;
			}
			.col-md-3,
			.col-lg-3,
			.col-md-3,
			.col-lg-2{
				padding-left: 0px;
				padding-right: 0px;
			}
			.dropdown {
				padding-left: 5px;
				font-size: 11px;
				color: #000;
				height: 35px;
				border-left: 1px solid transparent;
				border-top: 1px solid transparent;
				border-right: 1px solid transparent;
			}
			.selectTile {
				margin: 0px;
				padding: 0px;
			}
			.selectTile a {
				background: none;
				display: block;
				width: 40px;
				margin-left: 4px;
				margin-top: 1px;
			}
			span.searchTxt {
				height: 35px;
				line-height: 28px;
				float: left;
				width: 32px;
				display: block;
				overflow: hidden;
				text-overflow: ellipsis;
				color: #fff;
				padding: 0;
			}
			.selectTile a span {
				cursor: pointer;
				display: block;
				padding: 6 0 0 0;
				background: none;
				height: 25px;
				font-size: 14px;
			}
			.dropdown .e8_dropdown {
				background-image: url(/wui/theme/ecology8/page/images/toolbar/down_wev8.png);
				width: 12px;
				height: 12px;
				float: left;
				margin-top: 10px;
				background-position: 50% 50%;
			}
			.searchTxtSplit {
				color: #078BF3;
				font-size: 16px;
			}
			.selectContent {
				position: relative;
				z-index: 6;
			}
			.selectContent ul{
				background: #fff none repeat scroll 0 0;
				border-left: 1px solid #d0d0d0;
				border-right: 1px solid #d0d0d0;
				border-bottom: 1px solid #d0d0d0;
				color: #C5C0B0;
				display: none;
				left: -6px;
				position: absolute;
				top: 8px;
				min-width: 50px;
				list-style: none;
			}
			.selectContent ul li{
				list-style: none;!important;
				height: 25px;
				color: #656565
			}
			.selectContent ul li a {
				padding: 0px;
				display: block;
				margin: 0;
				height: 25px;
				line-height: 21px;
				text-align:center;
				/**font-size: 14pt;**/
			}
			.dropdown a, .dropdown a:visited{
				color: #666666;
				text-decoration: none;
				outline: none;
			}
			.searchkeywork{
				width: 112px;
				font-size: 12px;
				height: 32px !important;
				margin-top: 1;
				background-color: transparent;
				border: none !important;
				line-height: 30px;
				color: #FFFFFF;
			}
			.searchkeyworkbtn{
				float: right;
				background-color: #0782e4;
				height: 35px;
				width: 30px;
			}
		</style>
	</head>
<%
    /*用户验证*/
    if (user == null) {
        response.sendRedirect("/login/Login.jsp");
        return;
    }
%>

<%
	//判断是否有微搜功能
	boolean microSearch=weaver.fullsearch.util.RmiConfig.isOpenMicroSearch();
    MenuMaint mm = new MenuMaint("left", 3, user.getUID(), user.getLanguage());
    List menuArray = byClickCntSort(user, mm.getAllMenus(user, 1));
    boolean rmflg = menuArray.remove(null); 
    while(rmflg) {
    	rmflg = menuArray.remove(null);
    }
    String skin = (String)request.getAttribute("REQUEST_SKIN_FOLDER");
	if(null == skin) {
		skin = "default";
	}
    staticobj = StaticObj.getInstance();
    if(software == null) software="ALL";

    int isgoveproj = Util.getIntValue(IsGovProj.getPath(),0);//0:非政务系统，1：政务系统
    String Customertype = Util.null2String(""+user.getType()) ;
    String logintype = Util.null2String(user.getLogintype()) ;
	
	String signWeekBg = getWeekBg(user);
%>


<%
//获得动态绑定的搜索栏
RecordSet.executeSql("select * from mode_toolbar_search where isusedsearch=1 order by showorder");
%>


<%!
private String[] convMenuInfo(User user, Map map) {
	String[] result = new String[3];
	
    int level = Integer.parseInt((String) map.get("levelid"));
    String sicon = "";
    String sname = "";
	String position = "";
	String isDefault="1"; //是否具有默认图标菜单
    switch (level) {
    //任务管理
    case -2908:            
        sname = SystemEnv.getHtmlLabelName(1332, user.getLanguage());
        position += "-0 -111";
        break;
    //集成系统
    case -2774:            
        sname = SystemEnv.getHtmlLabelName(26267, user.getLanguage());
        position += "-0 -37";
        break;
    //计划任务
    case -2741:            
        sname = SystemEnv.getHtmlLabelName(407, user.getLanguage());
        position += "-148 -74";
        break;
    //临时LICENSE
    case -2080:
        sname = SystemEnv.getHtmlLabelName(18599, user.getLanguage());
        position += "-185 -74";
        break;
    //流程
    case 1:       
        sname = SystemEnv.getHtmlLabelName(22244, user.getLanguage());
        position += "-111 -0";
        break;
    //我的知识
    case 2:
        sname = SystemEnv.getHtmlLabelName(26268,user.getLanguage());
        position += "-74 -0";
        break;
    //我的客户
    case 3:
        sname = SystemEnv.getHtmlLabelName(21313, user.getLanguage());
        position += "-148 -0";
        break;
    //我的项目
    case 4:
        sname = SystemEnv.getHtmlLabelName(25106, user.getLanguage());
        position += "-185 -0";
        break;
    //人事
    case 5:
        sname = SystemEnv.getHtmlLabelName(20694, user.getLanguage());
        position += "-222 -0";
        break;
    //我的会议
    case 6:
        sname = SystemEnv.getHtmlLabelName(2103, user.getLanguage());
        position += "-74 -37";
        break;
    //我的资产
    case 7:
        sname = SystemEnv.getHtmlLabelName(535, user.getLanguage());
        position += "-37 -37";
        break;
    //我的邮件
    case 10:
        sname = SystemEnv.getHtmlLabelName(71, user.getLanguage());
        break;
    //我的短信
    case 11:
        sname = SystemEnv.getHtmlLabelName(22825, user.getLanguage());
        break;
    //我的协助    
    case 80:
        sname = SystemEnv.getHtmlLabelName(26269, user.getLanguage());
        position += "-37 -0";
        break;
    //目标绩效
    case 94:
        sname = SystemEnv.getHtmlLabelName(26270, user.getLanguage());
        position += "-111 -74";
        break;
    //我的通信
    case 107:
        sname = SystemEnv.getHtmlLabelName(26271, user.getLanguage());
        position += "-74 -111";
        break;
    //我的报表
    case 110:
        sname = SystemEnv.getHtmlLabelName(15101, user.getLanguage());
        position += "-37 -74";
        break;
    //信息中心
    case 111:
        sname = SystemEnv.getHtmlLabelName(70, user.getLanguage());
        position += "-0 -74";
        break;
    //系统设置
    case 114:
        sname = SystemEnv.getHtmlLabelName(22250, user.getLanguage());
        position += "-74 -74";
        break;
    //我的日程
    case 140:
        sname = SystemEnv.getHtmlLabelName(2211, user.getLanguage());
        position += "-148 -37";
        break;
     //车辆管理
    case 144:
        sname = SystemEnv.getHtmlLabelName(920, user.getLanguage());
        position += "-185 -37";
        break;
    //我的相册
    case 199:
        sname = SystemEnv.getHtmlLabelName(20003, user.getLanguage());
        position += "-222 -37";
        break;
    //临时LICENSE
    case 227:
        sname = SystemEnv.getHtmlLabelName(18599, user.getLanguage());
        position += "-185 -74";
        break;
    //计划任务
    case 263:
        sname = SystemEnv.getHtmlLabelName(407, user.getLanguage());
        position += "-148 -74";
        break;
    //我的客服
    case 352:
        sname = SystemEnv.getHtmlLabelName(26272, user.getLanguage());
        position += "-222 -74";
        break;
    case 392:
        sname = SystemEnv.getHtmlLabelName(26467, user.getLanguage());
        position += "-37 -111"; 
        break;  
    //我的邮件
    case 536:
        sname = SystemEnv.getHtmlLabelName(71, user.getLanguage());
        position += "-111 -37";
        break;
    default:
        sname = (String) map.get("name");
        String topIcon=(String)map.get("topIcon");
        if("".equals(topIcon))
    	   position += "";
        else{
           position+=topIcon;
           isDefault="0";
        }   
        break;
    }
    if (sname == null || "".equals(sname.trim())) {
    	sname = SystemEnv.getHtmlLabelName(149, user.getLanguage());
    } 
    
    if ("".equals(position)) {
        position += "-315 0";
    }
	//顶部菜单简称
   // sname = getTopMenuName(level,sname);
   //使用config里面的顶部简称
	if(map.get("customTopName")!=null && !"".equals((String)map.get("customTopName"))){
		sname = (String)map.get("customTopName");
	}
    result[0] = sname;
    result[1] = position;
    result[2] = isDefault;
    return result;
}

private String getTopMenuName(int menuId,String menuName){
	String result = "";
	weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
	rs.executeSql("select topmenuname,topname_e,topname_t from LeftMenuInfo where topmenuname is not null and id="+menuId);
	if(rs.next()){
		result = Util.null2String(rs.getString("topmenuname"));
	}
	result = "".equals(result)?menuName:result;

	return result;
}

private String getWeekBg(User user) {
	String strWeek = "";
    java.util.GregorianCalendar g=new java.util.GregorianCalendar();
    int week = g.get(java.util.Calendar.DAY_OF_WEEK);
    switch(week) {
    case 1:
    	strWeek = SystemEnv.getHtmlLabelName(24626,user.getLanguage());
        break;
    case 2:
    	strWeek = SystemEnv.getHtmlLabelName(392,user.getLanguage());
        break;
    case 3:
    	strWeek = SystemEnv.getHtmlLabelName(393,user.getLanguage());
        break;
    case 4:
    	strWeek = SystemEnv.getHtmlLabelName(394,user.getLanguage());
        break;
    case 5:
    	strWeek = SystemEnv.getHtmlLabelName(395,user.getLanguage());
        break;
    case 6:
    	strWeek = SystemEnv.getHtmlLabelName(396,user.getLanguage());
        break;
    case 7:
    	strWeek = SystemEnv.getHtmlLabelName(397,user.getLanguage());
        break;
    default:
    	break;
    }
    
    return strWeek;
}

private List byClickCntSort(User user, List target) {
	int displayMenuCount = 6;
	//result
	List sortList = new ArrayList();
	//初始化返回菜单列表
	for(int i=0; i<displayMenuCount; i++) {
		sortList.add(null);
	}
	//点击量最高的6个菜单id列表
	List menuIds = new ArrayList();
	
	weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
	String dbType = rs.getDBType();
	String exeSql = "";
	if ("sqlserver".equals((dbType))) {
		exeSql = "SELECT menuId, clickCnt FROM HrmUserMenuStatictics WHERE userid=" + user.getUID() + " order by clickCnt desc, menuid";
	} else {
		//exeSql = "select t.rownumber,t.menuId, t.clickCnt  from ( SELECT rownum as rownumber,menuId, clickCnt FROM HrmUserMenuStatictics WHERE userid=" + user.getUID() + " order by clickCnt desc, menuid) t  where t.rownumber<=6";	
		exeSql = "select t.rownumber,t.menuId, t.clickCnt  from ( SELECT rownum as rownumber,menuId, clickCnt from (SELECT menuId, clickCnt FROM HrmUserMenuStatictics WHERE userid=" + user.getUID() + " order by clickCnt desc, menuid) t ) t  where t.rownumber>=0";	
	}
	
	//取得点击量最高的6个菜单
	rs.executeSql(exeSql);
	int displayClickMenuCnt = 0;
	while(rs.next()) {
		long clickCnt = 0;
		int menuId = 0;
		try {
			clickCnt = Long.parseLong(rs.getString("clickCnt"));
		} catch(NumberFormatException e) {
			clickCnt = 0;
		}
		menuId = Util.getIntValue(rs.getString("menuId"), 0);
		
		if (clickCnt > 0 && hasVisible(menuId, target)) {
			if (displayClickMenuCnt > displayMenuCount - 1) {
				continue;				
			}
			displayClickMenuCnt++;
			menuIds.add(new Integer(menuId));	
		}
	}
	
	Iterator it = target.iterator();
	while(it.hasNext()) {
		Map map = (Map) it.next();
		int menuId = Integer.parseInt((String)map.get("levelid"));
		//是否显示调查模块
		if (!PortalUtil.isShowSurvey() && menuId == 635) {
		    continue;
		}
		//是否显示邮件模块
		if (!PortalUtil.isShowEMail() && menuId == 536) {
		    continue;
		}
		
		//是否显示通信模块
		if (!PortalUtil.isShowMessage() && menuId == 107) {
		    continue;
		}
		
		//是否显示报表模块
		if (!PortalUtil.isShowReport() && menuId == 110) {
		    continue;
		}
		
		//是否显示信息中心模块
		if (!PortalUtil.isShowInfoCenter() && menuId == 111) {
		    continue;
		}
		
		int index = menuIds.indexOf(new Integer(menuId));
		
		if (index != -1) {
			sortList.set(index, map);
			continue;
		}
		sortList.add(map);
	}
	
	return sortList;
}

private boolean hasVisible(int id, List target) {
	Iterator it = target.iterator();
	while(it.hasNext()) {
		Map map = (Map) it.next();
		int menuId = Integer.parseInt((String)map.get("levelid"));
		
		if (id == menuId) {
			return true;
		}
	}
	return false;
}
%>
	<body>
		
		
		<div class="box col-md-12	col-lg-12">
			
			
			<!--header-->
			<div class="header col-md-12 col-lg-12">
				
				<!--header left-->
				<div class="header-left col-md-5 col-lg-5">
					<p><i></i>南京市国土资源局</p>
				</div>
				
				<!--header center-->
				<div class="header-center col-md-5 col-lg-5">
					<form name="searchForm" method="post" action="/system/QuickSearchOperation.jsp" target="contentIframe">
						<input type="hidden" name="searchtype" value="<%=microSearch?9:2%>">
						<input type="hidden" id="mainid" name="mainid" value="">
						<input type="hidden" id="stype" name="stype" value="">
						<input type="hidden" id="fieldname" name="fieldname" value="">
						<div id="sample" class="dropdown" style="float:left;position:absolute;left:310px;top:36px;">
							<div class="selectTile">
								<a href="#" style="width:57px;">
									<span style="height:35px;line-height:28px;float:left;width:32px;display:block;overflow:hidden;text-overflow:ellipsis;color:#fff;padding:0;font-size:16px;" class="searchTxt">
										<%=microSearch?
												SystemEnv.getHtmlLabelName(31953,user.getLanguage()):
												SystemEnv.getHtmlLabelName(30042,user.getLanguage())%>
									</span>
									<div class="e8_dropdown"></div>
									<!--background: url(/wui/theme/ecology8/skins/default/page/ecologyShellImg_wev8.png);background-repeat: no-repeat; background-position: -262px  -62px;-->
									<div style="float:right;display: block; width:8px;height:18px;*height:18px;margin-top:-36px;" class="searchTxt searchTxtSplit">|</div>
								</a>
							</div>
							<div class="selectContent" style="margin-top:26px;*margin-top:20px;_margin-top:0px;">
								<ul id="searchBlockUl" style="font-size:16px;">
									<iframe src="" style="filter:alpha(opacity=0);opacity:0;position:absolute; visibility:inherit; top:0px; left:0px; width:100%; height:100%; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';" >
									</iframe>
									<% if(microSearch){ %>
									<li><a href="#">
									<!--<img src="/wui/theme/ecology8/page/images/toolbar/ws_wev8.png"/>-->
									<span searchType="9"><%=SystemEnv.getHtmlLabelName(31953,user.getLanguage())%></span></a></li>
									<%}%>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/hrs_wev8.png"/>--><span searchType="2"><%=SystemEnv.getHtmlLabelName(30042,user.getLanguage())%></span></a></li>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/wls_wev8.png"/>--><span searchType="5"><%=SystemEnv.getHtmlLabelName(30045,user.getLanguage())%></span></a></li>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/docs_wev8.png"/>--><span searchType="1"><%=SystemEnv.getHtmlLabelName(30041,user.getLanguage())%></span></a></li>
								<%if(isgoveproj==0){%>
									<%if((Customertype.equals("3") || Customertype.equals("4") || !logintype.equals("2"))&&("1".equals(MouldStatusCominfo.getStatus("crm"))||"".equals(MouldStatusCominfo.getStatus("crm")))){%> 
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/crm_wev8.png"/>--><span searchType="3"><%=SystemEnv.getHtmlLabelName(30043,user.getLanguage())%></span></a></li>
									<%
									}
								}
									%>
										<%if("1".equals(MouldStatusCominfo.getStatus("cwork"))||"".equals(MouldStatusCominfo.getStatus("cwork"))) {%>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/xz_wev8.png"/>--><span searchType="8"><%=SystemEnv.getHtmlLabelName(30047,user.getLanguage())%></span></a></li>
								<%} %>
								<%
								if (PortalUtil.isShowEMail()) {
								%>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/mail_wev8.png"/>--><span searchType="7"><%=SystemEnv.getHtmlLabelName(71,user.getLanguage())%></span></a></li>
								<%
								}
								%>
							<%
								if(isgoveproj==0&&("1".equals(MouldStatusCominfo.getStatus("proj"))||"".equals(MouldStatusCominfo.getStatus("proj")))){%>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/p_wev8.png"/>--><span searchType="6"><%=SystemEnv.getHtmlLabelName(30046,user.getLanguage())%></span></a></li>
								<%
								}
								%>
							<%
								if((!logintype.equals("2")) && software.equals("ALL")&&("1".equals(MouldStatusCominfo.getStatus("cpt"))||"".equals(MouldStatusCominfo.getStatus("cpt")))){%>
									<li><a href="#"><!--<img src="/wui/theme/ecology8/page/images/toolbar/zc_wev8.png"/>--><span searchType="4"><%=SystemEnv.getHtmlLabelName(30044,user.getLanguage())%></span></a></li>
								<%
								}
								%>
							
								<%  
								    while(RecordSet.next()){
								       String tempName = RecordSet.getString("searchname");
								       if(tempName.length() > 2){
								    	   tempName = tempName.substring(0,2);  
								       }
								    %>
                                  <li>
                                      <a href="#"><img height="14" width="14" src="<%=RecordSet.getString("imageurl") %>"/><span searchType="10"><%=tempName %></span></a>
                                      <span style="display:none;" name="mainid" ><%=RecordSet.getInt("mainid") %></span>
                                      <span style="display:none;" name="stype"><%=RecordSet.getInt("serachtype") %></span>
                                      <span style="display:none;" name="fieldname"><%=RecordSet.getInt("searchField") %></span>
                                  </li>
                                
                                <% } %>
								
								</ul>
							</div>
						</div>
						
						<div style="float:left;vertical-align :middle;position:absolute;left:380px;top:1px;" >
							<input type="text" id="searchvalue" name="searchvalue" onfocus="clearVal(this);" onblur="recoverVal(this);" class="searchkeywork" value="<%=SystemEnv.getHtmlLabelName(84128,user.getLanguage()) %>" style="vertical-align :middle;font-size:16px;"/>
						</div>
						<div class="keywordsearchbtn" style="position:absolute;left:490px;top:36px;">
							<div  onclick="clearVal(document.getElementById('searchvalue'));searchForm.submit()" id="searchbt" style="cursor:pointer;height:100%;width:100%;">
								<img style="vertical-align:middle;margin-top:10px;position:absolute;left:8px" src="/wui/theme/ecology8/skins/<%=skin %>/page/search_wev8.png"></img>
							</div>
						</div>
					</form>
				</div>
				
				<!--header right-->
				<div class="header-right col-md-2 col-lg-2">
					<div class="header-img col-md-2 col-lg-2">
						<div class="header-img-inner" style="cursor:pointer;" onclick="load()" title="主页"></div>
					</div>
					<div class="header-img col-md-2 col-lg-2">
						<div class="header-img-inner" style="cursor:pointer;" onclick="shoucang()" title="收藏夹"></div>
					</div>
					<div class="header-img col-md-2 col-lg-2">
						<div class="header-img-inner" style="cursor:pointer;" onclick="gengduo()" title="更多"></div>
					</div>
					
				</div>
			</div>
			
			<!--content-->
			<div class="content">
				
				<!--content head-->
				<div class="content-head col-md-12	col-lg-12">

					<div class="content-menu col-md-8 col-lg-8 col-md-offset-2 col-lg-offset-2">
						
						<div class="content-menu-list" id="personnal">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>个人桌面</span>
							</div>
						</div>
						<div class="content-menu-list" id="gwsf">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>公文收发</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>督察督办</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>信息拟报</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>固定资产</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>请假管理</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>车辆派发</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>材料传送</span>
							</div>
						</div>
						<div class="content-menu-list">
							<div class="content-menu-list-img"></div>
							<div class="content-menu-list-text">
								<span>会议管理</span>
							</div>
						</div>
						
					</div>
				</div>
				<div style="height:740px;background-color:#F2F2F2;border:1px solid #F2F2F2">
					<div style="margin:0 auto;margin:2% 8% 3% 8%;background-color:#fff;border:1px solid #fff;height:650px;">
						<div style="margin:0 auto;margin:2% 7% 3% 7%;background-color:#fff;border:1px solid fff;">
							<iframe src="personnal.jsp" id="contentIframe" name="contentIframe" marginheight="0" marginwidth="0" height="610px" onLoad="iFrameHeight()" frameBorder=no></iframe>
						</div>
					</div>
				</div>
				<!--<iframe src="personnal.jsp" id="contentIframe" name="contentIframe" width="1450" height="740" frameBorder=no></iframe>-->
				<!--<div style="width: 1400px;height: 740px;margin: 0 auto;background-color: #F2F2F2;">
					<iframe src="personnal.jsp" id="contentIframe" name="contentIframe" width="1055px" height="610px" frameBorder=no></iframe>
				</div>-->
			</div>
			
			<!--footer-->
			<div class="footer col-md-12 col-lg-12">
				<p>南京市国土资源局</p>
			</div>
			
		</div>
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript">
			
			$(function(){
				
				$("#title-btn-1").bind("click",function(){
					$("#top-box").toggle(1000);
					
				});
				
				$("#title-btn-2").bind("click",function(){
					$("#bottom-box").toggle(1000);
					
				});
				
				$("#personnal").bind("click",function(){
					$("#contentIframe").attr("src","personnal.jsp");
				});
				
				$("#gwsf").bind("click",function(){
					$("#contentIframe").attr("src","/homepage/Homepage.jsp?hpid=1&subCompanyId=42&isfromportal=1&isfromhp=0");
				});

			})
			
			function load(){
				$("#contentIframe").attr("src","personnal.jsp");
			}
			
			function shoucang(){
				var url = "/favourite/MyFavourite.jsp";
				var dialog = new window.top.Dialog();
				dialog.currentWindow = window;
				dialog.Title = "我的收藏";
				dialog.Width = 657;
				dialog.Height = 565;
				dialog.Drag = true;
				dialog.URL = url;
				dialog.show();
			}
			
			function gengduo(){
				
			}
			
			function clearVal(obj){
				if(obj.value=='请输入关键词搜索'){
					obj.value="";
				}
				//jQuery(obj).css("color","#fff");
			}

			function recoverVal(obj){
				if(obj.value==''||obj.value==null){
					obj.value="请输入关键词搜索";
					//jQuery(obj).css("color","#429ce1");
				}
			}
			
			jQuery(document).ready(function() {
			/*jQuery(".tbItm").hover(function() {
				/** by zfh 2011-09-20
				*由于IE用jQuery无法获取background-position的值，支持background-position-y，background-position-x，
				*而Safri，firefox，chrome正好相反，先使用下面方法获取background-position的值
				*//*
				var bp=jQuery(jQuery(this)[0]).css("background-position");
				if(bp===undefined  ){
					jQuery(jQuery(this)[0]).css("background-position-y","-25px");
				}else{
					bp=bp.split(" ","1")[0];
					jQuery(jQuery(this)[0]).css("background-position",bp+"  -25px");
				}
			}, function() {
				var bp=jQuery(jQuery(this)[0]).css("background-position");
				if(bp===undefined  ){
					jQuery(jQuery(this)[0]).css("background-position-y","0px");
				}else{
					bp=bp.split(" ","1")[0];
					jQuery(jQuery(this)[0]).css("background-position",bp+" 0px");
				}
			});*/
			
			jQuery(".dropdown img.flag").addClass("flagvisibility");

			jQuery(".selectTile a").click(function() {
				//hideOtherHoverDiv('search');
				
				jQuery(".selectContent ul").toggle();
				if(jQuery(".selectContent ul").css("display")=="none"){
					jQuery(this).closest("div#sample").removeClass("sampleSelected");
				}else{
					jQuery(this).closest("div#sample").addClass("sampleSelected");
				}
			});
						
			jQuery(".selectContent ul li a").click(function() {
				//获得元素信息
				var sibling = jQuery(this).nextAll();
				if(sibling.length > 0){
					for(var i=0; i<sibling.length;i++){
						if($(sibling[i]).attr("name") == "mainid"){
							$("#mainid").val($(sibling[i]).html());
						}else if($(sibling[i]).attr("name") == "stype"){
							$("#stype").val($(sibling[i]).html());
						}else{
							$("#fieldname").val($(sibling[i]).html());
						}
					}
				}
				var text = jQuery(this).children("span").html();
				jQuery(".selectTile a").children("span").html(text);
				jQuery("input[name='searchtype']").val(jQuery(this).children("span").attr("searchType"));
				jQuery(".selectContent ul").hide();
				jQuery(this).closest("div#sample").removeClass("sampleSelected");
			});
						
			function getSelectedValue(id) {
				return jQuery("#" + id).find("selectContent a span.value").html();
			}

			jQuery(document).bind('click', function(e) {
				var $clicked = jQuery(e.target);
				if (! $clicked.parents().hasClass("dropdown")){
					jQuery(".selectContent ul").hide();
					jQuery(".selectContent ul").closest("div#sample").removeClass("sampleSelected");
				}
			});


			jQuery("#flagSwitcher").click(function() {
				jQuery(".dropdown img.flag").toggleClass("flagvisibility");
			});
			
			jQuery("#searchbt").hover(function() {
				var bp=jQuery(jQuery(this)[0]).css("background-position");
				if(bp===undefined  ){
					//jQuery(jQuery(this)[0]).css("background-position-x","-27px");
				}else{
					bp=bp.split(" ","2")[1];
					//jQuery(jQuery(this)[0]).css("background-position","-27px "+bp);
				}
			}, function() {
				var bp=jQuery(jQuery(this)[0]).css("background-position");
				if(bp===undefined  ){
					jQuery(jQuery(this)[0]).css("background-position-x","-0px");
				}else{
					bp=bp.split(" ","2")[1];
					jQuery(jQuery(this)[0]).css("background-position","-0px "+bp);
				}
			});
			
			//更多按钮
			jQuery(".toolbarMore").bind("click", function() {
				jQuery("#toolbarMore").hide();
			});
			
			jQuery("#toolbarMoreBtn").hover(function(){
			},function(){
				window.setTimeout(function(){
					if(jQuery("#toolbarMoreBtn").data("isOpen")){
						jQuery("#toolbarMore").hide();
						jQuery("#toolbarMoreBtn").removeClass("moreBtnSel");
						jQuery("#toolbarMoreBtn").data("isOpen",false);
					}
				},600);
			});
			
			jQuery("#toolbarMore").hover(function() {
				jQuery("#toolbarMoreBtn").data("isOpen",false);
			}, function() {
				jQuery("#toolbarMore").hide();
				jQuery("#toolbarMoreBtn").removeClass("moreBtnSel");
				jQuery("#toolbarMoreBtn").data("isOpen",false);
			});
			
			jQuery(".moreBtn_option").hover(function(){
				jQuery(this).addClass("moreBtn_optionSelect");
			 },function(){
				jQuery(this).removeClass("moreBtn_optionSelect");
			 });
			
			//为了兼容ie6，收藏夹iframe动态载入
			//jQuery("#navFav").css("position", "relative");
			//jQuery("#navFav").load("/wui/theme/ecology8/page/FavouriteShortCut.jsp")
				/**页面加载时不加载菜单，改为点击时才加载，以保证每次打开都是最新的收藏夹快捷菜单*/
			jQuery("td.fav").css("cursor","pointer").bind("click",function(){
				/*
				 var subMenus = $mt("subMenusContainer");  //下拉菜单
				 if(subMenus)   //已存在，先销毁
				 {
					 subMenus.destroy();
				 }
				 jQuery("#navFav").load("/wui/theme/ecology8/page/FavouriteShortCut.jsp?t=" + Math.random(),function(){
					 if(myMenu)
					 {
						 $mt(myMenu.options.subMenusContainerId).getFirst().fireEvent('show');  //默认展开第一级菜单
					 }	
				 })*/
				 var url = "/favourite/MyFavourite.jsp";
				var dialog = new window.top.Dialog();
				dialog.currentWindow = window;
				dialog.Title = "我的收藏";
				dialog.Width = 657;
				dialog.Height = 565;
				dialog.Drag = true;
				dialog.URL = url;
				dialog.show();
			});
			//ie6下下拉菜单被select遮盖
			//jQuery("#searchBlockUl iframe").css("height", jQuery("#searchBlockUl").height());
			//console.log(jQuery("#searchBlockUl").height());
			jQuery("#searchBlockUl").hover(function () {
			}, function () {
				jQuery("#searchBlockUl").hide();
				jQuery("#searchBlockUl").closest("div#sample").removeClass("sampleSelected");
			});
			
			jQuery("#searchBlockUl li a").hover(function(){
				//var src = jQuery(this).find("img").attr("src");
				//src = src.replace(/\_wev8.png$/,"_sel_wev8.png");
				//jQuery(this).find("img").attr("src",src);
			},function(){
				//var src = jQuery(this).find("img").attr("src");
				//src = src.replace(/_sel_wev8\.png$/,"_wev8.png");
				//jQuery(this).find("img").attr("src",src);
			});
			
			jQuery(".selectTile").hover(function () {
			}, function () {
				var clientx = event.clientX;
				var clienty = event.clientY;
				
				var elex = jQuery(this).offset().left;
				var eley = jQuery(this).offset().top;
			
				if (clientx < elex || elex > jQuery(this).offset().right || clienty < eley) {
					jQuery("#searchBlockUl").hide();
					jQuery("#searchBlockUl").closest("div#sample").removeClass("sampleSelected");
				} else {
					return;
				}
			});
			
		});
		function iFrameHeight() {   
			var wid = window.screen.width;
			jQuery("#contentIframe").width(wid*0.72);
		} 
		</script>
	</body>
	
</html>