//$(function () { //do not use as it forces document to be ready and causes the ajax loaded forms to lose reference

//** incase of missing pics or pics that didn't load
$("img").error(function () {
    $(this).unbind("error").attr("src", "img/placeholder.jpg");
});

$("a").error(function () {
    $(this).unbind("error").attr("href", "img/placeholder.jpg");
});

/**
 * global error handler for ajax errors
 * */
$(document).ajaxError(function (event, jqxhr, settings, exception) {
    //console.log(JSON.stringify(event));
    console.log(JSON.stringify(settings));
    console.log(JSON.stringify(exception));
    if (jqxhr.responseText !== "") {
        fgAlert(jqxhr.responseText);//,"error");
        //fgDialog("Ajax Error",jqxhr.responseText);
    }
    loaderOff();
});

/**
 * Redirect to login page if session is expired
 * */
$(document).ajaxComplete(function (event, xhr, settings) {
    if (xhr.responseText.match('<title>Finance Georgia | Sign In</title>')) {
        fgAlert('Session expired! Redirecting to login page. <br>Please login to continue!');
        setTimeout(function () {
            window.location = 'account.html';
        }, 2000);

    }
});

/**
 * Form validation. Should call explicitly
 * */
var formValidation = function (formName) {
    $(formName).validate({
        errorPlacement: function (error, element) {
            $(element).attr('data-toggle', "popover");
            $(element).attr('data-content', error.text());
            $(element).attr('data-placement', "right");
            $(element).popover({trigger: 'manual'});
            $(element).popover('show');
        }, highlight: function (element, errClass) {
            $(element).parent().removeClass('has-success has-feedback');
            $(element).parent().addClass('has-error has-feedback');
            $(element).next('span').removeClass('glyphicon-ok');
            $(element).next('span').addClass('glyphicon-remove');
            //$(element).focus();
        }, unhighlight: function (element, errClass) {
            $(element).popover('hide');
            //$(element).next('span').html('');
            $(element).parent().removeClass('has-error has-feedback');
            $(element).parent().addClass('has-success has-feedback');
            $(element).next('span').removeClass('glyphicon-remove');
            $(element).next('span').addClass('glyphicon-ok');
            //element.blur();
        }
    });
};

/**
 * password validation - temporarily disabled
 * */
jQuery.validator.addMethod("password", function (value, element) {
    var result = this.optional(element) || value.length >= 8 && /\d/.test(value) && /[a-z]/i.test(value);
    if (!result) {
        //element.value = "";
        var validator = this;
        setTimeout(function () {
            validator.blockFocusCleanup = true;
            element.focus();
            validator.blockFocusCleanup = false;
        }, 1);
    }
    return result;
}, "Your password must be at least 8 characters long and contain at least one number and one character.");

//** functions to show/hide loaders
var loaderOn = function () {
    if ($("#div_loader").length === 0) {
        $("body").append('<div id="div_loader" style="z-index:100000;position:fixed;top:0px;left:0px;height:100%;width:100%;' +
                'background-color: #000000;opacity: 0.7;filter: alpha(opacity=70);">' +
                '<img src="img/loading.gif" style="position:fixed;height:50px;width:50px;top:43%;left:47.5%;"></div>');
    }
    $('#div_loader').fadeIn();
};
var loaderOff = function () {
    $('#div_loader').fadeOut();
    $("body").remove("#div_loader");
};

var fgDialog = function (title, msg) {
    $('#fgDialog').remove();
    $('body').append('<div id="fgDialog"></div>');
    $("#fgDialog").attr('title', title);
    $("#fgDialog").html(msg);
    $("#fgDialog").dialog({
        modal: true
    });
};

var fgConfirm = function (title, msg, okfunc) {
    $('#fgConfirm').remove();
    $('body').append('<div id="fgConfirm"></div>');
    $("#fgConfirm").attr('title', title);
    $("#fgConfirm").html('<h5>' + msg + '</h5>');
    $("#fgConfirm").dialog({
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
                okfunc();
            },
            Cancel: function () {
                $(this).dialog("close");
            }
        }
    });
};

var fgConfirmBS = function (header, content, confFunc) {
    $("#fgConfirmBS").remove();
    if ($("#fgConfirmBS").length === 0) {
        var markup = '<div id="fgConfirmBS" class="modal fade" tabindex="-1" role="dialog">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">';
        markup = markup + '<button type="button" class="close" data-dismiss="modal">×</button>' +
                '<h3>' + header + '</h3>' +
                '</div>';
        markup = markup + '<div class="modal-body">' +
                '<div id="mdlCont" >' + content + '</div>' +
                '</div>';
        markup = markup + '<div class="modal-footer">' +
//                '<button class="btn btn-default" data-dismiss="modal">Close</button>' +
                '<button class="btn btn-default orange" data-dismiss="modal" id="btnConfCont">Continue</button>' +
                '</div>';
        markup = markup +
                '</div>' +
                '</div>' +
                '</div>';
        $('body').append(markup);
        $('#fgConfirmBS').modal({show: true});
        $('#btnConfCont').click(function () {
            confFunc();
        });
    }
};

var fgAlert = function (msg) {
    $("#fgalert").remove();
    $("html, body").animate({scrollTop: 0}, "slow");
    //position:absolute;top:10px;right:10px;min-width:200px;
    //top:50%;left:50%;transform(-50%,-50%);
    if ($("#fgalert").length === 0) {
        $("body").append('<div id="fgalert" class="alert alert-info" style="position:absolute;top:10px;right:10px;z-index:100000;max-width:500px;max-height:500px">' +
                '<button type="button" class="close" data-dismiss="alert">&nbsp;&nbsp;×</button><div style="max-height:460px;overflow:auto"><h5><b>' + msg + '</b></h5></div></div>');
    }
//    $('#fgalert').position({
//        my: 'center',
//        at: 'center',
//        of: $(screen),
//        collision: "none"
//    });
    $('#fgalert').show();
    //diabled for QA since testers can't grab screen shots
    $('#fgalert').addClass("in").fadeOut(20000);//.effect('shake', 500);//
    //$('#fgalert').hide(5000);
};

fgModal = function (size, header, content, url, footer, isdoc) {
    //$("html, body").animate({scrollTop: 0}, "slow"); //cause modal to flicker
    $("#fgModal").remove();
    if ($("#fgModal").length === 0) {
        var markup = '<div id="fgModal" class="modal fade" tabindex="-1" role="dialog">' +
                '<div class="modal-dialog ' + size + '">' +
                '<div class="modal-content">' +
                '<div class="modal-header">';
        if (header != null) {
            markup = markup + '<button type="button" class="close" data-dismiss="modal">×</button>' +
                    '<h3>' + header + '</h3>' +
                    '</div>';
        }
        if (isdoc) { //for displaying documents
            markup = markup + '<div class="modal-body" style="height:' + (window.innerHeight - 100) + 'px;overflow:auto">' +
                    '<div id="mdlCont" >' + content + '</div>' +
                    '</div>';
        } else {
            markup = markup + '<div class="modal-body">' +
                    '<div id="mdlCont" >' + content + '</div>' +
                    '</div>';
        }
        if (footer != null) {
            markup = markup + '<div class="modal-footer">' +
                    '<button class="btn btn-default" data-dismiss="modal">Close</button>' +
                    '</div>';
        }
        markup = markup +
                '</div>' +
                '</div>' +
                '</div>';
        $('body').append(markup);
    }
    if (url !== null)
        $('#mdlCont').load(url + '?t=' + Math.random());
    $('#fgModal').modal({
        backdrop: 'static',
        keyboard: false
    });
};

fgDialogBS = function (msg) {
    fgModal("modal-md", "", msg, null, true, false);
    //fgDialog('Error',msg);
};

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//obsolete
$("#btnUser").click(function (e) {
//$('form').submit(function (e) {
    e.preventDefault();
    register("/rs/user/create", "location.html", null);
    $("#userId").val(userId);
});

//not using below
//var userId = null;
//var register = function (url, nextpage, uid) {
//    loaderOn();
//    var formData = $("form").serializeObject();
//    if (uid !== null) {
//        formData.userId = uid;
//    }
//    console.log(formData);
//    $.ajax({
//        type: "POST",
//        url: url,
//        contentType: "application/json",
//        data: JSON.stringify(formData),
//        async: false, //to get user id
//        //encode: true,
//        dataType: 'html',
//        success: function (result) {
//            loaderOff();
//            $("html, body").animate({scrollTop: 0}, "slow");
//            if (uid === null)
//                userId = result.id;
//            console.log(result);
//            if (nextpage !== null)
//                $("#register").load(nextpage);
//        }, error: function (err) {
//            loaderOff();
//            fgAlert(err.toString());
//            console.log(err);
//        }, complete: function () {
//            loaderOff();
//        }
//    });
//};

//var fgPop = function (element,error) {
//    $(element).attr('data-toggle', "popover");
//    //$(element).attr('title', "Popover title");
//    $(element).attr('data-content', error);
//    $(element).popover({trigger: 'manual', html: 'true'});
//    $(element).popover('show');
//};
//
//var fgValid=function(){
//    var email = $('#email').val();
//    if(email=='') fgPop('#email','Please enter email!');
//};

formValidation('#frmSignup');
// below doesnt do shit. inline works.

//$("#btnCreateAcc").click(function (e) {
$("#frmSignup").submit(function (e) {
    e.preventDefault();
    if ($("#frmSignup").valid()) {
        loaderOn();
        console.log(JSON.stringify($("form").serializeObject()));
        //verify recaptcha
        $.get("/ReCaptchaVerify?recaptchaResponse=" + grecaptcha.getResponse(), function (res) {
            console.log(res);
            if (res.match('true')) {
                //register("/rs/user/create", null, null);
//                $.post('/rs/user/create', JSON.stringify($("form").serializeObject()), function (data) {
//                    console.log(data);
//                    if (data.userId != null)
//                        window.location = "welcome.html";
//                    else
//                        fgAlert(JSON.stringfy(data));
//                });
                $.ajax({
                    url: '/rs/user/create',
                    type: "POST",
                    data: JSON.stringify($("form").serializeObject()),
                    contentType: "application/json",
                    dataType: "html",
                    success: function () {
                        loaderOff();
                        window.location = "welcome.html";
                    }
                });
            } else {
                //fgAlert("Please verify reCAPTCHA!");
                $('#divReCaptcha').effect('shake', 500);
                fgDialogBS("<h3>Please check reCAPTCHA!</h3>");
                loaderOff();
            }
            //log user in now
//            if (userId !== null) {
//                window.location = "welcome.html";
//            }
//            $("#userId").val(userId);
        });
    }
});

var usrObj, locObj; //for bank tab prevention
userType = localStorage.getItem('userType');
var loadData = function (url) {
    loaderOn();
    $.getJSON(url, function (res) {
        loaderOff();
        //console.log(JSON.stringify(res));
        if (url.match('/rs/user/'))
            usrObj = res; // localStorage.setItem("usrObj",res);
        if (url.match('/rs/location/'))
            locObj = res; //localStorage.setItem("locObj",res);
        if (res !== null) {
            $.each(res, function (key, value) {
                $('#' + key).val(value);
                if (key === 'userType') {
                    localStorage.setItem("userType", value);
                    userType = value;
                }
                if (url.match('/rs/user/') && key === 'id')
                    localStorage.setItem("userId", value);
                if (url.match('/rs/business/') && key === 'id')
                    localStorage.setItem("busId", value);
            });
            console.log('User Type: ' + $('#userType').val());
            if ($('#userType').val() !== "B") {
                //if( userType !== 'B') {
                $('#tabBus').hide();//remove();//.hide();
                $('#tabContBus').hide();//.remove();//.hide();
            } else {
                $('#tabBus').show();
                //$('#tabContBus').show();//don't need it - displays loading...
            }
        }
        createProfileLinks();
    });
};

function createProfileLinks() {
    //create profile linkes
    if ($('#firstName').val() !== '' && $('#address').val() !== '')
        $('#userprof').html('<a class="btn btn-default" target="_blank" href="userprofile.html#/' + localStorage.getItem("userId") + ' ">user profile</a>');
    if (localStorage.getItem("userType") === 'B' && localStorage.getItem("busId") !== null)
        $('#busprof').html('<a class="btn btn-default" target="_blank" href="businessprofile.html#/' + localStorage.getItem("busId") + '#' + localStorage.getItem("userId") + ' ">Business profile</a>');
    else
        $('#busprof').hide();
}

$('#tabLegal').click(function (e) {
    loaderOn();
    e.preventDefault();
    var docType;
    if ($('#userType').val() == 'B')
        docType = "listingagreement.pdf"
    if ($('#userType').val() == 'U')
        docType = "checklist_unaccinvestor.pdf"
    if ($('#userType').val() == 'A')
        docType = "checklist_accinvestor.pdf"
    $('#ifrmLegal').attr('src', "/EchoSign?action=widget&fileToBeUploaded=" + docType + "&jsorurl=js");
    $('#tabLegal').tab('show');
    loaderOff();
});

$('#tabFile').click(function (e) {
    loaderOn();
    e.preventDefault();
    $("#tabContFile").load("uploadfiles.html?t=" + Math.random());
    $('#tabFile').tab('show');
    loaderOff();
});

$('#tabBus').click(function (e) {
    e.preventDefault();
    $("#tabContBus").load("business2.html?t=" + Math.random());
    loadData("/rs/business/getme");
    $('#tabBus').tab('show');
});

$('#tabInvst').click(function (e) {
    e.preventDefault();
    loaderOn();
    $("#tabContInvst").load("investment.html?t=" + Math.random());
    $('#tabInvst').tab('show');
    loaderOff();
});

$('#tabLoc').click(function (e) {
    e.preventDefault();
    console.log('loading location');
    $("#tabContLoc").load("location2.html?t=" + Math.random());
    loadData("/rs/location/getme");
    $('#tabLoc').tab('show');
});

$('#tabSrch').click(function (e) {
    e.preventDefault();
    $("#tabContSrch").load("admin.html?t=" + Math.random());
    //loadData("/rs/user/getme");
    $('#tabSrch').tab('show');
});

$('#tabBank').click(function (e) {
    e.preventDefault();
    //usrObj = localStorage.getItem("usrObj");
    //locObj = localStorage.getItem("locObj");
    if (usrObj.firstName == null || usrObj.firstName == '') {
        fgDialogBS('<h3>Please fill out user and location information before setting up bank account!</h3>');
        return;
    }
    //below disabled cause it forces loc tab click even when loc info is there
//    if(locObj==null || locObj.address==null || locObj.address==''){
//        fgAlert('Please fill out user and location information before setting up bank account!');
//        return;
//    }
    $("#tabContBank").load("bankaccount.html?t=" + Math.random());
    $('#tabBank').tab('show');
});

var hash = window.location.hash; //for admin crap. not sure i need it
var userId = hash.substring(hash.indexOf('/') + 1, hash.length);
function loadUser() {
    console.log('loading user');
    $("#tabContUser").load("user2.html?t=" + Math.random());
    if ((userId !== null && userId !== '')) {
        loadData("/rs/user/" + userId);
    } else {
        loadData("/rs/user/getme");
    }
}

$('#tabUser').click(function (e) {
    e.preventDefault();
    $("#tabContUser").load("user2.html?t=" + Math.random());
    //loadData("/rs/user/getme");
    loadUser();
    $('#tabUser').tab('show');
});

var saveData = function (url, formId, method) {
    loaderOn();
    var formData = JSON.stringify($(formId).serializeObject());
    console.log(formData);
    $.ajax({
        type: method,
        url: url,
        contentType: "application/json",
        data: formData,
        async: true,
        encode: true,
        success: function (result) {
            loaderOff();
            $("html, body").animate({scrollTop: 0}, "slow");
            //fgAlert("Saved!");
            console.log(result);
            //console.log(status);
            //console.log(xhr.getAllResponseHeaders());//.getResponseHeader('Set-Cookie'));
        } //error: function (err) { not needed since I have the ajaxError handler
//            loaderOff();
//            fgAlert(JSON.stringify(err));
//            console.log(JSON.stringify(err));
//        }, complete: function () {
//            loaderOff();
//        }
    });
};
///(size, header, content, url, footer, isdoc)
$('#lnk_pp').click(function () {
    fgModal('modal-lg', '', '', "/doc/privacypolicy.html", null, true);
    $('#fgModal').hide();
    $('body').remove('#fgModal');
});

$('#lnk_tos').click(function () {
    fgModal('modal-lg', '', '', "/doc/termsofservice.html", null, true);
    $('#fgModal').hide();
    $('body').remove('#fgModal');
});

$('#navbar').load('navbar.html');
$('#banner').load('banner.html');
$('#footer').load('footer.html');
$('#copyright').load('copyright.html');
$('#contactpart').load('contactpart.html');

//make this sync so cause i need to know if user is admin
//async false doesn't work since the navbar doesn't load till after
//var isadmin;
var isUsrLoggedIn = false;
$.ajax({
    url: "/jsp/getloggedinuser.jsp",
    async: true,
    success: function (data) {
        console.log(data);
//        isadmin = data.role;
//        console.log(isadmin);
        if (data.email !== 'null') {
            //user if logged in
            console.log('User logged in - Changing nav bar');
            $('#signin').html('Sign Out');
            $('#signin').attr('href', 'logout.html');
            $('#signup').html('Account');
            $('#signup').attr('href', 'account.html');
            $("a[href='register.html']").hide(); //hide all links to register
            isUsrLoggedIn = true; //for business profile button blocking
        } else {
            console.log('User NOT logged in');
            $("a[href='register.html']").show(); //show all links to register
            localStorage.clear();
            localStorage.clear();
        }
    }
});

//addtional signin/out check cause of issue in qa
if (localStorage.getItem("userId") === null) {
    $('#signin').html('Sign In');
    $('#signin').attr('href', 'account.html');
    $('#signup').html('Sign Up');
    $('#signup').attr('href', 'register.html');
    $("a[href='register.html']").show(); //hide all links to register
}

//doesn't work - will fire even on leaving page
//window.onbeforeunload = function () {
//    alert('are you sure');
//    //clear session or local storage when logging out
//    localStorage.clear();
//    localStorage.clear();
//    //kill server session
//    $.get('/jsp/logout.jsp');
//}



var portfolioWidget = function (element, item, businessId) {
    var trgAmt = $.number(item.targetAmount);
    var amtInvstd = $.number(item.amountInvested);
    markup = "<div class='col-md-4 col-xs-6'>" +
            "<div class='member'>" +
            "<div class='img'>" +
            "<div class='social'>" +
            "<div class='icons'>" +
            "<img src='Assets/Finance-Georgia-Logo.png' alt=''/>" +
            "</div>" +
            "</div>" +
            "<img style='width:350px;height:250px' src='/Uploader?action=stream&folder=" + item.userId + "&filename=businessLogo.jpg&t=" + Math.random() + "'>" +
            "</div>" +
            "<div class='info' style='height:270px'>" +
            "<h4>" + item.businessName + "</h4>" +
            "<h5>" + item.address + " " + item.city + " " + item.state + //" " + item.zip + "</h5>" +
            "<h6><span class='target'>TARGET: <b>$" + trgAmt + "</b></span><span class='pledged'> PLEDGED: <b>$" + amtInvstd + "</b></span></h6>" +
            "<div class='progress'>" +
            "<div aria-valuemax='100' aria-valuemin='0' aria-valuenow='50' class='progress-bar progress-bar-success' role='progressbar' style='width:" + (100 * item.amountInvested / item.targetAmount) + "%'>" +
            "</div>" +
            "</div>" +
            "<p style='width:260px;height:50px'>" + item.summary.substring(0, 100) + "</p>" +
            "<a class='btn btn-default' href='businessprofile.html#/" + businessId + "#" + item.userId + "'>View Details</a>" +
            "</div>" +
            "</div>" +
            "</div>";
    $(element).append(markup);
};

var loadPortfolio = function (element, toLoad) {
    loaderOn();
    $.getJSON("/rs/public/all", function (data) {
        //                $.each(data, function (i, item) {
        //                    console.log(item);
        //                    portfolioWidget(item,item.businessId);
        //                    if(i===busId) return;
        //                });
        var count = 0;
        for (var i = data.length - 1; i >= 0; i--) {
            count++;
            var item = data[i];
            portfolioWidget(element, item, item.businessId);
            if (count >= toLoad)
                break;
        }
        loaderOff();
    });
};

/**
 * Load portofolio
 * */
loadPortfolio('#invPortfolio');

/**
 * Load index portfolio
 * */
loadPortfolio('#portfolioIndex', 3);

/**
 * Reset password
 * */
$('#lnkPassReset').click(function () {
//   $.get('/rs/resetpass/viplav.fauzdar@gmail.com',function(data){
//      alert(data);
//   });
    fgModal("modal-sm", "", "<h4>Please provide your email</h4> <input class='form-control' type=text id=txtPassReset> <button data-dismiss='modal' class='btn btn-default orange' id=btnPassReset>Reset Password</button>\n\
        <script>\n\
            $('#btnPassReset').click(function(){\n\
                if($('#txtPassReset').val()!=''){\n\
                    loaderOn();\n\
                    $.get('/rs/resetpass/' + $('#txtPassReset').val(),function(res){\n\
                        loaderOff();\n\
                        $(this).hide();\n\
                        fgAlert(res);\n\
                    });\n\
                }\n\
            });\n\
        </script>", null, false, false);
});

$('[data-toggle="tooltip"]').tooltip();

$('.savebtn').tooltip({
    title: 'tool tip via js',
    placement: 'right'
});



