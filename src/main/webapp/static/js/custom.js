// Material Select Initialization
$(document).ready(function() {
    $('.mdb-select').materialSelect();
    $('#categorySelector').change(function(){
        var id= $(this).val();
        var path = '/categoryId=' + id;
        window.location.replace(path);
    });

});