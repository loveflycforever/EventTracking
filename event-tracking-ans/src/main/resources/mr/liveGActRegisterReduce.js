function reduce(key, values) {
    var this_unique_visitor_ids = [];

    for (var i = 0; i < values.length; i++) {
        this_unique_visitor_ids = this_unique_visitor_ids.concat(values[i].unique_visitor_ids);
    }

    var temp = new Set(this_unique_visitor_ids);
    this_unique_visitor_ids = Array.from(temp);

    return {
        unique_visitor: temp.size,
        unique_visitor_ids: this_unique_visitor_ids
    };
}