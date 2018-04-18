<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}/"></c:set>
<% 
String namaOutlet = session.getAttribute("outletName").toString();
request.setAttribute("namaOutlet", namaOutlet);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Simple POS | ${title} </title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/font-awesome/css/font-awesome.min.css">

	 <!-- Ionicons -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/ionicons/dist/css/ionicons.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/datatables.net-bs/css/dataTables.bootstrap.css">

<link rel="stylesheet" href="${baseUrl }assets/node_modules/easy-autocomplete/dist/easy-autocomplete.min.css">
<link rel="stylesheet" href="${baseUrl }assets/node_modules/easy-autocomplete/dist/easy-autocomplete.themes.min.css">
<link rel="stylesheet" href="${baseUrl }assets/node_modules/daterangepicker/daterangepicker.css">
<link rel="stylesheet" href="${baseUrl }assets/node_modules/bootstrap-fileinput/css/fileinput.min.css">
<link rel="stylesheet" href="${baseUrl }assets/node_modules/highcharts/css/highcharts.css">
<style type="text/css">
input.parsley-success,
select.parsley-success,
textarea.parsley-success {
  color: #468847;
  background-color: #DFF0D8;
  border: 1px solid #D6E9C6;
}

input.parsley-error,
select.parsley-error,
textarea.parsley-error {
  color: #B94A48;
  background-color: #F2DEDE;
  border: 1px solid #EED3D7;
}

.parsley-errors-list {
  margin: 2px 0 3px;
  padding: 0;
  list-style-type: none;
  font-size: 0.9em;
  line-height: 0.9em;
  opacity: 0;

  transition: all .3s ease-in;
  -o-transition: all .3s ease-in;
  -moz-transition: all .3s ease-in;
  -webkit-transition: all .3s ease-in;
}

.parsley-errors-list.filled {
  opacity: 1;
}
.easy-autocomplete{
  width:100% !important
}

.easy-autocomplete input{
  width: 100%;
}

.form-wrapper{
  width: 500px;
}
</style>
  <!-- Theme style -->
  <link rel="stylesheet" href="${baseUrl }assets/dist/css/AdminLTE.min.css">
 
  <link rel="stylesheet" href="${baseUrl }assets/dist/css/skins/_all-skins.min.css">
 
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
<script type="text/javascript">
 var baseUrl = "${baseUrl}";
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini fixed">
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>PO</b>S</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Simple</b>POS</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
          
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="${baseUrl }assets/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><security:authentication property="principal.username" /> - <%= namaOutlet %></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="${baseUrl }assets/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                <p>
                 <security:authorize access="isAuthenticated()">
		<security:authentication property="principal.username" /> - <security:authentication property="principal.authorities" />
	</security:authorize>
                  <small>Member since Nov. 2012</small>
                </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  
                </div>
                <div class="pull-right">
                <form action="${baseUrl}logout" method="post" id="logoutForm">
  <input type="hidden"
	name="${_csrf.parameterName}"
	value="${_csrf.token}" />
	<button type="submit" name="logout" class="btn btn-default btn-flat">Sign out</button>
</form>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  </header>