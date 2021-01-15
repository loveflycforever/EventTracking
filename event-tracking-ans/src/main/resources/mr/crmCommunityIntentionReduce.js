function reduce(key, values) {
    var view_count = values[0].view_count;
    var community_name = values[0].community_name;
    var update_date = values[0].update_date;
    for(var i = 0; i < values.length; i++){
        if(update_date < values[i].update_date){
            update_date = values[i].update_date;
        }
        if(view_count < values[i].view_count){
            view_count = values[i].view_count;
            community_name = values[i].community_name;
        }
    }
    return { view_count: view_count, community_name: community_name, update_date: update_date };
}