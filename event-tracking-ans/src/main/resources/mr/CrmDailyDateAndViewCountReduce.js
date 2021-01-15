function reduce(key, values) {
    var date = values[0].date;
    var view_count = 0;
    for(var i = 0; i < values.length; i++){
        if(date < values[i].date){
            date = values[i].date;
        }
        view_count += values[i].view_count;
    }
    return { date: date, view_count: view_count };
}