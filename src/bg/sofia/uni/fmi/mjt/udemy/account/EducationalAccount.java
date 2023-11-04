package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.udemy.exception.MaxCourseCapacityReachedException;

public class EducationalAccount extends AccountBase {

    private int nextEligibleCourse;

    public EducationalAccount(String username, double balance) {

        super(username, balance);
        nextEligibleCourse = 6;
        this.accountType = AccountType.EDUCATION;
    }

    @Override
    public void buyCourse(Course course) throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {

        double discount = 0;
        boolean eligible = true;
        if (counter >= nextEligibleCourse) {
            int startIndex = counter - 6;
            int endIndex = counter;
            double averageGrade = 0.0;

            for (int i = startIndex; i < endIndex; i++) {

                if (!courses[i].isCompleted()) {
                    eligible = false;
                    break;
                } else {
                    averageGrade += courses[i].getGrade();
                }
            }

            if (averageGrade / 5 >= 4.50 && eligible) {
                discount = 0.15;
            }

            if (discount == 0.15) {
                nextEligibleCourse += 5;
            }
        }

        double specialPrice = course.getPrice() * (1 - discount);

        if (specialPrice > this.getBalance()) {
            throw new InsufficientBalanceException();
        }
        if (this.counter == 100) {
            throw new MaxCourseCapacityReachedException();
        }

        for (int i = 0; i < courses.length; i++) {
            if (course.equals(courses[i])) {
                throw new CourseAlreadyPurchasedException();
            }
        }

        courses[counter] = course;
        counter++;
        balance -= specialPrice;
    }
}
