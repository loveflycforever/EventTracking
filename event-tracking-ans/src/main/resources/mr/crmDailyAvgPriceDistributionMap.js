function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        avg_price_range: this.avg_price_range
    }, { date: this.op_time, view_count: 1 });
}