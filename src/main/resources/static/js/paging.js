//페이징 처리
const paging = function(totalData, dataPerPage, pageCount, currentPage) {

	totalPage = Math.ceil(totalData / dataPerPage); //총 페이지 수

	if (totalPage < pageCount) {
		pageCount = totalPage;
	}

	let pageGroup = Math.ceil(currentPage / pageCount); // 페이지 그룹
	let last = pageGroup * pageCount; //화면에 보여질 마지막 페이지 번호

	if (last > totalPage) {
		last = totalPage;
	}

	let first = last - (pageCount - 1); //화면에 보여질 첫번째 페이지 번호
	let next = last + 1;
	let prev = first - 1;

	let pageHtml = "";

	if (prev > 0) {
		pageHtml += "<li class='page-item'><a href='#'class='page-link' id='prev'>&lt;</a></li>";
	}

	//페이징 번호 표시 
	for (var i = first; i <= last; i++) {
		if (currentPage == i) {
			pageHtml +=
				"<li class='page-item active'><a href='#' class='page-link' id='" + i + "'>" + i + "</a></li>";
		} else {
			pageHtml += "<li class='page-item'><a href='#' class='page-link' id='" + i + "'>" + i + "</a></li>";
		}
	}

	if (last < totalPage) {
		pageHtml += "<li class='page-item'><a href='#' class='page-link' id='next'>&gt;</a></li>";
	}

	$("#pagingul").html(pageHtml);
	
	//전체값 표기
//	let displayCount = "";
//	displayCount = "현재 1 - " + totalPage + " 페이지 / " + totalData + "건";
//	$("#displayCount").text(displayCount);

	$("#pagingul li a").click(function() {
		
		let $id = $(this).attr("id");
		selectedPage = $(this).text();
	
		if ($id == "next") selectedPage = next;
		if ($id == "prev") selectedPage = prev;
	
		//전역변수에 선택한 페이지 번호를 담는다...
		page = selectedPage;
		
		pagingFun(page, size_per_page);
	
	});
}