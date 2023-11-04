package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.udemy.exception.MaxCourseCapacityReachedException;

public class BusinessAccount extends AccountBase{
    private Category[] allowedCategories;
    public BusinessAccount(String username, double balance, Category[] allowedCategories){
        super(username, balance);
        this.allowedCategories = allowedCategories;
        this.accountType = AccountType.BUSINESS;
    }

    @Override
    public void buyCourse(Course course) throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException{

        boolean lamp = false;
        for (int i = 0; i < allowedCategories.length; i++) {

            if (allowedCategories[i].equals(course.getCategory())) {
                lamp = true;
                break;
            }
        }

        if(!lamp){
            throw new IllegalArgumentException("Business account can't buy this course");
        }

        ///business account is allowed to buy this course
        super.buyCourse(course);
    }
}
