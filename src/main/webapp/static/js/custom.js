// Material Select Initialization
$(document).ready(function() {
    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)')
            .exec(window.location.search);

        return (results !== null) ? results[1] || '0' : '0';
    }

    $('.mdb-select').materialSelect();

    $('#categorySelector').change(function(){
        var id= $(this).val();
        var path = '/?categoryId=' + id + '&supplierId=' + $.urlParam('supplierId');
        window.location.replace(path);
    });

    $('#supplierSelector').change(function(){
        var id= $(this).val();
        var path = '/?supplierId=' + id + '&categoryId=' + $.urlParam('categoryId');
        window.location.replace(path);
    });

});