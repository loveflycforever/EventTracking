function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        block_id: this.block_id,
        block_name: this.block_name
    }, { view_count: this.view_count, update_date: this.date });
}