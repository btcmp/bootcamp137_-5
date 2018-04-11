$(document).ready(function() {
    var table = $('#adjust-list').DataTable({
    	"paging":false,
    	"searching":false,
        "ajax": baseUrl+"adjustment/get-all-data",
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
            	return data.notes;
            }
        },
        {
            "targets": 2,
            "data": null,
            "render": function(data){
            	return data.status;
            }
        },
        {
            "targets": 3,
            "data":null,
            "render": function(data){
            	return "<button class='btn btn-success view-adjust' data-id='"+data.id+"'>View</button>";
            }
        }
        ]
    });
    var detailAdjust = [];
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
	    				var check = $.grep(detailAdjust, function(obj){
	    						return obj.variantId.id === value.id;
	    				});
	    				if(check[0] == null){
	    					delete value.priceFormatted;
	    					var adjust = {
	    							"variantId":value,
	    							"actualStock":0,
	    							"inStock":value.singleInventory.endingQty
	    					}
	    					detailAdjust.push(adjust);
	    					$("#btn-add-item").prop("disabled", false);
	    					$("#form-list-item").append("<tr id='"+value.id+"'><td>"+value.itemId.name+" - "+value.name+"</td><td>"+value.singleInventory.endingQty+"</td><td><input type='number' class='form-control adjust-qty' data-id='"+value.id+"' value='0' min='0' requeired/></td></tr>");
	    					$("#item-name-variant").val('');
	    				}
    				}else{
    					alert("stock have not initialize");
    				}
    			}
    		},
    		adjustWidth: false
    	};

    	$("#item-name-variant").easyAutocomplete(options);
    	
    	$("#btn-cancel-item").on("click", function(){
    		$("#btn-add-item").prop("disabled", true);
			$("#form-list-item").empty();
			detailAdjust = [];
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
    				table.ajax.url(baseUrl+"adjustment/get-all-data/"+start.format('DD-MM-YYYY')+"/"+end.format('DD-MM-YYYY')).load();
    			});
    	
    	$("#reset-filter").on("click", function(){
    		table.ajax.url(baseUrl+"adjustment/get-all-data").load();
    	});
    	
    	var status = "";
    	$(document).on('change','#status-more', function(e) {
    		status = $(this).val();
    		var id = $(this).attr("data-id");
    		if(status != ""){
    			if(status == "Print"){
    				window.open(baseUrl+'adjustment/print/'+id, '_blank');
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
    			url :baseUrl+'adjustment/status/'+id+"/"+status,
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
        $('#form-adjust').parsley().on('field:validated', function() {
            ok = $('.parsley-error').length === 0;
            $('.callout-warning').toggleClass('hidden', ok);
        });
    $(document).on('submit','#form-adjust', function(e) {
    	e.preventDefault();
    	if(ok){	
    	var adjust = {
    				'notes'	: $('#adjust-notes').val(),
    				'adjustmentDetail' : detailAdjust
    			};
    		
    		$.ajax({
    			type : "POST",
    			url :baseUrl+'adjustment/save',
    			data :JSON.stringify(adjust),
    			contentType: 'application/json',
    			success:function(data){
    				if(data.status == 'success'){
    					table.ajax.reload( null, false );
    					$('#myModal').modal('hide');
    					clearForm();
    					displayNotif(data.keterangan, data.status);
    					$('#form-adjust').parsley().reset();
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
    	$('#adjust-notes').val('');
    	$('#form-adjust').parsley().reset();
    	detailAdjust = []
    	createTableAdjust(detailAdjust);
    	$("#btn-adjust-save").prop("disabled", true);
    	$('.callout-warning').toggleClass('hidden', true);
    }

    $('#add-data').on('click', function() {
    	clearForm();
    	$('#myModal').modal('show');
    });
    
    $("#btn-adjust-cancel").on("click", function(){
    	clearForm();
    });
    
    $("#btn-adjust-back").on("click", function(){
    	$('#myModal').modal('hide');
    });
    
    $('#add-item').on('click', function() {
    	formItemsShow();
    });
    
    $("#btn-back-item").on("click", function(){
    	detailAdjust = [];
    	formItemsHide();
    });
    
    var variantOk;
    $('#form-variant').parsley().on('field:validated', function() {
        variantOk = $(this).find('.parsley-error').length === 0;
     });
    $(document).on('submit','#form-variant', function(e) {
    	e.preventDefault();
    	if(variantOk){
	    	if(detailAdjust.length > 0){
		    	$.each($('.adjust-qty'), function(key, val){
		    		var id = val.getAttribute("data-id");
		    		$.map(detailAdjust, function(obj, index) {
		    		    if(obj.variantId.id == id) {
		    		        obj.actualStock = val.value;
		    		    }
		    		});
		    	});
		    	$("#btn-adjust-save").prop("disabled", false);
		    	createTableAdjust(detailAdjust);
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
    }
    
    $('#adjust-list').delegate('.view-adjust','click', function() {
    	var id = $(this).attr("data-id");
    	$.ajax({
	        		type : 'GET',
	        		url :baseUrl+'adjustment/get-adjustment-detail/'+id,
	        		success:function(response){
	        			if(response.status == 'success' || response.status =='warning'){
	        				var data = response.data;
	        				$("#adjust-status-label").html("");
	        				$("#adjust-status-label").html(data.status);
	        				$("#adjust-notes-label").val(data.notes);
	        				$("#adjust-created-by-label").html("");
	        				$("#adjust-created-by-label").html(data.createdBy.username);
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
	        				$.each(data.adjustmentHistory, function(key, val){
	        					$("#status-history").append("On "+val.createdOnFormatted+" - "+val.status+"</br>");
	        				});
	        				$.each(data.adjustmentDetail, function(key, val){
	        					$("#label-list-item").append("<tr><td>"+val.variantId.itemId.name+" - "+val.variantId.name+"</td><td>"+val.inStock+"</td><td>"+val.actualStock+"</td></tr>");
	        				});
	        				$('#modal-detail').modal('show');
	        			}
	        		},
	        		error:function(){
	        			alert('gagal menghubungi server');
	        		}
	        	});
    });
    
    $('#list-item-body').delegate('.delete-item','click', function() {
    	var id = $(this).attr("data-id");
    	if(detailAdjust.length >1){
    		if(confirm("delete item ?")){ 		
    			detailAdjust.splice(id, 1);
		    	createTableAdjust(detailAdjust);
    		}
    	}else{
			alert("Warning!!! Could not delete item. Adjustment must have at least one item");
		}
    });
    
    function createTableAdjust(data){
    	var index = 0;
    	$("#list-item-body").empty();
    	$.each(data, function(key, val){
    		$("#list-item-body").append("<tr><td>"+val.variantId.itemId.name+" - "+val.variantId.name+"</td><td>"+val.inStock+"</td><td>"+val.actualStock+"</td><td><button type='button' class='btn btn-danger delete-item' data-id="+index+">X</button></td></tr>");
    		index++;
    	});
    }
});