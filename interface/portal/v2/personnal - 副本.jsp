<%@ include file="/systeminfo/init_wev8.jsp" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
			    padding: 30px 20px 30px 30px;
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
				background-size: 50%;
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
		</style>
	</head>
	<body style="overflow: hidden;">
		
		
		<div class="box col-md-12	col-lg-12">
			<!--content-->
			<div class="content">
				<!--content body-->
				<div class="content-body col-md-12	col-lg-12">
					
					<div class="content-inner col-md-10 col-lg-10 col-md-offset-1 col-lg-offset-1">
						
						<div class="inner-box">
							
							<!-- left-- 常用流程-->
							<div class="content-inner-left col-md-3 col-lg-3">
								
								<!--标题-->
								<div class="content-inner-left-title">
									<h3><i></i>常用流程</h3>
								</div>
								
								
								
								<!--list-->
								<div class="content-inner-left-body col-md-12	col-lg-12">
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12">
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>信息拟报</span>
											</div>
										</div>
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公文拟报</span>
											</div>
										</div>
										
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12">
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>固定资产报废</span>
											</div>
										</div>
										
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>政府采购申报</span>
											</div>
										</div>
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12">
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公文传阅</span>
											</div>
										</div>
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>公务出差审批</span>
											</div>
										</div>
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12">
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>因私请假审批</span>
											</div>
										</div>
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>资产领用流程</span>
											</div>
										</div>
									</div>
									
									<div class="content-inner-left-body-list col-md-12	col-lg-12">
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>车辆派发</span>
											</div>
											
										</div>
										<div class="content-inner-left-body-list-inner col-md-6	col-lg-6">
											<div class="top-img"></div>
											<div class="bottom-text">
												<span>会议通知</span>
											</div>
										</div>
									</div>
								</div>
								
								
							</div>
							
							
							<!--right-->
							<div class="content-inner-right col-md-9 col-lg-9">
								
								
								<!--公文办理代办-->
								<div class="content-inner-right-top">
									<div class="content-inner-right-title col-md-12	col-lg-12">
										<p>
											<i class="title-img"></i>
											<span>公文办理代办</span>
											<i class="title-btn" id="title-btn-1"></i>
										</p>
									</div>
									<div class="content-inner-top-box col-md-12	col-lg-12" id="top-box">
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8" >
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								
								<!--机关事务代办-->
								<div class="content-inner-right-bottom">
									
									<div class="content-inner-right-title col-md-12	col-lg-12">
										<p>
											<i class="title-img"></i>
											<span>机关事务待办</span>
											<i class="title-btn" id="title-btn-2"></i>
										</p>
									</div>
									
									<div class="content-inner-bottom-box col-md-12	col-lg-12" id="bottom-box">
										<div class="content-inner-right-model col-md-3	col-lg-3">
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
										<div class="content-inner-right-model col-md-3	col-lg-3">
											
											<div class="border-box" style="background-color: #DAA6E3;">
												<div class="left-border col-md-4 col-lg-4" style="background-image: url(img/head-menu-4.png);"></div>
												<div class="right-border col-md-8 col-lg-8">
													<p>5</p>
													<p>公文拟报</p>
												</div>
											</div>
											
										</div>
																				
									</div>
									
									
								</div>
								
							</div>
							
						</div>
						
					</div>
					
				</div>
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
				
			})
			
		</script>
		
	</body>
</html>
