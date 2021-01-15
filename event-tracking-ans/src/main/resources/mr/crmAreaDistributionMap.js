function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        area_range: this.area_range
    }, { view_count: this.view_count, mark: this.mark });
}