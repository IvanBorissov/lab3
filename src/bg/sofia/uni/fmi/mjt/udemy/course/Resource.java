package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;

public class Resource implements Completable{

    private String name;
    private ResourceDuration duration;
    private boolean completed;
    public Resource(String name, ResourceDuration duration){
        this.name = name;
        this.duration = duration;
        this.completed = false;
    }
    /**
     * Returns the resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total duration of the resource.
     */
    public ResourceDuration getDuration() {
        return duration;
    }

    /**
     * Marks the resource as completed.
     */
    public void complete() {
        completed = true;
    }

    @Override
    public boolean isCompleted() {

        return this.completed;
    }

    @Override
    public int getCompletionPercentage(){
        if(this.completed){
            return 100;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }

        Resource r = (Resource) obj;

        return (this.name.equals(r.name));
    }
}
