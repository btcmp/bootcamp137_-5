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
					<h3>PURCHASE REQUEST</h3>
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
										</select>
									</div>
									<div class="col-sm-4"><input type="text" class="form-control" id="search-text" placeholder="Search"></div>
									<div class="col-sm-1"><button type="button" class="btn btn-primary" id="btn-export">Export</button></div>
									<div class="col-sm-1"><button type="button" class="btn btn-primary" id="btn-create">Create</button></div>
								</div>
							</div>
						</form>
					</div>
					
					<div>
						<table id="pr-table" class="table" style="width:100%">
							<thead>
								<tr>
									<th>Create Date</th>
									<th>PR No.</th>
									<th>Note</th>
									<th>Status</th>
									<th>#</th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${listPR}" var="pr">
									<tr>
										<td>${pr.createdOnFormatted}</td>
										<td>${pr.prNo}</td>
										<td>${pr.notes}</td>
										<td>${pr.status}</td>
										<td>
											<button id="${pr.id}" class="edit btn btn-secondary">Edit</button>
											<button id="${pr.id}" class="view btn btn-secondary">View</button>
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

	<!-- modal pr save -->
	<div class="modal fade" id="modal-save" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">Purchase Request</h2>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div>
							<h3 id="pr-outlet-save">CREATE NEW PR: <c:out value="${outlet.name}"></c:out></h3>
							<hr>
						</div>
						<div> 
							<form class="form-room">
								<div>
									<label for="save-item-ready">Target Waktu Item Ready</label>
									<input type="date" class="form-control" id="save-item-ready">
								</div>
								<br>
								<br>
								<div>
									<label for="save-notes">Notes</label>
									<textarea id="save-notes" class="form-control" style="height:100px"></textarea>
								</div>
								<br>
								<br>
								<div>
									<label for="table-item-save">Purchase Request</label>
									<table id = "table-item-save" style="width:100%">
										<thead>
											<tr>
												<th>Item</th>
												<th>In Stock</th>
												<th>Request Quantity</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="list-selected-item-save"></tbody>
									</table>
									<button type="button" class="btn btn-primary" id="btn-listItem-save">Add Item</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-cancel-save" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<button type="button" id="btn-exec-save" class="btn btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>


	<!-- modal pr edit -->
	<div class="modal fade" id="modal-edit" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">Edit Purchase Request</h2>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div>
							<h3 id="pr-outlet-edit">EDIT PR: <c:out value="${outlet.name}"></c:out></h3>
							<hr>
						</div>
						<div>
							<form class="form-room">
								<input type="hidden" class="form-control" id="edit-id" >
								<input type="hidden" class="form-control" id="edit-status" >
								<input type="hidden" class="form-control" id="edit-prNo" >
								<div>
									<label for="edit-item-ready">Target Waktu Item Ready</label>
									<input type="date" class="form-control" id="edit-item-ready">
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
												<th>In Stock</th>
												<th>Request Quantity</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="list-selected-item-edit">
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td><button type="button" class="listItem-edit btn btn-danger">X</button></td>
											</tr>
										</tbody>
									</table>
									
									<button type="button" class="btn btn-primary" id="btn-listItem-edit">Add Item</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-cancel-edit" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<button type="button" id="btn-exec-edit" class="btn btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- modal pr view-->
	<div class="modal fade" id="modal-view" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<p><b>Purchase Request Detail</b></p>
					<div align="right">
						<select id="set-status">
							<option value="approve">Approve</option>
							<option value="reject">Reject</option>
							<option value="print">Print</option>
							<option value="createPO">Create PO</option>
						</select>
					</div>
					
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div>
							<p id="view-pr-no">PR Number: </p>
							<p id="view-created-by">Created By: </p>
							<p id="view-item-ready">Target Waktu Item Ready: </p>
							<p id="view-pr-status">Status: </p>
						</div>
						<div>
							<p><b>Notes:</b></p>
							<input type="text" id="view-notes" style="height:100px; width:100%;" disabled>
						</div>
						<br>
						<br>
						<div>
							<p><b>Status History</b></p>
							<hr>
							<table id="view-table-history">
								<thead><tr><th></th></tr></thead>
								<tbody></tbody>
							</table>
						</div>
						<br>
						<br>
						<div>
							<p><b>Purchase Items</b></p>
							<hr>
							<table id="view-table-detail" style = "width:100%">
								<thead>
									<tr>
										<th>Item</th>
										<th>Request Qty.</th>
									</tr>
								</thead>
								<tbody></tbody>
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
	
	<!-- modal list item -->
	<div class="modal fade" id="modal-list-item" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<p>Add Purchase Item</p>
				</div>
				<div class="modal-body" style="max-height: calc(100vh - 210px); overflow-y: auto;">
					<div class="container-fluid">
						<form class="form-room">
							<input type="text" class="form-control" id="search-item" placeholder="search item or variant...">
							<br>
							<table id="table-listItem" style="width:100%">
								<thead>
									<tr>
										<th>Item</th>
										<th>In Stock</th>
										<th>Request Qty</th>
									</tr>
								</thead>
								<tbody id="list-item">
								</tbody>
								<tfoot id = "item-found"></tfoot>
							</table>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-addItem-cancel" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<button type="button" id="btn-addItem" class="btn btn-primary">Add</button>
				</div>
			</div>
		</div>
	</div>
	

<%@ include file="/WEB-INF/views/template/footer.jsp"%>