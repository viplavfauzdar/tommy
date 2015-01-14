$(function () {
    $('#send').click(function (e) {
        //Stop form submission & check the validation
        e.preventDefault();

        // Variable declaration
        var error = false;

        var email = $('#email').val();
        var phone = $('#phone').val();
        var message = $('#message').val();
        var datepicker = $('#datepicker').val();
        var password1 = $('#password1').val();
        var password = $('#password').val();
        var first_name = $('#first_name').val();
        var confirm_email = $('#confirm_email').val();
        var last_name = $('#last_name').val();
        var mi = $('#mi').val();
        var about_me = $('#about_me').val();
        var project_name = $('#project_name').val();
        var description = $('#description').val();
        var summary = $('#summary').val();
        var facebook = $('#facebook').val();
        var linkedin = $('#linkedin').val();
        var google_plus = $('#google_plus').val();
        var twitter = $('#twitter').val();
        var website = $('#website').val();
        var fax = $('#fax').val();
        var mobile = $('#mobile').val();
        var phone1 = $('#phone1').val();
        var city = $('#city').val();
        var state = $('#state').val();
        var zip = $('#zip').val();
        var id = $('#id').val();
        var image1 = $('#image1').val();
        var image2 = $('#image2').val();

        if (image2 == '') {
            var error = true;
            $('#image2').parent().addClass('has-error has-feedback');
        } else {
            $('#image2').parent().removeClass('has-error has-feedback');
        }
        if (image1 == '') {
            var error = true;
            $('#image1').parent().addClass('has-error has-feedback');
        } else {
            $('#image1').parent().removeClass('has-error has-feedback');
        }
        if (id == '') {
            var error = true;
            $('#id').parent().addClass('has-error has-feedback');
        } else {
            $('#id').parent().removeClass('has-error has-feedback');
        }
        if (zip == '') {
            var error = true;
            $('#zip').parent().addClass('has-error has-feedback');
        } else {
            $('#zip').parent().removeClass('has-error has-feedback');
        }
        if (state == '') {
            var error = true;
            $('#state').parent().addClass('has-error has-feedback');
        } else {
            $('#state').parent().removeClass('has-error has-feedback');
        }
        if (city == '') {
            var error = true;
            $('#city').parent().addClass('has-error has-feedback');
        } else {
            $('#city').parent().removeClass('has-error has-feedback');
        }
        if (phone1 == '') {
            var error = true;
            $('#phone1').parent().addClass('has-error has-feedback');
        } else {
            $('#phone1').parent().removeClass('has-error has-feedback');
        }
        if (mobile == '') {
            var error = true;
            $('#mobile').parent().addClass('has-error has-feedback');
        } else {
            $('#mobile').parent().removeClass('has-error has-feedback');
        }
        if (fax == '') {
            var error = true;
            $('#fax').parent().addClass('has-error has-feedback');
        } else {
            $('#fax').parent().removeClass('has-error has-feedback');
        }
        if (website == '') {
            var error = true;
            $('#website').parent().addClass('has-error has-feedback');
        } else {
            $('#website').parent().removeClass('has-error has-feedback');
        }
        if (twitter == '') {
            var error = true;
            $('#twitter').parent().addClass('has-error has-feedback');
        } else {
            $('#twitter').parent().removeClass('has-error has-feedback');
        }
        if (google_plus == '') {
            var error = true;
            $('#google_plus').parent().addClass('has-error has-feedback');
        } else {
            $('#google_plus').parent().removeClass('has-error has-feedback');
        }
        if (linkedin == '') {
            var error = true;
            $('#linkedin').parent().addClass('has-error has-feedback');
        } else {
            $('#linkedin').parent().removeClass('has-error has-feedback');
        }
        if (facebook == '') {
            var error = true;
            $('#facebook').parent().addClass('has-error has-feedback');
        } else {
            $('#facebook').parent().removeClass('has-error has-feedback');
        }
        if (summary == '') {
            var error = true;
            $('#summary').parent().addClass('has-error has-feedback');
        } else {
            $('#summary').parent().removeClass('has-error has-feedback');
        }
        if (description == '') {
            var error = true;
            $('#description').parent().addClass('has-error has-feedback');
        } else {
            $('#description').parent().removeClass('has-error has-feedback');
        }
        if (project_name == '') {
            var error = true;
            $('#project_name').parent().addClass('has-error has-feedback');
        } else {
            $('#project_name').parent().removeClass('has-error has-feedback');
        }
        if (email.length == 0 || email.indexOf('@') == '-1') {
            var error = true;
            $('#email').parent().addClass('has-error has-feedback');
        } else {
            $('#email').parent().removeClass('has-error has-feedback');
        }
        if (last_name == '') {
            var error = true;
            $('#last_name').parent().addClass('has-error has-feedback');
        } else {
            $('#last_name').parent().removeClass('has-error has-feedback');
        }
        if (first_name == '') {
            var error = true;
            $('#first_name').parent().addClass('has-error has-feedback');
        } else {
            $('#first_name').parent().removeClass('has-error has-feedback');
        }
        if (mi == '') {
            var error = true;
            $('#mi').parent().addClass('has-error has-feedback');
        } else {
            $('#mi').parent().removeClass('has-error has-feedback');
        }
        if (message == '') {
            var error = true;
            $('#message').parent().addClass('has-error has-feedback');
        } else {
            $('#message').parent().removeClass('has-error has-feedback');
        }

        if (password == '') {
            var error = true;
            $('#password').parent().addClass('has-error has-feedback');
        } else {
            $('#password').parent().removeClass('has-error has-feedback');
        }
        if (password1 == '') {
            var error = true;
            $('#password1').parent().addClass('has-error has-feedback');
        } else {
            $('#password1').parent().removeClass('has-error has-feedback');
        }

        if (last_name == '') {
            var error = true;
            $('#last_name').parent().addClass('has-error has-feedback');
        } else {
            $('#last_name').parent().removeClass('has-error has-feedback');
        }
        if (confirm_email == '') {
            var error = true;
            $('#confirm_email').parent().addClass('has-error has-feedback');
        } else {
            $('#confirm_email').parent().removeClass('has-error has-feedback');
        }
        if (about_me == '') {
            var error = true;
            $('#about_me').parent().addClass('has-error has-feedback');
        } else {
            $('#about_me').parent().removeClass('has-error has-feedback');
        }




        // If there is no validation error, next to process the mail function
        if (error == false) {
            // Disable submit button just after the form processed 1st time successfully.
            $('#send').attr({'disabled': 'true', 'value': 'Sending'});
            /* Post Ajax function of jQuery to get all the data from the submission of the form as soon as the form sends the values to email.php*/
            $.post("php/email.php", $("#form").serialize(), function (result) {
                if (result == 'sent') {
                    //If the email is sent successfully, remove the submit button
                    $('#send').attr({'disabled': 'true', 'value': 'Thank you!'});
                } else {
                    $('#send').removeAttr('disabled').attr('value', 'Send');
                }
            });
        } else {
            $('#send').attr('value', 'Error while sending.');
        }
    });
});
