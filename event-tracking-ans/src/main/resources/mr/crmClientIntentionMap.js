function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id
    }, {
        update_date: this.date,
        house_area: this.house_area,
        house_total_price: this.house_total_price,
        house_average_price: this.house_average_price,
        house_bedroom: this.house_bedroom,
        house_count: this.house_count
    });
}