package sec12;

import common.Util;
import sec12.assignment.SlackMember;
import sec12.assignment.SlackRoom;

public class Lec08Assignment {
    public static void main(String[] args) {
        var room = new SlackRoom("reactor");

        var sam = new SlackMember("sam");
        var jake = new SlackMember("jake");
        var mike = new SlackMember("mike");

        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hi all...");

        Util.sleepSeconds(4);

        jake.says("Hi!");
        sam.says("I simply wanted to say hi :D");

        Util.sleepSeconds(4);

        room.addMember(mike);
        mike.says("Hey guys... soy nuebo");
    }
}
