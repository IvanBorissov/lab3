package bg.sofia.uni.fmi.mjt.udemy.course.duration;

public record CourseDuration(int hours, int minutes) {
    public CourseDuration{
        if(hours < 0 || hours > 24 || minutes < 0 || minutes > 60){
            throw new IllegalArgumentException("Invalid Course duration hours and minutes");
        }
    }
}
