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
	asset.add("p_r");
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>PURCHASE ORDER</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<!-- START OF THE CONTENT -->
					<div class="container-fluid">
						<form class="form-room" >
							<div>
								<div class="row form-group">
									<div class="col-sm-3"><input type="date" class="form-control" id="" placeholder=""></div>
									<div class="col-sm-3"><input type="date" class="form-control" id="" placeholder=""></div>
									<div class="col-sm-2">
										<select id="">
											<option value="submitted">Submitted</option>
											<option value="approved">Approved</option>
											<option value="rejected">Rejected</option>
										</select>
									</div>
									<div class="col-sm-2"><input type="text" class="form-control" id="" placeholder="Search"></div>
									<div class="col-sm-2"><button type="button" class="btn btn-primary" id="btn-export">Export</button></div>
								</div>
							</div>
						</form>
					</div>
					
					<div>
						<table id="employee-table" class="table">
							<thead>
								<tr>
									<th>Create Date</th>
									<th>Supplier</th>
									<th>PO No.</th>
									<th>Total</th>
									<th>Status</th>
									<th>#</th>
								</tr>
							</thead>
							
							<tbody>				
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
							
						</table>
					</div>
					<!-- END OF THE CONTENT -->
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