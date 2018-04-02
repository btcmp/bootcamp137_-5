var customer = {};
$(function() {

	$('#table-customer').DataTable({
		searching : false
	});
	/*$('#salesorder').DataTable({
		searching : false
	});*/
//-------------------------------------------------------------------------------search item---------------------------------------------
	var allReadyId = [];
		var options = {
			url : baseUrl+"items/get-all-variant",
			getValue : function(response) {
				return  response.variantId.itemId.name+" - "+response.variantId.name;
			},
			list :{
				match :{
					enabled : true
				},
				onClickEvent : function() {
					if($('.customer').attr('id') === undefined){
						alert('tolol isi customer dlu')
					}
					
					else{
						$('#table-item').empty();	
						var value = $('#nama-item').getSelectedItemData();
						var c = ('x'+value.variantId.id);
						if(allReadyId.indexOf(c.toString())==-1){
							$('#table-item').append(
									"<tr>" +
									"<td class= nama-item"+value.variantId.id+">"+value.variantId.itemId.name+" - "+value.variantId.name+"</td>" +
									"<td class= harga-item"+value.variantId.id+">Rp."+value.variantId.price+"</td>" +
									"<td class= qty-item"+value.variantId.id+">"+value.endingQty+"</td>" +
									"<td><input id="+value.variantId.id+" class='check-item' type='checkbox'></input></td>" +
									"</tr>"
							);
							$('#nama-item').val('');}
						else{
							$('#table-item').append(
									"<tr>" +
									"<td class= nama-item"+value.variantId.id+">"+value.variantId.itemId.name+" - "+value.variantId.name+"</td>" +
									"<td class= harga-item"+value.variantId.id+">Rp."+value.variantId.price+"</td>" +
									"<td class= qty-item"+value.variantId.id+">"+value.endingQty+"</td>" +
									"<td><input id="+value.variantId.id+" class='check-item' type='checkbox' checked disabled></input></td>" +
									"</tr>"
							);
							$('#nama-item').val('');
						}
		
						
					}
					
				}
			}
			
			
	};
	$('#nama-item').easyAutocomplete(options);
	
	
	$('body').on('input', '.quantity', function(e) {
		var jumlah = $(this).val();
		var id = $(this).attr('id');
		var hargaUnit = parseInt($('.hargaaaa'+id).text().split("Rp.")[1]);
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
				"<td><input min='0' max="+$('.qty-item'+id).text()+" id=qty"+id+" type='number' class='quantity'></input></td>" +
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
				
	});
	
	$('body').on('click', 'a.delete', function(evt) {
		evt.preventDefault();
		var id = $(this).attr('id');
		var getIndex = allReadyId.indexOf(id.to=String());
		document.getElementById(idx).disabled=false;
		$('#'+idx).prop('checked', false);
		$('#ritem'+id).remove();
		allReadyId.splice(getIndex,1);
		$('#table-item').empty();
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
	})
	
	$('#btn-payment').click(function() {
		$('#finish').modal();
		$('#pembayaran').modal('toggle');
		var pay = $('#id-cash').val();
		var total = parseInt($('.bayar-sod').text().split("Charge Rp.")[1]);
		$('#id-pay').val("Out of Rp."+pay);
		$('#id-finish').val("Rp."+(pay-total));
		
	})
	
	$('#btn-done').click(function() {
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
						grandTotal :  parseInt($('.bayar-sod').text().split("Charge Rp.")[1])
				}
				console.log(so);
				$.ajax({
					url : baseUrl+"/salesorder/save",
					type : 'POST',
					data : JSON.stringify(so),
					contentType : 'application/json',
					success : function() {
						alert('save success')
						window.location = baseUrl+"/salesorder/index";
					},
					error : function() {
						alert('save error');
					}
				})
				//aaaaa
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
			renderData += "<td>";
			renderData += custom.email;
			renderData += "</td>";
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
			//console.log(id);
			var name = $('#customer-name'+id).text();
			console.log(name);
			$('.customer').text(name);
			$('.customer').attr("id",id);
			$('#choose-cust').modal('toggle');
			
			/*//alert("aa");
			var element = $(this).parent().parent();
			//console.log(element);
			var td = element.find("td").eq(0).text();
			//console.log(td);
			$('#customer').text(td);
			customer = {
					name : element.find("td").eq(0).text(),
					email : element.find("td").eq(1).text(),
					phone : element.find("td").eq(2).text()
			}
			//console.log(customer);
*/			
		});
	
	
	
//------------------------------------------------------------------------------choose------------------------------------------------	
	$('.customer').on('click', function() {
		$('#choose-cust').modal();
	})

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add-new').on('click', function() {
			$('#new-cust').modal();
		})
		$('#btn-simpan').on('click', function(evt) {
			evt.preventDefault();
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
			console.log(customer);
		
		
			$.ajax({
				url : baseUrl+"customer/save",
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(customer),
				success : function(data) {
					//console.log(data);
					alert('save success');
					window.location = baseUrl+"salesorder/index";
				},
				error : function() {
					alert('saving failed!');
				}                              
			});
			
		});
		
		
//---------------------------------------------------------------------------------------------------------list region---------------------------------------------
		
		$('#save-pro-cust').on('change', function() {
			var id = $(this).val();
			//console.log(id);
			if (id!==""){
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
			}
		});		
		
		
							
//-------------------------------------------------------------------------------------------list district-------------------------------------------------
							
		$('#save-reg-cust').on('change', function() {
			var id = $(this).val();
			//console.log(id);
			if (id!=""){
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
				
		
//------------------------------------------------------------------------------pilih cust----------------------------------------------------------------

		/*$('.pilih-customer').on('click', function() {
			//alert("terpilih");
		});*/

});