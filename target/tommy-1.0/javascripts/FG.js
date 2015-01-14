//$(function () { //do not use as it forces document to be ready and causes the ajax loaded forms to lose reference

/**
 * global error handler for ajax errors
 * */
$(document).ajaxError(function(event, jqxhr, settings, exception) {
	 if(jqxhr.responseText!="") fgAlert("Ajax Error: " + jqxhr.responseText);//,"error");
	 loaderOff();
});


/**
 * Form validation. Should call explicitly 
 * */
var formValidation = function(formName){
    $('form').validate({
        errorPlacement: function (error, element) {
            $(element).attr('data-toggle', "popover");
            $(element).attr('data-content', error.text());
            $(element).attr('data-placement', "right");
            $(element).popover({trigger: 'manual'});
            $(element).popover('show');
        }, highlight: function(element, errClass) {
            $(element).parent().removeClass('has-success has-feedback');
            $(element).parent().addClass('has-error has-feedback');
            $(element).next('span').removeClass('glyphicon-ok');
            $(element).next('span').addClass('glyphicon-remove');
        }, unhighlight: function (element, errClass) {
            $(element).popover('hide');
            $(element).next('span').html('');
            $(element).parent().removeClass('has-error has-feedback');
            $(element).parent().addClass('has-success has-feedback');
            $(element).next('span').removeClass('glyphicon-remove');
            $(element).next('span').addClass('glyphicon-ok');
        }
    });
};

/**
 * password validation - temporarily disabled
 * */
//jQuery.validator.addMethod("password", function( value, element ) {
//	var result = this.optional(element) || value.length >= 8 && /\d/.test(value) && /[a-z]/i.test(value);
//	if (!result) {
//		//element.value = "";
//		var validator = this;
//		setTimeout(function() {
//			validator.blockFocusCleanup = true;
//			element.focus();
//			validator.blockFocusCleanup = false;
//		}, 1);
//	}
//	return result;
//}, "Your password must be at least 8 characters long and contain at least one number and one character.");

//** functions to show/hide loaders
var loaderOn = function () {
    if ($("#div_loader").length === 0) {
        $("body").append('<div id="div_loader" style="z-index:100000;position:fixed;top:0px;left:0px;height:100%;width:100%;' +
                'background-color: #000000;opacity: 0.7;filter: alpha(opacity=70);">' +
                '<img src="../fg/img/loading.gif" style="position:fixed;height:50px;width:50px;top:43%;left:47%;"></div>');
    }
    $('#div_loader').fadeIn();
};
var loaderOff = function () {
    $('#div_loader').fadeOut(); 
    $("body").remove("#div_loader");
};

var fgAlert = function (msg) {
    $("html, body").animate({scrollTop: 0}, "slow");
    if ($("#fgalert").length === 0) {
        $("body").append('<div id="fgalert" class="alert alert-warning" style="position:absolute;top:10px;right:10px;z-index:100;min-width:300px;">' +
                '<button type="button" class="close" data-dismiss="alert">&nbsp;&nbsp;×</button><h5>' + msg + '</h5></div>');
    }
    $('#fgalert').show();
    //diabled for QA since testers can't grab screen shots
    //$('#fgalert').fadeOut(10000);//.effect('shake', 500);//.hide(5000);
};

fgModal = function (size, header, content, url, footer) {
    //$("html, body").animate({scrollTop: 0}, "slow"); //cause modal to flicker
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
        markup = markup + '<div class="modal-body" style="height:' + (window.innerHeight - 100) + 'px;overflow:auto">' +
                '<div id="mdlCont" >' + content + '</div>' +
                '</div>';
        if (footer != null) {
            markup = markup + '<div class="modal-footer">' +
                    '<button class="btn" data-dismiss="modal">Close</button>' +
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

$("#btnUser").click(function (e) {
//$('form').submit(function (e) {      
    e.preventDefault();
    register("/rs/user/create", "location.html", null);
    $("#userId").val(userId);
});

var userId = null;
var register = function (url, nextpage, uid) {
    loaderOn();
    var formData = $("form").serializeObject();
    if (uid !== null) {
        formData.userId = uid;
    }
    console.log(formData);
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        data: JSON.stringify(formData),
        async: false, //to get user id
        encode: true,
        success: function (result) {
            loaderOff();
            $("html, body").animate({scrollTop: 0}, "slow");
            if (uid === null)
                userId = result.id;
            console.log(result);
            if (nextpage !== null)
                $("#register").load(nextpage);
        }, error: function (err) {
            loaderOff();
            fgAlert(JSON.stringify(err));
            console.log(JSON.stringify(err));
        }, complete: function () {
            loaderOff();
        }
    });
};

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

//$("#btnCreateAcc").click(function (e) {
$("#frmSignup").submit(function (e) {
    //$("html, body").animate({scrollTop: 0}, "slow");
    e.preventDefault();
    if ($("#frmSignup").valid()) {
//    if($('#email').val()==''){    
//        $('#email').parent().addClass('has-error has-feedback');
//        $('#email').attr('data-toggle', "popover");    
//        $('#email').attr('data-content', "error");
//        $('#email').popover({trigger: 'manual', html: 'true'});
//        $('#email').popover('show');
//    }

        //verify recaptcha
        $.get("/ReCaptchaVerify?recaptchaResponse=" + grecaptcha.getResponse(), function (res) {
            console.log(res);
            if (res.match('true')) {
                register("/rs/user/create", null, null);
            } else {
                fgAlert("Please verify reCAPTCHA!");
                //$('#divReCaptcha').effect('shake', 500);            
            }
            //log user in now
            if (userId !== null) {
                window.location = "welcome.html";
            }
            $("#userId").val(userId);
        });
    }
});

var loadData = function (url) {
    loaderOn();
    $.getJSON(url, function (res) {
        loaderOff();
        console.log(JSON.stringify(res));
        $.each(res, function (key, value) {
            $('#' + key).val(value);
        });
        console.log('User Type: ' + $('#userType').val());
        if ($('#userType').val() !== 'B') {
            $('#tabBus').remove();//.hide();
            $('#tabContBus').remove();//.hide();
        }
    });
};

$('#tabLegal').click(function (e) {
    loaderOn();
    e.preventDefault();
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


function loadUser() {
    console.log('loading user');
    $("#tabContUser").load("user2.html?t=" + Math.random());
    loadData("/rs/user/getme");
}

$('#tabUser').click(function (e) {
    e.preventDefault();
    $("#tabContUser").load("user2.html?t=" + Math.random());
    loadData("/rs/user/getme");
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
            fgAlert("Saved!");
            console.log(result);
        }, error: function (err) {
            loaderOff();
            fgAlert(JSON.stringify(err));
            console.log(JSON.stringify(err));
        }, complete: function () {
            loaderOff();
        }
    });
};

$('#lnk_pp').click(function () {
    fgModal('modal-lg', '', '', "/doc/privacypolicy.html");
    $('#fgModal').hide();
    $('body').remove('#fgModal');
});

$('#lnk_tos').click(function () {
    fgModal('modal-lg', '', '', "/doc/termsofservice.html");
    $('#fgModal').hide();
    $('body').remove('#fgModal');
});

$('#navbar').load('navbar.html');
$('#banner').load('banner.html');
$('#footer').load('footer.html');
$('#copyright').load('copyright.html');
$('#contactpart').load('contactpart.html');

$.get("/jsp/getloggedinuser.jsp", function (data) {
    console.log(data);
    if (!data.match('null')) {
        $('#signin').html('Sign Out');
        $('#signin').attr('href', '/jsp/logout.jsp');
        $('#signup').html('Account');
        $('#signup').attr('href', 'account.html');
    }
});

var portfolioWidget = function (element, item, businessId) {
    markup = "<div class='col-md-4 col-xs-6'>" +
            "<div class='member'>" +
            "<div class='img'>" +
            "<div class='social'>" +
            "<div class='icons'>" +
            "<img src='Assets/Finance-Georgia-Logo.png' alt=''/>" +
            "</div>" +
            "</div>" +
            "<img style='width:320px;height:250px' src='/Uploader?action=stream&folder=" + item.userId + "&filename=businessLogo.jpg&t=" + Math.random() + "'>" +
            "</div>" +
            "<div class='info'>" +
            "<h4>" + item.businessName + "</h4>" +
            "<h5>" + item.address + " " + item.city + " " + item.state + " " + item.zip + "</h5>" +
            "<h6><span class='target'>TARGET: <b>" + item.targetAmount + "</b></span><span class='pledged'> PLEDGED: <b>" + item.amountInvested + "</b></span></h6>" +
            "<div class='progress'>" +
            "<div aria-valuemax='100' aria-valuemin='0' aria-valuenow='50' class='progress-bar progress-bar-success' role='progressbar' style='width:" + (100 * item.amountInvested / item.targetAmount) + "%'>" +
            "</div>" +
            "</div>" +
            "<p style='width:300px;height:50px'>" + item.summary.substring(0, 100) + "</p>" +
            "<a class='btn btn-default' href='businessprofile.html#/" + businessId + "'>View Details</a>" +
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
loadPortfolio('#portfolioIndex',3);


