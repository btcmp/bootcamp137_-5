$(document).ready(function() {
	
	$('#employee-table').DataTable({
		searching : false,
		paging : false
	});
	
    $('#user-form').hide();
    
    $('#save-hasAccount').on('click',function(){
		if(this.checked){
			$('#user-form').show(500);
		} else {
			$('#user-form').hide(500);
		}
    });
    
    $('#btn-assign-outlet').on('click', function(){
    	$('#modal-outlet').modal();
    });
    
    $('#btn-cancel').on('click', function(){
    	$('.cancelable').val('');
    });
    
    $('#btn-save').on('click', function(evt){
    	evt.preventDefault();
    	var account = $('#save-hasAccount').is(':checked') ? true : false;
    	var employee = {
    		firstName : $('#save-fname').val(),
    		lastName : $('#save-lname').val(),
    		email : $('#save-email').val(),
    		title : $('#save-title').val(),
    		haveAccount : account,
    		active : true
    	}
    	console.log(employee);
    	
    	if(account==true){
    		var user = {
    			
    		}
    	}
    	
    });
    
} );