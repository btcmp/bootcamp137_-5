<%@page import="com.miniproject.pos.model.ItemVariant"%>
<%@page import="com.miniproject.pos.model.Items"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();
	asset.add("summary");
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Summary</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="items-list"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Name - Variant</th>
								<th>Category</th>
								<th>Beginning</th>
								<th>Purchase Order</th>
								<th>Sales</th>
								<th>Transfer</th>
								<th>Adjustment</th>
								<th>Ending</th>
							</tr>
						</thead>

					</table>

				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</section>

<%@ include file="/WEB-INF/views/template/footer.jsp"%>