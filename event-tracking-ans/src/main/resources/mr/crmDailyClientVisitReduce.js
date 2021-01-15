function reduce(key, values) {
    var date = values[0].date;
    var view_count = 0;
    var collect_count = 0;
    var tel_count = 0;
    var collect_page_name = "";
    var collect_method_name = "";
    var tel_page_name = "";
    var tel_method_name = "";
    for(var i = 0; i < values.length; i++){
        if(date < values[i].date){
            date = values[i].date;
        }
        view_count += values[i].view_count;
        if(values[i].collect_page_name === "subPackages/shopDetail/newHouseDetail" || values[i].collect_method_name === "focusChoose"){
            collect_count += values[i].collect_count;
            collect_page_name = values[i].collect_page_name;
            collect_method_name = values[i].collect_method_name;
        }
        if(values[i].tel_page_name === "subPackages/shopDetail/newHouseDetail" || values[i].tel_method_name === "focusChoose"){
            tel_count += values[i].tel_count;
            tel_page_name = values[i].tel_page_name;
            tel_method_name = values[i].tel_method_name;
        }
    }
    return {
        date: date,
        collect_page_name: collect_page_name,
        collect_method_name: collect_method_name,
        tel_page_name: tel_page_name,
        tel_method_name: tel_method_name,
        view_count: view_count,
        collect_count: collect_count,
        tel_count: tel_count
    };
}