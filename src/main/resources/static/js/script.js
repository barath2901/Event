function validateForm() {
    const regNoField = document.getElementById("regNo");
    const regError = document.getElementById("regError");
    const regValue = regNoField.value.trim();

    // Reset Error State
    regError.style.display = "none";
    regNoField.classList.remove("input-error");

    // Check if Register Number contains only digits
    if (isNaN(regValue)) {
        showError(regNoField, regError, "Please enter numeric characters only.");
        return false;
    }

    // Check Length
    if (regValue.length < 5) {
        showError(regNoField, regError, "Register number is too short.");
        return false;
    }

    return true;
}

function showError(field, errorSpan, message) {
    errorSpan.innerText = message;
    errorSpan.style.display = "block";
    field.classList.add("input-error");
    field.focus();
}
function validateForm() {
    // 1. Validate Register Number (Existing logic)
    const regNoField = document.getElementById("regNo");
    const regError = document.getElementById("regError");
    const regValue = regNoField.value.trim();

    // Reset Errors
    regError.style.display = "none";
    document.getElementById("phoneError").style.display = "none";

    // College ID Validation
    if (regValue.length < 5) {
        regError.innerText = "Register Number is too short";
        regError.style.display = "block";
        return false;
    }

    // 2. Validate Phone Number (NEW)
    const phoneField = document.getElementById("phoneNumber");
    const phoneError = document.getElementById("phoneError");
    const phoneValue = phoneField.value.trim();

    // Check if it is a number and exactly 10 digits
    if (isNaN(phoneValue) || phoneValue.length !== 10) {
        phoneError.style.display = "block"; // Show error message
        phoneField.focus();
        return false; // Stop form submission
    }

    return true; // Submit form
}