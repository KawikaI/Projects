// Get references to HTML elements
const taskInput = document.getElementById("task");
const taskList = document.getElementById("taskList");


// function to add tasks
function addTask() {
    const taskInput = document.getElementById('taskB');;
    const taskList = document.getElementById('taskList');

    if(taskInput.value.trim()) { // Check if input is not just whitespace
        const li = document.createElement('li');
        li.textContent = taskInput.value;
        taskList.appendChild(li);
        taskInput.value = ''; // Clear the input
    }
}
document.getElementById('taskB').addEventListener('keyup', function(event) {
    if (event.key === 'Enter') {
        addTask();
    }
});




// Function to handle the enter key
taskInput.addEventListener("keyup", handleEnterKey);





// Function to complete a task
function completeTask(button) {
    const task = button.parentElement;
    task.classList.toggle("completed"); // Toggle the "completed" class
    button.style.display = "none";
}


// Function to change the theme
function changeTheme() {
    const body = document.body;

    // Toggle between "dark" and "light" classes
    if (body.classList.contains("dark")) {
        body.classList.remove("dark");
        body.classList.add("light");
    } else {
        body.classList.remove("light");
        body.classList.add("dark");
    }
}



// Function to delete a task
function deleteTask(button) {
    const taskItem = button.parentElement;
    if (taskItem) {
        taskItem.remove();
    }
}


// Function to clear all tasks
function clearTasks() {
    while (taskList.firstChild) {
        taskList.removeChild(taskList.firstChild);
    }
}



// Future ideas
// - Add a priority level to tasks
// - Add a due date to tasks
// - Add a search bar to filter tasks


