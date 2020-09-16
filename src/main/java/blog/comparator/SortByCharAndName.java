package blog.comparator;

import blog.model.Message;

import java.util.Comparator;

public class SortByCharAndName implements Comparator<Message> {
    @Override
    public int compare(Message a, Message b) {
        if (a.getCharacter().equals(b.getCharacter()))
            return a.getName().compareTo(b.getName());
        return a.getCharacter().compareTo(b.getCharacter());
    }
}
