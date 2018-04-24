var customer = {};
var soid;
$(function() {

	$('#table-customer').DataTable({
		searching : false
	});
//-------------------------------------------------------------------------------search item---------------------------------------------
	var allReadyId = [];
		var options = {
			url : baseUrl+"items/get-all-inventory",
			getValue : function(response) {
				return  response.variantId.itemId.name+" - "+response.variantId.name;
			},
			list :{
				match :{
					enabled : true
				},
				onClickEvent : function() {
					if($('.customer').attr('id') === undefined){
						alert('silahkan pilih customer terlebih dahulu');
						$('#nama-item').val('');
					}
					
					else{
						$('#table-item').empty();	
						var value = $('#nama-item').getSelectedItemData();
						var c = ('x'+value.variantId.id);
						if(allReadyId.indexOf(c.toString())==-1){
							if (value.endingQty==0) {
								alert('maaf untuk saat ini barang tidak tersedia, silahkan pilih barang lain');
								$('#nama-item').val('');
							}
							else{
								$('#table-item').append(
										"<tr id="+value.id+">" +
										"<td class= nama-item"+value.variantId.id+">"+value.variantId.itemId.name+" - "+value.variantId.name+"</td>" +
										"<td class= hargaaa-item"+value.variantId.id+">Rp."+(parseInt(value.variantId.price).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))+"</td>" +
										"<td style='display:none;' class= harga-item"+value.variantId.id+">Rp."+value.variantId.price+"</td>" +
										"<td style='display:none;' class= qty-itemqty"+value.variantId.id+">"+value.endingQty+"</td>" +
										"<td><input id="+value.variantId.id+" class='check-item' type='checkbox'></input></td>" +
										"</tr>"
								);
								var harga = $('#total-harga-fix').eq(0).text().split("Rp.")[1];
								if(harga == 0){
									$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled', true);
								}else{
									$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled', false);
								}
								$('#nama-item').val('');
								}
							}
						else{
							if (value.endingQty==0) {
								alert('maaf untuk saat ini barang tidak tersedia, silahkan pilih barang lain');
								$('#nama-item').val('');
							}
							else{
								$('#table-item').append(
										"<tr id="+value.id+">" +
										"<td class= nama-item"+value.variantId.id+">"+value.variantId.itemId.name+" - "+value.variantId.name+"</td>" +
										"<td class= hargaaa-item"+value.variantId.id+">Rp."+(parseInt(value.variantId.price).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))+"</td>" +
										"<td style='display:none;' class= harga-item"+value.variantId.id+">Rp."+value.variantId.price+"</td>" +
										"<td style='display:none;' class= qty-itemqty"+value.variantId.id+">"+value.endingQty+"</td>" +
										"<td><input id="+value.variantId.id+" class='check-item' type='checkbox' checked disabled></input></td>" +
										"</tr>"
								);
								var harga = $('#total-harga-fix').eq(0).text().split("Rp.")[1];
								if(harga == 0){
									$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled', true);
								}else{
									$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled', false);
								}
								$('#nama-item').val('');
							}
						}
		
						
					}
					
				}
			}
			
			
	};
	$('#nama-item').easyAutocomplete(options);
	
	$('body').on('input', '.quantity', function() {
		var id = $(this).attr('id');
		var jumlah = $(this).val();
		var min = parseInt($(this).attr('min'));
		var max = parseInt($(this).attr('max'));
		var jumlahfix=0;
		if(jumlah>max){
			jumlahfix=max;
		}
		else if (jumlah == ""){
			jumlahfix=1;
		}
		else{
			jumlahfix=jumlah;
		}
		var hargaUnit = $('.hargaaaa'+id).text().split("Rp.")[1];
		var hargaTotal = jumlahfix * hargaUnit;
		$('#totaal'+id).val("Rp."+hargaTotal);
		$('#total'+id).val("Rp."+(parseInt(hargaTotal).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,')));
		$('#total-harga-fix').empty();
		var total = 0;
		$('#salesorder > tbody > tr').each(function(index,value) {
			var price = $(value).find('td').eq(2).text().split("Rp.")[1];
			total = total + parseInt(price);
		})
		$('#total-harga-fix').append(
				"<tr>" +
				"<td style='display:none;'>Rp."+total+"</td>" +
				"<td>Rp."+(parseInt(total).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))+"</td>" +
				"</tr>" 
		)
		var harga = $('#total-harga-fix').eq(0).text().split("Rp.")[1];
		if(jumlah > max){
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled',false);
			$(this).val(max);
		}
		else if (jumlah<min){
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled',false);
			$(this).val(min);
		}
		else if(parseInt(harga) == 0){
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled',true);
		}
		else{
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled',false);
		}
	})	
	
	var idx;
	
	$('body').on('click', ':checkbox', function(){
		var id = $(this).attr('id');
		var idz = ('x'+id);
		allReadyId.push(idz);
		var newid = id.toString();
		idx = newid;
		document.getElementById(newid).disabled=true;
		$('#list-item-pilih').append(
				"<tr id=ritemx"+id+">" +
				"<td id="+id+">"+$('.nama-item'+id).text()+"</td>" +
				"<td><input min='1' max="+$('.qty-itemqty'+id).text()+" id=qty"+id+" type='number' class='quantity' value='0'></input></td>" +
				"<td style='display:none;' ><output type='text' id='totaalqty"+id+"'>Rp.0</output></td>" +
				"<td><output type='text' id='totalqty"+id+"'>Rp.0</output></td>" +
				"<td style='display:none;' class= hargaaaaqty"+id+">Rp."+$('.harga-item'+id).text().split("Rp.")[1]+"</td>" +
				"<td><a href='#' id=x"+id+" class='delete btn btn-danger'>X</td>" +
				"</tr>"
		);
		$('#total-harga-fix').empty();
		var total = 0;
		$('#salesorder > tbody > tr').each(function(index,value) {
			var price = $(value).find('td').eq(2).text().split("Rp.")[1];
			total = total + parseInt(price);
		})
		$('#total-harga-fix').append(
				"<tr>" +
				"<td>Rp."+total+"</td>" +
				"</tr>"
		)
		var harga = $('#total-harga-fix').text().split("Rp.")[1];
		if(harga == 0){
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled', true);
		}else{
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled', false);
		}
		$('#nama-item').val('');
				
	});
	
	$('body').on('click', 'a.delete', function(evt) {
		evt.preventDefault();
		var id = $(this).attr('id');
		$('#table-item').empty();
		var getIndex = allReadyId.indexOf(id.toString());
		allReadyId.splice(getIndex,1);
		$('#ritem'+id).remove();
		$('#'+idx).prop('checked', false);
		console.log(allReadyId);
		$('#total-harga-fix').empty();
		var total = 0;
		$('#salesorder > tbody > tr').each(function(index,value) {
			var price = $(value).find('td').eq(2).text().split("Rp.")[1];
			total = total + parseInt(price);
		})
		$('#total-harga-fix').append(
				"<tr>" +
				"<td>Rp."+total+"</td>" +
				"</tr>"
		)
		var harga = $('#total-harga-fix').text().split("Rp.")[1];
		if(parseInt(harga) == 0){
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled',true);
		}
		else{
			$('.bayar-sod').text("Charge Rp."+(parseInt(harga).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,'))).prop('disabled',false);
		}
		
	});
	
//-------------------------------------------------------------------------------------------------------------------------------
	
	$('.clear-sod').on('click', function() {
		$('#list-item-pilih').empty();
		$('#total-harga-fix').empty();
		$('#table-item').empty();
		allReadyId=[];
	})
	
//-----------------------------------------------------------------------------------------------payment---------------------------
	
	$('.bayar-sod').click(function() {
		
		$('#pembayaran').modal();
		$('#id-cash').inputmask('numeric', {
	        radixPoint: '.',
	        groupSeparator: ',',
	        digits: 0,
	        autoGroup: true,
	        prefix: 'Rp.', //No Space, this will truncate the first character
	        rightAlign: false
	    });

	    $('#id-cash').trigger('change');

	    $('#id-cash').on('change', function () {
	        var value = $(this).val().split('.');
	        var numDecimal = Number(value[1].replace(/[^0-9]+/g,''));
	        $('#cash-price').val(numDecimal);
	        $('#cash-price').trigger('change');
	    });
	})
	
	$('#btn-payment').click(function() {
		var pay = $('#cash-price').val();
		var total = parseInt($('#total-harga-fix').eq(0).text().split("Rp.")[1]);
		if(parseInt(pay) < parseInt(total)){
			alert('Maaf pembelian gagal');
		}
		else{
			$('#pembayaran').modal('toggle');
			$('#finish').modal({backdrop : 'static', keyboard : false});
			$('#id-pay').val("Out of Rp."+(parseInt(pay).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,')));
			$('#id-finish').val("Rp."+(parseInt(pay-total).toFixed().replace(/(\d)(?=(\d{3})+(,|$))/g, '$1,')));
			var salesOrderDetail = [];
			$('#list-item-pilih > tr').each(function(index,data) {
				var detail= {
						itemVariant : {
							id : $(data).find('td').eq(0).attr('id'),
						},
						qty : $('#qty'+$(data).find('td').eq(0).attr('id')).val(),
						unitCost : $(data).find('td').eq(4).text().split("Rp.")[1],
						subTotal : $(data).find('td').eq(2).text().split("Rp.")[1]
						
				}
				salesOrderDetail.push(detail);
				
			})
				var so ={
							customer : {
								id : $('.customer').attr('id'),
							},
							salesOrderDetails : salesOrderDetail,
							grandTotal :  parseInt($('#total-harga-fix').eq(0).text().split("Rp.")[1]),
							modifiedOn : null,
					}
					$.ajax({
						url : baseUrl+"/salesorder/save",
						type : 'POST',
						data : JSON.stringify(so),
						contentType : 'application/json',
						success : function(data) {
							soid= data;
							$.ajax({
									url : baseUrl+"/salesorder/update-stock",
									type : 'PUT',
									contentType : 'application/json',
									data : JSON.stringify(so),
									success : function(data) {
									},
									error : function() {
										alert('error update stock');
									},
											
								});
							alert('transaction success');
						},//ajaxsave
						error : function() {
							alert('save error');
						}
					})
		}
		
	})
	
	$('#btn-send').on('click', function() {
		alert('already send to your email');
	})
	
	$('#btn-done').click(function() {
		window.location = baseUrl+"/salesorder/index";
	})
	
//-------------------------------------------------------------------------------search cust----------------------------------------
	
	$('#cari-customer').on('click', function() {
		var kata = $('#name-cust').val();
		$.ajax({
			url : baseUrl+"salesorder/index/src-customer?search="+kata,
			type : 'GET',
			success : function(cust) {
				createTableCustomer(cust);
			},
			error : function(nf) {
				alert("customer not found!");
				notFound(nf);
			},
			dataType : 'json',
		});
	});
	
	function createTableCustomer(cust) {
		var newTable = $('#table-customer');
		var renderData = "";
		$.each(cust, function(index, custom) {
			renderData += "<tr>";
			renderData += "<td id='customer-name"+custom.id+"'>";
			renderData += custom.name;
			renderData += "</td>";
			renderData += "<td  id='customer-email"+custom.id+"'>";
			renderData += custom.email;
			renderData += "</td >";
			renderData += "<td>";
			renderData += custom.phone;
			renderData += "</td>";
			renderData += "<td>";
			renderData += "<a href='#' id='"+custom.id+"' class='pilih-customer btn btn-primary'>pilih</a>"
			renderData += "</tr>";
		});
		var tbody = newTable.find("tbody");
		tbody.empty();
		tbody.append(renderData);
	};
	
	function notFound(nf) {
		var newTablenf = $('#table-customer');
		var tbody = newTablenf.find("tbody");
		tbody.empty();
	}
	
	$(document).on('click', '.pilih-customer', function() {
		var id = $(this).attr('id');
		var name = $('#customer-name'+id).text();
		var email = $('#customer-email'+id).text();
		$('.customer').text(name);
		$('.customer').attr("id",id);
		$('.email-cust').text(email);
		$('#choose-cust').modal('toggle');
			

	});
	
	
	
//------------------------------------------------------------------------------choose------------------------------------------------	
	$('.customer').on('click', function() {
		$('#choose-cust').modal();
	})

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add-new').on('click', function() {
			$('#new-cust').modal();
		})
		$('#btn-cancel-save').on('click', function(evt) {
			$('#new-cust').modal('toggle');
		})
		$('#save-phone-cust').inputmask({
			"mask": "+##-###-####-####",
		})
		$('#save-phone-cust').on('change', function () {
			var value = $('#save-phone-cust').val();
	        var numDecimal = value.replace(/[^0-9]+/g,'');
	        $('#save-phone-cust-database').val(numDecimal);
	        $('#save-phone-cust-database').trigger('change');
		});
	$('#save-dob-cust').daterangepicker({
		singleDatePicker: true,
        showDropdowns: true,
        minDate : new Date('1940-01-01'),
        maxDate : new Date(),
        locale : {
			format: 'YYYY-MM-DD'
		}
	})
		
		$('#btn-simpan').on('click', function(evt) {
			evt.preventDefault();
			var formsave = $('#form-save');
			var validsave = formsave.parsley().validate();
			var customer = {
					name : $('#save-name-cust').val(),
					email : $('#save-email-cust').val(),
					phone : $('#save-phone-cust-database').val(),
					dob : $('#save-dob-cust').val(),
					address : $('#save-address-cust').val(),
					provinsi : {
						id : $('#save-pro-cust').val()
					},
					region : {
						id : $('#save-reg-cust').val()
					},
					district : {
						id : $('#save-dis-cust').val()
					},
					active : 1
			}
			var dupemail;
			var dupphone;
			$.ajax({
				url : baseUrl+"salesorder/get-all-email",
				type : 'GET',
				cotentType : 'application/json',
				success : function(listcustomer) {
					dupemail = 0;
					$.each(listcustomer , function(index, email) {
						if($('#save-email-cust').val() == email){
							dupemail = 1;
						}
					})
					if(dupemail == 1){
						alert('email has been used');
					}
					else{
						$.ajax({
							url : baseUrl+"salesorder/get-all-phone",
							type : 'GET',
							contentType : 'application/json',
							success : function(listphone) {
								dupphone=0;
								$.each(listphone , function(index, phone) {
									if($('#save-phone-cust-database').val() == phone){
										dupphone = 1;
									}
								})
								if (dupphone==1){
									alert('phone number has been used')
								}else{
									if(validsave==true){
										$.ajax({
											url : baseUrl+"customer/save",
											type : 'POST',
											contentType : 'application/json',
											data : JSON.stringify(customer),
											success : function(data) {
												alert('save success');
												window.location = baseUrl+"salesorder/index";
											},
											error : function() {
												alert('saving failed!');
											}                              
										});
									}//ajaxsave
								}
							},
							error : function() {
								alert('error')
							}
						})//get phone
					}//else email
				},//success email
				error: function() {
					alert('error');
					
				}
			})
		
			
		});
		
		
//---------------------------------------------------------------------------------------------------------list region---------------------------------------------
		
	
		$('#save-pro-cust').on('change', function() {
			var id = $(this).val();
			if (id !=""){
				$.ajax({
					url : baseUrl+"region/get-region?id="+id,
					type : 'GET',
					success : function(regionss) {
						var region = [];
							var reg = '<option value=/"/">Region</option>';
							region.push(reg);
							$(regionss).each(function(index, data2) {
								reg = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
								region.push(reg);
							})
							
							$('#save-reg-cust').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}else{
				var reg = '<option value=/"/">Region</option>';
				region.push(reg);
			}
		});		
		
		
							
//-------------------------------------------------------------------------------------------list district-------------------------------------------------
							
		$('#save-reg-cust').on('change', function() {
			var id = $(this).val();
			if (id !=""){
				$.ajax({
					url : baseUrl+"kecamatan/get-kecamatan?id="+id,
					type : 'GET',
					success : function(districtsss) {
						var district = [];
							var dis = '<option value=/"/">District</option>';
							district.push(dis);
							$(districtsss).each(function(index, data2) {
								dis = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
								district.push(dis);
							})
							
							$('#save-dis-cust').html(district);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
		
//---------------------------------------------------------------------------------------------------print----------------------
		
		$('#btn-print').click(function() {
			var id = soid;
			window.open(baseUrl+'generate/sales-order/'+id, '_blank');
			window.location = baseUrl+"salesorder/index";
			
		})
				
		
});