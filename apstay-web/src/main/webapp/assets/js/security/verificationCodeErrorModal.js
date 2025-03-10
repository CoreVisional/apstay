document.addEventListener('DOMContentLoaded', function() {
    const errorModal = document.getElementById('errorModal');
    if (errorModal) {
        setTimeout(function() {
            $("#errorModal").modal("show");
        }, 100);
    }
});