//파일 용량 확인
function formatBytes(bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes';

    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
} 

//영문, under bar, dash, 숫자 만
function handleOnInput(e)  {
	  e.value = e.value.replace(/[^A-Za-z0-9\_\-]/g, '')
}

//숫자, 콤마
function handleOnInputNumber(e){
	e.value = e.value.replace(/[^0-9\,]/g, '')
}

//널 체크
function isEmpty(str){ if(typeof str == "undefined" || str == null || str == "") return ''; else return str; }

/**
 * 테이블 rowspan
 */
function genRowspan(className){
	$("." + className).each(function() {
		var curr = $(this).text();
		var rows = $("." + className).filter(function() {
			return $(this).text() == curr;
		});			
		if (rows.length > 1) {
			rows.eq(0).attr("rowspan", rows.length);
			rows.not(":eq(0)").remove();
		}

	});
}

//요일 텍스트
function dayOfWeek(val){
	let week = ['일','월','화','수','목','금','토']
	return week[val];
}

//날짜 가져오기
function getDateStr(myDate){
	var year = myDate.getFullYear();
	var month = ("0"+(myDate.getMonth()+1)).slice(-2);
	var day = ("0"+myDate.getDate()).slice(-2);
	return ( year + month + day );
}

/* 오늘 날짜 */
function today(id) {
	var d = new Date();
	$("#"+id).val(getDateStr(d));
}

/* 오늘로부터 며칠후 날짜 */
function prevDay(days, days2, id, id2) {
	var d = new Date();
	var dayOfMonth = d.getDate();
	d.setDate(dayOfMonth + days);
	$("#"+id).val(getDateStr(d));
	
	d = new Date();
	dayOfMonth = d.getDate();
	d.setDate(dayOfMonth + days2);
	$("#"+id2).val(getDateStr(d));
}

/* 에러메세지 */
function errorMsg(data){
	
	console.log(data.status);
	console.log(data.return_code);
	console.log(data.type);
	
	if(data.status === 500){
		alert(JSON.stringify(data.error).replace(/\"/gi, ''));
		return true;
	}else if(data.status === 404){
		alert(JSON.stringify(data.error).replace(/\"/gi, ''));
		return true;
	}else if(data.type === "5000"){
		alert(JSON.stringify(data.title).replace(/\"/gi, ''));
		return true;
	}else if(isEmpty(data.return_code).indexOf("40") >= 0){
		alert(JSON.stringify(data.return_msg).replace(/\"/gi, ''));
		return true;
	}else if(isEmpty(data.return_code).indexOf("50") >= 0){
		alert(JSON.stringify(data.return_msg).replace(/\"/gi, ''));
		return true;
	}
}

function jsonToExcelDownload(fileNm, sheetNm, data){
	let filename = fileNm + '.xlsx';
	
	let wb = XLSX.utils.book_new();
	let ws = XLSX.utils.json_to_sheet(data);
	XLSX.utils.book_append_sheet(wb, ws, sheetNm);
	XLSX.writeFile(wb,(filename));
	
	return false;
}

const targetUrl = window.location.pathname;

let arrSplitUrl = [];
arrSplitUrl = targetUrl.split('/');

let firstUrl;
let twoUrl;
let threeUrl;
let fourUrl;
let arrSplitUrlAdd;

for(var i = 1; i < arrSplitUrl.length; i++){
	console.log('['+i+'] : '+arrSplitUrl[i]);
	
	if( i == 1){
		firstUrl = arrSplitUrl[i];
		arrSplitUrlAdd = arrSplitUrl[i];
	}else if( i == 2){
		twoUrl = arrSplitUrl[i];
		arrSplitUrlAdd += "_"+arrSplitUrl[i];
	}else if( i == 3){
		threeUrl = arrSplitUrl[i];
		arrSplitUrlAdd += "_"+arrSplitUrl[i];
	}else if( i == 4){
		fourUrl = arrSplitUrl[i];
		arrSplitUrlAdd += "_"+arrSplitUrl[i];
	}else{
		arrSplitUrlAdd += "_"+arrSplitUrl[i];
	}
}


if(isEmpty(firstUrl) == ''){
	$("#menu_server").addClass("active");
	$("#menu_sub_server").css("display","block");
	$("#server_receive").addClass("current-page");	
}else{
	if(firstUrl == 'server'){
		$("#menu_"+firstUrl).addClass("active");
		$("#menu_sub_"+firstUrl).css("display","block");
		$("#"+firstUrl+"_"+twoUrl).addClass("current-page");	
	}else if(firstUrl == 'sandbox'){
		$("#menu_"+firstUrl).addClass("active");
		$("#menu_sub_"+firstUrl).css("display","block");
		if(twoUrl == 'env' || twoUrl == 'info' || twoUrl == 'model'){
			$("#sub_"+firstUrl+"_"+twoUrl).addClass("active");
			$("#menu_sub_"+firstUrl+"_"+twoUrl).css("display","block");
		}
		$("#"+firstUrl+"_"+twoUrl+"_"+threeUrl).addClass("current-page");	
	}else if(firstUrl == 'admin'){
		firstUrl = twoUrl;
		twoUrl = threeUrl;
		threeUrl = fourUrl;
		$("#menu_"+firstUrl).addClass("active");
		$("#menu_sub_"+firstUrl).css("display","block");
		if(twoUrl == 'env' || twoUrl == 'info' || twoUrl == 'model'){
			$("#sub_"+firstUrl+"_"+twoUrl).addClass("active");
			$("#menu_sub_"+firstUrl+"_"+twoUrl).css("display","block");
		}
		$("#admin_"+firstUrl+"_"+twoUrl+"_"+threeUrl).addClass("current-page");			
	}else{
		$("#menu_dms").addClass("active");
		$("#menu_sub_dms").css("display","block");
		if(firstUrl == 'metaTable' || firstUrl == 'systemMgmt' || firstUrl == 'dataMgmt'){
			$("#sub_"+firstUrl+"_"+twoUrl).addClass("active");
			$("#menu_sub_dms_"+firstUrl).css("display","block");
			$("#"+firstUrl+"_"+twoUrl).addClass("current-page");
		}else if(firstUrl == 'adaptor' || firstUrl == 'linkTarget'){
			
			if(twoUrl == "operate" || twoUrl == "detail" || twoUrl == "connectionSet" || twoUrl == "machingInfo"){
				twoUrl = "config";
			}else if(twoUrl == "preProcessAdd"){
				twoUrl = "preProcessList";
			}else if(twoUrl == "update" || twoUrl == "add"){
				twoUrl = "list";
			}
			$("#sub_dms_"+firstUrl).addClass("active");
			$("#menu_sub_dms_"+firstUrl).css("display","block");
			$("#"+firstUrl+"_"+twoUrl).addClass("current-page");			
		}else{
			if(firstUrl == 'collectionError' || firstUrl == 'dataSet' || firstUrl == 'monitoring'){
				twoUrl = "list";
			}
			$("#"+firstUrl+"_"+twoUrl).addClass("current-page");
		}
		
	}
}











