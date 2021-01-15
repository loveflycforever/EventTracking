function reduce(key, values) {
    var this_page_view = 0;
    var this_unique_visitor_ids = new Array();
    var this_collected = 0;
    var this_reposted = 0;
    var this_im_connected = 0;
    var this_phone_connected = 0;

    var this_house_name = '';
    var this_house_area = '';
    var this_house_total_price = '';
    var this_house_average_price = '';
    var this_house_area_lower = '';
    var this_house_area_upper = '';
    var this_house_living_room = '';
    var this_house_bedroom = '';
    var this_house_bathroom = '';
    var this_community_id = '';
    var this_community_name = '';
    var this_plate_id = '';
    var this_plate_name = '';
    var this_agent_id = '';
    var this_store_id = '';

    for (var i = 0; i < values.length; i++) {
        this_page_view += values[i].page_view;
        this_collected += values[i].collected;
        this_reposted += values[i].reposted;
        this_im_connected += values[i].im_connected;
        this_phone_connected += values[i].phone_connected;

        this_unique_visitor_ids = this_unique_visitor_ids.concat(values[i].unique_visitor_ids);

        var value_house_name = values[i].house_name;
        var value_house_area = values[i].house_area;
        var value_house_total_price= values[i].house_total_price;
        var value_house_average_price = values[i].house_average_price;
        var value_house_area_lower = values[i].house_area_lower;
        var value_house_area_upper = values[i].house_area_upper;
        var value_house_living_room = values[i].house_living_room;
        var value_house_bedroom = values[i].house_bedroom;
        var value_house_bathroom = values[i].house_bathroom;
        var value_community_id = values[i].community_id;
        var value_community_name = values[i].community_name;
        var value_plate_id = values[i].plate_id;
        var value_plate_name = values[i].plate_name;
        var value_agent_id = values[i].agent_id;
        var value_store_id = values[i].store_id;

        if(value_house_name !== '') {
            this_house_name = value_house_name;
        }

        if(value_house_area !== '') {
            this_house_area = value_house_area;
        }

        if(value_house_total_price !== '') {
            this_house_total_price = value_house_total_price;
        }

        if(value_house_average_price !== '') {
            this_house_average_price = value_house_average_price;
        }

        if(value_house_area_lower !== '') {
            this_house_area_lower = value_house_area_lower;
        }

        if(value_house_area_upper !== '') {
            this_house_area_upper = value_house_area_upper;
        }

        if(value_house_living_room !== '') {
            this_house_living_room = value_house_living_room;
        }

        if(value_house_bedroom !== '') {
            this_house_bedroom = value_house_bedroom;
        }

        if(value_house_bathroom !== '') {
            this_house_bathroom = value_house_bathroom;
        }

        if(value_community_id !== '') {
            this_community_id = value_community_id;
        }

        if(value_community_name !== '') {
            this_community_name = value_community_name;
        }

        if(value_plate_id !== '') {
            this_plate_id = value_plate_id;
        }

        if(value_plate_name !== '') {
            this_plate_name = value_plate_name;
        }

        if(value_agent_id !== '') {
            this_agent_id = value_agent_id;
        }

        if(value_store_id !== '') {
            this_store_id = value_store_id;
        }
    }
    var temp = new Set(this_unique_visitor_ids);
    this_unique_visitor_ids = Array.from(temp);
    return {
        page_view: this_page_view,
        unique_visitor: temp.size,
        unique_visitor_ids: this_unique_visitor_ids,
        collected: this_collected,
        reposted: this_reposted,
        im_connected: this_im_connected,
        phone_connected: this_phone_connected,

        house_name: this_house_name,
        house_area: this_house_area,
        house_total_price: this_house_total_price,
        house_average_price: this_house_average_price,
        house_area_lower: this_house_area_lower,
        house_area_upper: this_house_area_upper,
        house_living_room: this_house_living_room,
        house_bedroom: this_house_bedroom,
        house_bathroom: this_house_bathroom,
        community_id: this_community_id,
        community_name: this_community_name,
        plate_id: this_plate_id,
        plate_name: this_plate_name,
        agent_id: this_agent_id,
        store_id: this_store_id
    };
}