$(document).ready(function(){
	
	//setting up data tables
	var tabPO = $('#po-table').DataTable({searching : false, paging : false});
	var tabEditItem = $('#table-item-edit').DataTable({searching : false,paging : false,dataTables_info:false});
	var tabHistory = $('#view-table-history').DataTable({searching : false, paging : false});
	var tabDetail = $('#view-table-detail').DataTable({searching : false, paging : false});
	
	//view transition
	$('#po-table').delegate('.edit', 'click', function(){
		var id  = $(this).attr('id');
		$.ajax({
			url : baseUrl+'purchase-order/get-po/'+id,
			type : 'GET',
			contentType : 'application/json',
			success : function(po){
				if(po.status != 'submitted' && po.status != 'rejected' && po.status != 'approved' && po.status != 'processed'){
					$('#modal-edit').modal();
					tabEditItem.clear();
					$('#edit-id').val(po.id);
					$('#edit-status').val(po.status);
					$('#edit-poNo').val(po.poNo);
					$('#edit-notes').val(po.notes);
					$('#edit-grandTotal').val(po.grandTotal);
					if(po.supplier != null){
						$('#edit-supplier').val(po.supplier.id);
						$('#btn-exec-submit').prop('disabled', false);
					} else {
						$('#btn-exec-submit').prop('disabled', true);
					}
					
					var	listDetail = po.listPurchaseOrderDetail;
					var listItem = [];
					var item;
					
					$.each(listDetail, function(index, detail){
						item = {
								name: detail.variant.itemId.name + ' - ' + detail.variant.name,
								id: detail.variant.id,
								requestQty : detail.requestQty,
								unitCost : detail.unitCost,
								subTotal : detail.subTotal
							};
						listItem.push(item);
					});
					
					$.each(listItem, function(index,item){
						tabEditItem.row.add([
							item.name,
							item.requestQty,
							'<input type="text" style="border:none" class="edit-unitCost" id="'+ item.id +'" value="'+ item.unitCost +'">',
							'<input type="text" style="border:none" class="edit-subTotal" value="'+ item.subTotal +'" readonly>'
						]).draw();
					});
					
				} else {
					alert('this pr cannot be editted');
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	});
	
	$('#po-table').delegate('.view', 'click', function(){
		var id  = $(this).attr('id');
		$.ajax({
			url : baseUrl+'purchase-order/get-po/'+id,
			type : 'GET',
			contentType : 'application/json',
			success : function(po){
				$('#modal-view').modal();
				tabDetail.clear();
				tabHistory.clear();
				
				if(po.supplier != null){
					$('#view-supplier-id').val(po.supplier.id);
					$('#view-supplier-name').text(po.supplier.name);
					$('#view-supplier-phone').text(po.supplier.phone);
					$('#view-supplier-email').text(po.supplier.email);
					$('#view-supplier-address').text(po.supplier.address);
					$('#view-supplier-postal').text(po.supplier.postalCode);
				}
				
				
				$('#view-id').val(po.id);
				$('#view-notes').val(po.notes);
				$('#view-po-no').text('PO Number : ' + po.poNo);
				$('#view-created-by').text('Created By: ' + po.createdBy.employee.firstName + ' ' + po.createdBy.employee.lastName);
				$('#view-email').text('Email : '+ po.createdBy.employee.email);
				$('#view-outlet').text('Outlet : '+ po.purchaseRequest.outlet.name);
				$('#view-phone').text('Phone : '+ po.purchaseRequest.outlet.phone);
				$('#view-po-status').text('Status : '+ po.status);
				
				var listHistory = po.listPurchaseOrderHistory;
				$.each(listHistory, function(index, history){
					tabHistory.row.add(['On "'+ history.createdOnFormatted + '" - ' + po.poNo + ' is ' + history.status]).draw();
				});
				
				var listDetail = po.listPurchaseOrderDetail;	
				$.each(listDetail, function(index, detail){
					tabDetail.row.add([
						detail.variant.itemId.name + ' - ' + detail.variant.name,
						detail.requestQty,
						detail.unitCost,
						detail.subTotal
					]).draw();
				});
				$('#view-grandTotal').val(po.grandTotal);
				$('#btn-done-view').attr('currentstatus',po.status);
			},
			error : function(){
				alert('error getting data');
			}
		})
	});
	
	
	//edit execution
	$('#table-item-edit').delegate('.edit-unitCost','change', function(){
		var qty = $(this).closest('tr').find('td').eq(2).text();
		var unitCost = $(this).val();
		var subTotal = parseInt(qty * unitCost);
		$(this).closest('tr').find('.edit-subTotal').val(subTotal);
		
		var grandTotal = 0;
		$('#list-selected-item-edit').find('.edit-subTotal').each(function(){
			grandTotal += parseInt($(this).val());
		});
		$('#edit-grandTotal').val(parseInt(grandTotal));
		
	});
	
	
	$('#btn-exec-edit').on('click',function(){
		var listDetail = [];
		
		$('#list-selected-item-edit').find('tr').each(function(){
			var itemVariantId = $(this).find('.edit-unitCost').attr('id');
			listDetail.push({
				variant : {id : itemVariantId},
				unitCost : parseInt($(this).find('.edit-unitCost').val()),
				subTotal : parseInt($(this).find('.edit-subTotal').val())
			});
		});
		
		var purchaseOrder = {
				id : $('#edit-id').val(),
				poNo : $('#edit-poNo').val(),
				status : $('#edit-status').val(),
				grandTotal : parseInt($('#edit-grandTotal').val()),
				notes : $('#edit-notes').val(),
				listPurchaseOrderDetail : listDetail,
				supplier : {
					id : $('#edit-supplier').val()
				}
		}
		
		$.ajax({
			url : baseUrl+'purchase-order/edit',
			type : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify(purchaseOrder),
			success : function(){
				alert('edit po success');
				window.location = baseUrl+'purchase-order';
			},
			error : function(){
				alert('edit po failed');
			}
		});
		
	});
	
	
	//submit execution
	$('#btn-exec-submit').on('click', function(){
		var idPO = $('#edit-id').val();
		
		$.ajax({
			url : baseUrl+'purchase-order/submit',
			type : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify(idPO),
			success : function(){
				alert('submit po success');
				window.location = baseUrl+'purchase-order';
			},
			error : function(){
				alert('submit po failed');
			}
		});
	});
	
	
	//view execution
	$('#btn-done-view').on('click', function(){
		var setstatus = $('#set-status').val();
		var currentstatus = $(this).attr('currentstatus');
		var idPO = $('#view-id').val();
		
		if(setstatus=='approve'){
			if (currentstatus == 'rejected'){
				alert('po has been rejected and cannot be approved');
			} else if (currentstatus == 'created'){
				alert('please submit your po first');
			} else if (currentstatus == 'processed'){
				alert('po has been processed');
			} else if(currentstatus == 'approved'){
				$('#modal-view').modal('hide');
			} else {
				$.ajax({
					url : baseUrl+'purchase-order/approve',
					type : 'PUT',
					contentType : 'application/json',
					data : JSON.stringify(idPO),
					success : function(){
						alert('approve po success');
						window.location = baseUrl+'purchase-order';
					},
					error : function(){
						alert('approve po failed');
					}
				});
			}
		} else if(setstatus=='reject'){
			
			if (currentstatus == 'processed'){
				alert('po has been processed');
			} else if(currentstatus == 'rejected'){
				$('#modal-view').modal('hide');
			} else {
				$.ajax({
					url : baseUrl+'purchase-order/reject',
					type : 'PUT',
					contentType : 'application/json',
					data : JSON.stringify(idPO),
					success : function(){
						alert('reject po success');
						window.location = baseUrl+'purchase-order';
					},
					error : function(){
						alert('reject po failed');
					}
				});	
			}
		} else if(setstatus=='process'){
			
			if (currentstatus == 'rejected'){
				alert('po has been rejected and cannot be processed');
			} else if(currentstatus == 'processed'){
				$('#modal-view').modal('hide');
			} else if(currentstatus != 'approved') {
				alert('please approve the po first');
			} else {
				$.ajax({
					url : baseUrl+'purchase-order/process',
					type : 'PUT',
					contentType : 'application/json',
					data : JSON.stringify(idPO),
					success : function(){
						alert('process po success');
						window.location = baseUrl+'purchase-order';
					},
					error : function(){
						alert('process po failed');
					}
				});
			}
		} else if(setstatus=='print'){
			window.location = baseUrl + "generate/purchase-order-detail/"+idPO;
		}
	});
	
	
	//refresh po list (show all po data)
	function refreshPOList(){
		tabPO.clear().draw();
		$.ajax({
			url : baseUrl+'purchase-order/get-all-po',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	//search PO by status
	function getSubmittedPOList(){
		tabPO.clear().draw();
		$.ajax({
			url : baseUrl+'purchase-order/get-all-submitted-po',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	function getApprovedPOList(){
		tabPO.clear().draw();
		$.ajax({
			url : baseUrl+'purchase-order/get-all-approved-po',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	function getRejectedPOList(){
		tabPO.clear().draw();
		$.ajax({
			url : baseUrl+'purchase-order/get-all-rejected-po',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	function getProcessedPOList(){
		tabPO.clear().draw();
		$.ajax({
			url : baseUrl+'purchase-order/get-all-processed-po',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	$('#search-status').on('change', function(){
		if ($(this).val() == 'default'){
			refreshPOList();
		} else if ($(this).val() == 'submitted'){
			getSubmittedPOList();
		} else if ($(this).val() == 'approved'){
			getApprovedPOList();
		} else if ($(this).val() == 'rejected'){
			getRejectedPOList();
		} else if ($(this).val() == 'processed'){
			getProcessedPOList();
		}
	});
	
	//search by text input
	$('#search-text').on('change', function(){
		if ($(this).val()==''){
			refreshPOList();
		} else {
			getPOListBySearch($(this).val());
		}
	});
	
	function getPOListBySearch(search){
		tabPO.clear();
		$.ajax({
			url : baseUrl+'purchase-order/get-list-by-search/'+search,
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	
	//search by date range
	$('#search-date-range').daterangepicker({
		locale : {
			format: 'YYYY-MM-DD'
		}	
	});
	
	$('#search-date-range').on('change', function(){
		tabPO.clear();
		var daterange = $(this).val().split(" - ");
		var start = daterange[0];
		var end = daterange[1];
		$.ajax({
			url : baseUrl+'purchase-order/search-by-date?start='+start+'&end='+end,
			type : 'GET',
			contentType : 'application/json',
			success : function(listPO){
				if(listPO != ''){
					$.each(listPO, function(index, po){
						if(po.supplier==null){
							tabPO.row.add([
								po.createdOnFormatted,
								'-',
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						} else {
							tabPO.row.add([
								po.createdOnFormatted,
								po.supplier.name,
								po.poNo,
								po.grandTotalFormatted,
								po.status,
								'<button id="' + po.id + '" class="edit btn btn-secondary">Edit</button><button id="' + po.id + '" class="view btn btn-secondary">View</button>'		
							]).draw();
						}
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		});
	});
	
	
	$('#btn-export').on('click', function(){
		window.location = baseUrl + "generate/purchase-order";
	});
	
});