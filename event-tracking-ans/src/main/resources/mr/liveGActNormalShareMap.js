function map() {
    emit({
        game_id: this.game_id,
        test: this.test
    }, {
        page_view: 1
    });
}