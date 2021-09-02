
url = "http://localhost:8080/flight-api/flight";

window.onload = function(){


    var flightal = [];
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url)
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            JSON.parse(xhr.responseText).forEach(element => {
                flightal.push(element);
                updateTable(element);
            })
        }
    }
    xhr.send();

    //post
    document.getElementById("new-flight").addEventListener("submit", function(e){
        e.preventDefault();
        var flight = {plane_id:"",
                departure_location:"",
                arrival_location:"",
                departure_date:"",
                arrival_date:""
                }
        
        flight.plane_id = document.getElementById("plane_id").value;
        flight.departure_location = document.getElementById("departure_location").value;
        flight.arrival_location = document.getElementById("flight-date").value;
        flight.departure_date = document.getElementById("return-date").value;
        flight.arrival_date = document.getElementById("flight-place").value;
        var xhrPost = new XMLHttpRequest();
        xhrPost.open("POST", url)
        xhrPost.onreadystatechange = function(){
            
            if(xhrPost.readyState === 4 && xhrPost.status < 400){
                flight = JSON.parse(xhrPost.response);
                updateTable(flight);
                alert("Successfully added flightal");
                document.getElementById("plane_id").value = "";
                document.getElementById("departure_location").value = "";
                document.getElementById("flight-date").value = "";
                document.getElementById("return-date").value = "";
                document.getElementById("flight-place").value = "";
                document.getElementById("return-place").value = "";
            }else if(xhrPost.readyState === 4){
                alert("There was an error adding your flightal");
            }
        }
        xhrPost.send(JSON.stringify(flight));
    });
}

//update
updateTable = function(flightal){
        var row = document.createElement("TR");
        var idCol = document.createElement("TH");
        var plane_idCol = document.createElement("TD");
        var departure_locationCol = document.createElement("TD");
        var arrival_locationCol = document.createElement("TD");
        var departure_dateCol = document.createElement("TD");
        var arrival_dateCol = document.createElement("TD");
        var deleteCol = document.createElement("TD");
        var editCol = document.createElement("TD");

        //buttons
        var editButton = document.createElement("button");
        var deleteButton = document.createElement("button");
        editButton.innerHTML = "Edit";
        editButton.setAttribute("class", "btn btn-secondary");
        editCol.appendChild(editButton);
        deleteButton.innerHTML = "Delete";
        editButton.setAttribute("onclick", `editMode(${flightal.flight_id})`);
        deleteButton.setAttribute("class", "btn btn-secondary");
        deleteCol.appendChild(deleteButton);
        deleteButton.setAttribute("onclick", `deleteItem(${flightal.flight_id})`);

        //bootstrap/css stuff
        idCol.innerText = flightal.flight_id;
        idCol.setAttribute("scope", "row");

        plane_idCol.innerText = flightal.plane_id;
        departure_locationCol.innerText = flightal.departure_location;
        arrival_locationCol.innerText = flightal.arrival_location;
        departure_dateCol.innerText = flightal.departure_date;
        arrival_dateCol.innerText = flightal.arrival_date;

        //delete and edit stuff
        row.setAttribute("id", `${flightal.flight_id}a`);
        plane_idCol.setAttribute("id", `${flightal.flight_id}b`);
        departure_locationCol.setAttribute("id", `${flightal.flight_id}c`);
        arrival_locationCol.setAttribute("id", `${flightal.flight_id}d`);
        departure_dateCol.setAttribute("id", `${flightal.flight_id}e`);
        arrival_dateCol.setAttribute("id", `${flightal.flight_id}f`);
        idCol.setAttribute("id", `${flightal.flight_id}edit`);





        row.appendChild(idCol);
        row.appendChild(plane_idCol);
        row.appendChild(departure_locationCol);
        row.appendChild(arrival_locationCol);
        row.appendChild(departure_dateCol);
        row.appendChild(arrival_dateCol);
        row.appendChild(deleteCol);
        row.appendChild(editCol);
        
        document.getElementById("tbody").appendChild(row);
    }
    //delete
deleteItem = function(flight_id){
        console.log(flight_id);
        var xhrDelete = new XMLHttpRequest();
        xhrDelete.open("DELETE", url)
        xhrDelete.send(JSON.stringify(flight_id));
        xhrDelete.onreadystatechange = function(){
            if(xhrDelete.readyState === 4 && xhrDelete.status < 400){
                console.log(xhrDelete.readyState);
                console.log(flight_id + "a")
                let body = document.getElementById("tbody");
                let row = document.getElementById(flight_id + "a");
                body.removeChild(row);
            }else if(xhrDelete.readyState === 4){
                alert("There was an error deleting");
            }
        }
    }

    //edit
editItem = function(flight_id){
        
        var flight = {
                flight_id:"",
                plane_id:"",
                departure_location:"",
                arrival_location:"",
                departure_date:"",
                arrival_date:"",
                
                }
        flight.flight_id=flight_id;
        flight.plane_id = document.getElementById(`inIdb${flight_id}`).value;
        flight.departure_location = document.getElementById(`inIdc${flight_id}`).value;
        flight.arrival_location = document.getElementById(`inIdd${flight_id}`).value;
        flight.departure_date = document.getElementById(`inIde${flight_id}`).value;
        flight.arrival_date = document.getElementById(`inIdf${flight_id}`).value;

        var xhrEdit = new XMLHttpRequest();
        xhrEdit.open("PUT", url)
        xhrEdit.send(JSON.stringify(flight));
        xhrEdit.onreadystatechange = function(){
            if(xhrEdit.readyState === 4 && xhrEdit.status == 201){
                flight = JSON.parse(xhrEdit.response);
                let submitButton = document.getElementById(`submitButton${flight_id}`);
                submitButton.parentElement.removeChild(submitButton);
                let columns = ['b','c','d','e','f'];
                console.log(flight);
                columns.forEach(function(letter, index, array){
                    editObj = document.getElementById(flight_id + letter);
                    switch(letter){
                        case 'b': editObj.innerHTML = flight.plane_id;
                        break;
                        case 'c': editObj.innerHTML = flight.departure_location;
                        break;
                        case 'd': editObj.innerHTML = flight.arrival_location;
                        break;
                        case 'e': editObj.innerHTML = flight.departure_date;
                        break;
                        case 'f': editObj.innerHTML = flight.arrival_date;
                
                    }

                })

            }
        }
    }
    //Edit mode (enable editing on row)
    editMode = function(flight_id){
        if(document.getElementById(`submitButton${flight_id}`) == null)
        {
        let columns = ['b','c','d','e','f'];
        columns.forEach(function(letter, index, array){
            editObj = document.getElementById(flight_id + letter);
            let input = document.createElement("input");
            input.setAttribute("id", `inId${letter}${flight_id}`);
            input.defaultValue = editObj.innerHTML;
            editObj.innerHTML = "";
            editObj.appendChild(input);
        })
        submitCol = document.getElementById(flight_id+"edit");
        submitButton = document.createElement("button");
        submitButton.setAttribute("id", `submitButton${flight_id}`);
        submitButton.innerHTML = "submit";
        submitButton.setAttribute("class", "btn btn-primary");
        submitButton.setAttribute("onclick", `editItem(${flight_id})`);
        submitCol.appendChild(submitButton);
        }else{
            console.log("already in edit mode");
        }
    }

    //search specific flight_id
    searchByID = function(){
        var flight_id = document.getElementById("flightalID").value;
        console.log(flight_id);
        var getXHR = new XMLHttpRequest();
        var url01 = url+ "?" + `flight_id=${flight_id}`;
        getXHR.open("GET", url01);
        getXHR.send();
        getXHR.onreadystatechange = function(){
            if(getXHR.readyState == 4 && getXHR.status < 400){
                clearTable();
                flight = JSON.parse(getXHR.response)[0];
                updateTable(flight);
                createAlert("Successfully Found!", "success");
            }
            else if(getXHR.readyState == 4){
                createAlert("There was an error", "danger");

            }
        }
    }
    //clear the table
   clearTable = function(){
        tableBody = document.getElementById("tbody");
        tableBody.innerHTML = "";
    }

    //alerts
createAlert = function(msg, type){

        switch(type){
            case "success":
                alert(msg);
            break;
            case "danger":{
                alert(msg);
            break;
            }
        }

    }


