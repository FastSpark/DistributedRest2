/**
 * Created by Buddhi on 11/10/2017.
 */

//<![CDATA[

var baseUrl = window.location.href;
var tablUpdIntId;

document.getElementById("connect").addEventListener("click", function () {

});

document.getElementById("search").addEventListener("click", function () {
    var file_name = document.getElementById("filesearchstring").value;
    // var sendString = "SEARCH_FILES " + file_name;

    $.ajax({
        contentType: 'application/json;charset=UTF-8',
        url: baseUrl + 'search/' + file_name,
        dataType: 'json',
        type: 'GET',
        cache: false, // Force requested pages not to be cached by the browser
        processData: false, // Avoid making query string instead of JSON
    }).done(function (data) {
        console.log('AJAX call was successfully executed!' + data);
    }).fail(function (data) {
        console.log('AJAX call failed!');
    });

});

//
// var tbody = document.getElementById('file_body'), tr, td;
// files.forEach(function (element) {
//     tr = document.createElement('tr');
//     td = document.createElement('td');
//     var t = document.createTextNode(element);
//     td.appendChild(t);
//     tr.appendChild(td);
//     tbody.appendChild(tr);
//     console.log(element);
// });
//
// var fileUpdateID;
//
//
// document.getElementById("filesearch").addEventListener("keypress",
//     function (e) {
//         var key = e.which || e.keyCode;
//         if (key === 13) { // 13 is enter
//             var searchelement = document.getElementById("filesearch").value;
//
//             searchFile(searchelement);
// //      fileUpdateID=setInterval(function(){
// //
// //    	  searchNodeFile(searchelement);
// //
// //    	}, 1000);
// //      clearInterval(fileUpdateID)
//
//         }
//
//     }, false);
//
// document.getElementById("submit_button").addEventListener("click",
//     function () {
//         var username = document.getElementById("user_name").value;
//         var serverIp = document.getElementById("server_ip").value;
//         var serverPort = document.getElementById("server_port").value;
//         console.log(serverIp);
//         if (username.trim() != "" && serverIp.trim() != "" && serverPort.trim() != "") {
//             registerUser(username, serverIp, serverPort);
//         }
//
//     }, false);
//
// document.getElementById("refresh_button").addEventListener("click",
//     function () {
//         var file_name = document.getElementById("filesearch").value;
//         searchNodeFile(file_name);
//
//     }, false);
//
// document.getElementById("unregister_button").addEventListener("click",
//     function () {
//         clearInterval(tablUpdIntId);
//         var myip = document.getElementById("ip").value;
//         var myport = document.getElementById("port").value;
//         var username = document.getElementById("user_name").value;
//         if (username.trim() != "") {
//             unregisterUser(username);
//         }
//
//     }, false);
//
// function registerUser(username, serverIp, serverPort) {
//
//     $.ajax({
//         contentType: 'application/json;charset=UTF-8',
//         url: baseUrl + 'register/user',
//         dataType: 'json',
//         type: 'POST',
//         cache: false, // Force requested pages not to be cached by the browser
//         processData: false, // Avoid making query string instead of JSON
//         data: JSON.stringify({
//             user: username,
//             ip: serverIp,
//             port: serverPort
//         })
//
//     }).done(function (data) {
//         console.log('AJAX call was successfully executed ;)' + data);
//         if (data.success == "true") {
//             document.getElementById("user_name").disabled = true;
//             document.getElementById("submit_button").disabled = true;
//             document.getElementById("server_ip").disabled = true;
//             document.getElementById("server_port").disabled = true;
//             document.getElementById("div_bottom").hidden = false;
//             tablUpdIntId = setInterval(function () {
//                 getNeighbours();
//             }, 20000);
//             window.alert(data.result);
//         }
//         else {
//             window.alert(data.result);
//         }
//     }).fail(function (data) {
//         console.log(data);
//         console.log('AJAX call failed :(');
//     });
// }
//
// function unregisterUser(username) {
//
//     $.ajax({
//         contentType: 'application/json;charset=UTF-8',
//         url: baseUrl + 'register/nouser',
//         dataType: 'json',
//         type: 'POST',
//         cache: false, // Force requested pages not to be cached by the browser
//         processData: false, // Avoid making query string instead of JSON
//         data: JSON.stringify({
//             username: username
//         })
//
//     }).done(function (data) {
//         console.log('AJAX call was successfully executed ;)');
//         if (data.success == "true") {
//             document.getElementById("user_name").disabled = false;
//             document.getElementById("submit_button").disabled = false;
//             document.getElementById("server_ip").disabled = false;
//             document.getElementById("server_port").disabled = false;
//             document.getElementById("div_bottom").hidden = true;
//             window.alert(data.result);
//         }
//         else {
//             window.alert(data.result);
//         }
//     }).fail(function (data) {
//         console.log(data);
//         console.log('AJAX call failed :(');
//     });
// }
//
// function getNeighbours() {
//     $.ajax({
//         contentType: 'application/json;charset=UTF-8',
//         url: baseUrl + '/request/neighbours',
//         dataType: 'json',
//         type: 'GET',
//         cache: false, // Force requested pages not to be cached by the browser
//         processData: false, // Avoid making query string instead of JSON
//
//     }).done(function (data) {
//
//         console.log('AJAX call was successfully executed ;)');
//         console.log(data);
//         var neighBody = document.getElementById("table_neighbours");
//         while (neighBody.firstChild) {
//             neighBody.removeChild(neighBody.firstChild);
//         }
//
//         data.forEach(function (element) {
//             tr = document.createElement('tr');
//             td = document.createElement('td');
//             var t = document.createTextNode(element.ip);
//             td.appendChild(t);
//             tr.appendChild(td);
//             td = document.createElement('td');
//             var t = document.createTextNode(element.port);
//             td.appendChild(t);
//             tr.appendChild(td);
//             neighBody.appendChild(tr);
//             console.log(element);
//         });
//
//     }).fail(function (data) {
//         console.log(data);
//         console.log('AJAX call failed :(');
//     });
// }
//
//
// function searchFile(fileName) {
//
//     $.ajax({
//         contentType: 'application/json;charset=UTF-8',
//         url: baseUrl + 'selfSearch',
//         dataType: 'json',
//         type: 'POST',
//         cache: false, // Force requested pages not to be cached by the browser
//         processData: false, // Avoid making query string instead of JSON
//         data: JSON.stringify({
//             file_name: fileName
//         })
//
//     }).done(function (data) {
//         console.log('AJAX call was successfully executed ;)');
//         var searchedbody = document.getElementById('searched_file'), tr, td;
//         while (searchedbody.firstChild) {
//             searchedbody.removeChild(searchedbody.firstChild);
//         }
//         console.log(data);
//         tr = document.createElement('tr');
//         td1 = document.createElement('td');
//         tr.appendChild(td1);
//         var t1 = document.createTextNode(baseUrl);
//         td1.appendChild(t1);
//         td2 = document.createElement('td');
//         for (var key in data) {
//             var val = data[key];
//             tr1 = document.createElement('tr');
//             td3 = document.createElement('td');
//             var t2 = document.createTextNode(val);
//             td3.appendChild(t2);
//             tr1.appendChild(td3);
//             td2.appendChild(tr1);
//         }
//         tr.appendChild(td2);
//         td4 = document.createElement('td');
//         var t3 = document.createTextNode("1");
//         td4.appendChild(t3);
//         tr.appendChild(td4);
//         if (data.length > 0) {
//             searchedbody.appendChild(tr);
//         }
//         searchNodeFile(fileName);
//     }).fail(function (data) {
//         console.log(data);
//         console.log('AJAX call failed :(');
//     });
// }
//
// function searchNodeFile(fileName) {
//
//     $.ajax({
//         contentType: 'application/json;charset=UTF-8',
//         url: baseUrl + 'request/result',
//         dataType: 'json',
//         type: 'GET',
//         cache: false, // Force requested pages not to be cached by the browser
//         processData: false, // Avoid making query string instead of JSON
//
//
//     }).done(function (data) {
//         console.log('AJAX call was successfully executed ;)');
//         var searchedbody = document.getElementById('searched_file'), tr, td;
//         while (searchedbody.firstChild) {
//             searchedbody.removeChild(searchedbody.lastChild);
//         }
//         console.log(data);
//         data.forEach(function (key) {
//             console.log(key);
//             tr = document.createElement('tr');
//             td1 = document.createElement('td');
//             td2 = document.createElement('td');
//             var t1 = document.createTextNode("http://" + key.ipPort[0] + ":" + key.ipPort[1] + "/");
//             td1.appendChild(t1);
//             key.files.forEach(function (element) {
//                 tr1 = document.createElement('tr');
//                 td3 = document.createElement('td');
//                 console.log(element);
//                 var t2 = document.createTextNode(element);
//                 td3.appendChild(t2);
//                 tr1.appendChild(td3);
//                 td2.appendChild(tr1);
//             });
//             tr.appendChild(td1);
//             tr.appendChild(td2);
//             td4 = document.createElement('td');
//             var t3 = document.createTextNode(key.ipPort[2]);
//             td4.appendChild(t3);
//             tr.appendChild(td4);
//             searchedbody.appendChild(tr);
//         });
//     }).fail(function (data) {
//         console.log(data);
//         console.log('AJAX call failed :(');
//     });
// }
//
//
// function getNeighbours() {
//     $.ajax({
//         contentType: 'application/json;charset=UTF-8',
//         url: baseUrl + '/request/neighbours',
//         dataType: 'json',
//         type: 'GET',
//         cache: false, // Force requested pages not to be cached by the browser
//         processData: false, // Avoid making query string instead of JSON
//
//     }).done(function (data) {
//
//         console.log('AJAX call was successfully executed ;)');
//         console.log(data);
//         var neighBody = document.getElementById("table_neighbours");
//         while (neighBody.firstChild) {
//             neighBody.removeChild(neighBody.firstChild);
//         }
//
//         data.forEach(function (element) {
//             tr = document.createElement('tr');
//             td = document.createElement('td');
//             var t = document.createTextNode(element.ip);
//             td.appendChild(t);
//             tr.appendChild(td);
//             td = document.createElement('td');
//             var t = document.createTextNode(element.port);
//             td.appendChild(t);
//             tr.appendChild(td);
//             neighBody.appendChild(tr);
//             console.log(element);
//         });
//
//     }).fail(function (data) {
//         console.log(data);
//         console.log('AJAX call failed :(');
//     });
// }
//
//
//

//]]>