// Instructions at http://blog.teamtreehouse.com/create-ajax-contact-form
$(function() {

    // Get the form.
    var form = $('#contactForm');

    // Set up an event listener for the contact form.
    $(form).submit(function(event) {
        // Stop the browser from submitting the form.
        event.preventDefault();

        // Get the messages div.
        //var formMessages = $('#form-messages');

        // Serialize the form data.
        var formData = $(form).serialize();

        // Submit the form using AJAX.
        $.ajax({
            type: 'POST',
            url: 'https://stagingsiteapi.epxlabs.com/'.concat($(form).attr('action')),
            data: formData
        }).done(function(response) {
            // Make sure that we display the success
            $('#contactSuccess').removeClass('hidden');
            $('#contactError').addClass('hidden');

            // Set the message text.
            //$(formMessages).text(response);

            // Clear the form.
            $('#name').val('');
            $('#email').val('');
            $('#phone').val('');
            $('#message').val('');
        }).fail(function(data) {
            // Make sure that we display an error
            // TODO: Display the error message from the backend
            $('#contactSuccess').addClass('hidden');
            $('#contactError').removeClass('hidden');

            // Set the message text.
            /*
             if (data.responseText !== '') {
             $(formMessages).text(data.responseText);
             } else {
             $(formMessages).text('Sorry! Your message could not be delivered');
             }
             */
        });
    });

});
