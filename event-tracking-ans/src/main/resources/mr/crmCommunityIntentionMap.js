function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id
    }, {
        view_count: this.view_count,
        community_name: this.community_name,
        update_date: this.update_date
    });
}