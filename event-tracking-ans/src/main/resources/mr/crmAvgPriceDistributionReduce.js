function reduce(key, values) {
    var view_count = 0;
    for(var i = 0; i < values.length; i++){
        if(values[i].mark === 0){
            view_count += values[i].view_count;
        }
    }
    return { view_count: view_count, mark: 0 };
}