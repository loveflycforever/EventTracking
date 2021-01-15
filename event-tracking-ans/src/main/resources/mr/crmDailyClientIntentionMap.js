function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id
    }, {
        date: this.op_time,
        house_total_price: this.house_total_price,
        house_average_price: this.house_average_price,
        house_area: this.house_area,
        house_bedroom: this.house_bedroom,
        house_count: 1
    });
}