function map() {

    var this_page_view = 0;
    var this_unique_visitor = 0;
    var this_collected = 0;
    var this_reposted = 0;
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
    }



    emit({
        agent_id: this.agent_id,
        community_id: this.community_id,
        house_type: this.house_type,
        city: this.city
    }, {
        page_view: this_page_view,
        unique_visitor: this_unique_visitor,
        unique_visitor_ids: this_unique_visitor_ids,
        collected: this_collected,
        reposted: this_reposted,

        community_name: this.community_name,
        plate_id: this.plate_id,
        plate_name: this.plate_name
    });
}