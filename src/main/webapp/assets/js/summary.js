$(document).ready(function() {
		
    var table = $('#items-list').DataTable( {
    	"paging":false,
    	"searching":false,
        "ajax": baseUrl+"items/get-all-inventory2",
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
            	return data.begining;
            }
        },
        {
            "targets": 3,
            "data": null,
            "render": function(data){
            	return data.purchaseQty;
            }
        },
        {
            "targets": 4,
            "data": null,
            "render": function(data){
            	return data.salesOrderQty;
            }
        },
        {
            "targets": 5,
            "data":null,
            "render": function(data){
            	return data.transferStockQty;
            }
        },
        {
            "targets": 6,
            "data":null,
            "render": function(data){
            	return data.adjustmentQty;
            }
        },
        {
            "targets": 7,
            "data":null,
            "render": function(data){
            	return data.endingQty;
            }
        }
        ]
    } );
    
} );
