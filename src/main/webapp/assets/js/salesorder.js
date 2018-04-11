var customer = {};
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
						var c = ('x'+value.id);
						if(allReadyId.indexOf(c.toString())==-1){
							$('#table-item').append(
									"<tr id="+value.id+">" +
									"<td class= nama-item"+value.variantId.id+">"+value.variantId.itemId.name+" - "+value.variantId.name+"</td>" +
									"<td class= harga-item"+value.variantId.id+">Rp."+value.price+"</td>" +
									"<td style='display:none;' class= qty-itemqty"+value.variantId.id+">"+value.endingQty+"</td>" +
									"<td><input id="+value.variantId.id+" class='check-item' type='checkbox'></input></td>" +
									"</tr>"
							);
							/*$('#pay-sods').prop('disabled',true);*/
							$('#nama-item').val('');}
						else{
							$('#table-item').append(
									"<tr id="+value.id+">" +
									"<td class= nama-item"+value.variantId.id+">"+value.variantId.itemId.name+" - "+value.variantId.name+"</td>" +
									"<td class= harga-item"+value.variantId.id+">Rp."+value.variantId.price+"</td>" +
									"<td style='display:none;' class= qty-itemqty"+value.variantId.id+">"+value.endingQty+"</td>" +
									"<td><input id="+value.variantId.id+" class='check-item' type='checkbox' checked disabled></input></td>" +
									"</tr>"
							);
							/*$('#pay-sods').prop('disabled',true);*/
							$('#nama-item').val('');
						}
		
						
					}
					
				}
			}
			
			
	};
	$('#nama-item').easyAutocomplete(options);
	
	$('body').on('input', '.quantity', function() {
		var jumlah = $(this).val();
		console.log(jumlah);
		var id = $(this).attr('id');
		var hargaUnit = $('.hargaaaa'+id).text().split("Rp.")[1];
		var hargaTotal = jumlah * hargaUnit;
		$('#total'+id).val("Rp."+hargaTotal);
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
		$('.bayar-sod').text("Charge Rp."+harga);
	})	
	
	var idx ;
	
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
				"<td><input min='0' max="+$('.qty-itemqty'+id).text()+" id=qty"+id+" type='number' class='quantity'></input></td>" +
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
		$('.bayar-sod').text("Charge Rp."+harga);
		/*
		if(harga == 0){
			$('.bayar-sod').text("Charge Rp."+harga).prop('disabled', true);
		}else{
			$('.bayar-sod').text("Charge Rp."+harga).prop('disabled', false);
		}*/
				
	});
	
	$('body').on('click', 'a.delete', function(evt) {
		evt.preventDefault();
		var id = $(this).attr('id');
		$('#table-item').empty();
		var getIndex = allReadyId.indexOf(id.toString());
		allReadyId.splice(getIndex,1);
		document.getElementById(idx).disabled=false;
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
		$('.bayar-sod').text("Charge Rp."+harga);
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
		var total = parseInt($('.bayar-sod').text().split("Charge Rp.")[1]);
		if(parseInt(pay) < parseInt(total)){
			alert('Maaf pembelian gagal');
		}
		else{
			$('#pembayaran').modal('toggle');
			$('#finish').modal();
			$('#id-pay').val("Out of Rp."+pay);
			$('#id-finish').val("Rp."+(pay-total));
			var salesOrderDetail = [];
			$('#list-item-pilih > tr').each(function(index,data) {
				var detail= {
						itemVariant : {
							id : $(data).find('td').eq(0).attr('id'),
						},
						qty : $('#qty'+$(data).find('td').eq(0).attr('id')).val(),
						unitCost : $(data).find('td').eq(3).text().split("Rp.")[1],
						subTotal : $(data).find('td').eq(2).text().split("Rp.")[1]
						
				}
				salesOrderDetail.push(detail);
				//console.log(salesOrderDetail);
				
			})
				var so ={
							customer : {
								id : $('.customer').attr('id'),
							},
							salesOrderDetails : salesOrderDetail,
							grandTotal :  parseInt($('.bayar-sod').text().split("Charge Rp.")[1]),
							modifiedOn : null,
					}
					console.log(so);
					$.ajax({
						url : baseUrl+"/salesorder/save",
						type : 'POST',
						data : JSON.stringify(so),
						contentType : 'application/json',
						success : function(data) {
								$.ajax({
									url : baseUrl+"/salesorder/update-stock",
									type : 'PUT',
									contentType : 'application/json',
									data : JSON.stringify(so),
									success : function(data) {
										//alert('update stoke done');
									},
									error : function() {
										alert('error update stock');
									},
											
								});
							alert('transaction success');
							//window.location = baseUrl+"/salesorder/index";
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
		$('#btn-simpan').on('click', function(evt) {
			evt.preventDefault();
			var formsave = $('#form-save');
			var validsave = formsave.parsley().validate();
			var customer = {
					name : $('#save-name-cust').val(),
					email : $('#save-email-cust').val(),
					phone : $('#save-phone-cust').val(),
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
							console.log($('#save-email-cust').val()+ "+" +email);
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
									if($('#save-phone-cust').val() == phone){
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
				$.ajax({
					url : baseUrl+"region/get-region?id="+id,
					type : 'GET',
					success : function(regionss) {
						var region = [];
							var reg = '<option value=/"/">Region</option>';
							region.push(reg);
							
							$('#save-reg-cust').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
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
				
		
});