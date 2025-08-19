package task;

public class Task {
	    private final String taskId;
	    private String name;
	    private String description;

	    public Task(String taskId, String name, String description) {
	        if(taskId == null || taskId.length() > 10) throw new IllegalArgumentException("Invalid Task ID");
	        if(name == null || name.length() > 20) throw new IllegalArgumentException("Invalid Task Name");
	        if(description == null || description.length() > 50) throw new IllegalArgumentException("Invalid Description");

	        this.taskId = taskId;
	        this.name = name;
	        this.description = description;
	    }

	    public String getTaskId() {
	        return taskId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        if(name != null && name.length() <= 20) {
	            this.name = name;
	        } else {
	            throw new IllegalArgumentException("Invalid Task Name");
	        }
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        if(description != null && description.length() <= 50) {
	            this.description = description;
	        } else {
	            throw new IllegalArgumentException("Invalid Task Description");
	        }
	    }

}
