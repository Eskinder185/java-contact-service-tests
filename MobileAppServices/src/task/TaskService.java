package task;

import java.util.HashMap;
import java.util.Map;

public class TaskService {
	    private Map<String, Task> tasks = new HashMap<>();

	    public void addTask(Task task) {
	        if (tasks.containsKey(task.getTaskId())) {
	            throw new IllegalArgumentException("Task ID already exists");
	        }
	        tasks.put(task.getTaskId(), task);
	    }

	    public void deleteTask(String taskId) {
	        tasks.remove(taskId);
	    }

	    public void updateTask(String taskId, String name, String description) {
	        Task task = tasks.get(taskId);
	        if (task != null) {
	            task.setName(name);
	            task.setDescription(description);
	        } else {
	            throw new IllegalArgumentException("Task not found");
	        }
	    }

	    public Task getTask(String taskId) {
	        return tasks.get(taskId);
	    }
	}

