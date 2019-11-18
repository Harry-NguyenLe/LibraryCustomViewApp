package vn.edu.csc.indicatorlibrary.IncatorView;

public class PageLessException extends Exception {
    @Override
    public String getMessage() {
        return "Pages must equal or larger than 2";
    }
}
