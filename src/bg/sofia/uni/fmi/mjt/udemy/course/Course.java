package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

public class Course implements Completable, Purchasable {

    private String name;
    private String description;
    private double price;
    private Resource[] content;
    private Category category;
    private boolean completed;
    private boolean purchased;
    private double grade;
    CourseDuration totalTime;

    public Course(String name, String description, double price, Resource[] content, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.content = content;
        this.category = category;
        this.completed = false;
        this.purchased = false;

        int hours, minutes = 0;
        for (int i = 0; i < content.length; i++) {
            minutes += content[i].getDuration().minutes();
        }

        hours = minutes / 60;
        minutes = minutes - (hours * 60);

        this.totalTime = new CourseDuration(hours, minutes);
    }

    /**
     * Returns the name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the course.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the course.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the category of the course.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the content of the course.
     */
    public Resource[] getContent() {
        return content;
    }

    /**
     * Returns the total duration of the course.
     */
    public CourseDuration getTotalTime() {
        return totalTime;
    }

    /**
     * Completes a resource from the course.
     *
     * @param resourceToComplete the resource which will be completed.
     * @throws ResourceNotFoundException if the resource could not be found in the course.
     */
    public void completeResource(Resource resourceToComplete) throws ResourceNotFoundException {

        if (resourceToComplete == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < content.length; i++) {
            if (content[i].equals(resourceToComplete)) {
                content[i].complete();
                return;
            }
        }

        throw new ResourceNotFoundException();
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public int getCompletionPercentage() {

        int resourcesCount = content.length;
        if (resourcesCount == 0) {
            return 0;
        }

        int completedCoursesCounter = 0;
        for (int i = 0; i < resourcesCount; i++) {
            if (content[i].isCompleted()) {
                completedCoursesCounter++;
            }
        }

        double percentage = ((double) completedCoursesCounter / (double) resourcesCount);
        percentage = percentage * 100;
        int intPercentage = (int) Math.round(percentage);

        if (intPercentage >= 100) {
            intPercentage = 100;
            //completed = true;
        }
//        System.out.println(percentage);
//        System.out.println(intPercentage);

        return intPercentage;
    }

    @Override
    public void purchase() {
        purchased = true;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    public void completeCoursee(double grade) {

        completed = true;
        this.grade = grade;
    }

    public double getGrade() {
        return this.grade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Course r = (Course) obj;

        if (!this.name.equals(r.getName())) {
            return false;
        }

        if (!this.description.equals(r.getDescription())) {
            return false;
        }

        Resource[] contentOfR = r.getContent();
        if (this.content.length != contentOfR.length) {
            return false;
        }

        for (int i = 0; i < content.length; i++) {
            boolean lamp = false;
            for (int j = 0; j < contentOfR.length; j++) {
                if (content[i].equals(contentOfR[j])) {
                    lamp = true;
                    break;
                }
            }

            if (lamp == false) {
                return false;
            }
        }

        return true;
    }

}
