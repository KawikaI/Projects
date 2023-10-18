// Get references to HTML elements
const taskInput = document.getElementById("task");
const taskList = document.getElementById("taskList");

// Function to add a new task
function addTask() {
    const taskText = taskInput.value.trim();

    if (taskText !== "") {
        const li = document.createElement("li");
        li.innerHTML = `
            <span>${taskText}</span>
            <button onclick="completeTask(this)">Complete</button>
            <button onclick="deleteTask(this)">Delete</button>`;
        taskList.appendChild(li);
        taskInput.value = "";
    }
}

// Function to complete a task
function completeTask(button) {
    const task = button.parentElement;
    task.classList.add("completed");
    button.style.display = "none";
}

// Function to delete a task
function deleteTask(button) {
    const task = button.parentElement;
    task.remove();
}

// Function to clear all tasks
function clearTasks() {
    while (taskList.firstChild) {
        taskList.removeChild(taskList.firstChild);
    }
}
