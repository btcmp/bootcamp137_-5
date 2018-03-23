$(document).ready(function() {
    $('#items-list').DataTable( {
        "ajax": baseUrl+"items/get-all-data",
        "columns": [
            { "data": "id" },
            { "data": "id" },
            { "data": "id" },
            { "data": "id" },
            { "data": "id" },
            { "data": "id" }
        ]
    } );
    
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
        
    function clearForm(){
    	$('#id').val('');
       	$('#nama-barang').val('');
       	$('#kode-barang').val('');
    	$('#harga').val('');
    	$('#harga-mask').val('');
    	$('#stock').val('');
    }
        
    var ok;
    var state;
    $('#form-items').parsley().on('field:validated', function() {
        ok = $('.parsley-error').length === 0;
        $('.callout-warning').toggleClass('hidden', ok);
    })
    .on('form:submit', function() {
    	if(ok){
    		var barang;
    		var link;
    		var method;
    		if(state == 'simpan'){
    			barang = {
    				'namaBarang'	: $('#nama-barang').val(),
    				'kodeBarang'	: $('#kode-barang').val(),
    				'harga' : $('#harga-mask').val(),
    				'stock' : $('#stock').val()
    			};
    			link = baseUrl+'barang/save'
    			method = 'POST';
    		}else{
    			barang = {
    					'id' : $('#id').val(),
    					'namaBarang'	: $('#nama-barang').val(),
    					'kodeBarang'	: $('#kode-barang').val(),
    					'harga' : $('#harga-mask').val(),
    					'stock' : $('#stock').val()
    				};
    			link = baseUrl+'barang/update';
    			method = 'PUT';
    		}
    		
    		$.ajax({
    			type : method,
    			url :link,
    			data :JSON.stringify(barang),
    			contentType: 'application/json',
    			success:function(data){
    				if(data.status == 'success'){
    					createTable(data);
    					$('#myModal').modal('hide');
    					clearForm();
    					displayNotif(data.keterangan, data.status);
    					$('#form-barang').parsley().reset();
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

    function ambilDataById(id, env){
    	$.ajax({
    			type : 'GET',
    			url :baseUrl+'barang/get-one/'+id,
    			success:function(data){
    				if(data.status == 'success'){
    					var value = data.data;
    					if(env == 'update'){
    						$('#id').val(value.id);
    					   	$('#nama-barang').val(value.namaBarang);
    					   	$('#kode-barang').val(value.kodeBarang);
    					   	$('#stock').val(value.stock);
    					   	$('#harga').val(value.harga);
    						$('#harga').trigger('change');
    					}else{
    						$('#barang-detail-id').html(value.id);
    						$('#barang-detail-kode').html(value.kodeBarang);
    					   	$('#barang-detail-nama').html(value.namaBarang);
    						$('#barang-detail-harga').html(value.formatedHarga);
    						$('#barang-detail-stock').html(value.stock);
    					}
    				}else{
    					$('#barang-detail-id').html('');
    					$('#barang-detail-kode').html('');
    				   	$('#barang-detail-nama').html('');
    					$('#barang-detail-harga').html('');
    					$('#barang-detail-stock').html('');
    					createTable(data);
    					displayNotif(data.keterangan, data.status);
    				}
    			},
    			error:function(){
    				alert('data gagal disimpan');
    			}
    		});
    }
        
    $('#data-barang').delegate('.hapus-data', 'click', function() {
    	var id = $(this).attr('data-id');
    	$('#hapus-data').attr('data-id', id);
    	$('#modal-danger').modal('show');
    });

    $('#data-barang').delegate('.update-data','click', function() {
    	state = 'update';
    	var id = $(this).attr('data-id');
    	ambilDataById(id, 'update');
    	clearForm();
    	$('.callout-warning').toggleClass('hidden', true);
    	$('#form-barang').parsley().reset();
    	$('#form-barang-action').attr('value', 'Update');
    	$('#myModal-title').html('Ubah data barang');
    	$('#myModal').modal('show');
    });

    $('#data-barang').delegate('.lihat-data', 'click',function() {
    	var id = $(this).attr('data-id');
    	ambilDataById(id, 'lihat');
    	$('#modal-detail').modal('show');
    });

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

    $('#tambah-data').on('click', function() {
    	state = 'simpan';
    	$('#form-items').parsley().reset();
    	clearForm();
    	$('.callout-warning').toggleClass('hidden', true);
    	$('#form-barang-action').attr('value', 'Simpan');
    	$('#myModal-title').html('Tambah data barang baru');
    	$('#myModal').modal('show');
    });
    
    $('#tambah-variant').on('click', function() {
    	formVariantShow();
    });
    
    var listVariant = [];
    $('#btn-add-variant').on('click', function() {
    	var variant = {
    			"name" : $("#variant-name").val(),
    			"price" : $("#variant-price").val(),
    			"sku" : $("#variant-sku").val(),
    			"inventory" : {
    				"begining": $("#inventory-begining").val(),
    				"alertAtQty": $("#inventory-alert-at").val()
    			},
    	}
    	listVariant.push(variant);
    	createTableVariant(listVariant);
    	formVariantHide();
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
    	$("#variant-sku").val("");
    	$("#inventory-begining").val("");
    	$("#inventory-alert-at").val("");
    }
    
    $('#list-variant').delegate('.edit-variant','click', function() {
    	var state = $(this).attr("state");
    	var id = $(this).attr("data-id");
    	if(state == "new"){
    		var data = listVariant[id];
    		$("#variant-name").val(data.name);
        	$("#variant-price").val(data.price);
        	$("#variant-sku").val(data.sku);
        	$("#inventory-begining").val(data.inventory.begining);
        	$("#inventory-alert-at").val(data.inventory.alertAtQty);
    	}
    	$('#modal-variant').modal('show');
    });
    
    function createTableVariant(data){
    	var id = "";
    	var state = "";
    	var index = 0;
    	$("#list-variant-body").empty();
    	$.each(data, function(key, val){
    		if(val.id == null){
    			id = index;
    			state = "new";
    		}else{
    			id = val.id;
    			state = "update";
    		}
    		$("#list-variant-body").append("<tr><td>"+val.name+"</td><td>"+val.price+"</td><td>"+val.sku+"</td><td>"+val.inventory.begining+"</td><td><button type='button' class='btn btn-success edit-variant' state="+state+" data-id="+id+">edit</button></td></tr>");
    		index++;
    	});
    }
} );