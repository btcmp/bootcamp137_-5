<%@page import="com.miniproject.pos.utils.Menu"%>
<%@page import="com.miniproject.pos.utils.Breadcrumb"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="${baseUrl }assets/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><security:authentication property="principal.username" /> - <%= namaOutlet %></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">MAIN NAVIGATION</li>
        <%
        String currentUrl = request.getAttribute("javax.servlet.forward.request_uri").toString();
        String[][] menu = {
        		{"home", "fa fa-dashboard", "Dashboard", "ADMIN,BACK_OFFICE"},
        		{"kategori/index", "fa fa-dashboard", "Kategori", "ADMIN"},
        		{"outlet/index","fa fa-dashboard", "Outlet", "ADMIN"},
        		{"items/index","fa fa-dashboard", "Items", "ADMIN"},
        		{"adjustment/index","fa fa-dashboard", "Adjustment", "BACK_OFFICE"},
        		{"transfer-stock/index","fa fa-dashboard", "Transfer Stock", "BACK_OFFICE"},
        		};	
        Menu mn = new Menu((String) pageContext.getAttribute("baseUrl"),currentUrl, menu);
        out.print(mn.renderMenu());
        %>
		</ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        ${title}
      </h1>
      ${bc.data}
    </section>