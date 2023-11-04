package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.Account;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;

public class Udemy implements LearningPlatform{
    Account[] accounts;
    Course[] courses;
    public Udemy(Account[] accounts, Course[] courses){
        this.accounts = accounts;
        this.courses = courses;
    }


    @Override
    public Course findByName(String name) throws CourseNotFoundException {

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("name is null or blank");
        }

        for (int i = 0; i < courses.length; i++) {
            String currentCourseName = courses[i].getName();
            if(currentCourseName.equals(name)) {
                return courses[i];
            }
        }

        throw new CourseNotFoundException();
    }

    @Override
    public Course[] findByKeyword(String keyword) {

        if(keyword == null || keyword.isBlank()){
            throw new IllegalArgumentException();
        }

        int counter = 0;

        for (int i = 0; i < courses.length; i++) {

            String currentCourseName = courses[i].getName();
            String currentCourseDescription = courses[i].getDescription();

            if(currentCourseName.contains(keyword) || currentCourseDescription.contains(keyword)){
                counter++;
            }
        }

//        if(counter == 0){
//            throw new IllegalArgumentException();
//        }

        Course[] containingKeyword = new Course[counter];
        int currPosition = 0;

        for (int i = 0; i < courses.length; i++) {

            String currentCourseName = courses[i].getName();
            String currentCourseDescription = courses[i].getDescription();

            if(currentCourseName.contains(keyword) || currentCourseDescription.contains(keyword)){

                containingKeyword[currPosition] = courses[i];
                currPosition++;
            }
        }

        return containingKeyword;

    }

    @Override
    public Course[] getAllCoursesByCategory(Category category) {

        if(category == null){
            throw new IllegalArgumentException("Category is null");
        }

        int counter = 0;

        for (int i = 0; i < courses.length; i++) {

            Category currentCourseCategory = courses[i].getCategory();
            if(currentCourseCategory.equals(category)){
                counter++;
            }
        }

        Course[] matchingCourseCategory = new Course[counter];
        int currPosition = 0;

        for (int i = 0; i < courses.length; i++) {

            Category currentCourseCategory = courses[i].getCategory();
            if(currentCourseCategory.equals(category)){
                matchingCourseCategory[currPosition] = courses[i];
                currPosition++;
            }
        }

        return matchingCourseCategory;
    }

    @Override
    public Account getAccount(String name) throws AccountNotFoundException {

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name is null");
        }

        for (int i = 0; i < accounts.length; i++) {

            String currentAccountName = accounts[i].getUsername();
            if(currentAccountName.equals(name)){
                return accounts[i];
            }
        }

        throw new AccountNotFoundException();
    }

    @Override
    public Course getLongestCourse() {

        if(courses.length == 0){
            return null;
            ///check here if something fails
        }

        int maxDurationMinutes = courses[0].getTotalTime().hours() * 60 + courses[0].getTotalTime().minutes();
        Course longestCourse = courses[0];

        for (int i = 1; i < courses.length; i++) {

            int currentCourseDuration = courses[i].getTotalTime().hours() * 60 + courses[i].getTotalTime().minutes();
            if(maxDurationMinutes < currentCourseDuration){
                maxDurationMinutes = currentCourseDuration;
                longestCourse = courses[i];
            }
        }

        return longestCourse;
    }

    @Override
    public Course getCheapestByCategory(Category category) {
        if(category == null){
            throw new IllegalArgumentException();
        }

        if(courses.length == 0){
            return null;
        }

        boolean lamp = false;
        Course cheapestCourse = null;

        for (int i = 0; i < courses.length; i++) {
            if(courses[i].getCategory().equals(category)){
                if(!lamp){
                    cheapestCourse = courses[i];
                    lamp = true;
                } else if (cheapestCourse.getPrice() > courses[i].getPrice()) {
                    cheapestCourse = courses[i];
                }
            }
        }

        if(!lamp){
            throw new IllegalArgumentException();
        }

        return cheapestCourse;
    }
}
