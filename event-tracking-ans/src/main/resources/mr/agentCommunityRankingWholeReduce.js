function reduce(key, values) {
    var this_page_view = 0;
    var this_collected = 0;
    var this_reposted = 0;
    var this_unique_visitor_ids = new Array();
    var this_community_name = '';
    var this_plate_id = '';
    var this_plate_name = '';
    for (var i = 0; i < values.length; i++) {
        var value_community_name = values[i].community_name;
        if (value_community_name !== '') {
            this_community_name = value_community_name;
        }

        var value_plate_id = values[i].plate_id;
        if (value_plate_id !== '') {
            this_plate_id = value_plate_id;
        }

        var value_plate_name = values[i].plate_name;
        if (value_plate_name !== '') {
            this_plate_name = value_plate_name;
        }

        this_page_view += values[i].page_view;
        this_collected += values[i].collected;
        this_reposted += values[i].reposted;

        this_unique_visitor_ids = this_unique_visitor_ids.concat(values[i].unique_visitor_ids);
    }
    var temp = new Set(this_unique_visitor_ids);
    this_unique_visitor_ids = Array.from(temp);
    return {
        page_view: this_page_view,
        unique_visitor: temp.size,
        unique_visitor_ids: this_unique_visitor_ids,
        collected: this_collected,
        reposted: this_reposted,

        community_name: this_community_name,
        plate_id: this_plate_id,
        plate_name: this_plate_name
    };
}