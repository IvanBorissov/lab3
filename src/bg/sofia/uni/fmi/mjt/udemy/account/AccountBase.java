package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.exception.*;

public abstract class AccountBase implements Account {
    private String username;
    protected AccountType accountType;
    protected double balance;
    protected Course[] courses;
    protected int counter;

    public AccountBase(String username, double balance) {
        this.username = username;
        this.balance = balance;
        courses = new Course[100];
        counter = 0;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void addToBalance(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException();
        }

        this.balance += amount;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    public void buyCourse(Course course) throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {

        double specialPrice = course.getPrice() * (1 - accountType.getDiscount());

        if (specialPrice > this.balance) {
            throw new InsufficientBalanceException();
        }
        if (this.counter == 100) {
            throw new MaxCourseCapacityReachedException();
        }

        for (int i = 0; i < counter; i++) {
            if (course.equals(courses[i])) {
                throw new CourseAlreadyPurchasedException();
            }
        }

        courses[counter] = course;
        counter++;
        balance -= specialPrice;
    }

    @Override
    public void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete) throws CourseNotPurchasedException, ResourceNotFoundException {

        if (course == null || resourcesToComplete == null) {
            throw new IllegalArgumentException();
        }

        if (counter == 0) {
            throw new CourseNotPurchasedException();
        }

        boolean bought = false;

        for (int i = 0; i < counter; i++) {

            if (course.equals(courses[i])) {

                bought = true;
                boolean lamp = false;
                Resource[] currentCourseResources = courses[i].getContent();

                for (int j = 0; j < resourcesToComplete.length; j++) {

                    courses[i].completeResource(resourcesToComplete[j]);
//                    for (int k = 0; k < currentCourseResources.length; k++) {
//
//                        if (resourcesToComplete[j].equals(currentCourseResources[k])) {
//                            courses[i].completeKContent(k);
//                            lamp = true;
//                        }
//                    }
//                    if (!lamp) {
//                        throw new ResourceNotFoundException();
//                    }
                }
            }
        }

        if (!bought) {
            throw new CourseNotPurchasedException();
        }

    }

    @Override
    public void completeCourse(Course course, double grade) throws CourseNotPurchasedException, CourseNotCompletedException {

        if (grade < 2.0 || grade > 6.0) {
            throw new IllegalArgumentException();
        }
        if (course == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < counter; i++) {

            if (courses[i].equals(course)) {

//                boolean lamp = false;
//                Resource[] currentCourseResources = courses[i].getContent();
//                for (int j = 0; j < currentCourseResources.length; j++) {
//
//                    if(!currentCourseResources[j].isCompleted()){
//                        lamp = true;
//                    }
//                }
//
//                if(lamp){
//                    throw new CourseNotCompletedException();
//                }
                int percentage = courses[i].getCompletionPercentage();
                if (percentage == 100) {
                    courses[i].completeCoursee(grade);
                    return;
                } else {
                    throw new CourseNotCompletedException();
                }
            }
        }
        throw new CourseNotPurchasedException();
    }

    @Override
    public Course getLeastCompletedCourse() {

        if (counter == 0) {
            return null;
        }

        Course leastCompletedCourse = courses[0];
        for (int i = 1; i < counter; i++) {

            if (leastCompletedCourse.getCompletionPercentage() > courses[i].getCompletionPercentage()) {
                leastCompletedCourse = courses[i];
            }
        }

        return leastCompletedCourse;

    }
}
