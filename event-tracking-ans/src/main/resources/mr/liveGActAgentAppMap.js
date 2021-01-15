function map() {
    emit({
        game_id: this.game_id,
        login_account: this.login_account
    }, {
        page_view: 1
    });
}