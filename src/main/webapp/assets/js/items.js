$(document).ready(function() {
	
	$("#file").fileinput({
		showUpload:false,  
		previewFileType:'any'
	});
	
    var table = $('#items-list').DataTable( {
    	"paging":false,
    	"searching":false,
        "ajax": baseUrl+"items/get-all-data",
        "columnDefs": [ 
        {
            "targets": 0,
            "data": null,
            "render": function(data){
            	return data.variantId.itemId.name+" - "+data.variantId.name;
            }
        },
        {
            "targets": 1,
            "data": null,
            "render": function(data){
            	return data.variantId.itemId.categoryId.name;
            }
        },
        {
            "targets": 2,
            "data": null,
            "render": function(data){
            	return data.variantId.priceFormatted;
            }
        },
        {
            "targets": 3,
            "data": null,
            "render": function(data){
            	return data.endingQty;
            }
        },
        {
            "targets": 4,
            "data": null,
            "render": function(data){
            	return (data.endingQty > data.alertAtQty)?"Green":"Red";
            }
        },
        {
            "targets": 5,
            "data":null,
            "render": function(data){
            	return "<button class='btn btn-success update-items' data-id='"+data.variantId.itemId.id+"'>Edit</button>";
            }
        }
        ]
    } );
    
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
    				table.ajax.url(baseUrl+"items/get-all-data/"+value.id).load();
    			}
    		},
    		adjustWidth: false
    	};

    	$("#item-name-variant").easyAutocomplete(options);
    	
    	$("#item-name-variant").on("change", function(){
    		if($("#item-name-variant").val() == ""){
    			table.ajax.url(baseUrl+"items/get-all-data").load();
    		}
    	});
    
    $('#variant-price-mask').inputmask('numeric', {
        radixPoint: '.',
        groupSeparator: ',',
        digits: 0,
        autoGroup: true,
        prefix: 'Rp', //No Space, this will truncate the first character
        rightAlign: false
    });

    $('#variant-price-mask').trigger('change');

    $('#variant-price-mask').on('change', function () {
        var value = $(this).val().split('.');
        var numDecimal = Number(value[0].replace(/[^0-9]+/g,''));
        $('#variant-price').val(numDecimal);
        $('#variant-price').trigger('change');
    });
    
    var ok;
    var state;
    var listVariant = [];
    var tempUpdate;
    $('#form-items').parsley().on('field:validated', function() {
        ok = $('.parsley-error').length === 0;
        $('.callout-warning').toggleClass('hidden', ok);
    });
    $(document).on('submit','#form-items', function(e) {
    	e.preventDefault();
    	if(ok){
    		var oMyForm = new FormData();
    		var image = $('#file').get(0).files[0];
    		if(image != null){
	        	oMyForm.append("file", image);
	        	$.ajax({
	        	    url: baseUrl+'items/upload',
	        	    type: "POST",
	        	    contentType: false,
	        	    processData: false,
	        	    cache: false,
	        	    data: oMyForm,
	        	    success: function(response) {
	        	        if(response.status == "success"){
	        	        	submitForm(response.data);
	        	        }else{
	        	        	displayNotif(response.keterangan, response.status);
	        	        }
	        	    },
	        	    error: function() {
	        	        alert("unable to create the record");
	        	    }
	        	});
    		}else{
    			submitForm(null);
    		}
    	}
      	ok = false;
        return false;
    });
    
    function submitForm(imageName){
    	var items;
		var link;
		var method;
		if(state == 'simpan'){
			items = {
				'name'	: $('#items-name').val(),
				'categoryId'	: {
					'id' : $('#items-category-id').val(),
				},
				'image' : imageName,
				'variants' : listVariant
			};
			link = baseUrl+'items/save';
			method = 'POST';
		}else{
			tempUpdate.name = $('#items-name').val();
			if(tempUpdate.categoryId == null){
				tempUpdate.categoryId = {"id":$('#items-category-id').val()};
			}else{
				tempUpdate.categoryId.id = $('#items-category-id').val();
			}
			if(imageName != null){
				tempUpdate.image = imageName;
			}
			tempUpdate.variants = listVariant;
			items = tempUpdate;
			link = baseUrl+'items/update';
			method = 'PUT';
		}
		
		$.ajax({
			type : method,
			url :link,
			data :JSON.stringify(items),
			contentType: 'application/json',
			success:function(data){
				if(data.status == 'success'){
					table.ajax.reload( null, false );
					$('#myModal').modal('hide');
					clearForm();
					displayNotif(data.keterangan, data.status);
					$('#form-items').parsley().reset();
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

    function clearForm(){
    	$('#items-id').val('');
       	$('#items-name').val('');
       	$('#items-category-id').val('');
       	$('#file').fileinput('clear');
    	listVariant = [];
    }
    
    function ambilDataById(id){
    	$.ajax({
    			type : 'GET',
    			url :baseUrl+'items/get-one/'+id,
    			success:function(response){
    				if(response.status == 'success'){
    					var tampung;
    					listVariant = [];
        				$.each(response.data, function(key, val){
        					var inventory =  Object.assign({}, val);
        					var variant = Object.assign({}, val.variantId);
        					inventory.variantId = null;
        					variant.inventory = [inventory];
        					variant.itemId = null;
        					delete variant.priceFormatted;
        					listVariant.push(variant);
        					tempUpdate = val.variantId.itemId;
        				});
        				$('#items-name').val(tempUpdate.name);
    					$('#items-id').val(tempUpdate.id);
        				$('#items-category-id').val(tempUpdate.categoryId.id);
        				if(tempUpdate.image != null){
        					$('#file').fileinput('destroy');
        					$("#file").fileinput({
        						showUpload:false,  
        						previewFileType:'any',
        						initialPreview: [
        						    "<img src='"+baseUrl+"assets/img/"+tempUpdate.image+"' class='file-preview-image' style='width:auto;height:auto;max-width:100%;max-height:100%;'>",
        						]
        					});
        				}
        				createTableVariant(listVariant);
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
    	$('#form-items').parsley().reset();
    	clearForm();
    	createTableVariant(listVariant);
    	$('.callout-warning').toggleClass('hidden', true);
    	$('#form-barang-action').attr('value', 'Simpan');
    	$('#myModal-title').html('Add New Items');
    	$('#myModal').modal('show');
    });
    
    $('#add-variant').on('click', function() {
    	$("#btn-add-variant").attr("state", "create");
    	formVariantShow();
    });
    
    $('#btn-back-variant').on('click', function() {
    	formVariantHide();
    });
    
    $('#btn-items-back').on('click', function() {
    	clearForm();
    	$('#myModal').modal('hide');
    });
    
    function enableSave(){
        $('#btn-items-save').prop('disabled', false);
    }
    
    $('#btn-add-variant').on('click', function() {
    	var state = $(this).attr("state");
    	if(state == "create"){
    		var variant = {
        			"name" : $("#variant-name").val(),
        			"price" : $("#variant-price").val(),
        			"sku" : $("#variant-sku").val(),
        			"inventory" : [{
        				"begining": $("#inventory-begining").val(),
        				"alertAtQty": $("#inventory-alert-at").val()
        			}],
        	};
    		listVariant.push(variant);
    	}else{
    		var index = $(this).attr("data-id");
    		var variant = listVariant[index];
    		variant.name = $("#variant-name").val();
    		variant.price = $("#variant-price").val();
    		variant.sku = $("#variant-sku").val();
    		variant.inventory.begining = $("#inventory-begining").val();
    		variant.inventory.alertAtQty = $("#inventory-alert-at").val();
    		listVariant[index] = variant;
    	}
    	enableSave();
    	createTableVariant(listVariant);
    	formVariantHide();
    });
    
    $('#items-list').delegate('.update-items','click', function() {
    	var id = $(this).attr("data-id");
    	$('#myModal-title').html('Update Items');
    	state = 'update';
    	ambilDataById(id);
    	$('#form-items').parsley().reset();
    	clearForm();
        $('#myModal').modal('show');
    });
    
    function formVariantHide(){
    	$('#modal-variant').modal('hide');
    	resetFormVariant();
    }
    
    function formVariantShow(){
    	$('#modal-variant').modal('show');
    	resetFormVariant();
    }
    
    function resetFormVariant(){
    	$("#variant-name").val("");
    	$("#variant-price").val("");
    	$("#variant-price-mask").val("0");
    	$("#variant-sku").val("");
    	$("#inventory-begining").val("");
    	$("#inventory-alert-at").val("");
    }
    
    $('#list-variant').delegate('.edit-variant','click', function() {
    	var id = $(this).attr("data-id");
    	var data = listVariant[id];
    	$("#variant-name").val(data.name);
        $("#variant-price").val(data.price);
        $("#variant-price-mask").val(data.price);
        $('#variant-price-mask').trigger('change');
        $("#variant-sku").val(data.sku);
        $("#inventory-begining").val(data.inventory[0].begining);
        $("#inventory-alert-at").val(data.inventory[0].alertAtQty);
    	$("#btn-add-variant").attr("state", "update");
    	$("#btn-add-variant").attr("data-id", id);
        $('#modal-variant').modal('show');
    });
    
    $('#list-variant').delegate('.delete-variant','click', function() {
    	var id = $(this).attr("data-id");
    	if(confirm("delete variant ?")){
    		var tamp = listVariant[id];
    		if(tamp.id == null){
    			listVariant.splice(id, 1);
	    		createTableVariant(listVariant);
    		}else{
	    		$.ajax({
	        		type : 'DELETE',
	        		url :baseUrl+'items/delete-variant/'+tamp.id,
	        		success:function(data){
	        			if(data.status == 'success' || data.status =='warning'){
	        				listVariant.splice(id, 1);
	        	    		createTableVariant(listVariant);
	        			}
	        		},
	        		error:function(){
	        			alert('gagal menghapus data');
	        		}
	        	});
    		}
    	}
    });
    
    function createTableVariant(data){
    	var index = 0;
    	$("#list-variant-body").empty();
    	$.each(data, function(key, val){
    		var myProp = 'alertStatus';
    		$("#list-variant-body").append("<tr><td>"+val.name+"</td><td>Rp "+(parseInt(val.price).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))+"</td><td>"+val.sku+"</td><td>"+val.inventory[0].begining+"</td><td><button type='button' class='btn btn-success edit-variant' data-id="+index+">edit</button> <button type='button' class='btn btn-danger delete-variant' data-id="+index+">X</button></td></tr>");
    		index++;
    	});
    }
} );
