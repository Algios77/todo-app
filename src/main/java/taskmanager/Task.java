package taskmanager;

public class Task {
    private  String name;
    private boolean isdone;

    public Task(String name) {
        this.name = name;
        this.isdone = false;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return isdone;
    }

    public void markAsDone() {
        isdone = true;
    }

    @Override
    public String toString() {
        return (isdone ? "done" : "not done") + " " + name;
    }
}
