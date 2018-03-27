$(document).ready(function() {
    var table = $('#adjust-list').DataTable({
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
    			return response.variantId.itemId.name+" - "+response.variantId.name;
    		},
    		list: {
    			match: {
    				enabled: true
    			},
    			onClickEvent: function() {
    				var value = $("#item-name-variant").getSelectedItemData();
    				var check = $.grep(detailAdjust, function(obj){
    						return obj.variantId.id === value.variantId.id;
    				});
    				if(check[0] == null){
    					delete value.variantId.priceFormatted;
    					var adjust = {
    							"variantId":value.variantId,
    							"actualStock":0,
    							"inStock":value.endingQty
    					}
    					detailAdjust.push(adjust);
    					$("#form-list-item").append("<tr id='"+value.variantId.id+"'><td>"+value.variantId.itemId.name+" - "+value.variantId.name+"</td><td>"+value.endingQty+"</td><td><input type='number' class='form-control adjust-qty' data-id='"+value.variantId.id+"' value='0' min='0' requeired/></td><td><button type='button' class='btn btn-danger delete-item-form' data-id='"+value.variantId.id+"'>X</button></td></tr>");
    					$("#item-name-variant").val('');
    				}
    			}
    		},
    		adjustWidth: false
    	};

    	$("#item-name-variant").easyAutocomplete(options);
        
    	$(document).on('change','#status-more', function(e) {
    		var status = $(this).val();
    		var id = $(this).attr("data-id");
    		if(status != ""){
    			$.ajax({
        			type : "POST",
        			url :baseUrl+'adjustment/status/'+id+"/"+status,
        			success:function(data){
        				if(data.status == 'success'){
        					
        				}
        			},
        			error:function(){
        				alert('Terjadi kesalahan saat menghubugni server');
        			}
        		});
    		}
    	});
    $(document).on('submit','#form-adjust', function(e) {
    	e.preventDefault();
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
        return false;
    });

    function clearForm(){
    	$('#adjust-id').val('');
       	$('#adjust-name').val('');
       	$('#adjust-category-id').val('');
    	listVariant = [];
    }
        

    $('#hapus-data').on('click', function() {
    	var id = $(this).attr('data-id');
    	$.ajax({
    		type : 'DELETE',
    		url :baseUrl+'barang/delete/'+id,
    		success:function(data){
    			if(data.status == 'success' || data.status =='warning'){
    				createTable(data);
    				$('#modal-danger').modal('hide');
    				displayNotif(data.keterangan, data.status);
    			}
    		},
    		error:function(){
    			alert('gagal menghapus data');
    		}
    	});
    });

    $('#add-data').on('click', function() {
    	state = 'simpan';
    	$('#form-adjust').parsley().reset();
    	clearForm();
    	createTableAdjust(listVariant);
    	$('.callout-warning').toggleClass('hidden', true);
    	$('#form-barang-action').attr('value', 'Simpan');
    	$('#myModal-title').html('Add data barang baru');
    	$('#myModal').modal('show');
    });
    
    $('#add-item').on('click', function() {
    	formItemsShow();
    });
    
    
    $('#btn-add-item').on('click', function() {
    	$.each($('.adjust-qty'), function(key, val){
    		var id = val.getAttribute("data-id");
    		$.map(detailAdjust, function(obj, index) {
    		    if(obj.variantId.id == id) {
    		        obj.actualStock = val.value;
    		    }
    		});
    	});
    	createTableAdjust(detailAdjust);
    	formItemsHide();
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
	        				$("#status-more").attr("data-id", id);
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
    
    $('#list-item').delegate('.delete-item','click', function() {
    	var id = $(this).attr("data-id");
    	if(confirm("delete item ?")){
    		var tamp = listVariant[id];
    		if(tamp.id == null){
    			listVariant.splice(id, 1);
	    		createTableAdjust(listVariant);
    		}else{
	    		$.ajax({
	        		type : 'DELETE',
	        		url :baseUrl+'adjust/delete-item/'+tamp.id,
	        		success:function(data){
	        			if(data.status == 'success' || data.status =='warning'){
	        				listVariant.splice(id, 1);
	        	    		createTableAdjust(listVariant);
	        			}
	        		},
	        		error:function(){
	        			alert('gagal menghapus data');
	        		}
	        	});
    		}
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
} );