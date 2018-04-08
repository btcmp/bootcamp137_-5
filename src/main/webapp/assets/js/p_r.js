$(document).ready(function(){
	
	$('#search-date-range').daterangepicker();
	
	//setting up data tables
	var tabPR = $('#pr-table').DataTable({searching : false, paging : false});
	var tabSaveItem = $('#table-item-save').DataTable({searching : false, paging : false});
	var tabEditItem = $('#table-item-edit').DataTable({searching : false, paging : false});
	var tabListItem = $('#table-listItem').DataTable({searching : false , paging : false});
	var tabHistory = $('#view-table-history').DataTable({searching : false, paging : false});
	var tabDetail = $('#view-table-detail').DataTable({searching : false, paging : false});
	
	//view transition
	$('#btn-create').on('click', function(){
		$('#modal-save').modal();
		tabSaveItem.clear().draw();
	});
	
	$('#pr-table').delegate('.edit', 'click', function(){
		var id  = $(this).attr('id');
		$.ajax({
			url : baseUrl+'purchase-request/get-pr/'+id,
			type : 'GET',
			contentType : 'application/json',
			success : function(pr){
				if(pr.status != 'approved' && pr.status != 'PO created'){
					$('#modal-edit').modal();
					tabEditItem.clear();
					$('#edit-id').val(pr.id);
					$('#edit-status').val(pr.status);
					$('#edit-prNo').val(pr.prNo);
					$('#edit-item-ready').val(pr.readyTime);
					$('#edit-notes').val(pr.notes);
					
					var	listDetail = pr.listPurchaseRequestDetail;
					var listItem = [];
					var item;
					
					$.each(listDetail, function(index, detail){
						item = {
								name: detail.variant.itemId.name + ' - ' + detail.variant.name,
								quantity: 100,
								id: detail.variant.id,
								requestQty : detail.requestQty
							};
						listItem.push(item);
					})
					$('#list-selected-item-edit').attr('listItem',JSON.stringify(listItem));
				} else {
					alert('this pr has been approved and cannot be editted');
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
		
	});
	
	$('#pr-table').delegate('.view', 'click', function(){
		
		var id  = $(this).attr('id');
		
		$.ajax({
			url : baseUrl+'purchase-request/get-pr/'+id,
			type : 'GET',
			contentType : 'application/json',
			success : function(pr){
				$('#modal-view').modal();
				$('#view-pr-no').text('PR Number: '+ pr.prNo);
				$('#view-created-by').text('Created By: '+ pr.createdBy.username);
				$('#view-item-ready').text('Target Waktu Item Ready: '+ pr.readyTime);
				$('#view-pr-status').text('PR Status: '+ pr.status);
				$('#view-notes').val(pr.notes);
				
				var listHistory = pr.listPurchaseRequestHistory;
				tabHistory.clear();
				$.each(listHistory, function(index, history){
					tabHistory.row.add(['On "'+ history.createdOnFormatted + '" - ' + pr.prNo + ' is ' + history.status]).draw();
				});
				
				var listDetail = pr.listPurchaseRequestDetail;
				tabDetail.clear();
				$.each(listDetail, function(index, detail){
					tabDetail.row.add([
						detail.variant.itemId.name + ' - ' + detail.variant.name,
						detail.requestQty
					]).draw();
				});
				
				$('#btn-done-view').attr('pr',JSON.stringify(pr));
			},
			error : function (){
				alert('error getting data');
			}
		});
	});
	
	$('#btn-listItem-save').on('click', function(){
		$('#modal-list-item').attr('parent','save');
		$('#modal-list-item').modal();
		var listAlreadySelected = [];
		
		$('#table-item-save').find('button[type="button"]').each(function(){
			listAlreadySelected.push($(this).attr('id'));
		});
		
		refreshItemList(listAlreadySelected);
		$('#modal-save').modal('hide');
	});
	
	$('#btn-listItem-edit').on('click', function(){
		$('#modal-list-item').attr('parent','edit');
		$('#modal-list-item').modal();
		var listAlreadySelected = [];
		
		$('#table-item-edit').find('button[type="button"]').each(function(){
			listAlreadySelected.push($(this).attr('id'));
		});
		
		refreshItemList(listAlreadySelected);
		$('#modal-edit').modal('hide');
	});
	
	$('#modal-list-item').on('hidden.bs.modal', function(){
		parent = $('#modal-list-item').attr('parent');
		if(parent=='save'){
			$('#modal-save').modal();
		} else if (parent=='edit'){
			$('#modal-edit').modal();
		}
	});
	
	
	//refresh item list collection
	function refreshItemList(listAlreadySelected){
		tabListItem.clear();
		var dontAdd;
		$.ajax({
			url : baseUrl+'purchase-request/get-all-item',
			type : 'GET',
			contentType : 'application/json',
			success : function(listItem){
				$.each(listItem, function(index, item){
					dontAdd = 0;
					$.each(listAlreadySelected, function(index,selected){
						if(item.variantId.id == selected){
							dontAdd = 1;
						}
					})
					if(dontAdd == 0){
						tabListItem.row.add([
							item.variantId.itemId.name + ' - ' + item.variantId.name,
							item.endingQty,
							'<input type="number" id="' + item.variantId.id + '" class="request-qty form-room" min="0" max="' + item.endingQty + '" style="width:100%">'
						]).draw();
					}
				});
			},
			error : function(){
				alert('error getting data');
			}
		});
	}
	
	
	//add item into request form
	function refreshSelection(){
		$('#list-item').find('input[type="number"]').each(function(){
			$(this).val('');
		});
	}
	
	$('#btn-addItem').on('click', function(){
		var listItem = [];
		var item;
		var offside = 0;
		$('#list-item').find('input[type="number"]').each(function(){
			if($(this).val() > 0){
				if($(this).val() > parseInt($(this).closest('tr').find('td').eq(1).text())){
					offside = 1;
				} else {
					item = {
						name: $(this).closest('tr').find('td').eq(0).text(),
						quantity: $(this).closest('tr').find('td').eq(1).text(),
						id: $(this).attr('id'),
						requestQty : $(this).val()
					};
					listItem.push(item);
				}
			}
		});
		if (offside == 1){
			alert('requested quantity is more than stock, please review your request.');
		} else {
			parent = $('#modal-list-item').attr('parent')
			if(parent=='save'){
				$('#list-selected-item-save').attr('listItem',JSON.stringify(listItem));
			} else if(parent=='edit'){
				$('#list-selected-item-edit').attr('listItem',JSON.stringify(listItem));
			}
			$('#modal-list-item').modal('hide');
		}
	});
	
	$('#table-item-save').delegate('.del-item', 'click', function(){
		var id  = $(this).attr('id');
		tabSaveItem.row($(this).parents('tr')).remove().draw();
		
		$('#list-item').find('input[type="number"]').each(function(){
			if($(this).attr('id') == id){
				$(this).val('');
			}
		});
	});
	
	$('#table-item-edit').delegate('.del-item', 'click', function(){
		var id  = $(this).attr('id');
		tabEditItem.row($(this).parents('tr')).remove().draw();
		
		$('#list-item').find('input[type="number"]').each(function(){
			if($(this).attr('id') == id){
				$(this).val('');
			}
		});
	});
	
	//save purchase request
	$('#modal-save').on('shown.bs.modal', function(){
		try{
			var hasSelect = $('#list-selected-item-save').attr('listItem');
			if(typeof hasSelect !== typeof undefined && hasSelect !== ''){
				var listItem = JSON.parse($('#list-selected-item-save').attr('listItem'));
				var tabSaveItem = $('#table-item-save').DataTable();
				$.each(listItem, function(index,item){
					tabSaveItem.row.add([
						item.name,
						item.quantity,
						item.requestQty,
						'<button type="button" class="del-item btn btn-danger" id="'+ item.id +'">X</button>'
					]).draw();
				});
				$('#list-selected-item-save').attr('listItem',null);
			}
		} catch(ex){
			console.error(ex);
		}
	});
	
	$('#btn-exec-save').on('click', function(){
		var listDetail = [];
		$('#table-item-save').find('button[type="button"]').each(function(){
			var itemVariantId = $(this).attr('id');
			listDetail.push({
				variant : {id : itemVariantId},
				requestQty : $(this).closest('tr').find('td').eq(2).text()
			});
		});
		
		var listHistory = [{
			status : 'submitted'
		}];
		
		var purchaseRequest = {
			readyTime : $('#save-item-ready').val(),
			notes : $('#save-notes').val(),
			listPurchaseRequestDetail : listDetail,
			listPurchaseRequestHistory : listHistory
		}
		$.ajax({
			url : baseUrl+'purchase-request/save',
			type : 'POST',
			contentType : 'application/json',
			data : JSON.stringify(purchaseRequest),
			success : function(){
				alert('save pr success');
				window.location = baseUrl+'purchase-request';
			},
			error : function(){
				alert('save pr failed');
			}
		});
		
	});
	
	
	//edit purchase request
	$('#modal-edit').on('shown.bs.modal', function(){
		try{
			var hasSelect = $('#list-selected-item-edit').attr('listItem');
			if(typeof hasSelect !== typeof undefined && hasSelect !== ''){
				var listItem = JSON.parse($('#list-selected-item-edit').attr('listItem'));
				tabEditItem = $('#table-item-edit').DataTable();
				$.each(listItem, function(index,item){
					tabEditItem.row.add([
						item.name,
						item.quantity,
						item.requestQty,
						'<button type="button" class="del-item btn btn-danger" id="'+ item.id +'">X</button>'
					]).draw();
				});
			}
		} catch(ex){
			console.error(ex);
		}
	});
	
	$('#btn-addItem-cancel').on('click', function(){
		listItem = [];
		$('#list-selected-item-edit').attr('listItem',JSON.stringify(listItem));
	});
	
	$('#btn-exec-edit').on('click', function(){
		var listDetail = [];
		$('#table-item-edit').find('button[type="button"]').each(function(){
			var itemVariantId = $(this).attr('id');
			listDetail.push({
				variant : {id : itemVariantId},
				requestQty : $(this).closest('tr').find('td').eq(2).text()
			});
		});
		
		var listHistory;
		if($('#edit-status').val()=='rejected'){
			listHistory = [{
				status : 'submitted'
			}];
		}
		
		var purchaseRequest = {
			id : $('#edit-id').val(),
			prNo : $('#edit-prNo').val(),
			status : 'submitted',
			readyTime : $('#edit-item-ready').val(),
			notes : $('#edit-notes').val(),
			listPurchaseRequestDetail : listDetail,
			listPurchaseRequestHistory : listHistory
		}
		$.ajax({
			url : baseUrl+'purchase-request/edit',
			type : 'PUT',
			contentType : 'application/json',
			data : JSON.stringify(purchaseRequest),
			success : function(){
				alert('edit pr success');
				window.location = baseUrl+'purchase-request';
			},
			error : function(){
				alert('edit pr failed');
			}
		});
		
	});
	
	
	//view purchase request
	$('#btn-done-view').on('click', function(){
		try{
			var pr = JSON.parse($(this).attr('pr'));
			if($('#set-status').val()=='approve'){
				if(pr.status=='rejected'){
					alert('pr has been rejected and cannot be approved...');
				} else if(pr.status=='approved'){
					$('#modal-view').modal('hide');
				} else if(pr.status=='PO created'){
					alert('Purchase Order has been created');
				} else {
					pr.createdOn = null;
					pr.modifiedOn = null;
					pr.listPurchaseRequestDetail = null;
					delete pr.createdOnFormatted;
					listHistory = [{
						status : 'approved'
					}];
					pr.listPurchaseRequestHistory = listHistory;
					
					console.log(pr);
					$.ajax({
						url : baseUrl+'purchase-request/approve',
						type : 'PUT',
						contentType : 'application/json',
						data : JSON.stringify(pr),
						success : function(){
							alert('pr approved!');
							window.location = baseUrl+'purchase-request';
						},
						error : function(){
							alert('approving failed');
						}
					});
					
				}
			} else if($('#set-status').val()=='reject'){
				if(pr.status=='approved'){
					alert('pr has been approved and cannot be rejected...');
				} else if(pr.status=='rejected'){
					$('#modal-view').modal('hide');
				} else if(pr.status=='PO created'){
					alert('Purchase Order has been created');
				} else {
					pr.createdOn = null;
					pr.modifiedOn = null;
					pr.listPurchaseRequestDetail = null;
					delete pr.createdOnFormatted;
					listHistory = [{
						status : 'rejected'
					}];
					pr.listPurchaseRequestHistory  = listHistory;
					$.ajax({
						url : baseUrl+'purchase-request/reject',
						type : 'PUT',
						contentType : 'application/json',
						data : JSON.stringify(pr),
						success : function(){
							alert('pr rejected!');
							window.location = baseUrl+'purchase-request';
						},
						error : function(){
							alert('rejecting failed');
						}
					});
				}
			} else if($('#set-status').val()=='print'){
				console.log('print');
			} else if($('#set-status').val()=='createPO'){
				if(pr.status=='approved'){
					delete pr.createdOnFormatted;
					console.log(JSON.stringify(pr));
					$.ajax({
						url : baseUrl+'purchase-order/save',
						type : 'POST',
						contentType : 'application/json',
						data : JSON.stringify(pr.id),
						success : function(){
							alert('saving po success');
							window.location = baseUrl+'purchase-request';
						},
						error : function(){
							alert('saving po failed');
						}
					});
				} else if(pr.status=='PO created'){
					alert('Purchase Order has been created');
				} else {
					alert('Purchase Request is not approved yet');
				}
				
				
				
			}
		} catch(ex){
			console.error(ex);
		}
		
	});
	
	
	
	//Search Item Variant
	var options = {
			url : baseUrl+'items/get-all-variant',
			getValue : function(response){
				return response.variantId.itemId.name + ' - ' + response.variantId.name;
			},
			list : {
				match : {
					enabled: true
				},
				onClickEvent : function(){
					$('#list-item').empty();
					var item = $('#search-item').getSelectedItemData();
					$('#list-item').append(
							'<tr>' +
							'<td>' + item.variantId.itemId.name + ' - ' + item.variantId.name + '</td>' +
							'<td>' + item.endingQty + '</td>' +
							'<td>' + '<input type="number" id="' + item.variantId.id + '" class="request-qty form-room" min="0" max="' + item.endingQty + '" style="width:100%">' + '</td>' +
							'</tr>'
						);
				}
			},
			theme : 'square'
	}
	$('#search-item').easyAutocomplete(options);
	
	
	//refresh pr list (show all pr data)
	function refreshPRList(){
		tabPR.clear();
		$.ajax({
			url : baseUrl+'purchase-request/get-all-pr',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPR){
				if(listPR != ''){
					$.each(listPR, function(index, pr){
						tabPR.row.add([
							pr.createdOnFormatted,
							pr.prNo,
							pr.notes,
							pr.status,
							'<button id="' + pr.id + '" class="edit btn btn-secondary">Edit</button><button id="' + pr.id + '" class="view btn btn-secondary">View</button>'		
						]).draw();
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	
	//search PR by status
	function getSubmittedPRList(){
		tabPR.clear();
		$.ajax({
			url : baseUrl+'purchase-request/get-all-submitted-pr',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPR){
				if(listPR != ''){
					$.each(listPR, function(index, pr){
						tabPR.row.add([
							pr.createdOnFormatted,
							pr.prNo,
							pr.notes,
							pr.status,
							'<button id="' + pr.id + '" class="edit btn btn-secondary">Edit</button><button id="' + pr.id + '" class="view btn btn-secondary">View</button>'		
						]).draw();
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	function getApprovedPRList(){
		tabPR.clear();
		$.ajax({
			url : baseUrl+'purchase-request/get-all-approved-pr',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPR){
				if(listPR != ''){
					$.each(listPR, function(index, pr){
						tabPR.row.add([
							pr.createdOnFormatted,
							pr.prNo,
							pr.notes,
							pr.status,
							'<button id="' + pr.id + '" class="edit btn btn-secondary">Edit</button><button id="' + pr.id + '" class="view btn btn-secondary">View</button>'		
						]).draw();
					});
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	function getRejectedPRList(){
		tabPR.clear();
		$.ajax({
			url : baseUrl+'purchase-request/get-all-rejected-pr',
			type : 'GET',
			contentType : 'application/json',
			success : function(listPR){
				if(listPR != ''){
					$.each(listPR, function(index, pr){
						tabPR.row.add([
							pr.createdOnFormatted,
							pr.prNo,
							pr.notes,
							pr.status,
							'<button id="' + pr.id + '" class="edit btn btn-secondary">Edit</button><button id="' + pr.id + '" class="view btn btn-secondary">View</button>'		
						]).draw();
					});
				} else {
					tabPR.draw();
				}
			},
			error : function(){
				alert('error getting data');
			}
		})
	}
	
	$('#search-status').on('change', function(){
		if ($(this).val() == 'default'){
			refreshPRList();
		} else if ($(this).val() == 'submitted'){
			getSubmittedPRList();
		} else if ($(this).val() == 'approved'){
			getApprovedPRList();
		} else if ($(this).val() == 'rejected'){
			getRejectedPRList();
		}
	});
	
	
});