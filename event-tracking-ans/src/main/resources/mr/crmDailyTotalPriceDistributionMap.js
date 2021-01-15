function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        total_price_range: this.total_price_range
    }, { date: this.op_time, view_count: 1 });
}