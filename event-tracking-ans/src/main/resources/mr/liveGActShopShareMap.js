function map() {
    emit({
        game_id: this.game_id,
        store_id: this.store_id
    }, {
        page_view: 1
    });
}