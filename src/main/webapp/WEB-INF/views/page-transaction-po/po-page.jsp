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
	asset.add("p_o");
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
									<div class="col-sm-3">
										<input type="text" class="form-control pull-right" id="search-date-range">
									</div>
									<div class="col-sm-2">
										<select id="search-status" class="form-control">
											<option value="default">-status-</option>
											<option value="submitted">Submitted</option>
											<option value="approved">Approved</option>
											<option value="rejected">Rejected</option>
											<option value="processed">Processed</option>
										</select>
									</div>
									<div class="col-sm-4"><input type="text" class="form-control" id="search-text" placeholder="Search"></div>
									<div class="col-sm-1"><button type="button" class="btn btn-primary" id="btn-export">Export</button></div>
								</div>
							</div>
						</form>
					</div>
					
					<div>
						<table id="po-table" class="table" style="width:100%">
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
								<c:forEach items="${listPO}" var="po">
									<tr>
										<td>${po.createdOnFormatted}</td>
										<td>${po.supplier.name}</td>
										<td>${po.poNo}</td>
										<td>${po.grandTotalFormatted}</td>
										<td>${po.status}</td>
										<td>
											<button id="${po.id}" class="edit btn btn-secondary">Edit</button>
											<button id="${po.id}" class="view btn btn-secondary">View</button>
										</td>
									</tr>
								</c:forEach>
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


	<!-- modal po edit -->
	<div class="modal fade" id="modal-edit" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">Edit Purchase Order</h2>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div>
							<h3 id="pr-outlet-edit">EDIT PO: ${outlet.name}</h3>
							<hr>
						</div>
						<div>
							<form class="form-room">
								<input type="hidden" class="form-control" id="edit-id" >
								<input type="hidden" class="form-control" id="edit-status" >
								<input type="hidden" class="form-control" id="edit-poNo" >
								<div>
									<label for="edit-supplier">Choose Supplier</label>
									<select id="edit-supplier" class="form-control" style="width:100%">
										<c:forEach items="${listSupplier}" var="supplier">
											<option value="${supplier.id}">${supplier.name}</option>
										</c:forEach>
									</select>
								</div>
								<br>
								<br>
								<div>
									<label for="edit-notes">Notes</label>
									<textarea id="edit-notes" class="form-control" style="height:100px"></textarea>
								</div>
								<br>
								<br>
								<div>
									<label for="table-item-edit">Purchase Request</label>
									<table id = "table-item-edit" style="width:100%">
										<thead>
											<tr>
												<th>Item</th>
												<th>Qty.</th>
												<th>Unit Cost</th>
												<th>Sub Total</th>
											</tr>
										</thead>
										<tbody id="list-selected-item-edit">
										</tbody>
										<tfoot>
											<tr>
												<td><label for="edit-grandTotal">Total</label></td>
												<td></td>
												<td></td>
												<td><input style="border:none" id="edit-grandTotal" readonly></td>
											</tr>
										</tfoot>
									</table>
									<br>
									<br>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-exec-submit" class="btn btn-success">Submit</button>
					<button type="button" id="btn-cancel-edit" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<button type="button" id="btn-exec-edit" class="btn btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- modal po view-->
	<div class="modal fade" id="modal-view" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<p><b>Purchase Request Detail</b></p>
					<div align="right">
						<select id="set-status">
							<option value="approve">Approve</option>
							<option value="reject">Reject</option>
							<option value="process">Process</option>
							<option value="print">Print</option>
						</select>
					</div>
					
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div>
							<input type="hidden" class="form-control" id="view-supplier-id" >
							<p id="view-supplier-name">Supplier Name</p>
							<table id="view-supplier-detail" class="table">
								<tr>
									<td id="view-supplier-phone">phone</td>
									<td id="view-supplier-email">email</td>
								</tr>
								<tr>
									<td id="view-supplier-address">address</td>
									<td id="view-supplier-postal">postal code</td>
								</tr>
							</table>
						</div>
						<div>
							<p><b>Notes:</b></p>
							<textarea id="view-notes" style="height:100px; width:100%;" disabled></textarea>
						</div>
						<div>
							<input type="hidden" class="form-control" id="view-id" >
							<p id="view-po-no">PO Number: </p>
							<p id="view-created-by">Created By: </p>
							<p id="view-email">Email: </p>
							<p id="view-outlet">Outlet: </p>
							<p id="view-phone">Phone: </p>
							<p id="view-po-status">Status: </p>
						</div>
						<hr>
						<div>
							<p><b>Status History</b></p>
							<hr>
							<table id="view-table-history">
								<thead><tr><th></th></tr></thead>
								<tbody></tbody>
							</table>
						</div>
						<hr>
						<div>
							<p><b>Purchase Items</b></p>
							<hr>
							<table id="view-table-detail" style = "width:100%">
								<thead>
									<tr>
										<th>Item</th>
										<th>Qty. Order</th>
										<th>Unit Cost</th>
										<th>Total</th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>			
									<tr>
										<td><label for="view-grandTotal"><b>Total</b></label></td>
										<td></td>
										<td></td>
										<td><input style="border:none" id="view-grandTotal" readonly></td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-done-view" class="btn btn-primary">Done</button>
				</div>
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/template/footer.jsp"%>