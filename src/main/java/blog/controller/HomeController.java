package blog.controller;

import blog.comparator.SortByCharAndName;
import blog.model.Message;
import blog.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@Controller
public class HomeController {

    static String myWord;
    static String myLetter;
    static Character myLetterFavorite;
    static int myMessageId;

    private MessageService messageService;

    public HomeController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String findAll(ModelMap map) {
        map.put("letters", MessageService.letters);

        List<Message> list = messageService.findAll();

        if (list.isEmpty()) {
            map.put("status", "Main");
            map.put("noRecords", "No Records Found");
        } else {
            map.put("messages", list);
            map.put("status", "Main (" + list.size() + ")");
        }

        return "index";
    }


    @GetMapping("/showFavorites")
    public String showFavorites(ModelMap map) {
        map.put("letters", MessageService.letters);
        map.put("page", "f");

        List<Message> list = messageService.findAllFavorites();
        Collections.sort(list, new SortByCharAndName());
        map.put("messages", list);

        if (list.isEmpty()) {
            map.put("noRecords", "No Records Found");
            map.put("status", "Favorites");
        } else map.put("status", "Favorites (" + list.size() + ")");
        return "index";
    }


    @GetMapping("/favorite")
    public String isFavorite(ModelMap map, @RequestParam("messageId") int id, @RequestParam("page") String page) {
        messageService.checkFavorite(id);
        if (page.equals("f")) return "redirect:/showFavorites";
        if (page.equals("c")) return "redirect:/showToComplete";
        if (page.equals("w")) return "redirect:/findWord";
        if (page.equals("fl")) return "redirect:/find";
        if (page.equals("flf")) return "redirect:/findFavorites";

        return "redirect:";
    }


    @GetMapping("/showToComplete")
    public String showToComplete(ModelMap map) {
        map.put("letters", MessageService.letters);
        map.put("page", "c");

        List<Message> list = messageService.findAllToComplete();
        Collections.sort(list, new SortByCharAndName());
        map.put("messages", list);

        if (list.isEmpty()) {
            map.put("noRecords", "No Records Found");
            map.put("status", "To Complete");
        } else map.put("status", "To Complete (" + list.size() + ")");
        return "index";
    }


    @GetMapping("/complete")
    public String isComplete(ModelMap map, @RequestParam("messageId") int id, @RequestParam("page") String page) {
        messageService.checkComplete(id);
        if (page.equals("c")) return "redirect:/showToComplete";
        if (page.equals("f")) return "redirect:/showFavorites";
        if (page.equals("w")) return "redirect:/findWord";
        if (page.equals("fl")) return "redirect:/find";
        if (page.equals("flf")) return "redirect:/findFavorites";
        return "redirect:";
    }


    @GetMapping("/find")
    public String findByChar(ModelMap map, @RequestParam(value = "info", required = false) String info) {
        map.put("letters", MessageService.letters);
        map.put("page", "fl");

        if (info == null) info = myLetter;
        else myLetter = info;

        if (info.equals("all")) return "redirect:";
        Character character = info.charAt(0);

        List<Message> list = messageService.findByChar(character);

        if (list.isEmpty()) {
            map.put("noRecords", "No Records Found");
            map.put("status", character);
        } else {
            map.put("messages", list);
            map.put("status", character + " (" + list.size() + ")");
        }

        return "index";
    }


    @GetMapping("/findWord")
    public String findByWord(ModelMap map, @RequestParam(value = "word", required = false) String word) {
        map.put("letters", MessageService.letters);
        map.put("page", "w");

        if (word == null) word = myWord;
        else myWord = word;

        List<Message> list = messageService.findByWord(word);

        if (list.isEmpty()) {
            map.put("noRecords", "No Records Found");
            map.put("status", "Search Results");
        } else {
            map.put("messages", list);
            map.put("status", "Search Results (" + list.size() + ")");
        }

        return "index";
    }


    @GetMapping("/findFavorites")
    public String findFavoritesByChar(ModelMap map, @RequestParam(value = "info", required = false) Character info) {
        map.put("letters", MessageService.letters);
        map.put("page", "flf");

        if (info == null) info = myLetterFavorite;
        else myLetterFavorite = info;

        List<Message> list = messageService.findFavoritesByChar(info);
        if (list.isEmpty()) {
            map.put("noRecords", "No Records Found");
            map.put("status", info);
        } else {
            map.put("messages", list);
            map.put("status", info + " (" + list.size() + ")");
        }

        return "index";
    }


    @GetMapping("/findRecord")
    public String findByChar(ModelMap map, @RequestParam(value = "messageId", required = false) Integer id) {
        map.put("letters", MessageService.letters);
        if (id == null) id = myMessageId;
        map.put("messages", messageService.findById2(id));
        map.put("status", "Record");
        return "index";
    }


    @GetMapping("/delete")
    public String deleteById(@RequestParam("messageId") int id) {
        messageService.deleteById(id);
        return "redirect:";
    }


    //____________________________________________


    @GetMapping("/showFormForAdd")
    public String showFormForAdd(ModelMap map) {
        Message message = new Message();
        map.put("message", message);
        map.put("letters", MessageService.letters);
        return "form";
    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("messageId") int id, ModelMap map) {
        Message message = messageService.findById(id);
        map.put("message", message);
        map.put("letters", MessageService.letters);
//        map.put("myLetter", message.getCharacter());
        return "form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("message") Message message) {
        messageService.save(message);
        myMessageId = message.getId();
        return "redirect:/findRecord";
    }

}
