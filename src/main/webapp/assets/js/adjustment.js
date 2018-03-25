$(document).ready(function() {
    var table = $('#adjust-list').DataTable( {
        "ajax": baseUrl+"adjustment/get-all-data",
        "columnDefs": [ 
        {
            "targets": 0,
            "data": null,
            "render": function(data){
            	return data.createdOn;
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
    } );
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
    				var check = $.grep(detailAdjust, function(obj){
    						return obj.variantId.id === value.id;
    				});
    				if(check[0] == null){
    					var adjust = {
    							"variantId":value,
    							"actualStock":0
    					}
    					detailAdjust.push(adjust);
    					$("#form-list-item").append("<tr id='"+value.id+"'><td>"+value.itemId.name+" - "+value.name+"</td><td>10</td><td><input type='number' class='form-control adjust-qty' data-id='"+value.id+"' value='0' requeired/></td><td><button type='button' class='btn btn-danger delete-item-form' data-id='"+value.id+"'>X</button></td></tr>");
    					$("#item-name-variant").val('');
    				}
    			}
    		},
    		adjustWidth: false
    	};

    	$("#item-name-variant").easyAutocomplete(options);
    
    $('#harga').inputmask('numeric', {
        radixPoint: '.',
        groupSeparator: ',',
        digits: 0,
        autoGroup: true,
        prefix: 'Rp', //No Space, this will truncate the first character
        rightAlign: false,
        oncleared: function () { self.Value(''); }
    });

    $('#harga').trigger('change');

    $('#harga').on('change', function () {
        var value = $(this).val().split('.');
        var numDecimal = Number(value[0].replace(/[^0-9]+/g,''));
        $('#harga-mask').val(numDecimal);
        $('#harga-mask').trigger('change');
    });
        
    var ok;
    var state;
    var listVariant = [];
    $('#form-adjust').parsley().on('field:validated', function() {
        ok = $('.parsley-error').length === 0;
        $('.callout-warning').toggleClass('hidden', ok);
    });
    $(document).on('submit','#form-adjust', function(e) {
    	e.preventDefault();
    	if(ok){
    		var adjust;
    		var link;
    		var method;
    		if(state == 'simpan'){
    			adjust = {
    				'name'	: $('#adjust-name').val(),
    				'categoryId'	: {
    					'id' : $('#adjust-category-id').val(),
    				},
    				'items' : listVariant
    			};
    			link = baseUrl+'adjust/save'
    			method = 'POST';
    		}else{
    			adjust = {
    					'id'	: $('#adjust-id').val(),
        				'name'	: $('#adjust-name').val(),
        				'categoryId'	: {
        					'id' : $('#adjust-category-id').val(),
        				},
        				'items' : listVariant
        			};
    			link = baseUrl+'adjust/update';
    			method = 'PUT';
    		}
    		
    		$.ajax({
    			type : method,
    			url :link,
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
    	$('#adjust-id').val('');
       	$('#adjust-name').val('');
       	$('#adjust-category-id').val('');
    	listVariant = [];
    }
    
    function ambilDataById(id){
    	$.ajax({
    			type : 'GET',
    			url :baseUrl+'adjust/get-one/'+id,
    			success:function(response){
    				if(response.status == 'success'){
    					var tampung;
    					listVariant = [];
        				$.each(response.data, function(key, val){
        					var item = {
        							"id" : val.itemId.id,
        			    			"name" : val.itemId.name,
        			    			"price" : val.itemId.price,
        			    			"sku" : val.itemId.sku,
        			    			"inventory" : [{
        			    				"id" : val.id,
        			    				"begining": val.begining,
        			    				"alertAtQty": val.alertAtQty
        			    			}]
        			    	}
        					listVariant.push(item);
        					tampung = val.itemId.itemId;
        				});
        				$('#adjust-name').val(tampung.name);
    					$('#adjust-id').val(tampung.id);
        				$('#adjust-category-id').val(tampung.categoryId.id);
        				createTableAdjust(listVariant);
    				}
    			},
    			error:function(){
    				alert('data gagal disimpan');
    			}
    		});
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
    	$("#item-name").val("");
    	$("#item-price").val("");
    	$("#item-sku").val("");
    	$("#inventory-begining").val("");
    	$("#inventory-alert-at").val("");
    }
    
    $('#list-item').delegate('.edit-item','click', function() {
    	var id = $(this).attr("data-id");
    	var data = listVariant[id];
    	$("#item-name").val(data.name);
        $("#item-price").val(data.price);
        $("#item-sku").val(data.sku);
        $("#inventory-begining").val(data.inventory[0].begining);
        $("#inventory-alert-at").val(data.inventory[0].alertAtQty);
    	$("#btn-add-item").attr("state", "update");
    	$("#btn-add-item").attr("data-id", id);
        $('#modal-item').modal('show');
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
    		$("#list-item-body").append("<tr><td>"+val.variantId.itemId.name+" - "+val.variantId.name+"</td><td>"+10+"</td><td>"+val.actualStock+"</td><td><button type='button' class='btn btn-danger delete-item' data-id="+index+">X</button></td></tr>");
    		index++;
    	});
    }
} );