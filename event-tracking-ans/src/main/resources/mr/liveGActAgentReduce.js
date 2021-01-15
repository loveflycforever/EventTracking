function reduce(key, values) {
    var this_page_view = 0;

    for (var i = 0; i < values.length; i++) {
        this_page_view += values[i].page_view;
    }

    return {
        page_view: this_page_view
    };
}