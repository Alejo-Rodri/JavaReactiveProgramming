package sec10.assignment.buffer;

import common.Util;

public record BookOrder(String genre,
                        String title,
                        Integer price) {

    public static BookOrder create() {
        return new BookOrder(
                Util.getFaker().book().genre(),
                Util.getFaker().book().title(),
                Util.getFaker().random().nextInt(10, 120)
        );
    }
}
