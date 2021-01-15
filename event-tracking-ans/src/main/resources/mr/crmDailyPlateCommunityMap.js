function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        plate_id: this.plate_id,
        plate_name: this.plate_name,
        community_id: this.community_id,
        community_name: this.community_name
    }, { date: this.op_time, view_count: 1 });
}