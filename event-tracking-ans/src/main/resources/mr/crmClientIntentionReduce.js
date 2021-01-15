function reduce(key, values) {
    var house_area = 0;
    var house_total_price = 0;
    var house_average_price = 0;
    var house_bedroom = 0;
    var house_count = 0;
    var update_date = values[0].update_date;
    for(var i = 0; i < values.length; i++) {
        if(update_date < values[i].update_date){
            update_date = values[i].update_date;
        }
        house_area = house_area + values[i].house_area;
        house_bedroom = house_bedroom + values[i].house_bedroom;
        house_total_price = house_total_price + values[i].house_total_price;
        house_average_price = house_average_price + values[i].house_average_price;
        house_count = house_count + values[i].house_count;
    }
    return {
        update_date: update_date,
        house_area: house_area,
        house_total_price: house_total_price,
        house_average_price: house_average_price,
        house_bedroom: house_bedroom,
        house_count: house_count
    };
}