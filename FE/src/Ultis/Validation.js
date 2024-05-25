export function validateEmail(email) {
    // Regular expression pattern for email validation
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    // Test the email against the pattern
    return emailPattern.test(email);
}

export function checkPasswordLength(password) {
    // Check if the length of the password is greater than 8
    return password?.length >= 8;
}

export function checkPhoneNumber(phone) {
    // Check if the length of the password is greater than 8
    var phonePattern = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
    // Test the email against the pattern
    return phonePattern.test(phone);
}