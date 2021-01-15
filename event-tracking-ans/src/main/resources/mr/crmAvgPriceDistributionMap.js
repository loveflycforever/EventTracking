function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        avg_price_range: this.avg_price_range
    }, { view_count: this.view_count, mark: this.mark });
}