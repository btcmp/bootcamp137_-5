$(document).ready(function() {
    var table = $('#transfer-list').DataTable({
    	"paging":false,
    	"searching":false,
        "ajax": baseUrl+"transfer-stock/get-all-data/all",
        "columnDefs": [ 
        {
            "targets": 0,
            "data": null,
            "render": function(data){
            	return data.createdOnFormatted;
            }
        },
        {
            "targets": 1,
            "data": null,
            "render": function(data){
            	return data.fromOutlet.name;
            }
        },
        {
            "targets": 2,
            "data": null,
            "render": function(data){
            	return data.toOutlet.name;
            }
        },
        {
            "targets": 3,
            "data": null,
            "render": function(data){
            	return data.notes;
            }
        },
        {
            "targets": 4,
            "data": null,
            "render": function(data){
            	return data.status;
            }
        },
        {
            "targets": 5,
            "data":null,
            "render": function(data){
            	return "<button class='btn btn-success view-transfer' data-id='"+data.id+"'>View</button>";
            }
        }
        ]
    });
    var detailTransfer = [];
    var options = {
    		url: baseUrl+"items/get-all-variant",
    		getValue: function(response) {
    			return response.itemId.name+" - "+response.name;
    		},
    		list: {
    			match: {
    				enabled: true
    			},
    			onClickEvent: function() {
    				var value = $("#item-name-variant").getSelectedItemData();
    				if(value.singleInventory != null){
    					if(value.singleInventory.endingQty > 0){
	    				var check = $.grep(detailTransfer, function(obj){
	    						return obj.variantId.id === value.id;
	    				});
	    				if(check[0] == null){
	    					delete value.priceFormatted;
	    					var transfer = {
	    							"variantId":value,
	    							"transferQty":0,
	    							"inStock":value.singleInventory.endingQty
	    					}
	    					detailTransfer.push(transfer);
	    					$("#btn-add-item").prop("disabled", false);
	    					$("#form-list-item").append("<tr id='"+value.id+"'><td>"+value.itemId.name+" - "+value.name+"</td><td>"+value.singleInventory.endingQty+"</td><td><input type='number' class='form-control transfer-qty' data-id='"+value.id+"' max='"+value.singleInventory.endingQty+"' value='1' min='1' requeired/></td></tr>");
	    					$("#item-name-variant").val('');
	    				}
    					}else{
    						alert("stock is empty");
    					}
    				}else{
    					alert("stock have not initialize");
    				}
    			}
    		},
    		adjustWidth: false
    	};

    	$("#item-name-variant").easyAutocomplete(options);
    	
    	$("#filter-to-outlet").on("change", function(){
    		var id = $(this).val();
    		table.ajax.url(baseUrl+"transfer-stock/get-all-data/"+id).load();
    	});
    	
    	$("#btn-cancel-item").on("click", function(){
    		$("#btn-add-item").prop("disabled", true);
			$("#form-list-item").empty();
			detailTransfer = [];
    	});
    	
    	function getDate(){
    		var d = new Date();

    		var month = d.getMonth()+1;
    		var day = d.getDate();

    		return (day<10 ? '0' : '')  + day + '/' +
    		    (month<10 ? '0' : '') + month + '/' + d.getFullYear();
    	}
    	
    	$('#search-by-date').daterangepicker(
    			{
    			    locale: {
    			      format: 'DD/MM/YYYY'
    			    },
    			    startDate: getDate(),
    			    endDate: getDate()
    			}, 
    			function(start, end, label) {
    				table.ajax.url(baseUrl+"transfer-stock/get-all-data/"+start.format('DD-MM-YYYY')+"/"+end.format('DD-MM-YYYY')).load();
    			});
    	
    	$("#reset-filter").on("click", function(){
    		table.ajax.url(baseUrl+"transfer-stock/get-all-data").load();
    	});
    	
    	var status = "";
    	$(document).on('change','#status-more', function(e) {
    		status = $(this).val();
    		var id = $(this).attr("data-id");
    		if(status != ""){
    			if(status == "Print"){
    				window.open(baseUrl+'transfer-stock/print/'+id, '_blank');
    			}else{
    				$("#ubah-status").attr("data-id", id);
    				$("#modal-status").modal("show");
    			}
    		}
    	});
    	
    	$("#ubah-status").on("click", function(){
    		var id = $(this).attr("data-id");
    		$.ajax({
    			type : "POST",
    			url :baseUrl+'transfer-stock/status/'+id+"/"+status,
    			success:function(data){
    				if(data.status == 'success'){
    					displayNotif(data.keterangan, data.status);
    					table.ajax.reload( null, false );
    					$('#modal-status').modal('hide');
    					$('#modal-detail').modal('hide');
    				}
    			},
    			error:function(){
    				alert('Terjadi kesalahan saat menghubugni server');
    			}
    		});
        });
    	
    	var ok;
        $('#form-transfer').parsley().on('field:validated', function() {
            ok = $('.parsley-error').length === 0;
            $('.callout-warning').toggleClass('hidden', ok);
        });
    $(document).on('submit','#form-transfer', function(e) {
    	e.preventDefault();
    	if(ok){	
    	var transfer = {
    				'notes'	: $('#transfer-notes').val(),
    				'toOutlet' : {
    					"id" : $("#transfer-outlet-id").val(),
    				},
    				'transferStockDetail' : detailTransfer
    			};
    		
    		$.ajax({
    			type : "POST",
    			url :baseUrl+'transfer-stock/save',
    			data :JSON.stringify(transfer),
    			contentType: 'application/json',
    			success:function(data){
    				if(data.status == 'success'){
    					table.ajax.reload( null, false );
    					$('#myModal').modal('hide');
    					clearForm();
    					displayNotif(data.keterangan, data.status);
    					$('#form-transfer').parsley().reset();
    				}else{
    					$.each(data.error, function(key, value) {
    						$('#'+value[0]).parsley().removeError(value[0]+'-error', {updateClass: true});
    						$('#'+value[0]).parsley().addError(value[0]+'-error', {message: value[1], updateClass: true});
    					});
    					$('.callout-warning').toggleClass('hidden', false);
    				}
    			},
    			error:function(){
    				alert('Terjadi kesalahan saat menghubugni server');
    			}
    		});
    	}
    	ok = false;
        return false;
    });

    function clearForm(){
    	$('#transfer-notes').val('');
    	$('#transfer-outlet-id').val('');
    	$('#form-transfer').parsley().reset();
    	detailTransfer = []
    	createTableTransfer(detailTransfer);
    	$("#btn-transfer-save").prop("disabled", true);
    	$('.callout-warning').toggleClass('hidden', true);
    }

    $('#add-data').on('click', function() {
    	clearForm();
    	$('#myModal').modal('show');
    });
    
    $("#btn-transfer-cancel").on("click", function(){
    	clearForm();
    });
    
    $("#btn-transfer-back").on("click", function(){
    	$('#myModal').modal('hide');
    });
    
    $('#add-item').on('click', function() {
    	formItemsShow();
    });
    
    $("#btn-back-item").on("click", function(){
    	detailTransfer = [];
    	formItemsHide();
    });
    
    var variantOk;
    $('#form-variant').parsley().on('field:validated', function() {
        variantOk = $('.parsley-error').length === 0;
     });
    $(document).on('submit','#form-variant', function(e) {
    	e.preventDefault();
    	if(variantOk){
    		if(detailTransfer.length > 0){
		    	$.each($('.transfer-qty'), function(key, val){
		    		var id = val.getAttribute("data-id");
		    		$.map(detailTransfer, function(obj, index) {
		    		    if(obj.variantId.id == id) {
		    		        obj.transferQty = val.value;
		    		    }
		    		});
		    	});
		    	$("#btn-transfer-save").prop("disabled", false);
		    	createTableTransfer(detailTransfer);
		    	formItemsHide();
	    	}
    	}
    	variantOk =false;
    	return false;
    });
    
    function formItemsHide(){
    	$('#modal-item').modal('hide');
    	resetFormVariant();
    }
    
    function formItemsShow(){
    	$('#modal-item').modal('show');
    	resetFormVariant();
    }
    
    function resetFormVariant(){
    	$("#form-list-item").empty();
    	$("#btn-add-item").prop("disabled", true);
    }
    
    $('#transfer-list').delegate('.view-transfer','click', function() {
    	var id = $(this).attr("data-id");
    	$.ajax({
	        		type : 'GET',
	        		url :baseUrl+'transfer-stock/get-transfer-detail/'+id,
	        		success:function(response){
	        			if(response.status == 'success' || response.status =='warning'){
	        				var data = response.data;
	        				$("#transfer-status-label").html("");
	        				$("#transfer-status-label").html(data.status);
	        				$("#transfer-to-outlet-label").html("");
	        				$("#transfer-to-outlet-label").html(data.toOutlet.name);
	        				$("#transfer-from-outlet-label").html("");
	        				$("#transfer-from-outlet-label").html(data.fromOutlet.name);
	        				$("#transfer-notes-label").val(data.notes);
	        				$("#transfer-created-by-label").html("");
	        				$("#transfer-created-by-label").html(data.createdBy.username);
	        				$("#status-more").attr("data-id", id);
	        				if(data.status == "Submitted"){
	        					$("#status-more").html(`<option value="">More</option>
									<option value="Approved">Approved</option>
									<option value="Reject">Reject</option>
									<option value="Print">Print</option>`);
	        				}else{
	        					$("#status-more").html(`<option value="">More</option>
										<option value="Print">Print</option>`);
	        				}
	        				$("#status-history").html("");
	        				$("#label-list-item").empty();
	        				$.each(data.transferStockHistory, function(key, val){
	        					$("#status-history").append("On "+val.createdOnFormatted+" - "+val.status+"</br>");
	        				});
	        				$.each(data.transferStockDetail, function(key, val){
	        					$("#label-list-item").append("<tr><td>"+val.variantId.itemId.name+" - "+val.variantId.name+"</td><td>"+val.inStock+"</td><td>"+val.transferQty+"</td></tr>");
	        				});
	        				$('#modal-detail').modal('show');
	        			}
	        		},
	        		error:function(){
	        			alert('gagal menghubungi server');
	        		}
	        	});
    });
    
    function createTableTransfer(data){
    	var index = 0;
    	$("#list-item-body").empty();
    	$.each(data, function(key, val){
    		$("#list-item-body").append("<tr><td>"+val.variantId.itemId.name+" - "+val.variantId.name+"</td><td>"+val.inStock+"</td><td>"+val.transferQty+"</td><td><button type='button' class='btn btn-danger delete-item' data-id="+index+">X</button></td></tr>");
    		index++;
    	});
    }
});