//페이징 처리
const paging = function(totalData, dataPerPage, pageCount, currentPage) {

	if(isEmpty(totalData) !== ''){
		
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
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='first'>&lt;&lt;</a></li>";
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='prev'>&lt;</a></li>";
		}
	
		//페이징 번호 표시 
		for (var i = first; i <= last; i++) {
			if (currentPage == i) {
				pageHtml +=
					"<li class='page-item active'><a href='javascript:void(0);' class='page-link' id='" + i + "'>" + i + "</a></li>";
			} else {
				pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='" + i + "'>" + i + "</a></li>";
			}
		}
	
		if (last < totalPage) {
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='next'>&gt;</a></li>";
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='last'>&gt;&gt;</a></li>";
		}
	
		$("#pagingul").html(pageHtml);
		
		//전체값 표기
	//	let displayCount = "";
	//	displayCount = "현재 1 - " + totalPage + " 페이지 / " + totalData + "건";
	//	$("#displayCount").text(displayCount);
	
		$("#pagingul li a").click(function() {
			
			let $id = $(this).attr("id");
			selectedPage = $(this).text();
		
			if ($id == "first") selectedPage = 1;
			if ($id == "next") selectedPage = next;
			if ($id == "prev") selectedPage = prev;
			if ($id == "last") selectedPage = totalPage;
		
			//전역변수에 선택한 페이지 번호를 담는다...
			page = selectedPage;
			
			pagingFun(page, size_per_page);
		
		});
	}
}

//페이징 처리
const paging2 = function(totalData, dataPerPage, pageCount, currentPage) {

	if(isEmpty(totalData) !== ''){
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
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='first'>&lt;&lt;</a></li>";
			pageHtml += "<li class='page-item'><a href='javascript:void(0);'class='page-link' id='prev'>&lt;</a></li>";
		}
	
		//페이징 번호 표시 
		for (var i = first; i <= last; i++) {
			if (currentPage == i) {
				pageHtml +=
					"<li class='page-item active'><a href='javascript:void(0);' class='page-link' id='" + i + "'>" + i + "</a></li>";
			} else {
				pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='" + i + "'>" + i + "</a></li>";
			}
		}
	
		if (last < totalPage) {
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='next'>&gt;</a></li>";
			pageHtml += "<li class='page-item'><a href='javascript:void(0);' class='page-link' id='last'>&gt;&gt;</a></li>";
		}
	
		$("#pagingul2").html(pageHtml);
		
		//전체값 표기
	//	let displayCount = "";
	//	displayCount = "현재 1 - " + totalPage + " 페이지 / " + totalData + "건";
	//	$("#displayCount").text(displayCount);
	
		$("#pagingul2 li a").click(function() {
			
			let $id = $(this).attr("id");
			selectedPage = $(this).text();
		
			if ($id == "first") selectedPage = 1;
			if ($id == "next") selectedPage = next;
			if ($id == "prev") selectedPage = prev;
			if ($id == "last") selectedPage = totalPage;
			
			//전역변수에 선택한 페이지 번호를 담는다...
			page = selectedPage;
			
			pagingFun2(page, size_per_page);
		
		});
	}
}