function map() {
    emit({
        game_id: this.game_id,
        agent_id: this.agent_id
    }, {
        page_view: 1
    });
}