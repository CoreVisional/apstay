document.addEventListener('DOMContentLoaded', function() {
    $('#verificationCodeModal').modal('show');

    $("#copyButton").on('click', function() {
        var codeInput = $("#verificationCodeInput");
        codeInput.select();
        navigator.clipboard.writeText(codeInput.val()).then(function() {
            var button = $("#copyButton");
            var originalHTML = button.html();
            button.html('<i class="fas fa-check"></i> Copied!');
            setTimeout(function() {
                button.html(originalHTML);
            }, 2000);
        });
    });
});