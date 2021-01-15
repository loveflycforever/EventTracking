function map() {

    var this_page_view = 0;
    var this_unique_visitor = 0;
    var this_collected = 0;
    var this_reposted = 0;
    var this_im_connected = 0;
    var this_phone_connected = 0;
    var this_unique_visitor_ids = new Array();

    var this_action_definition = this.action_definition;

    for (var i = 0; i < this_action_definition.length; i++) {

        if (this_action_definition[i] === 'VIS_HUS') {
            this_page_view = 1;
            var this_unique_id = this.unique_id;
            if(this_unique_id !== '') {
                this_unique_visitor = 1;
                this_unique_visitor_ids = this_unique_visitor_ids.concat(this_unique_id)
            }
        }

        if (this_action_definition[i] === 'COL_HUS') {
            this_collected = 1;
        }

        if (this_action_definition[i] === 'RPS_HUS') {
            this_reposted = 1;
        }

        if (this_action_definition[i] === 'IMC_HUS') {
            this_im_connected = 1;
        }

        if (this_action_definition[i] === 'PNC_HUS') {
            this_phone_connected = 1;
        }
    }

    emit({
        house_id: this.house_id,
        house_type: this.house_type,
        city: this.city
    }, {
        page_view: this_page_view,
        unique_visitor: this_unique_visitor,
        unique_visitor_ids: this_unique_visitor_ids,
        collected: this_collected,
        reposted: this_reposted,
        im_connected: this_im_connected,
        phone_connected: this_phone_connected,

        house_name: this.house_name,
        house_area: this.house_area,
        house_total_price: this.house_total_price,
        house_average_price: this.house_average_price,
        house_area_lower: this.house_area_lower,
        house_area_upper: this.house_area_upper,
        house_living_room: this.house_living_room,
        house_bedroom: this.house_bedroom,
        house_bathroom: this.house_bathroom,
        community_id: this.community_id,
        community_name: this.community_name,
        plate_id: this.plate_id,
        plate_name: this.plate_name,
        agent_id: this.agent_id,
        store_id: this.store_id
    });
}