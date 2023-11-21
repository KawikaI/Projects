document.addEventListener('DOMContentLoaded', (event) => {
    // Your existing JavaScript code here
    function calculateMoonPhase(year, month, day) {
        const knownFullMoon = new Date('2000-01-01');
        const givenDate = new Date(year, month - 1, day); // JavaScript months are 0-indexed
        
        const daysSinceKnownFullMoon = (givenDate - knownFullMoon) / (1000 * 3600 * 24);
        const phaseLength = 29.53;
        const phase = (daysSinceKnownFullMoon % phaseLength) / phaseLength;
        
        if (phase < 0.25) return 'New Moon';
        else if (phase < 0.50) return 'First Quarter';
        else if (phase < 0.75) return 'Full Moon';
        else return 'Last Quarter';
    }
    
    // Example usage
    document.getElementById('moonPhaseForm').onsubmit = function(event) {
        event.preventDefault();
        const dateInput = document.getElementById('dateInput').value;
        const dateParts = dateInput.split('-').map(part => parseInt(part)); // Convert to integers
        const phase = calculateMoonPhase(...dateParts);
        document.getElementById('result').innerText = `The moon phase is: ${phase}`;
    
        const imageUrl = getMoonPhaseImageUrl(phase); // Add the correct path
        const img = document.getElementById('moonPhaseImage');
        img.src = imageUrl;
        img.style.display = 'block'; // Make sure to set the display property
    };
    
    
    function getMoonPhaseImageUrl(phase) {
        // Assuming you have images named like 'new-moon.jpg', 'first-quarter.jpg', etc.
        const phaseImageMapping = {
            'New Moon': 'newmoon.jpg',
            'First Quarter': 'firstquarter.jpg',
            'Full Moon': 'fullmoon.jpg',
            'Last Quarter': 'lastquarter.jpg'
            // Add other phases as necessary
        };
        return phaseImageMapping[phase] || 'default.jpg';
    }
    
    // Api stuff below
    // Api stuff below
    function getUserLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition, showError);
        } else {
            alert("Geolocation is not supported by this browser.");
        }
    }
    
    function showPosition(position) {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        getMoonData(latitude, longitude);
    }
    
    function showError(error) {
        switch(error.code) {
            case error.PERMISSION_DENIED:
                alert("User denied the request for Geolocation.");
                break;
            case error.POSITION_UNAVAILABLE:
                alert("Location information is unavailable.");
                break;
            case error.TIMEOUT:
                alert("The request to get user location timed out.");
                break;
            case error.UNKNOWN_ERROR:
                alert("An unknown error occurred.");
                break;
        }
    }
    
    function getMoonData(latitude, longitude) {
        // Use the latitude and longitude from the user's location
        const apiUrl = `https://moon-phase.p.rapidapi.com/basic?lat=${latitude}&lon=${longitude}`;
    
        fetch(apiUrl, {
            method: "GET",
            headers: {
                "x-rapidapi-key": "3f09df65abmsh594a95d23859fc1p1d0677jsnd338105525a6",
                "x-rapidapi-host": "moon-phase.p.rapidapi.com"
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);  
            displayMoonInfo(data);
        })
        .catch(err => {
            console.error(err);
        });
    }
    
    
    
    function displayMoonInfo(moonData) {
        const moonPhaseText = `The moon phase is: ${moonData.phase_name}, ${moonData.stage}`;
        document.getElementById('moonInfo').innerText = moonPhaseText;
    
        const imageUrl = getMoonPhaseImageUrl(moonData.phase_name); // Use the phase_name to get the image URL
        const img = document.getElementById('moonPhaseImage');
        img.src = imageUrl;
        img.style.display = 'block'; // Make the image visible
    }
    
    // Example of triggering the process
    document.getElementById('getMoonPhaseBtn').addEventListener('click', getUserLocation);
     
});

// collision
function isColliding(button1, button2) {
    const rect1 = button1.getBoundingClientRect();
    const rect2 = button2.getBoundingClientRect();
  
    return !(rect2.left > rect1.right || 
             rect2.right < rect1.left || 
             rect2.top > rect1.bottom ||
             rect2.bottom < rect1.top);
  }
  




// floating button


// Original speed values
const originalDx = 0.5, originalDy = 0.25;
const originalDx2 = 0.25, originalDy2 = 0.5;
const originalDx3 = 0.15, originalDy3 = 0.35;




const floatButton = document.getElementById('floatButton');
const floatButton2 = document.getElementById('floatButton2');
const floatButton3 = document.getElementById('floatButton3');

let x = 50, y = 50; // Initial position for the first button
let x2 = 100, y2 = 100; // Initial position for the second button
let x3 = 150, y3 = 150; // Initial position for the third button

let dx1 = 0.5, dy1 = 0.25; // Speed for the first button
let dx2 = 0.25, dy2 = 0.5; // Speed for the second button
let dx3 = 0.15, dy3 = 0.35; // Speed for the third button


let buttonWidth = floatButton.offsetWidth;
let buttonHeight = floatButton.offsetHeight;

const movementArea = {
  width: window.innerWidth / 2,
  height: window.innerHeight / 2
};

// popups
let isHovered1 = false, isHovered2 = false, isHovered3 = false;

const popup1 = document.getElementById('popup1');
const popup2 = document.getElementById('popup2');
const popup3 = document.getElementById('popup3');

floatButton.addEventListener('mouseover', () => { isHovered1 = true; dx1 = 0; dy1 = 0; popup1.style.display = 'block'; });
floatButton.addEventListener('mouseout', () => { isHovered1 = false; dx1 = 0.5; dy1 = 0.25; popup1.style.display = 'none'; });

floatButton2.addEventListener('mouseover', () => { isHovered2 = true; dx2 = 0; dy2 = 0; popup2.style.display = 'block'; });
floatButton2.addEventListener('mouseout', () => { isHovered2 = false; dx2 = 0.25; dy2 = 0.5; popup2.style.display = 'none'; });

floatButton3.addEventListener('mouseover', () => { isHovered3 = true; dx3 = 0; dy3 = 0; popup3.style.display = 'block'; });
floatButton3.addEventListener('mouseout', () => { isHovered3 = false; dx3 = 0.15; dy3 = 0.35; popup3.style.display = 'none'; });







function updateButtonPosition() {
    // collision
    if (isColliding(floatButton, floatButton2)) {
        dx1 = -dx1;
        dy1 = -dy1;
        dx2 = -dx2;
        dy2 = -dy2;
      }
      if (isColliding(floatButton, floatButton3)) {
        dx1 = -dx1;
        dy1 = -dy1;
        dx3 = -dx3;
        dy3 = -dy3;
      }
      if (isColliding(floatButton2, floatButton3)) {
        dx2 = -dx2;
        dy2 = -dy2;
        dx3 = -dx3;
        dy3 = -dy3;
      }










    if (!isHovered1) {
      x += dx1;
      if (x <= 0 || x + buttonWidth >= movementArea.width) {
        dx1 = -dx1;
      }
      y += dy1;
      if (y <= 0 || y + buttonHeight >= movementArea.height) {
        dy1 = -dy1;
      }
    }
    floatButton.style.left = x + 'px';
    floatButton.style.top = y + 'px';
    popup1.style.left = (x + 20) + 'px';
    popup1.style.top = (y + 60) + 'px';
  
    if (!isHovered2) {
      x2 += dx2;
      if (x2 <= 0 || x2 + buttonWidth >= movementArea.width) {
        dx2 = -dx2;
      }
      y2 += dy2;
      if (y2 <= 0 || y2 + buttonHeight >= movementArea.height) {
        dy2 = -dy2;
      }
    }
    floatButton2.style.left = x2 + 'px';
    floatButton2.style.top = y2 + 'px';
    popup2.style.left = (x2 + 20) + 'px';
    popup2.style.top = (y2 + 60) + 'px';
  
    if (!isHovered3) {
      x3 += dx3;
      if (x3 <= 0 || x3 + buttonWidth >= movementArea.width) {
        dx3 = -dx3;
      }
      y3 += dy3;
      if (y3 <= 0 || y3 + buttonHeight >= movementArea.height) {
        dy3 = -dy3;
      }
    }
    floatButton3.style.left = x3 + 'px';
    floatButton3.style.top = y3 + 'px';
    popup3.style.left = (x3 + 20) + 'px';
    popup3.style.top = (y3 + 60) + 'px';
  
    requestAnimationFrame(updateButtonPosition);
  }
  

// Start the animation
requestAnimationFrame(updateButtonPosition);










