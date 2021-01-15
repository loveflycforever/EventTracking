function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        plate_id: this.plate_id,
        plate_name: this.plate_name
    }, { view_count: this.view_count, update_date: this.date  });
}