package blog.service;

import blog.comparator.SortByCharAndName;
import blog.model.Message;
import blog.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    public static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K","L","M","N","O","P","R","S","T","U","W","Y","Z"};

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> findAll() {
        List<Message> sortedMessages = messageRepository.findAll();
        if (sortedMessages.isEmpty()) return sortedMessages;
        Collections.sort(sortedMessages, new SortByCharAndName());

        Character character = sortedMessages.get(0).getCharacter();

        for (int i = 1; i < sortedMessages.size(); i++) {
            if (character.equals(sortedMessages.get(i).getCharacter())) {
                sortedMessages.get(i).setCharacter(null);
            } else
                character = sortedMessages.get(i).getCharacter();
        }
        return sortedMessages;
    }


    public List<Message> findByChar(Character character) {
        List<Message> sortedMessages = messageRepository.findByCharacter(character);
        Collections.sort(sortedMessages, new SortByCharAndName());
        for (int i = 1; i < sortedMessages.size(); i++)
            sortedMessages.get(i).setCharacter(null);
        return sortedMessages;
    }


    public void deleteById(int id) {
        messageRepository.deleteById(id);
    }


    public void save(Message message) {
        messageRepository.save(message);
    }

    public Message findById(int id) {
        return messageRepository.findById(id).get();
    }

    public List<Message> findById2(int id) {
        List<Message> messages = new ArrayList<>();
        messages.add(messageRepository.findById(id).get());
        return messages;
    }
}
