$(function(){

    const appendCar = function(data){
        var carCode = '<a href="#" class="car-link" data-id="'+
        data.id + '">' + data.name + '</a><br>';
        $('#car-list')
           .append('<div>' + carCode + '</div>');
    };

   /* //Loading books on load page
    $.get('/cars/', function(response)
    {
        for(i in response) {
            appendCar(response[i]);
        }
    });*/

    //Show adding book form
    $('#show-add-car-form').click(function(){
        $('#car-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#car-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting book
        $(document).on('click', '.car-link', function(){
            var link = $(this);
            var carId = link.data('id');
            $.ajax({
                method: "GET",
                url: '/cars/' + carId,
                success: function(response)
                {
                    var code = '<span>Год выпуска:' + response.year + '</span>';
                    link.parent().append(code);
                },
                error: function(response)
                {
                    if(response.status == 404) {
                        alert('Автомобиль отсутствует');
                    }
                }
            });
            return false;
        });

    //Adding book
        $('#save-car').click(function()
        {
            var data = $('#car-form form').serialize();
           /* if(data.name = null){
            return false;
            }*/
            $.ajax({
                method: "POST",
                url: '/cars/',
                data: data,
                success: function(response)
                {
                    $('#car-form').css('display', 'none');
                    var car = {};
                    car.id = response;
                    var dataArray = $('#car-form form').serializeArray();
                    for(i in dataArray) {
                        todo[dataArray[i]['name']] = dataArray[i]['value'];
                    }
                    appendCar(car);
                }
            });
            return false;
        });
});