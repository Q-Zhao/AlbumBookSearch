$(function () {
 

    $("#searchBtn").click(function() {
        
        var keywordInput = $('#searchText').val();
        if (keywordInput.trim() == '') {
            alert("Search input is empty");
            return;
        }
        
        var requestData = {
            "keyword": keywordInput
        };

        console.log(getBaseServiceUrl())
        $.ajax({
            url: getBaseServiceUrl() + "/search",
            type:"POST",
            data: JSON.stringify(requestData),
            dataType: "json",
            contentType: "application/json",
            success:function(result) {
                processResponse(result);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                $("#resultTable").empty();
                alert("Error occured. Status: " + textStatus); 
            }
        });

    });

    function processResponse(response) {
        console.log(response.statusCode);
        var message = "Search of all domains failed."
        if (response.statusCode === "SUCCESS" || response.statusCode === "PARTIAL_SUCCESS") {
            buildResultTable(response.results)
            message = createStatusMessage(response.statusDetails) 
        }
        buildStatusLabel(message);
    }

    function createStatusMessage(statusDetails) {
        var message = "";
        $.each(statusDetails, function(index, detail) {
            if (detail.statusCode === "SUCCESS") {
                message += (detail.source + " search status: " + detail.statusCode + ", " + detail.timeComsumed + " seconds");
            }
            else {
                message += (detail.source + " search status: " + detail.statusCode)
            }
            message += "<br/>"
        });
        return message;
    }

    function buildStatusLabel(message) {
        $("#detailInfo").empty();
        $("<h5></h5>").append(message).appendTo("#detailInfo");
    }

    function buildResultTable(results) {
        $("#resultTable").empty();
        var rowNum = 1;

        $("<tr></tr>")
            .append($("<th></th>").append(""))
            .append($("<th></th>").append("Title"))
            .append($("<th></th>").append("Type"))
            .append($("<th></th>").append("Authors/Artists"))
            .appendTo("#resultTable");

        $.each(results, function(index, result){
            var title = result.title;
            var type = result.type;
            var creators = result.creaters.join(", ");

            var rowNumTd = $("<td></td>").append(rowNum);
            var titleTd = $("<td></td>").append(title);
            var typeTd = $("<td></td>").append(type);
            var creatorsTd = $("<td></td>").append(creators);

            $("<tr></tr>")
                .append(rowNumTd)
                .append(titleTd)
                .append(typeTd)
                .append(creatorsTd)
                .appendTo("#resultTable");
            rowNum++;
        });
    }

    function getBaseServiceUrl() {
        var urlInfo = window.location.href.split('/');
        var protocal = urlInfo[0];
        var hostnamePort = urlInfo[2];
        var appName = urlInfo[3];
        var baseUrl = protocal + '//' + hostnamePort + "/" + appName;
        return baseUrl;
    }

});