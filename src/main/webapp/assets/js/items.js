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
} );