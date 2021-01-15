function reduce(key, values) {
    var date = values[0].date;
    var house_total_price = 0;
    var house_average_price = 0;
    var house_area = 0;
    var house_bedroom = 0;
    var house_count = 0;
    for(var i = 0; i < values.length; i++){
        if(date < values[i].date){
            date = values[i].date;
        }
        house_count += values[i].house_count;
        house_bedroom += values[i].house_bedroom;
        if(key.house_type === "NWHS"){
            house_total_price = house_total_price + values[i].house_total_price;
            house_average_price = house_average_price + values[i].house_average_price;
            house_area = house_area + values[i].house_area;
        }else if(key.house_type === "SHHS"){
            house_total_price = house_total_price + values[i].house_total_price;
            house_average_price = house_average_price + values[i].house_average_price;
            house_area = house_area + values[i].house_area;
        }else if(key.house_type === "RTHS"){
            house_average_price = house_average_price + values[i].house_average_price;
            house_area = house_area + values[i].house_area;
        }
    }
    if(key.house_type !== "RTHS"){
        return {
            date: date,
            house_total_price: house_total_price,
            house_average_price: house_average_price,
            house_area: house_area,
            house_bedroom: house_bedroom,
            house_count: house_count
        };
    }else{
        return {
            date: date,
            house_total_price: 0,
            house_average_price: house_average_price,
            house_area: house_area,
            house_bedroom: house_bedroom,
            house_count: house_count
        };
    }
}