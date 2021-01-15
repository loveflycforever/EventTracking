function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        block_id: this.block_id,
        community_id: this.community_id,
        community_name: this.community_name
    }, { view_count: this.view_count, update_date: this.date });
}