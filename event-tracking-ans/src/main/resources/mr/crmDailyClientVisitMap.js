function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id
    }, {
        date: this.op_time,
        collect_page_name: this.page_name,
        collect_method_name: this.method_name,
        tel_page_name: this.page_name,
        tel_method_name: this.method_name,
        view_count: 1,
        collect_count: 1,
        tel_count: 1
    });
}