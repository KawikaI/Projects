// Global variable to keep track of the total amount and selected items
let totalAmount = 0;

// array of items so all showup
let selectedItems = [
    { itemName: "None", price: 0 },
    { itemName: "None", price: 0 },
    { itemName: "None", price: 0 },
    { itemName: "None", price: 0 },
    { itemName: "None", price: 0 }
];



// function to add dropwdown options to total if number
function selectOption(optionText, dropdownId) {
    const splitText = optionText.split(' $');
    const itemName = splitText[0] || "None";
    const amount = parseFloat(splitText[1]) || 0;
    const dropdownIndex = parseInt(dropdownId.replace('dropdown-button', '')) - 1;

    totalAmount += amount;
    selectedItems[dropdownIndex] = { itemName: itemName, price: amount };

    document.getElementById(dropdownId).textContent = optionText;
    closeDropdown(dropdownId);
}


// function to save total amount and redirect to receipt
function handleSubmit() {
    localStorage.setItem('selectedItems', JSON.stringify(selectedItems));
    localStorage.setItem('totalAmount', totalAmount);

    // Redirect to receipt.html
    window.location.href = 'receipt.html';
}

// function to be called when the window's load event is triggered.(when everyhitng is loaded)
document.querySelector('.review-button').addEventListener('click', handleSubmit);





document.addEventListener('click', function(event) {
    // ... existing code for handling dropdowns ...
    if (!event.target.matches('.dropdown-button')) {
        var dropdowns = document.querySelectorAll('.dropdown-content');
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.style.display === 'block') {
                openDropdown.style.display = 'none';
            }
        }
    }
});

// function that toggles the dropdown menu
function toggleDropdown(dropdownContentId) {
    const dropdownContent = document.getElementById(dropdownContentId);
    if (dropdownContent.style.display === 'none' || !dropdownContent.style.display) {
        dropdownContent.style.display = 'block';
    } else {
        dropdownContent.style.display = 'none';
    }
}

// function that closes the dropdown menu
function closeDropdown(dropdownId) {
    const dropdownContentId = dropdownId.replace('dropdown-button', 'dropdown-content');
    const dropdownContent = document.getElementById(dropdownContentId);
    if (dropdownContent) {
        dropdownContent.style.display = 'none';
    }
}


window.onload = function() {
    // Reset totalAmount to 0
    totalAmount = 0;

    // Retrieve the totalAmount from localStorage
    const storedTotalAmount = localStorage.getItem('totalAmount') || '0';
    document.getElementById('total-display').textContent = 'Total: $' + storedTotalAmount;
}













