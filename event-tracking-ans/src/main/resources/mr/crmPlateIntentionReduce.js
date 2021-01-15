function reduce(key, values) {
    var view_count = values[0].view_count;
    var plate_name = values[0].plate_name;
    var update_date = values[0].update_date;
    for(var i = 0; i < values.length; i++){
        if(update_date < values[i].update_date){
            update_date = values[i].update_date;
        }
        if(view_count < values[i].view_count){
            view_count = values[i].view_count;
            plate_name = values[i].plate_name;
        }
    }
    return { view_count: view_count, plate_name: plate_name, update_date: update_date };
}