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
<jsp:useBean id="baseBean" class="weaver.general.BaseBean" scope="page" />
<jsp:useBean id="MouldStatusCominfo" class="weaver.systeminfo.MouldStatusCominfo" scope="page" />
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
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
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script src="js/jquery-migrate-1.2.1.js"></script>
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<script type="text/javascript"src="/js/ecology8/jNice/jNice/jquery.jNice_wev8.js"></script>
		<link rel="stylesheet"href="/js/ecology8/jNice/jNice/jNice_wev8.css"type="text/css"/>
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
				font-family:微软雅黑;
			}
			html,body{
				
			}
			iframe{
				outline: none;
				border: none;
			}
			.box{
				
			}
			
			/*header*/
			.box .header{
				height: 1rem;
				background-image: url(img/header-bg.png);
				background-size: cover;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/header-bg.png',sizingMethod='scale'); ;
			}
			.box .header .header-left,
			.box .header .header-right,
			.box .header .header-center{
				height: 1rem;
				line-height: 1rem;
				display: inline-block;
			}
			.box .header .header-left{
				padding-left: 2.25rem;
			}
			.box .header .header-left p{
				height: 1rem;
				line-height: 1rem;
				color: #FFFFFF;
				height: 1rem;
			    line-height: 1rem;
			    color: #FFFFFF;
			    font-size: 0.30rem;
			    font-weight: bold;
			    position: relative;
			    padding-left: 0.9rem;
				
			}
			.box .header .header-left p i{
				display: inline-block;
				height: 0.86rem;
				width: 0.86rem;
				top: 50%;
				margin-top: -0.43rem;
				position: absolute;
				background-image: url(img/header-logo.png);
				left: 0rem;
			}
			.box .header .header-center{
				
			}
			.box .header .header-right{
				
			}
			
			.box .header .header-right .header-img{
				height: 1rem;
				line-height: 1.20rem;
				text-align: center;
			}
			.box .header .header-right .header-img .header-img-inner{
				height: 0.34rem;
				width: 0.44rem;
				display: inline-block;
			}
			
			.box .header .header-right .header-img:first-child .header-img-inner{
				background-image: url(img/header-img1.png);
			}
			.box .header .header-right .header-img:first-child+div .header-img-inner{
				background-image: url(img/header-img2.png);
				border-left: 0.02rem solid #FFF;
    			border-right: 0.02rem solid #FFF;
			}
			.box .header .header-right .header-img:first-child+div+div .header-img-inner{
				background-image: url(img/header-img3.png);
			}
			
			/*content*/
			.box .content{
				
			}
			.box .content .content-head{
				height: 1rem;
				line-height: 1rem;
				background-color: #FFFFFF;
				
			}
			.box .content .content-head .content-menu{
				height: 1rem;
				line-height: 1rem;
				padding-left: 0rem;
				padding-right: 0rem;
			}
			.box .content .content-head .content-menu .content-menu-list{
			    height: 1rem;
			    line-height: 1rem;
			    display: inline-block;
			    width: 11.11%;
			    padding: 0;
			    margin: 0;
			    float: left;
		        cursor: pointer;
			}
			
			.box .content .content-head .content-menu .content-menu-list .content-menu-list-img{
				height: 0.56rem;
				background-size: 30% 70%;
			}
			
			.box .content .content-head .content-menu .content-menu-list .content-menu-list-text{
				height: 0.42rem;
				line-height: 0.42rem;
				text-align: center;
			}
			
			.box .content .content-head .content-menu .content-menu-list .content-menu-list-text span{
				text-align: center;
				color: #61C3F2;
				font-size: 0.24rem;
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
				background-color: #F2F2F2;
			}
			.box .content .content-body .content-inner{
				background-color: #FFFFFF;
			    height: 7rem;
			    padding: 0.60rem 0.78rem 0.26rem 0.78rem;
			}
			.box .content .content-body .content-inner .inner-box{
				height: 6.12rem;
				width: 100%;
			}
			.box .content .content-body .content-inner .content-inner-left,
			.box .content .content-body .content-inner .content-inner-right{
				height: 7.7rem;
			}
			.box .content .content-body .content-inner .content-inner-left{
				
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-title{
				height: 0.90rem;
				line-height: 0.90rem;
				background-color: #3966A7;
				text-align: center;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-title h3{
				color: #FFFFFF;
				position: relative;
				height: 0.80rem;
				line-height: 0.80rem;
				font-size: 0.36rem;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-title h3 i{
				width: 0.35rem;
			    height: 0.35rem;
			    background-image: url(img/left-title.png);
			    position: absolute;
			    top: 50%;
			    margin-top: -0.175rem;
			    left: 0.15rem;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body{
				
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list{
				height: 1.36rem;
				line-height: 1.36rem;
				/**background-color: #F3F3F3;**/
				background-color: #FFF;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner .top-img{
				background-size: 30% 65%;
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
				height: 1.36rem;
				line-height: 1.36rem;
				border: 0.01rem solid #E5E5E5;
				cursor: pointer;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner:hover{
				background-color:#F2F2F2;
				font-weight: bold;
			}
			/** **/
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:nth-child(1) .content-inner-left-body-list-inner:nth-child(1):hover .top-img {
				/** background-image: url(img/left-2.png) !important;**/
				transition: all linear 0.1s;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list:nth-child(1) .content-inner-left-body-list-inner:nth-child(2):hover .top-img {
				/** background-image: url(img/left-3.png) !important;**/
				transition: all linear 0.1s;
			}
			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner .top-img{
				height: 0.70rem;
				line-height: 0.70rem;
			}
			/** **/

			.box .content .content-body .content-inner .content-inner-left .content-inner-left-body .content-inner-left-body-list .content-inner-left-body-list-inner .bottom-text{
				height: 0.45rem;
				line-height: 0.45rem;
				text-align: center;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
			
			.box .content .content-body .content-inner .content-inner-right{
				
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top{
				border: 0.01rem solid #E5E5E5;
			    margin-bottom: 0.15rem;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom{
				border: 0.01rem solid #E5E5E5;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p{
				height: 0.9rem;
				line-height: 0.9rem;
				font-size: 0.25rem;
				color: #191919;
				position: relative;
				padding-left: 0.60rem;
				border-bottom: 0.01rem dashed #E5E5E5;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-img1 {
				height: 0.50rem;
			    line-height:  0.50rem;
			   /* background-color: red;*/
			    display: inline-block;
			    width:  0.50rem;
			    position: absolute;
			    top: 50%;
			    margin-top: -0.25rem;
			    left: 0rem;
		        cursor: pointer;
			    /**background-size: 50% 50%;**/
			    background-image: url(img/img_title1.png);
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-btn-title1 {
				height: 0.50rem;
			    line-height:  0.50rem;
			   /* background-color: red;*/
			    display: inline-block;
			    width:  0.50rem;
			    position: absolute;
			    top: 50%;
			    margin-top: -0.25rem;
			    left: 0rem;
		        cursor: pointer;
			    /**background-size: 50% 50%;**/
			    background-image: url(img/img_down.png);
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-btn-title1 {
				height: 0.50rem;
			    line-height:  0.50rem;
			   /* background-color: red;*/
			    display: inline-block;
			    width:  0.50rem;
			    position: absolute;
			    top: 50%;
			    margin-top: -0.25rem;
			    left: 0rem;
		        cursor: pointer;
			    /**background-size: 50% 50%;**/
			    background-image: url(img/img_down.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-img,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-img,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-btn,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-btn{
			    height: 0.50rem;
			    line-height:  0.50rem;
			   /* background-color: red;*/
			    display: inline-block;
			    width:  0.50rem;
			    position: absolute;
			    top: 50%;
			    margin-top: -0.25rem;
			    left: 0rem;
		        cursor: pointer;
			    /**background-size: 50% 50%;**/
			    background-image: url(img/img_title1.png);
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-right-title p .title-btn-title1,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-right-title p .title-btn-title1{
				left: 93%;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box{
				height: 2.4rem;
				padding: 0 0.6rem;
				/**overflow-y: scroll;**/
				overflow:hidden;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model,
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model{
				height: 1.08rem;
			    padding: 0.10rem 0.20rem;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box{
				height: 0.88rem;
    			width: 100%;
				cursor:pointer;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(1) .border-box{
				background-color: #DAA6E3;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(1) .border-box:hover{
				background-color: #C987DD;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(2) .border-box{
				background-color: #88C896;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(2) .border-box:hover{
				background-color: #77B583;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(3) .border-box{
				background-color: #F2A6A3;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(3) .border-box:hover{
				background-color: #ED8992;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(4) .border-box{
				background-color: #8B96CA;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(4) .border-box:hover{
				background-color: #7878BE;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(5) .border-box{
				background-color: #65D0BC;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(5) .border-box:hover{
				background-color: #56BFAE;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(6) .border-box{
				background-color: #F387BC;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(6) .border-box:hover{
				background-color: #ED64AF;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(7) .border-box{
				background-color: #B1D365;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(7) .border-box:hover{
				background-color: #A3C352;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(8) .border-box{
				background-color: #51A1B9;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(8) .border-box:hover{
				background-color: #42868D;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(9) .border-box{
				background-color: #F29A75;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(9) .border-box:hover{
				background-color: #EC7D61;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(10) .border-box{
				background-color: #1b96d3;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model:nth-child(10) .border-box:hover{
				background-color: #3965a7;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .left-border{
				height: 0.88rem;
				background-size: 50%;
				padding-right: 0px;
				background-position: right;
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
				height: 0.44rem;
			    line-height: 0.44rem;
			    color: #FFF;
			    text-align: center;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
			}
			
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box{
				height: 3.3rem;
				padding: 0 0.6rem;
				/**overflow-y: scroll;**/
				overflow:hidden;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .left-border{
				height: 0.88rem;
				background-size: 50%;
				padding-right: 0px;
				background-position: right;
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
				height: 0.88rem;
			    
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border p{
				height: 0.44rem;
			    line-height: 0.44rem;
			    color: #FFF;
			    text-align: center;
				overflow:hidden;
				white-space:nowrap;
				text-overflow:ellipsis;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .right-border  p:nth-child(1),
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border p:nth-child(1){
				font-size: 0.25rem;
				cursor: pointer;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-bottom .content-inner-bottom-box .content-inner-right-model .border-box .right-border  p:nth-child(2),
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .right-border p:nth-child(2){
				font-size: 0.26rem;
			
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model{
			    padding: 0.10rem  0.20rem;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model .border-box{
				height: 0.88rem;
				width:100%;
				cursor:pointer;
			}
			
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(1) .border-box{
				background-color: #6FC07C;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(1) .border-box:hover{
				background-color: #609669;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(2) .border-box{
				background-color: #ECC941;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(2) .border-box:hover{
				background-color: #E6AA33;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(3) .border-box{
				background-color: #F2A6A3;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(3) .border-box:hover{
				background-color: #ED8292;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(4) .border-box{
				background-color: #F5AA6D;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(4) .border-box:hover{
				background-color: #F0905A;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(5) .border-box{
				background-color: #65D0BC;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(5) .border-box:hover{
				background-color: #56BFAE;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(6) .border-box{
				background-color: #D37371;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(6) .border-box:hover{
				background-color: #C7525D;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(7) .border-box{
				background-color: #D37371;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(7) .border-box:hover{
				background-color: #C7525D;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(8) .border-box{
				background-color: #D37371;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(8) .border-box:hover{
				background-color: #C7525D;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(9) .border-box{
				background-color: #D37371;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(9) .border-box:hover{
				background-color: #C7525D;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(10) .border-box{
				background-color: #D37371;
			}
			.box .content .content-body .content-inner .content-inner-right .content-inner-right-top .content-inner-top-box .content-inner-right-model:nth-child(10) .border-box:hover{
				background-color: #C7525D;
			}
			
			
			/*footer*/
			.box .footer{
				height: 0.50rem;
				line-height: 0.50rem;
				background-color: #434448;
				text-align: center;
				color: #F0F0F0;
			}
			
			/*重写bootstrap的样式*/
			.col-md-12,
			.col-lg-12{
				padding-left:0rem;
				padding-right: 0rem;
			}
			.col-md-3,
			.col-lg-3,
			.col-md-3,
			.col-lg-2{
				padding-left: 0rem;
				padding-right: 0rem;
			}
			.dropdown {
				padding-left: 0.05rem;
				font-size: 0.11rem;
				color: #000;
				height: 0.35rem;
				border-left: 0.01rem solid transparent;
				border-top: 0.01rem solid transparent;
				border-right: 0.01rem solid transparent;
			}
			.selectTile {
				margin: 0rem;
				padding: 0rem;
			}
			.selectTile a {
				background: none;
				display: block;
				width: 0.40rem;
				margin-left: 0.04rem;
				margin-top: 0.01rem;
			}
			span.searchTxt {
				height: 0.35rem;
				line-height: 0.28rem;
				float: left;
				width: 0.32rem;
				display: block;
				overflow: hidden;
				text-overflow: ellipsis;
				color: #656565;
				padding: 0;
			}
			.selectTile a span {
				cursor: pointer;
				display: block;
				padding: 6 0 0 0;
				background: none;
				height: 0.25rem;
				font-size: 0.14rem;
			}
			.dropdown .e8_dropdown {
				background-image: url(img/down.png);
				width: 0.12rem;
				height: 0.12rem;
				float: left;
				margin-top: 0.20rem;
				background-position: 50% 50%;
			}
			.searchTxtSplit {
				color: #078BF3;
				font-size: 0.18rem;
			}
			.selectContent {
				position: relative;
				z-index: 6;
			}
			.selectContent ul{
				background: #fff none repeat scroll 0 0;
				border-left: 0.01rem solid #d0d0d0;
				border-right: 0.01rem solid #d0d0d0;
				border-bottom: 0.01rem solid #d0d0d0;
				color: #C5C0B0;
				display: none;
				left: -0.07rem;
				position: absolute;
				top: 0.23rem;
				min-width: 0.60rem;
				list-style: none;
				font-size:0.18rem;
			}
			.selectContent ul li{
				list-style: none;!important;
				height: 0.25rem;
				color: #656565;
				margin:0.1rem;
				
			}
			.selectContent ul li a {
				padding: 0rem;
				display: block;
				margin: 0;
				height: 0.25rem;
				line-height: 0.21rem;
				text-align:center;
				/**font-size: 14pt;**/
			}
			.dropdown a, .dropdown a:visited{
				color: #666666;
				text-decoration: none;
				outline: none;
			}
			.searchkeywork{
				width: 1.8rem;
				font-size: 0.12rem;
				height: 0.32rem !important;
				background-color: transparent;
				border: none !important;
				line-height: 0.32rem;
				color: #FFFFFF;
			}
			.searchkeyworkbtn{
				float: right;
				background-color: #0782e4;
				height: 0.35rem;
				width: 0.30rem;
			}
		</style>
	</head>
	<body style="overflow: auto;">
		
		
		<div class="box col-md-12	col-lg-12 col-xs-12	col-sm-12">
			<!--content-->
			<div class="content">
				<!--content body-->
				<div class="content-body col-md-12	col-lg-12 col-xs-12	col-sm-12">
					
					<div class="content-inner" style="padding:0;margin:0">
						
						<div class="inner-box">
							
							<!-- left-- 常用流程-->
							<div class="content-inner-left col-md-3 col-lg-3 col-xs-3	col-sm-3">
								
								<!--标题-->
								<div class="content-inner-left-title">
									<h3><i></i>常用新建流程</h3>
								</div>
								
								
								
								<!--list-->
								<div class="content-inner-left-body col-md-12	col-lg-12 col-xs-12	col-sm-12">
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12 col-xs-12	col-sm-12">
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="gwbl">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公文收文办理</span>
											</div>
										</div>
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="hytzsw">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>会议通知收文</span>
											</div>
										</div>
										
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12 col-xs-12	col-sm-12">
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="gwcysw">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公文传阅收文</span>
											</div>
										</div>
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="gwnb">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公文拟报</span>
											</div>
										</div>
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12 col-xs-12	col-sm-12">
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="xxnb">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>信息拟报</span>
											</div>
										</div>
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="zfcgsb">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>政府采购申报</span>
											</div>
										</div>
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12 col-xs-12	col-sm-12">
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="bxsplc">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>报销审核流程</span>
											</div>
										</div>
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="gwccsp">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公务出差审批</span>
											</div>
										</div>
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12 col-xs-12	col-sm-12">
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="ysqjsp">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>因私请假审批</span>
											</div>
											
										</div>
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6 col-xs-6	col-sm-6" id="clpf">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>车辆派发</span>
											</div>
										</div>
									</div>
								</div>
								
								
							</div>
							
							
							<!--right-->
							<div class="content-inner-right col-md-9 col-lg-9 col-xs-9	col-sm-9">
								
								
								<!--公文办理代办-->
								<div class="content-inner-right-top">
									<div class="content-inner-right-title col-md-12	col-lg-12 col-xs-12	col-sm-12" style="padding: 0 0.4rem;">
										<p>
											<i class="title-img1"></i>
											<span style="margin-left: 0.1rem; font-size: 0.35rem;">公文办理待办&nbsp;&nbsp;<font style="color:#61C3F2;font-size:0.29rem;">(仅显示新版公文流程信息)</font></span>
											<i class="title-btn-title1" id="title-btn-1" data="1"></i>
										</p>
									</div>
									<div class="content-inner-top-box col-md-12	col-lg-12 col-xs-12	col-sm-12" id="top-box">
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gwswbl_right" title="公文收文办理">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gwnb.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8" >
													<p id="gwswbl_right_p" data="61,62"></p>
													<p>公文收文办理</p><!-- 原公文拟报-->
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="hytzsw_right" title="会议通知收文">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gwblsf.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="hytzsw_right_p" data="201,202"></p>
													<p>会议通知收文</p><!-- 原公文收文办理-->
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gwcysw_right" title="公文传阅收文">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/hytz.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="gwcysw_right_p" data="203"></p>
													<p>公文传阅收文</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gwnb_right" title="公文拟报">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gwcy.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="gwnb_right_p" data="59,60|-161"></p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="dcdb_right" title="督查督办">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/dcdb.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="dcdb_right_p" data="63,81"></p>
													<p>督查督办</p>
												</div>
											</div>
										</div>
										<!--<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="xxnb_right">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/xxnb.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="xxnb_right_p" data="">5</p>
													<p>信息拟报</p>
												</div>
											</div>
										</div>	-->
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="jnfwcy_right">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/xxnb.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="jnfwcy_right_p" data="204"></p>
													<p>局内发文传阅</p>
												</div>
											</div>
										</div>					
									</div>
								</div>
								
								<!--机关事务代办-->
								<div class="content-inner-right-bottom">
									
									<div class="content-inner-right-title col-md-12	col-lg-12 col-xs-12	col-sm-12"  style="padding: 0 0.4rem;">
										<p>
											<i class="title-img"></i>
											<span style="margin-left: 0.1rem; font-size: 0.35rem;">机关事务待办&nbsp;&nbsp;<font style="color:#61C3F2;font-size:0.29rem;">(仅显示新版公文流程信息)</font></span>
											<i class="title-btn-title1" id="title-btn-2" data="1"></i>
										</p>
									</div>
									
									<div class="content-inner-bottom-box col-md-12	col-lg-12 col-xs-12	col-sm-12" id="bottom-box">
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="xxnb_right" title="信息拟报">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/zfcgsb.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="xxnb_right_p" data="181,221"></p>
													<p>信息拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="zfcgsb_right" title="政府采购申报">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gdzcbf.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="zfcgsb_right_p" data="205"></p>
													<p>政府采购申报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="bxsplc_right" title="报销审核流程">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/zcly.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="bxsplc_right_p" data="222"></p>
													<p>报销审核流程</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gdzcdj_right" title="固定资产登记">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gwcc.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="gdzcdj_right_p" data="241"></p>
													<p>固定资产登记</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gwccsp_right" title="公务出差审批">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/yglgwc.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="gwccsp_right_p" data="103"></p>
													<p>公务出差审批</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="yglgwc_right" title="因公离岗外出">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/yglgwc.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="yglgwc_right_p" data="122"></p>
													<p>因公离岗外出</p>
												</div>
											</div>
										</div>		
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="ysqjsp_right" title="因私请假审批">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gwccfj.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="ysqjsp_right_p" data="123"></p>
													<p>因私请假审批</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gwccsp_fj_right" title="公务出差审批(分局)">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/yglgwc.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="gwccsp_fj_right_p" data="141"></p>
													<p>公务出差审批(分局)</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="ysqjsp_fj_right" title="因私请假流程(分局)">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/gwccfj.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="ysqjsp_fj_right_p" data="143"></p>
													<p>因私请假流程(分局)</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="clpf_right" title="车辆派发">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/ysqjsp.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="clpf_right_p" data="161"></p>
													<p>车辆派发</p>
												</div>
											</div>
										</div>
										<!--
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="gdzcbf_right" title="固定资产报废">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/clpf.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="gdzcbf_right_p" data=""></p>
													<p>固定资产报废</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3 col-xs-3	col-sm-3" id="zcly_right" title="资产领用">
											<div class="border-box" >
												<div class="left-border col-md-4 col-lg-4 col-xs-4	col-sm-4" style="background-image: url(img/hysq.png);"></div>
												<div class="right-border col-md-8 col-lg-8 col-xs-8	col-sm-8">
													<p id="zcly_right_p" data=""></p>
													<p>资产领用</p>
												</div>
											</div>
										</div>	
							                -->
									</div>
								</div>
								
							</div>
							
						</div>
						
					</div>
					
				</div>
			</div>
		</div>
		<script type="text/javascript">
			
			$(function(){
				
				
				
				$("#title-btn-1").bind("click",function(){
					$("#top-box").toggle(1000);
					var data = $(this).attr("data");
					if(data == "1") {
						$(this).css("background-image","url(\"img/img_up.png\")");
						$(this).attr("data","0");
					} else {
						$(this).css("background-image","url(\"img/img_down.png\")");
						$(this).attr("data","1");
					}
				});
				
				$("#title-btn-2").bind("click",function(){
					$("#bottom-box").toggle(1000);
					var data = $(this).attr("data");
					if(data == "1") {
						$(this).css("background-image","url(\"img/img_up.png\")");
						$(this).attr("data","0");
					} else {
						$(this).css("background-image","url(\"img/img_down.png\")");
						$(this).attr("data","1");
					}
				});

				jQuery('#top-box').perfectScrollbar();
				jQuery('#bottom-box').perfectScrollbar();
				
				
				$("#gwnb").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=59&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				
				$("#gwbl").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=61&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#hytzsw").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=201&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#gwcysw").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=203&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#xxnb").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=181&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#zfcgsb").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=205&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#bxsplc").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=222&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#gwccsp").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=103&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#ysqjsp").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=123&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				$("#clpf").bind("click",function(){
					window.open("/workflow/request/AddRequest.jsp?workflowid=161&isagent=0&beagenter=0&f_weaver_belongto_userid=","_blank");
				});
				
				
				$("#gwswbl_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=28&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#hytzsw_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=29&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#gwcysw_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=30&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#gwnb_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=27&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#dcdb_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=31&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
		
				$("#gwccsp_fj_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=101&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				$("#ysqjsp_fj_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=102&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				$("#jnfwcy_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=41&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				$("#xxnb_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=32&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#zfcgsb_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=33&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#bxsplc_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=34&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#gdzcdj_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=35&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#gwccsp_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=36&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#ysqjsp_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=38&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#clpf_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=39&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#gdzcbf_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=81&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#zcly_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=82&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				$("#yglgwc_right").bind("click",function(){
					parent.document.getElementById("contentIframe").src = "/homepage/Homepage.jsp?hpid=37&subCompanyId=1&isfromportal=1&isfromhp=0";
				});
				
				loadDataFromWorkflow(jQuery("#gwswbl_right_p").attr("data"),"gwswbl_right_p");
				loadDataFromWorkflow(jQuery("#hytzsw_right_p").attr("data"),"hytzsw_right_p");
				loadDataFromWorkflow(jQuery("#gwcysw_right_p").attr("data"),"gwcysw_right_p");
				loadDataFromWorkflow(jQuery("#gwnb_right_p").attr("data"),"gwnb_right_p");
				loadDataFromWorkflow(jQuery("#dcdb_right_p").attr("data"),"dcdb_right_p");
				loadDataFromWorkflow(jQuery("#xxnb_right_p").attr("data"),"xxnb_right_p");
				loadDataFromWorkflow(jQuery("#zfcgsb_right_p").attr("data"),"zfcgsb_right_p");
				loadDataFromWorkflow(jQuery("#bxsplc_right_p").attr("data"),"bxsplc_right_p");
				loadDataFromWorkflow(jQuery("#gdzcdj_right_p").attr("data"),"gdzcdj_right_p");
				loadDataFromWorkflow(jQuery("#gwccsp_right_p").attr("data"),"gwccsp_right_p");
				loadDataFromWorkflow(jQuery("#ysqjsp_right_p").attr("data"),"ysqjsp_right_p");
				loadDataFromWorkflow(jQuery("#clpf_right_p").attr("data"),"clpf_right_p");
				loadDataFromWorkflow(jQuery("#yglgwc_right_p").attr("data"),"yglgwc_right_p");
				loadDataFromWorkflow(jQuery("#jnfwcy_right_p").attr("data"),"jnfwcy_right_p");
				loadDataFromWorkflow(jQuery("#gwccsp_fj_right_p").attr("data"),"gwccsp_fj_right_p");
				loadDataFromWorkflow(jQuery("#ysqjsp_fj_right_p").attr("data"),"ysqjsp_fj_right_p");
			})
			function loadDataFromWorkflow(obj,p_id){
				$.ajax({
					url:'/interface/portal/wfcount.jsp',
					data:{
						workflowid:obj
					},
					type:'post',
					cache:false,
					success:function(data){
						data = jQuery.parseJSON(data);
						jQuery("#"+p_id).text(data.count);
					}
				})
			}
		</script>
		
	</body>
</html>
