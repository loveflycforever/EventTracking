function reduce(key, values) {
    var view_count = 0;
    var update_date = values[0].update_date;
    for(var i = 0; i < values.length; i++){
        if(update_date < values[i].update_date){
            update_date = values[i].update_date;
        }
        view_count = view_count + values[i].view_count;
    }
    return { view_count: view_count, update_date: update_date };
}