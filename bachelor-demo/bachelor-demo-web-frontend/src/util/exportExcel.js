//导出excel
function exportExcel(data) {
  // console.log(data)
  require.ensure([], () => {
    let {export_json_to_excel} = require("./Export2Excel");
    let tHeader = []; //生成Excel表格的头部标题栏
    let filterVal = []; //生成Excel表格的内容栏（根据自己的数据内容属性填写）
    let list = data.excelData;//需要导出Excel的数据
    let obj = []
    data.tHeader.forEach((item,index)=>{
      // console.log(item.key,key,item.name)
      tHeader.push(item.name)
      filterVal.push(item.key)
    })
    // console.log(filterVal)
    // console.log(list)
    let dataList = formatJson(filterVal, list);
    // console.log(dataList)
    export_json_to_excel(tHeader, dataList, data.fileName); //这里可以定义你的Excel表的默认名称
  });
}
function formatJson(filterVal, jsonData) {
  return jsonData.map(v => filterVal.map(j => v[j]));
}
export default {
  exportExcel,
}
