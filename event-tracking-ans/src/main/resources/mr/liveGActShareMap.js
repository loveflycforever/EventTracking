function map() {
    emit({
        game_id: this.game_id
    }, {
        page_view: 1
    });
}