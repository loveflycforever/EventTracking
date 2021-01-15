function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id
    }, {
        view_count: this.view_count,
        block_name: this.block_name,
        update_date: this.update_date
    });
}